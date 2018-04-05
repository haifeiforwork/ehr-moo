/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.security.acl.aspects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 컨텐츠의 접근 권한에 관련된 Aspect reference is
 * http://stackoverflow.com/questions/3565718
 * /pointcut-matching-methods-with-annotated-parameters
 * 
 * @author 주길재(giljae@gmail.com)
 * @version $Id: ACLAspector.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Aspect
public class ACLAspector {
	@Autowired
	private ACLService aclService;

	/**
	 * IsAccess annotation을 사용한 케이스
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* *(.., @com.lgcns.ikep4.support.security.acl.annotations.IsAccess (*), ..))")
	public Object isAccess(final ProceedingJoinPoint joinPoint) throws Throwable {
		final Signature signature = joinPoint.getSignature();
		IsAccess isAccess = null;
		boolean hasAccess = false;

		// IsAccess annotation 정보를 읽음
		if (signature instanceof MethodSignature) {
			final MethodSignature methodSignature = (MethodSignature) signature;
			final Method method = methodSignature.getMethod();
			final String[] parameterNames = methodSignature.getParameterNames();
			final Class<?>[] parameterTypes = methodSignature.getParameterTypes();
			final Annotation[][] parameterAnnotations = method.getParameterAnnotations();

			for (int index = 0; index < parameterAnnotations.length; index++) {
				final Annotation[] annotations = parameterAnnotations[index];
				isAccess = getAnnotationByType(annotations, IsAccess.class);

				// 다양한 annotation이 파라메터앞에 선언될수 있기 때문에 IsAccess annotation인지 확인
				if (isAccess != null) {
					// IsAccess annotation에 composite된 Attribute annotation 리스트를
					// 획득.
					Attribute[] attributes = isAccess.value();
					for (Attribute attribute : attributes) {
						// Attibute annotation parameters
						String resourceName = this.processParameter(parameterNames[index], parameterTypes[index], attribute, joinPoint.getArgs()[index]);

						// 접근 권한 체크
						hasAccess = permissionCheck(attribute.className(), attribute.operationName(), resourceName,
								attribute.type());

						// 접근 권한이 있으면, 루프를 빠져 나온다.
						if (hasAccess) {
							break;
						}
					}
					break;
				}
			}
		}

		int resultIndex = 0;
		if (signature instanceof MethodSignature) {
			final MethodSignature methodSignature = (MethodSignature) signature;
			final Class<?>[] parameterTypes = methodSignature.getParameterTypes();
			for (int index = 0; index < parameterTypes.length; index++) {
				// 접근 권한 결과를 담을 AccessingResult 클래스의 index를 찾는다.
				if (parameterTypes[index] == AccessingResult.class) {
					resultIndex = index;
					break;
				}
			}
		}

		Object[] args = joinPoint.getArgs();

		// Resource 접근 가능 결과를 argument에 담는다
		AccessingResult accessingResult = new AccessingResult();
		accessingResult.setResult(hasAccess);
		args[resultIndex] = accessingResult;

		return joinPoint.proceed(args);
	}

	/**
	 * In an array of annotations, find the annotation of the specified type, if
	 * any.
	 * 
	 * @return the annotation if available, or null
	 */
	@SuppressWarnings("unchecked")
	private static <T extends Annotation> T getAnnotationByType(final Annotation[] annotations, final Class<T> clazz) {
		T result = null;
		for (final Annotation annotation : annotations) {
			if (clazz.isAssignableFrom(annotation.getClass())) {
				result = (T) annotation;
				break;
			}
		}
		return result;
	}

	/**
	 * 리소스 접근 권한 체크
	 * 
	 * @param className
	 * @param operationName
	 * @param resourceName
	 * @param accessType
	 * @return
	 */
	private boolean permissionCheck(String className, String[] operationName, String resourceName, AccessType accessType) {
		boolean isAccess = false;

		if (accessType.equals(AccessType.SYSTEM)) {
			// 정적(System) 리소스 접근 권한 체크
			isAccess = this.aclService.hasSystemPermission(className, operationName, resourceName, getUserId());
		} else if (accessType.equals(AccessType.CONTENT)) {
			// 동적(Content) 리소스 접근 권한 체크
			isAccess = this.aclService.hasContentPermission(className, operationName, resourceName, getUserId());
		}

		return isAccess;
	}

	/**
	 * Do some processing based on what we found.
	 * 
	 * @param signature method signature
	 * @param paramName parameter name
	 * @param paramType parameter type
	 * @param paramAnnotation annotation we found
	 * @param args parameter value
	 */
	private String processParameter(final String paramName, final Class<?> paramType,
			final Attribute attriuteAnnotation, final Object args) throws IllegalArgumentException, IllegalAccessException{

		// Parameter Object가 null일 경우 Exception? or return false?

		if (paramType.isAssignableFrom(String.class) && paramName.equals(attriuteAnnotation.resourceName())) {
			return (String) args;
		}

		Field field = null;
		try {
			Class<?> clazz = args.getClass();
			// get the reflected object
			field = clazz.getDeclaredField(attriuteAnnotation.resourceName());
			// set accessible true
			field.setAccessible(true);
		} catch (Exception ex) {
			return attriuteAnnotation.resourceName();
		}

		return (String) field.get(args);
	}

	/**
	 * 유저 아이디를 읽어온다
	 * 
	 * @return
	 */
	private String getUserId() {
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);
		return user.getUserId();
	}
}
