package com.lgcns.ikep4.portal.portlet.util;

import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 날씨 Util
 *
 * @author 임종상
 * @version $Id: WeatherUtil.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class WeatherUtil {

	/**
	 * 날씨 포틀릿 작은 이미지 파일명 조회
	 * @param iconValue 아이콘 value 
	 * @return tempValue 아이콘 파일명
	 */
	public String makeWeatherIconSmall(String iconValue)
	{
		String tempValue = makeWeatherIconSmallTempValue(iconValue);
		
		if(StringUtil.isEmpty(tempValue)) {
			if("mist.gif".equals(iconValue))
			{
				tempValue="weather_mist_s.png";
			}
			else if("mostly_sunny.gif".equals(iconValue))
			{
				tempValue="weather_mostly_sunny_s.png";
			}
			else if("rain.gif".equals(iconValue))
			{
				tempValue="weather_rain_s.png";
			}
			else if("snow.gif".equals(iconValue))
			{
				tempValue="weather_snow_s.png";
			}
			else if("stom.gif".equals(iconValue))
			{
				tempValue="weather_stom_s.png";
			}
			else if("sunny.gif".equals(iconValue))
			{
				tempValue="weather_sunny_s.png";
			}
			else
			{
				tempValue="weather_cloudy_s.png";
			}
		}
		
		return tempValue;
	}
	
	

	/**
	 * 날씨 포틀릿 작은 이미지 파일명 조회 Cyclomatic Complexity로 인한 분리 
	 * @param iconValue 아이콘 value
	 * @return
	 */
	private String makeWeatherIconSmallTempValue (String iconValue) {
		
		String tempValue = "";
		
		if("chance_of_rain.gif".equals(iconValue))
		{
			tempValue="weather_chance_of_rain_s.png";
		}
		else if("chance_of_snow.gif".equals(iconValue))
		{
			tempValue="weather_chance_of_snow_s.png";
		}
		else if("chance_of_stom.gif".equals(iconValue))
		{
			tempValue="weather_chance_of_stom_s.png";
		}
		else if("cloudy.gif".equals(iconValue))
		{
			tempValue="weather_cloudy_s.png";
		}
		else if("dust.gif".equals(iconValue))
		{
			tempValue="weather_dust_s.png";
		}
		else if("flurries.gif".equals(iconValue))
		{
			tempValue="weather_flurries_s.png";
		}
		else if("fog.gif".equals(iconValue))
		{
			tempValue="weather_fog_s.png";
		}
		else if("haze.gif".equals(iconValue))
		{
			tempValue="weather_haze_s.png";
		}
		else if("icy.gif".equals(iconValue))
		{
			tempValue="weather_icy_s.png";
		}
		
		return tempValue;
	}

	/**
	 * 날씨 포틀릿 이미지 파일명 조회
	 * @param iconValue 아이콘 value 
	 * @return tempValue 아이콘 파일명
	 */
	public String makeWeatherIcon(String iconValue)
	{
		String tempValue = makeWeatherIconTempValue(iconValue);
		
		if(StringUtil.isEmpty(tempValue)) {
			if("mist.gif".equals(iconValue))
			{
				tempValue="weather_mist.png";
			}
			else if("mostly_sunny.gif".equals(iconValue))
			{
				tempValue="weather_mostly_sunny.png";
			}
			else if("rain.gif".equals(iconValue))
			{
				tempValue="weather_rain.png";
			}
			else if("snow.gif".equals(iconValue))
			{
				tempValue="weather_snow.png";
			}
			else if("stom.gif".equals(iconValue))
			{
				tempValue="weather_stom.png";
			}
			else if("sunny.gif".equals(iconValue))
			{
				tempValue="weather_sunny.png";
			}
			else
			{
				tempValue="weather_cloudy.png";
			}
		}
		
		return tempValue;
	}
	
	/**
	 * 날씨 포틀릿 이미지 파일명 조회 Cyclomatic Complexity로 인한 분리
	 * @param iconValue
	 */
	private String makeWeatherIconTempValue(String iconValue) {
		
		String tempValue = "";
		
		if("chance_of_rain.gif".equals(iconValue))
		{
			tempValue="weather_chance_of_rain.png";
		}
		else if("chance_of_snow.gif".equals(iconValue))
		{
			tempValue="weather_chance_of_snow.png";
		}
		else if("chance_of_stom.gif".equals(iconValue))
		{
			tempValue="weather_chance_of_stom.png";
		}
		else if("cloudy.gif".equals(iconValue))
		{
			tempValue="weather_cloudy.png";
		}
		else if("dust.gif".equals(iconValue))
		{
			tempValue="weather_dust.png";
		}
		else if("flurries.gif".equals(iconValue))
		{
			tempValue="weather_flurries.png";
		}
		else if("fog.gif".equals(iconValue))
		{
			tempValue="weather_fog.png";
		}
		else if("haze.gif".equals(iconValue))
		{
			tempValue="weather_haze.png";
		}
		else if("icy.gif".equals(iconValue))
		{
			tempValue="weather_icy.png";
		}
		
		return tempValue;
	}
	
	/**
	 * 날씨 포틀릿 작은 이미지 파일명 조회
	 * @param iconValue 아이콘 value 
	 * @return tempValue 아이콘 파일명
	 */
	public String getSmallWeatherIcon(String iconValue)
	{
		String tempValue = "";

		if(!StringUtil.isEmpty(iconValue)) {

			int iconType = Integer.parseInt(iconValue);
			switch(iconType)
			{
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
					tempValue = "weather_stom_s.png";
				    break;
				case 5:
					tempValue = "weather_snow_s.png"; 
				case 6: 
					tempValue = "weather_snow_s.png"; 
					break;
				case 7: 
					tempValue = "weather_flurries_s.png";
					break;
				case 8: 
				case 9: 
					tempValue = "weather_icy_s.png";
					break;
				case 10: 
					tempValue = "weather_snow_s.png"; 
					break;
				case 11: 
					tempValue = "weather_rain_s.png";
					break;
				case 12: 
					tempValue = "weather_rain_s.png";
					break;
				case 13: 
					tempValue = "weather_flurries_s.png";
					break;
				case 14:
				case 15:
				case 16: 
					tempValue =  "weather_snow_s.png";
					break;
				case 17: 
					tempValue = "weather_stom_s.png" ;
					break;
				case 18: 
					tempValue = "weather_rain_s.png";
					break;
				case 19: 
					tempValue = "weather_dust_s.png";
					break;
				case 20: 
					tempValue = "weather_fog_s.png";
					break;
				case 21:
				case 22: 
					tempValue = "weather_haze_s.png";
					break;
				case 23:
				case 24: 
					tempValue = "weather_mostly_sunny_s.png";
					break;
				case 25: 
					tempValue = "weather_icy_s.png"; ;
					break;
				case 26: 
					tempValue = "weather_cloudy_s.png";
					break;
				case 27: 					
				case 28: 
					tempValue = "weather_cloudy_s.png";
					break;
				case 29: 
				case 30: 
					tempValue = "weather_mostly_sunny_s.png";
					break;
				case 31:
				case 32: 
					tempValue = "weather_sunny_s.png";
					break;
				case 33:
				case 34: 
					tempValue = "weather_mostly_sunny_s.png";
					break;
				case 35: 
					tempValue = "weather_stom_s.png";
					break;
				case 36: 
					tempValue = "weather_sunny_s.png";
					break;
				case 37:
				case 38: 
					tempValue = "weather_chance_of_stom_s.png" ;
					break; 
				case 39: 
					tempValue = "weather_chance_of_rain_s.png";
					break;
				case 40: 
					tempValue = "weather_rain_s.png";
					break;
				case 41: 
					tempValue = "weather_chance_of_snow_s.png";
					break; 
				case 42:
				case 43: 
					tempValue =  "weather_snow_s.png";
					break;				
				case 45: 
					tempValue = "weather_chance_of_rain_s.png";
					break; 
				case 46: 
					tempValue = "weather_chance_of_snow_s.png";
					break;
				case 47: 
					tempValue = "weather_chance_of_stom_s.png";
					break;
				default : 
					tempValue = "weather_sunny_s.png";
					break;
			}			
		}
		else
		{
			tempValue="weather_sunny_s.png";
		}

		return tempValue;
	}	
	/**
	 * 날씨 포틀릿 작은 이미지 파일명 조회
	 * @param iconValue 아이콘 value 
	 * @return tempValue 아이콘 파일명
	 */
	public String getWeatherIcon(String iconValue)
	{
		String tempValue = "";

		if(!StringUtil.isEmpty(iconValue)) {

			int iconType = Integer.parseInt(iconValue);
			switch(iconType)
			{
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
					tempValue = "weather_stom.png";
				    break;
				case 5:
					tempValue = "weather_snow.png"; 
				case 6: 
					tempValue = "weather_snow.png"; 
					break;
				case 7: 
					tempValue = "weather_flurries.png";
					break;
				case 8: 
				case 9: 
					tempValue = "weather_icy.png";
					break;
				case 10: 
					tempValue = "weather_snow.png"; 
					break;
				case 11: 
					tempValue = "weather_rain.png";
					break;
				case 12: 
					tempValue = "weather_rain.png";
					break;
				case 13: 
					tempValue = "weather_flurries.png";
					break;
				case 14:
				case 15:
				case 16: 
					tempValue =  "weather_snow.png";
					break;
				case 17: 
					tempValue = "weather_stom.png" ;
					break;
				case 18: 
					tempValue = "weather_rain.png";
					break;
				case 19: 
					tempValue = "weather_dust.png";
					break;
				case 20: 
					tempValue = "weather_fog.png";
					break;
				case 21:
				case 22: 
					tempValue = "weather_haze.png";
					break;
				case 23:
				case 24: 
					tempValue = "weather_mostly_sunny.png";
					break;
				case 25: 
					tempValue = "weather_icy.png"; ;
					break;
				case 26: 
					tempValue = "weather_cloudy.png";
					break;
				case 27: 					
				case 28: 
					tempValue = "weather_cloudy.png";
					break;
				case 29: 
				case 30: 
					tempValue = "weather_mostly_sunny.png";
					break;
				case 31:
				case 32: 
					tempValue = "weather_sunny.png";
					break;
				case 33:
				case 34: 
					tempValue = "weather_mostly_sunny.png";
					break;
				case 35: 
					tempValue = "weather_stom.png";
					break;
				case 36: 
					tempValue = "weather_sunny.png";
					break;
				case 37:
				case 38: 
					tempValue = "weather_chance_of_stom.png" ;
					break; 
				case 39: 
					tempValue = "weather_chance_of_rain.png";
					break;
				case 40: 
					tempValue = "weather_rain.png";
					break;
				case 41: 
					tempValue = "weather_chance_of_snow.png";
					break; 
				case 42:
				case 43: 
					tempValue =  "weather_snow.png";
					break;				
				case 45: 
					tempValue = "weather_chance_of_rain.png";
					break; 
				case 46: 
					tempValue = "weather_chance_of_snow.png";
					break;
				case 47: 
					tempValue = "weather_chance_of_stom.png";
					break;
				default : 
					tempValue = "weather_sunny.png";
					break;

			}			
		}
		else
		{
			tempValue = "weather_sunny.png";
		}
		
		
		/*0, 1 ,2, 3 ,4, 17, 35 - Thunderstorm
		•5 - Rain/Snow mix
		•6 - Sleet/Snow mix
		•7 - Rain/Snow/Sleet mix
		•8,9 - Icy
		•10 - Rain/Sleet mix
		•11 - Light Rain
		•12 - Rain
		•13 - Light Snow
		•14,16,42,43 - Snow
		•15 - Blizzard
		•18,40 - Showers
		•19 - Dust
		•20 - Fog
		•21 - Haze
		•22 - Smoke
		•23,24 - Windy
		•25 - Frigid
		•26 - Cloudy
		•27,29,33 - Partly Cloudy (night)
		•28,30,34 - Partly Cloudy
		•31 - Clear (night)
		•32 - Clear
		•36 - Hot
		•37,38 - Scattered Thunderstorms
		•39 - Scattered Showers
		•41 - Scattered Snow Showers
		•44 - N/A
		•45 - Scattered Rain Showers (night)
		•46 - Scattered Snow Showers (night)
		•47 - Scattered Thunderstorms (night)*/
		 


		return tempValue;
	}	
}
