package com.lgcns.ikep4.util.ical;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.UtcOffset;
import net.fortuna.ical4j.model.WeekDayList;
import net.fortuna.ical4j.model.component.Standard;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Member;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.Created;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.Priority;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Transp;
import net.fortuna.ical4j.model.property.Trigger;
import net.fortuna.ical4j.model.property.TzName;
import net.fortuna.ical4j.model.property.TzOffsetFrom;
import net.fortuna.ical4j.model.property.TzOffsetTo;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

import com.lgcns.ikep4.util.ical.model.IAlarm;
import com.lgcns.ikep4.util.ical.model.IAttendee;
import com.lgcns.ikep4.util.ical.model.ICalendar;
import com.lgcns.ikep4.util.ical.model.IDur;
import com.lgcns.ikep4.util.ical.model.IEvent;
import com.lgcns.ikep4.util.ical.model.IRecur;
import com.lgcns.ikep4.util.ical.model.ITimeZone;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * ICal 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ICalUtil.java 17319 2012-02-09 04:51:39Z yruyo $
 */
@SuppressWarnings("unchecked")
public final class ICalUtil {

	private ICalUtil() {
		throw new AssertionError();
	}

	public enum ICalProperty {

		UID, BEGIN, END, PRODID, VERSION, DTSTAMP, CREATED, DTSTART, DTEND, SUMMARY, DESCRIPTION, LOCATION, ATTENDEE, ORGANIZER, RRULE, TRIGGER, ACTION, TRANSP, PRIORITY, STATUS, METHOD, CATEGORIES, TZID, TZOFFSETFROM, TZOFFSETTO, TZNAME;

		ICalProperty() {}
	}

