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
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.model.DLFileEntry" %>
<%@ page import="com.liferay.portlet.documentlibrary.model.DLFileVersion" %>
<%@ page import="com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants" %>
<%@ page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.util.DLUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.storage.Fields" %>
<%@ page import="com.liferay.portal.kernel.repository.model.FileEntry" %>
<%@ page import="com.liferay.portal.kernel.repository.model.FileVersion" %>
<%@ page import="com.liferay.portal.service.ServiceContext" %>
<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
long userId = themeDisplay.getUserId();
long folderId = 12041l;
long repositoryId = 10180l;
String mimeType = "image/jpeg";
java.util.Random rand = new java.util.Random();
String title = "Test" + rand.nextInt(99999);
String description = "Test description";
String changeLog = "";
long fileEntryTypeId = DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL;
java.util.Map<String, Fields> fieldsMap = null;
java.io.File file = new java.io.File("/Users/bij/Downloads/20100409112405426.jpg");
String sourceFileName = file.getName();
long fileSize = file.length();
ServiceContext serviceContext = new ServiceContext();
int status = com.liferay.portal.kernel.workflow.WorkflowConstants.STATUS_APPROVED;
FileEntry fileEntry2 = DLAppLocalServiceUtil.addFileEntry(
userId, repositoryId, folderId, sourceFileName, mimeType, title,
description, changeLog, file, serviceContext);
FileVersion fileVersion2 = fileEntry2.getLatestFileVersion();
String imagePreview = "&imagePreview=1";
String previewURL = DLUtil.getPreviewURL(fileEntry2, fileVersion2, themeDisplay, imagePreview);

%>
<img src="<%= previewURL %>">
