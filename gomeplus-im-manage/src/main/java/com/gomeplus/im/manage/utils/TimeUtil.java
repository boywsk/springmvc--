package com.gomeplus.im.manage.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/*暂时没有用到*/
public class TimeUtil {
	public static long normalTimeToMillisecondRL(String time) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String msTime = Long.toString(sdf.parse(time).getTime());
		long milliSecond = Long.parseLong(msTime);
		return milliSecond;
	}
	public static String normalTimeToMillisecondRS(String time) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String msTime = Long.toString(sdf.parse(time).getTime());
		return msTime;
	}
	public static String millisecondToNormalTime(String millisecondTime) throws ParseException{
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String  normalTime = simple.format(Long.parseLong(millisecondTime));
		return normalTime;
	}
	public static void main(String[] args){
		String millisecondTime = "1461921132414";
		String normalTime = "2016-05-04 10:15:54";
		try {
			System.out.println("1: "+normalTimeToMillisecondRS(normalTime));
			System.out.println("2: "+millisecondToNormalTime(millisecondTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