	/**
	 * 파일에서 Calendar 정보를 읽어옴 ical4j 를 이용함
	 * (http://m2.modularity.net.au/projects/ical4j/)
	 * 
	 * @param inp
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static ICalendar readICalendar(InputStream inp) {

		ICalendar icalendar = new ICalendar();

		try {
			// Calendar 정보 얻기
			StringReader reader = makeValidFile(new InputStreamReader(inp));

			CalendarBuilder builder = new CalendarBuilder();
			Calendar calendar = builder.build(reader);

			if (calendar.getVersion() != null) {
				icalendar.setVersion(calendar.getVersion().getValue());
			}
			if (calendar.getProductId() != null) {
				icalendar.setProdid(calendar.getProductId().getValue());
			}
			if (calendar.getCalendarScale() != null) {
				icalendar.setCalscale(calendar.getCalendarScale().getValue());
			}

			// VEvent 리스트에서 정보 얻기
			Iterator i = calendar.getComponents().iterator();
			while (i.hasNext()) {

				try {
					Component component = (Component) i.next();

					// TIMEZONE 이면
					if (component.getName().equals(Component.VTIMEZONE)) {

						ITimeZone iTimeZone = new ITimeZone();

						Iterator j = component.getProperties().iterator();
						while (j.hasNext()) {

							try {
								Property property = (Property) j.next();

								// 타임존 설정 담기
								if (property.getName().equals(Property.TZID)) {
									iTimeZone.setTzId(property.getValue());
								}

							} catch (Exception e) {
								// ex.printStackTrace();
							}
						}

						// ComponentList cmpList = ((VTimeZone)
						// component).getObservances();
						// setCalForTimeZone(cmpList, iTimeZone);

						icalendar.setiTimeZone(iTimeZone);

					} // VEVENT 이면
					else if (component.getName().equals(Component.VEVENT)) {

						IEvent ievent = new IEvent();

						// VEvent Property 리스트에서 정보 얻기
						Iterator j = component.getProperties().iterator();
						while (j.hasNext()) {

							try {
								Property property = (Property) j.next();

								// 일반 설정 담기
								setCalForRead(property, ievent);

							} catch (Exception e) {
								// ex.printStackTrace();
							}
						}

						// VEvent의 VAlarm 정보 얻기
						ComponentList cmpList = ((VEvent) component).getAlarms();
						setAlarmForRead(cmpList, ievent);

						icalendar.addIEventList(ievent);
					}

				} catch (Exception e) {
					// ex.printStackTrace();
				}
			}

			inp.close();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		// Calendar 정보
		return icalendar;
	}

	/**
	 * TIMEZONE 설정 담기
	 * 
	 * @param property
	 * @param iTimeZone
	 * @throws ParseException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	private static void setCalForTimeZone(ComponentList cmpList, ITimeZone iTimeZone) throws ParseException,
			IOException, URISyntaxException {

		if (cmpList != null) {
			for (int m = 0; m < cmpList.size(); m++) {

				try {
					Standard standard = (Standard) cmpList.get(m);

					// TIMEZONE 설정을 얻기
					Iterator k = standard.getProperties().iterator();
					while (k.hasNext()) {

						try {
							Property property = (Property) k.next();

							if (property.getName().equals(Property.TZNAME)) {
								iTimeZone.setTzName(property.getValue());
							} else if (property.getName().equals(Property.TZOFFSETFROM)) {
								iTimeZone.setTzOffsetFrom((new UtcOffset(property.getValue())).getOffset());
							} else if (property.getName().equals(Property.TZOFFSETTO)) {
								iTimeZone.setTzOffsetTo((new UtcOffset(property.getValue())).getOffset());
							} else if (property.getName().equals(Property.DTSTART)) {
								iTimeZone.setDtstart((DateTime) ((DtStart) property).getDate());
							}
						} catch (Exception e) {
							// ex.printStackTrace();
						}
					}
				} catch (Exception e) {
					// ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 일반 설정 담기
	 * 
	 * @param property
	 * @param ievent
	 * @throws ParseException
	 */
	private static void setCalForRead(Property property, IEvent ievent) throws ParseException, IOException,
			URISyntaxException {

		if (property.getName().equals(Property.PRODID)) {
			ievent.setProdId(property.getValue());
		} else if (property.getName().equals(Property.VERSION)) {
			ievent.setVersion(property.getValue());
		} else if (property.getName().equals(Property.UID)) {
			ievent.setUid(property.getValue());
		} else if (property.getName().equals(Property.CALSCALE)) {
			ievent.setCalScale(property.getValue());
		} else if (property.getName().equals(Property.METHOD)) {
			ievent.setMethod(property.getValue());
		} else if (property.getName().equals(Property.TRANSP)) {
			ievent.setTransp(property.getValue());
		} else if (property.getName().equals(Property.STATUS)) {
			ievent.setStatus(property.getValue());
		} else if (property.getName().equals(Property.PRIORITY)) {
			ievent.setPriority(Integer.parseInt(property.getValue()));
		} else if (property.getName().equals(Property.SEQUENCE)) {
			ievent.setSeq(Integer.parseInt(property.getValue()));
		} else if (property.getName().equals(Property.CREATED)) {
			ievent.setCreated(new DateTime(property.getValue()));
		} else if (property.getName().equals(Property.DTSTAMP)) {
			ievent.setDtstamp(new DateTime(property.getValue()));
		} else if (property.getName().equals(Property.DTSTART)) {
			String value = property.getValue();
			if(value.length() > 8) {
				ievent.setWholeday(0);
				ievent.setDtstart((DateTime) ((DtStart) property).getDate());
			}
			else{
				ievent.setWholeday(1);
				ievent.setDtstart2((Date) ((DtStart) property).getDate());
			}
		} else if (property.getName().equals(Property.DTEND)) {
			String value = property.getValue();
			if(value.length() > 8) {
				ievent.setWholeday(0);
				ievent.setDtend((DateTime) ((DtEnd) property).getDate());
			}
			else{
				ievent.setWholeday(1);
				ievent.setDtend2((Date) ((DtEnd) property).getDate());
			}
		} else if (property.getName().equals(Property.SUMMARY)) {
			ievent.setSummary(property.getValue());
		} else if (property.getName().equals(Property.DESCRIPTION)) {
			ievent.setDescription(property.getValue());
		} else if (property.getName().equals(Property.CATEGORIES)) {
			ievent.setCategories(property.getValue());
		} else if (property.getName().equals(Property.LOCATION)) {
			ievent.setLocation(property.getValue());
		} else if (property.getName().equals(Property.ORGANIZER)) {
			IAttendee attendee = new IAttendee();
			attendee.setMail(property.getValue().substring(property.getValue().indexOf(":") + 1));
			attendee.setCn(property.getParameter(Parameter.CN).getValue());
			if (property.getParameter(Parameter.MEMBER).getValue() != null) {
				attendee.setMember(property.getParameter(Parameter.MEMBER).getValue().replaceAll("\"", ""));
			}
			ievent.setOrganizer(attendee);
		} else if (property.getName().equals(Property.ATTENDEE)) {
			IAttendee attendee = new IAttendee();
			attendee.setMail(property.getValue().substring(property.getValue().indexOf(":") + 1));
			attendee.setCn(property.getParameter(Parameter.CN).getValue());
			if (property.getParameter(Parameter.MEMBER).getValue() != null) {
				attendee.setMember(property.getParameter(Parameter.MEMBER).getValue().replaceAll("\"", ""));
			}
			ievent.addAttendeeList(attendee);
		} else if (property.getName().equals(Property.RRULE)) {

			// VEvent 반복 설정 얻기
			Recur recur = ((RRule) property).getRecur();

			IRecur irecur = new IRecur();
			irecur.setCount(recur.getCount());
			irecur.setInterval(recur.getInterval());
			irecur.setFrequency(recur.getFrequency());
			irecur.setUntil(recur.getUntil());
			irecur.setWeekStartDay(recur.getWeekStartDay());
			irecur.setSecondList(recur.getSecondList());
			irecur.setMinuteList(recur.getMinuteList());
			irecur.setHourList(recur.getHourList());
			irecur.setMonthDayList(recur.getMonthDayList());
			irecur.setMonthList(recur.getMonthList());
			irecur.setDayList(recur.getDayList());
			irecur.setSetPosList(recur.getSetPosList());

			ievent.setRrule(property.getValue());
			ievent.setIrecur(irecur);
		}

	}

