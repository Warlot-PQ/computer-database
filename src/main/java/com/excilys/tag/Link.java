package com.excilys.tag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Define a custom link tag Link.java, myLib.xml and web.xml are replaced by
 * link.tag
 * 
 * @author pqwarlot
 *
 */
public class Link extends SimpleTagSupport {
	StringWriter sw = new StringWriter();
	private int page;
	private int limit;

	public Link() {

	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

		getJspBody().invoke(sw);

		getJspContext().getOut().write("<a " + "href='" + request.getContextPath() + "/Router"
				+ "?action=dashboard&page=" + page + "&limit=" + limit + "'>" + sw.toString() + "</a>");
	}
}