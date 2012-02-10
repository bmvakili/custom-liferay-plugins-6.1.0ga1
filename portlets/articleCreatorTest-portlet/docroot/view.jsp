<%
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ page isELIgnored="false" %>
<%@ page import="com.liferay.portlet.journal.model.JournalArticle" %>
<%@ page import="com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil" %>
<%@ page import="com.liferay.portal.service.ServiceContext" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Map" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%

// article id = 12318

long userId = themeDisplay.getUserId();
// scopeGroupId
JournalArticle sample = null;
long classNameId = 0l;
long structurePrimaryKey = 0l;
String articleId = "12318";
boolean autoArticleId = true;
double version = 0.0d;
Map<Locale, String> titleMap = new HashMap<Locale, String>();
Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
String content = "";
String type = "test";
String structureId = null;
String templateId = null;
String layoutUuid = null;
int displayDateMonth = 1;
int displayDateDay = 12;
int displayDateYear = 2011;
int displayDateHour = 1;
int displayDateMinute = 1;
int expirationDateMonth = 1;
int expirationDateDay = 1;
int expirationDateYear = 2014;
int expirationDateHour = 1;
int expirationDateMinute = 1;
boolean neverExpire = true;
int reviewDateMonth = 1;
int reviewDateDay = 2;
int reviewDateYear = 2011;
int reviewDateHour = 1;
int reviewDateMinute = 1;
boolean neverReview = true;
boolean indexable = true;
boolean smallImage = false;
String smallImageURL = null;
File smallImageFile = null;
Map<String, byte[]> images = null;
String articleURL = null;
ServiceContext serviceContext = new ServiceContext();
serviceContext.setScopeGroupId(scopeGroupId);

sample = JournalArticleLocalServiceUtil.getArticle(scopeGroupId, articleId);
%>

The Article with articleId <%= articleId %> has following toString method output: <br/>
<%= sample.toString() %>
<br/>

<%

articleId = "";
titleMap.put(locale, "Testing2");
content="<?xml version=\"1.0\"?><root available-locales=\"en_US\" default-locale=\"en_US\">	<static-content language-id=\"en_US\">Testing...</static-content></root>";
content="";
String xmlOutput = "";
xmlOutput += "<?xml version=\"1.0\"?>";
xmlOutput += "";
xmlOutput += "<root available-locales=\"en_US\" default-locale=\"en_US\">";
xmlOutput += "	<dynamic-element name=\"image\" type=\"document_library\" index-type=\"\">";
xmlOutput += "		<dynamic-content>http://localhost:9090/documents/10180/12041/Test17446?version=1.0&amp;t=1327268418701&amp;imagePreview=1</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"headline\" type=\"text\" index-type=\"text\">";
xmlOutput += "		<dynamic-content>" + "test 1" + "</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"measurement\" type=\"text\" index-type=\"text\">";
xmlOutput += "		<dynamic-content>" + "test 2" + "</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"contain\" type=\"text\" index-type=\"text\">";
xmlOutput += "		<dynamic-content>Test</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"name\" type=\"text\" index-type=\"keyword\">";
xmlOutput += "		<dynamic-content>" + "test 3" + "</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"material\" type=\"text\" index-type=\"text\">";
xmlOutput += "		<dynamic-content>" + "test 4" + "</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"weight\" type=\"text\" index-type=\"keyword\">";
xmlOutput += "		<dynamic-content>" + "test 5" + "</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"color\" type=\"text\" index-type=\"keyword\">";
xmlOutput += "		<dynamic-content>" + "test 6" + "</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"indoorOutdoor\" type=\"text\" index-type=\"keyword\">";
xmlOutput += "		<dynamic-content>" + "test 7" + "</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"hangingMaterial\" type=\"text\" index-type=\"text\">";
xmlOutput += "		<dynamic-content>" + "test test" + "</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"feature\" type=\"text\" index-type=\"text\">";
xmlOutput += "		<dynamic-content>" + "test test" + "</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"port\" type=\"text\" index-type=\"text\">";
xmlOutput += "		<dynamic-content>" + "test test" + "</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"other\" type=\"text_box\" index-type=\"text\">";
xmlOutput += "		<dynamic-content>" + "test test" + "</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"picture\" type=\"document_library\" index-type=\"\">";
xmlOutput += "		<dynamic-content>http://localhost:9090/documents/10180/12041/Test17446?version=1.0&amp;t=1327268418701&amp;imagePreview=1</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "	<dynamic-element name=\"picture\" type=\"document_library\" index-type=\"\">";
xmlOutput += "		<dynamic-content>http://localhost:9090/documents/10180/12041/Test17446?version=1.0&amp;t=1327268418701&amp;imagePreview=1</dynamic-content>";
xmlOutput += "	</dynamic-element>";
xmlOutput += "</root> ";
content = xmlOutput;
structureId = "12031";
templateId = "12036";
System.out.println("<Validate xml>");
System.out.println(content);
System.out.println("</Validate xml>");

sample = JournalArticleLocalServiceUtil.addArticle(userId, scopeGroupId, classNameId, structurePrimaryKey, articleId, autoArticleId, version, titleMap, descriptionMap, content, type, structureId, templateId, layoutUuid, displayDateMonth, displayDateDay, displayDateYear, displayDateHour, displayDateMinute, expirationDateMonth, expirationDateDay, expirationDateYear, expirationDateHour, expirationDateMinute, neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour, reviewDateMinute, neverReview, indexable, smallImage, smallImageURL, smallImageFile, images, articleURL, serviceContext);

%>

The Article with articleId <%= sample.getArticleId() %> has following toString method output: <br/>
<%= sample.toString() %>