	/**
	 * 알람 설정 담기
	 * 
	 * @param cmpList
	 * @param ievent
	 */
	@SuppressWarnings("rawtypes")
	private static void setAlarmForRead(ComponentList cmpList, IEvent ievent) {

		if (cmpList != null) {
			for (int m = 0; m < cmpList.size(); m++) {

				try {
					VAlarm alarm = (VAlarm) cmpList.get(m);
					IAlarm ialarm = new IAlarm();

					// VAlarm 반복설정을 얻기
					Iterator k = alarm.getProperties().iterator();
					while (k.hasNext()) {

						try {
							Property alarmProperty = (Property) k.next();

							if (alarmProperty.getName().equals(Property.ACTION)) {
								ialarm.setAction(alarmProperty.getValue());
							} else if (alarmProperty.getName().equals(Property.SUMMARY)) {
								ialarm.setSummary(alarmProperty.getValue());
							} else if (alarmProperty.getName().equals(Property.DESCRIPTION)) {
								ialarm.setDescription(alarmProperty.getValue());
							} else if (alarmProperty.getName().equals(Property.ATTENDEE)) {
								IAttendee attendee = new IAttendee();
								attendee.setMail(alarmProperty.getValue().substring(
										alarmProperty.getValue().indexOf(":") + 1));
								attendee.setCn(alarmProperty.getParameter(Parameter.CN).getValue());
								if (alarmProperty.getParameter(Parameter.MEMBER).getValue() != null) {
									attendee.setMember(alarmProperty.getParameter(Parameter.MEMBER).getValue()
											.replaceAll("\"", ""));
								}
								ialarm.addAttendeeList(attendee);
							} else if (alarmProperty.getName().equals(Property.TRIGGER)) {

								Dur dur = ((Trigger) alarmProperty).getDuration();

								IDur idur = new IDur();
								idur.setSeconds(dur.getSeconds());
								idur.setMinutes(dur.getMinutes());
								idur.setDays(dur.getDays());
								idur.setHours(dur.getHours());
								idur.setWeeks(dur.getWeeks());

								ialarm.setTrigger(alarmProperty.getValue());
								ialarm.setIdur(idur);
							}
						} catch (Exception e) {
							// ex.printStackTrace();
						}
					}

					ievent.addIAlarmList(ialarm);

				} catch (Exception e) {
					// ex.printStackTrace();
				}
			}
		}

	}

