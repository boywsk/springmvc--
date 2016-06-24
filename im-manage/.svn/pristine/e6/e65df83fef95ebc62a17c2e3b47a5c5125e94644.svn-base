package cn.com.gome.manage.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * http request param工具类
 * 
 * @author liuxm
 * 
 */
public class ParamUtil {
	/**
	 * 从HttpServletRequest读取出参数名为paramName的String类型的值，如果值为空，返还一个默认值
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param paramName
	 *            String 参数名
	 * @return String
	 */
	public static String getParams(HttpServletRequest request,
			String paramName, String defaultValue) {
		String value = defaultValue;
		if (request.getParameter(paramName) != null) {
			value = request.getParameter(paramName);
		}
		return value;
	}

	/**
	 * 从HttpServletRequest读取出参数名为paramName的Integer类型的值，如果值为空，返还一个默认值
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param paramName
	 *            String 参数名
	 * @param defaultValue
	 *            Integer 默认值
	 * @return Integer
	 */
	public static int getIntParams(HttpServletRequest request,
			String paramName, int defaultValue) {
		int value = defaultValue;
		String o = request.getParameter(paramName);
		if (o != null && o.length() > 0)
			value = new Integer(o).intValue();
		return value;
	}

	/**
	 * 从HttpServletRequest读取出参数名为paramName的Integer类型的值，如果值为空，返还一个NULL
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param paramName
	 *            String 参数名
	 * @return Integer
	 */
	public static int getIntParams(HttpServletRequest request, String paramName) {
		String temp = request.getParameter(paramName);
		if (temp != null) {
			try {
				return new Integer(temp).intValue();
			} catch (Exception e) {
				return -1;
			}
		} else
			return -1;
	}

	/**
	 * 从HttpServletRequest读取出参数名为paramName的Long类型的值，如果值为空，返还一个默认值
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param paramName
	 *            String 参数名
	 * @param defaultValue
	 *            Long 默认值
	 * @return Long
	 */
	public static long getLongParams(HttpServletRequest request,
			String paramName, long defaultValue) {
		long value = defaultValue;
		String o = request.getParameter(paramName);
		if (o != null && o.length() > 0)
			value = new Long(o).longValue();
		return value;
	}

	/**
	 * 从HttpServletRequest读取出参数名为paramName的Long类型的值，如果值为空，返还null
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param paramName
	 *            String 参数名
	 * @return Long
	 */
	public static long getLongParams(HttpServletRequest request,
			String paramName) {
		String temp = request.getParameter(paramName);
		if (temp != null) {
			try {
				return new Long(temp).longValue();
			} catch (Exception e) {
				return -1L;
			}
		} else
			return -1L;
	}
}
