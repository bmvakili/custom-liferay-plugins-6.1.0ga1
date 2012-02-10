<%@ include file="/html/portlet/journal_articles/init.jsp" %>
<div id='atbl'>
<style type="text/css">
.aui-w30 {
	width: 20%;
}
.aui-w70 {
	width: 80%;
}
div#atbl .portlet-section-header, div#atbl .lfr-template {
	display: none;
}
div#atbl td {
	background:repeat scroll 0 0 #F5F8FB;
	padding: 0px;
	margin: 0px;
	padding-left: 10px;
	padding-bottom: 10px;
	border-width: 0px
}
div#atbl tr {
	padding-right: 10px;
	border-width: 0px
}
div.lfr-search-container table.taglib-search-iterator tbody {
	display: inline;
}
div.lfr-search-container table.taglib-search-iterator tr, div.lfr-search-container table.taglib-search-iterator td{
	vertical-align: top;
	width: 185px;
	height: 200px;
	display: inline;
	text-align: center;
	float: left;
}
div#atbl img.thumb {
	width: 165px;
	height: 165px;
	text-align: center;
	vertical-align: top;
	padding-top: 10px;
	padding-top: 10px;

}
</style>

<%
String redirect = ParamUtil.getString(request, "redirect");

String articleId = ParamUtil.getString(request, "articleId");
double version = ParamUtil.getDouble(request, "version");
%>

<c:choose>
<c:when test="<%= Validator.isNull(articleId) %>">

<%
if (Validator.isNull(type)) {
type = null;
}

String status = "approved";

PortletURL portletURL = renderResponse.createRenderURL();

if (pageUrl.equals("normal")) {
portletURL.setWindowState(WindowState.NORMAL);
}
else {
portletURL.setWindowState(WindowState.MAXIMIZED);
}

portletURL.setParameter("struts_action", "/journal_articles/view");

PortletURL articleURL = PortletURLUtil.clone(portletURL, renderResponse);

ArticleSearch searchContainer = new ArticleSearch(renderRequest, portletURL);

searchContainer.setDelta(pageDelta);
searchContainer.setDeltaConfigurable(false);
searchContainer.setOrderByCol(orderByCol);
searchContainer.setOrderByType(orderByType);
searchContainer.setOrderByComparator(orderByComparator);

List headerNames = searchContainer.getHeaderNames();

headerNames.clear();

//headerNames.add("title");
//headerNames.add("display-date");
//headerNames.add("author");
//headerNames.add("image");

searchContainer.setOrderableHeaders(null);

ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

searchTerms.setGroupId(groupId);
searchTerms.setType(type);

if (Validator.isNotNull(structureId)) {
searchTerms.setStructureId(structureId);
}

searchTerms.setDisplayDateLT(new Date());
searchTerms.setStatus(status);
searchTerms.setVersion(version);
searchTerms.setAdvancedSearch(true);

List<JournalArticle> results = null;
%>

<c:choose>
<c:when test="<%= PropsValues.JOURNAL_ARTICLES_SEARCH_WITH_INDEX %>">
<%@ include file="/html/portlet/journal/article_search_results_index.jspf" %>
</c:when>
<c:otherwise>
<%@ include file="/html/portlet/journal/article_search_results_database.jspf" %>
</c:otherwise>
</c:choose>

<%
List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
JournalArticle article = results.get(i);

article = article.toEscapedModel();

ResultRow row = new ResultRow(article, article.getArticleId() + EditArticleAction.VERSION_SEPARATOR + article.getVersion(), i);

String rowHREF = null;

if (pageUrl.equals("popUp")) {
StringBundler sb = new StringBundler(7);

sb.append(themeDisplay.getPathMain());
sb.append("/journal_articles/view_article_content?groupId=");
sb.append(article.getGroupId());
sb.append("&articleId=");
sb.append(article.getArticleId());
sb.append("&version=");
sb.append(article.getVersion());

rowHREF = sb.toString();
}
else {
articleURL.setParameter("returnToFullPageURL", currentURL);
articleURL.setParameter("groupId", String.valueOf(article.getGroupId()));
articleURL.setParameter("articleId", article.getArticleId());
articleURL.setParameter("version", String.valueOf(article.getVersion()));

if (pageUrl.equals("viewInContext")) {
AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalArticle.class.getName());

AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(article.getId());

String viewFullContentURLString = articleURL.toString();

viewFullContentURLString = HttpUtil.setParameter(viewFullContentURLString, "redirect", currentURL);

rowHREF = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, viewFullContentURLString);