	/**
	 * Calendar 정보를 파일로 저장함 ical4j 를 이용함
	 * (http://m2.modularity.net.au/projects/ical4j/)
	 * 
	 * @param icalendarVO
	 * @param fileName
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String saveICalendar(ICalendar icalendarVO, String fileNameStr, HttpServletResponse response) {

		try {
			// Calendar 정보 담기
			Calendar calendar = new Calendar();
			calendar.getProperties().add(new ProdId(icalendarVO.getProdid()));
			calendar.getProperties().add(Version.VERSION_2_0);
			calendar.getProperties().add(CalScale.GREGORIAN);
			// calendar.getProperties().add(icalendarVO.getXprop());

			// TimeZone 설정 담기
			if (icalendarVO.getiTimeZone() != null) {

				ITimeZone iTimeZone = icalendarVO.getiTimeZone();

				TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
				VTimeZone timeZone = registry.getTimeZone(iTimeZone.getTzId()).getVTimeZone();

				// setTimeZoneForSave(timeZone, iTimeZone);

				calendar.getComponents().add(timeZone);
			}

			// VEvent 리스트 정보 담기
			for (IEvent ievent : icalendarVO.getIeventList()) {

				try {
					VEvent event = new VEvent();

					UidGenerator uid = new UidGenerator(ievent.getUid());
					event.getProperties().add(uid.generateUid());

					// 일반 설정 담기
					setCalForSave(event, ievent);

					// VEvent의 반복설정 담기
					setRecurForSave(event, ievent);

					// VEvent의 VAlarm정보 담기
					setAlarmForSave(event, ievent);

					calendar.getComponents().add(event);

				} catch (Exception e) {
					// ex.printStackTrace();
				}
			}

			// 파일 저장
			// OutputStream out = response.getOutputStream();
			String fileName = fileNameStr;
			fileName = new String(fileName.getBytes("euc-kr"), "8859_1");
			// PrintWriter out = new PrintWriter(new
			// OutputStreamWriter(response.getOutputStream(), "KSC5601"));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream()));

			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");

			CalendarOutputter outputter = new CalendarOutputter();
			outputter.output(calendar, out);

			out.flush();
			out.close();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return "ok";
	}

	/**
	 * TIMEZONE 설정 담기
	 * 
	 * @param event
	 * @param ievent
	 * @throws URISyntaxException
	 */
	@SuppressWarnings("unused")
	private static void setTimeZoneForSave(VTimeZone timeZone, ITimeZone iTimeZone) throws URISyntaxException {

		Standard standard = new Standard();

		if (iTimeZone.getTzName() != null) {
			standard.getProperties().add(new TzName(iTimeZone.getTzName()));
		}
		standard.getProperties().add(new TzOffsetFrom(new UtcOffset(iTimeZone.getTzOffsetFrom())));
		standard.getProperties().add(new TzOffsetTo(new UtcOffset(iTimeZone.getTzOffsetTo())));
		if (iTimeZone.getDtstart() != null) {
			standard.getProperties().add(new DtStart(new DateTime(iTimeZone.getDtstart())));
		}

		timeZone.getObservances().add(standard);
	}

