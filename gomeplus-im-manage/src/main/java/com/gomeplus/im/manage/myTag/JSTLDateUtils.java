package com.gomeplus.im.manage.myTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 用于页面jstl时间格式化
 * @author liuxm
 *
 */
public class JSTLDateUtils extends TagSupport {

	private static final long serialVersionUID = -3354015192721342312L;
	private String value;
	private String pattern;

	public void setValue(String value) {
		this.value = value;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public int doStartTag() throws JspException {
		String vv = String.valueOf(value);
		long time = Long.valueOf(vv);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
		String s = dateformat.format(c.getTime());
		try {
			pageContext.getOut().write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	public static void main(String[] args) {
		long a = 1332744845078l;
		long time = Long.valueOf(a);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		SimpleDateFormat dateformat = new SimpleDateFormat("MM-dd HH:mm");
		String s = dateformat.format(c.getTime());
		System.out.println(s);
	}
}
