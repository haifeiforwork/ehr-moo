package com.lgcns.ikep4.servicepack.survey.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.MethodInvoker;

public final class ReflectionUtils
{
	
  private ReflectionUtils(){}
  
  public static Object newInstance(String className)
    throws InstantiationException, IllegalAccessException, ClassNotFoundException
  {
    return Class.forName(className).newInstance();
  }

  public static Object newInstance(ClassLoader classLoader, String className)
    throws InstantiationException, IllegalAccessException, ClassNotFoundException
  {
    if (classLoader == null){ return null;}
    return classLoader.loadClass(className).newInstance();
  }

  public static Object newInstance(String className, Object[] params)
    throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
  {
    Class clazz = Class.forName(className);
    Class[] paramTypes = getParamTypes(params);
    Constructor constructor = clazz.getConstructor(paramTypes);
    return constructor.newInstance(params);
  }

  public static Method getMethod(String className, String methodName, Class[] paramTypes)
    throws ClassNotFoundException, SecurityException, NoSuchMethodException
  {
    Class clazz = Class.forName(className);
    return clazz.getMethod(methodName, paramTypes);
  }

  public static Method getMethod(Class clazz, String methodName, Object[] params)
    throws ClassNotFoundException, NoSuchMethodException
  {
    MethodInvoker invoker = new MethodInvoker();
    invoker.setTargetClass(clazz);
    invoker.setTargetMethod(methodName);
    invoker.setArguments(params);
    invoker.prepare();
    return invoker.getPreparedMethod();
  }

  public static Method getMethod(Object targetObject, String methodName, Object[] params)
    throws ClassNotFoundException, NoSuchMethodException
  {
    return getMethod(targetObject.getClass(), methodName, params);
  }

  public static Method getMethod(Class clazz, String methodName, Class[] paramTypes)
    throws ClassNotFoundException, NoSuchMethodException
  {
    return getMethod(clazz.getName(), methodName, paramTypes);
  }

  public static Method getMethod(String classDotMethodName, Class[] paramTypes)
    throws SecurityException, ClassNotFoundException, NoSuchMethodException
  {
    String[] classAndMethodNames = classDotMethodName.split("\\.");
    if (classAndMethodNames.length != 2) {
      throw new NoSuchMethodException("Cannot find method [" + classDotMethodName + "].");
    }
    return getMethod(classAndMethodNames[0], classAndMethodNames[1], paramTypes);
  }

  public static Method getMethod(Object targetObject, String methodName, Class[] paramTypes)
    throws SecurityException, ClassNotFoundException, NoSuchMethodException
  {
    return getMethod(targetObject.getClass().getName(), methodName, paramTypes);
  }

  public static Object invokeMethod(String className, String methodName, Object[] params)
    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException
  {
    Object targetObject = newInstance(className);
    return invokeMethod(targetObject, methodName, getParamTypes(params), params);
  }

  public static Object invokeMethod(Object targetObject, String methodName, Object[] params)
    throws SecurityException, ClassNotFoundException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
  {
    return invokeMethod(targetObject, methodName, getParamTypes(params), params);
  }

  public static Object invokeMethod(Object targetObject, String methodName, Class[] paramTypes, Object[] params)
    throws SecurityException, ClassNotFoundException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
  {
    Method method = targetObject.getClass().getDeclaredMethod(methodName, paramTypes);
    return method.invoke(targetObject, params);
  }

  public static void invokeVoidMethod(String className, String methodName, Object[] params, boolean checkReturnType)
    throws SecurityException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
  {
    if (checkReturnType) {
      Method method = getMethod(className, methodName, getParamTypes(params));
      if (!("void".equals(method.getReturnType().getName()))) {
        throw new NoSuchMethodException("Method return type must be 'void'.");
      }
      Object targetObject = newInstance(className);
      method.invoke(targetObject, params);
    }
    else {
      invokeMethod(className, methodName, params);
    }
  }

  public static Class[] getAllInterfaces(Class c)
  {
    List list = new ArrayList();

    if (c == null){ return null;}

    putList(c.getInterfaces(), list);
    Class parent = c.getSuperclass();

    if (!(Object.class.getName().equalsIgnoreCase(parent.getName()))) {
      putList(getAllInterfaces(parent), list);
    }

    Class[] interfaces = new Class[list.size()];
    return ((Class[])list.toArray(interfaces));
  }

  private static void putList(Class[] classes, List list)
  {
    for (int i = 0; i < classes.length; ++i){
      list.add(classes[i]);
    }  
  }

  private static Class[] getParamTypes(Object[] params)
  {
    if (params == null) {
      return null;
    }

    Class[] paramTypes = new Class[params.length];

    for (int i = 0; i < params.length; ++i) {
      paramTypes[i] = params[i].getClass();
    }

    return paramTypes;
  }
}