rowHREF = HttpUtil.setParameter(rowHREF, "redirect", currentURL);

if (Validator.isNull(rowHREF)) {
rowHREF = articleURL.toString();
}
}
else {
rowHREF = articleURL.toString();
}
}

String target = null;

if (pageUrl.equals("popUp")) {
target = "_blank";
}

TextSearchEntry rowTextEntry = new TextSearchEntry();

rowTextEntry.setHref(rowHREF);
rowTextEntry.setName(article.getArticleId());
rowTextEntry.setTarget(target);

/*// Article id

row.addText(rowTextEntry);

// Version

rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

rowTextEntry.setName(String.valueOf(article.getVersion()));

row.addText(rowTextEntry);*/

// Title

//rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

//rowTextEntry.setName(article.getTitle(locale));

//row.addText(rowTextEntry);

// Display date

//rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

//rowTextEntry.setName(dateFormatDateTime.format(article.getDisplayDate()));

//row.addText(rowTextEntry);

// Author
// Icon/image

rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

//rowTextEntry.setName(HtmlUtil.escape(PortalUtil.getUserName(article.getUserId(), article.getUserName())));
String smallImageURL = article.getSmallImageURL();
int siui = smallImageURL.indexOf("//");
if (siui >= 0) {
	int siuip = smallImageURL.indexOf("/", siui + 2);
	if (siuip >= 0) {
		smallImageURL = smallImageURL.substring(siuip);
	}
}
rowTextEntry.setName("<div><img class='thumb' src='" + smallImageURL + "'></div><div>" + article.getTitle(locale) + "</div>");
row.addText(rowTextEntry);

%>
<%
// Add result row

resultRows.add(row);
}
%>
<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</c:when>
<c:otherwise>

<%
String languageId = LanguageUtil.getLanguageId(request);
int articlePage = ParamUtil.getInteger(renderRequest, "page", 1);
String xmlRequest = PortletRequestUtil.toXML(renderRequest, renderResponse);

JournalArticleDisplay articleDisplay = JournalContentUtil.getDisplay(groupId, articleId, null, null, languageId, themeDisplay, articlePage, xmlRequest);

JournalArticle article = null;

try {
article = JournalArticleLocalServiceUtil.getLatestArticle(scopeGroupId, articleId, WorkflowConstants.STATUS_ANY);

boolean expired = article.isExpired();

if (!expired) {
Date expirationDate = article.getExpirationDate();

if ((expirationDate != null) && expirationDate.before(new Date())) {
expired = true;
}
}
%>

<c:choose>
<c:when test="<%= (articleDisplay != null) && !expired %>">
<c:if test='<%= pageUrl.equals("normal") %>'>
<portlet:renderURL var="backURL">
<portlet:param name="struts_action" value="/journal_articles/view" />
<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:renderURL>

<liferay-ui:header
backURL="<%= backURL %>"
localizeTitle="<%= false %>"
title="<%= article.getTitle(locale) %>"
/>
</c:if>

<%
AssetEntryServiceUtil.incrementViewCounter(JournalArticle.class.getName(), articleDisplay.getResourcePrimKey());
%>

<div class="journal-content-article">
<%= articleDisplay.getContent() %>
</div>

<c:if test="<%= articleDisplay.isPaginate() %>">

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("articleId", articleId);
portletURL.setParameter("version", String.valueOf(version));
%>

<br />

<liferay-ui:page-iterator
cur="<%= articleDisplay.getCurrentPage() %>"
curParam='<%= "page" %>'
delta="<%= 1 %>"
maxPages="<%= 25 %>"
total="<%= articleDisplay.getNumberOfPages() %>"
type="article"
url="<%= portletURL.toString() %>"
/>

<br />
</c:if>
</c:when>
<c:otherwise>
<div class="portlet-msg-error">
<liferay-ui:message key="this-content-has-expired-or-you-do-not-have-the-required-permissions-to-access-it" />
</div>
</c:otherwise>
</c:choose>

<%
} catch (NoSuchArticleException nsae) {
%>

<div class="portlet-msg-error">
<%= LanguageUtil.get(pageContext, "the-selected-web-content-no-longer-exists") %>
</div>

<%
}
%>

</c:otherwise>
</c:choose>
</div>