	/**
	 * 일반 설정 담기
	 * 
	 * @param event
	 * @param ievent
	 * @throws URISyntaxException
	 */
	private static void setCalForSave(VEvent event, IEvent ievent) throws URISyntaxException {

		if (ievent.getMethod() != null) {
			event.getProperties().add(new Method(ievent.getMethod()));
		}
		if (ievent.getTransp() != null) {
			event.getProperties().add(new Transp(ievent.getTransp()));
		}
		if (ievent.getStatus() != null) {
			event.getProperties().add(new Status(ievent.getStatus()));
		}
		if (ievent.getPriority() != 0) {
			event.getProperties().add(new Priority(ievent.getPriority()));
		}
		if (ievent.getSeq() != 0) {
			event.getProperties().add(new Sequence(ievent.getSeq()));
		}
		if (ievent.getCreated() != null) {
			event.getProperties().add(new Created(new DateTime(ievent.getCreated())));
		}
		if (ievent.getDtstamp() != null) {
			event.getProperties().add(new DtStamp(new DateTime(ievent.getDtstamp())));
		}
		if (ievent.getDtstart() != null) {
			if (ievent.getWholeday() == 1) {
				event.getProperties().add(new DtStart(new Date(ievent.getDtstart())));
			} else {
				event.getProperties().add(new DtStart(new DateTime(ievent.getDtstart())));
			}
		}
		if (ievent.getDtend() != null) {
			if (ievent.getWholeday() == 1) {
				event.getProperties().add(new DtEnd(new Date(ievent.getDtend())));
			} else {
				event.getProperties().add(new DtEnd(new DateTime(ievent.getDtend())));
			}
		}
		if (ievent.getSummary() != null) {
			event.getProperties().add(new Summary(ievent.getSummary()));
		}
		if (ievent.getDescription() != null) {
			event.getProperties().add(new Description(ievent.getDescription()));
		}
		if (ievent.getCategories() != null) {
			event.getProperties().add(new Categories(ievent.getCategories()));
		}
		if (ievent.getLocation() != null) {
			event.getProperties().add(new Location(ievent.getLocation()));
		}
		if (ievent.getOrganizer() != null) {
			IAttendee attendee = ievent.getOrganizer();
			if (!StringUtil.isEmpty(attendee.getMail())) {
				Organizer att = new Organizer(new URI(attendee.getMail()));
				if (!StringUtil.isEmpty(attendee.getCn())) {
					att.getParameters().add(new Cn(attendee.getCn()));
				}
				if (!StringUtil.isEmpty(attendee.getMember())) {
					att.getParameters().add(new Member(attendee.getMember()));
				}
				event.getProperties().add(att);
			}
		}
		if (ievent.getAttendeeList() != null) {

			for (IAttendee attendee : ievent.getAttendeeList()) {
				if (!StringUtil.isEmpty(attendee.getMail())) {
					Attendee att = new Attendee(new URI(attendee.getMail()));
					if (!StringUtil.isEmpty(attendee.getCn())) {
						att.getParameters().add(new Cn(attendee.getCn()));
					}
					if (!StringUtil.isEmpty(attendee.getMember())) {
						att.getParameters().add(new Member(attendee.getMember()));
					}
					event.getProperties().add(att);
				}
			}
		}
	}

