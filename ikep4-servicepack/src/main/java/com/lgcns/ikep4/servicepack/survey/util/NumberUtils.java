package com.lgcns.ikep4.servicepack.survey.util;

import com.lgcns.ikep4.framework.core.model.BaseObject;
/**
 *  * @see  #SUNDAY 1
     * @see #MONDAY 2
     * @see #TUESDAY 3
     * @see #WEDNESDAY 4
     * @see #THURSDAY 5
     * @see #FRIDAY 6
     * @see #SATURDAY 7
 */
public class NumberUtils extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -4756099823318501324L;

	/**
	 * 반올림 생성
	 * @param prefix
	 * @param postfix
	 * @return
	 */
	public static double round(int prefix ,int postfix){
		try
		{
			if( postfix <= 0 ){
				return 0;
			}
			else
			{
				float temp = (new Float(prefix)/new Float(postfix) ) * MagicNumUtils.MOD_100 ;
				
				return Math.round(  (temp*MagicNumUtils.MOD_I_10) )/MagicNumUtils.MOD_D_10;
			}
		}
		catch(Exception e){
			return 0;
		}
	}
	/**
	 * 반올림 생성
	 * @param prefix
	 * @param postfix
	 * @return
	 */
	public static double round2(int prefix ,int postfix){
		try
		{
			if( postfix <= 0 ){
				return 0;
			}
			else
			{
				float temp = (new Float(prefix)/new Float(postfix) ) * MagicNumUtils.MOD_100 ;
				return Math.round(  temp*100 ) / MagicNumUtils.MOD_D_100;
			}
		}
		catch(Exception e){
			return 0;
		}
	}	
}