	/**
	 * 반복 설정 담기
	 * 
	 * @param event
	 * @param ievent
	 */
	private static void setRecurForSave(VEvent event, IEvent ievent) {

		if (ievent.getIrecur() != null) {
			IRecur irecur = ievent.getIrecur();

			Recur recur = new Recur(irecur.getFrequency(), irecur.getCount());

			recur.setInterval(irecur.getInterval());
			recur.setUntil(irecur.getUntil());
			recur.setWeekStartDay(irecur.getWeekStartDay());
			
			if(irecur.getCount() > 0) {
				recur.setCount(irecur.getCount());
			}

			if (irecur.getSecondList() != null) {
				for (Integer itg : irecur.getSecondList()) {
					recur.getSecondList().add(itg);
				}
			}
			if (irecur.getMinuteList() != null) {
				for (Integer itg : irecur.getMinuteList()) {
					recur.getMinuteList().add(itg);
				}
			}
			if (irecur.getHourList() != null) {
				for (Integer itg : irecur.getHourList()) {
					recur.getHourList().add(itg);
				}
			}
			if (irecur.getMonthList() != null) {
				for (Integer itg : irecur.getMonthList()) {
					recur.getMonthList().add(itg);
				}
			}
			if (irecur.getMonthDayList() != null) {
				for (Integer itg : irecur.getMonthDayList()) {
					recur.getMonthDayList().add(itg);
				}
			}
			if (irecur.getSetPosList() != null) {
				for (Integer itg : irecur.getSetPosList()) {
					recur.getSetPosList().add(itg);
				}
			}
			if (irecur.getDayList() != null) {
				WeekDayList wdList = irecur.getDayList();

				for (int i = 0; i < wdList.size(); i++) {
					recur.getDayList().add(wdList.get(i));
				}
			}

			event.getProperties().add(new RRule(recur));
		}
	}

	/**
	 * 알람 설정 담기
	 * 
	 * @param event
	 * @param ievent
	 */
	private static void setAlarmForSave(VEvent event, IEvent ievent) {

		if (ievent.getIalarmList() != null) {

			for (IAlarm ialarm : ievent.getIalarmList()) {

				try {
					VAlarm alarm = new VAlarm();

					if (ialarm.getAction() != null) {
						alarm.getProperties().add(new Action(ialarm.getAction()));
					}
					if (ialarm.getSummary() != null) {
						alarm.getProperties().add(new Summary(ialarm.getSummary()));
					}
					if (ialarm.getDescription() != null) {
						alarm.getProperties().add(new Description(ialarm.getDescription()));
					}
					if (ialarm.getAttendeeList() != null) {

						for (IAttendee attendee : ialarm.getAttendeeList()) {
							Attendee att = new Attendee(new URI(attendee.getMail()));
							if (!StringUtil.isEmpty(attendee.getCn())) {
								att.getParameters().add(new Cn(attendee.getCn()));
							}
							if (!StringUtil.isEmpty(attendee.getMember())) {
								att.getParameters().add(new Member(attendee.getMember()));
							}
							alarm.getProperties().add(att);
						}
					}
					if (ialarm.getIdur() != null) {

						// VAlarm 반복설정 담기
						IDur idur = ialarm.getIdur();
						Dur dur = new Dur(idur.getDays(), idur.getHours(), idur.getMinutes(), idur.getSeconds());

						alarm.getProperties().add(new Trigger(dur));
					}

					event.getAlarms().add(alarm);

				} catch (Exception e) {
					// ex.printStackTrace();
				}
			}
		}

	}

	/**
	 * Valid 한 파싱 파일 만들기
	 * 
	 * @param reader
	 * @return
	 */
	private static StringReader makeValidFile(InputStreamReader reader) {

		StringReader returnReader = null;

		try {

			BufferedReader bf = new BufferedReader(reader);
			StringWriter sw = new StringWriter();

			String strLine = "";
			boolean find = false;

			while ((strLine = bf.readLine()) != null) {

				find = false;
				for (ICalProperty property : ICalProperty.values()) {
					if (strLine.startsWith(property + "")) {
						find = true;
						break;
					}
				}

				if (find) {
					sw.write(strLine + "\n");
				}
			}

			returnReader = new StringReader(sw.toString());

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return returnReader;

	}

}
