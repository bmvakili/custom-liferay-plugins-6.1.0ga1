package com.bvakili.portlets.bobuutils;

import com.bvakili.portlets.bobuutils.model.BobuProductEntry;
import com.bvakili.portlets.bobuutils.converter.WebpageConverter;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class BobuWebConvertUtil {

	@Autowired
	private WebpageConverter bobuWebpageConverter;


	public void convert(String folderPath, ThemeDisplay themeDisplay) {
		File f = new File(".");
		System.out.println("In BobuWebConvertUtil");
		if (folderPath != null) {
			if (folderPath.trim() != "") {
				f = new File(folderPath);
			}
		}
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for( File file : files) {
				if( !file.isDirectory() ) {
					try {
						BobuProductEntry entry = bobuWebpageConverter.convertToWebArticle(file, "0", "0");
						if (entry == null) {
							continue;
						}
						addEntry(entry, themeDisplay);
						System.out.println(entry);
						System.out.println("----------------");
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	private void addEntry(BobuProductEntry entry, ThemeDisplay themeDisplay) {
		long userId = themeDisplay.getUserId();
		entry = uploadImages(entry, themeDisplay);
		String content = getContent(entry);
		long classNameId = 0l;
		long structurePrimaryKey = 0l;
		String articleId = "";
		boolean autoArticleId = true;
		double version = 1.0d;
		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
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
		serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
		titleMap.put(themeDisplay.getLocale(), entry.getName());
		structureId = "12031";
		templateId = "12036";
		List<String> imageURLs = entry.getImageURLs();
		if (entry.getSmallImageURL() != null) {
			smallImage = true;
		}
		smallImageURL = entry.getSmallImageURL();

		try {
		JournalArticle sample = JournalArticleLocalServiceUtil.addArticle(userId, themeDisplay.getScopeGroupId(), classNameId, structurePrimaryKey, articleId, autoArticleId, version, titleMap, descriptionMap, content, type, structureId, templateId, layoutUuid, displayDateMonth, displayDateDay, displayDateYear, displayDateHour, displayDateMinute, expirationDateMonth, expirationDateDay, expirationDateYear, expirationDateHour, expirationDateMinute, neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour, reviewDateMinute, neverReview, indexable, smallImage, smallImageURL, smallImageFile, images, articleURL, serviceContext);
		JournalArticleLocalServiceUtil.addArticleResources(sample, true, true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public BobuProductEntry uploadImages(BobuProductEntry entry, ThemeDisplay themeDisplay) {
	
		List<byte[]> images = entry.getImages();
		List<String> imageNames = entry.getImageNames();
		List<String> imageURLs = new ArrayList<String>();
		for (int i = -1; i < images.size(); i++) {

		byte[] image = null;
		if (i == -1) {
			image = entry.getSmallImage();
		} else {
			image= images.get(i);
		}
		String entryName = entry.getName().trim().replaceAll("\\s","_");
		String imageTitle = entryName + "_" + (i+1);
		try {
		long userId = themeDisplay.getUserId();
		long folderId = 12041l;
		long repositoryId = 10180l;
		String mimeType = "image/jpeg";
		String title = imageTitle;
		if (i != -1) {
			imageNames.set(i, imageTitle);
		}
		String description = "This is a picture of the " + entry.getName() + " panel.";
		if (i == -1) {
			description = "This is the display image for the " + entry.getName() + " panel.";
		}
		String changeLog = "";
		long fileEntryTypeId = DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL;
		java.util.Map<String, Fields> fieldsMap = null;
		String sourceFileName = entry.getName();
		ServiceContext serviceContext = new ServiceContext();
		String[] guestPermissions = new String[] { ActionKeys.VIEW };
		serviceContext.setGuestPermissions(guestPermissions);
		serviceContext.setGroupPermissions(guestPermissions);
		int status = com.liferay.portal.kernel.workflow.WorkflowConstants.STATUS_APPROVED;
		FileEntry fileEntry2 = DLAppLocalServiceUtil.addFileEntry(
			userId, repositoryId, folderId, sourceFileName, mimeType, title,
			description, changeLog, image, serviceContext);
		FileVersion fileVersion2 = fileEntry2.getLatestFileVersion();
		String imagePreview = "&imagePreview=1";
		String previewURL = DLUtil.getPreviewURL(fileEntry2, fileVersion2, themeDisplay, imagePreview);
		int httpIndex = previewURL.indexOf("//");
		if (httpIndex != -1) {
			int slashIndex = previewURL.indexOf('/', httpIndex + 2);
			if (slashIndex != -1) {
				previewURL = previewURL.substring(slashIndex);
			}
		}
		if ( i== -1) {
			entry.setSmallImageURL(previewURL);
		} else {
			imageURLs.add(previewURL);
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
		}
		entry.setImageURLs(imageURLs);
		entry.setImageNames(imageNames);
		return entry;
	}
	public void setBobuWebpageConverter(WebpageConverter bobuWebpageConverter) {
		this.bobuWebpageConverter = bobuWebpageConverter;
	}
	public WebpageConverter getBobuWebpageConverter() {
		return this.bobuWebpageConverter;
	}
	public String getContent(BobuProductEntry entry) {
		List<String> imageURLs = entry.getImageURLs();
		String xmlOutput = "";
		xmlOutput += "<?xml version=\"1.0\"?>";
		xmlOutput += "";
		xmlOutput += "<root available-locales=\"en_US\" default-locale=\"en_US\">";
		if (imageURLs.size() > 0) {
		xmlOutput += "	<dynamic-element name=\"image\" type=\"document_library\" index-type=\"\">";
		xmlOutput += "		<dynamic-content>" + x(imageURLs.get(0)) + "</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		}
		xmlOutput += "	<dynamic-element name=\"headline\" type=\"text\" index-type=\"text\">";
		xmlOutput += "		<dynamic-content>" + x(entry.getName()) + "</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		xmlOutput += "	<dynamic-element name=\"measurement\" type=\"text\" index-type=\"text\">";
		xmlOutput += "		<dynamic-content>" + x(entry.getMeasurement()) + "</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		xmlOutput += "	<dynamic-element name=\"contain\" type=\"text\" index-type=\"text\">";
		xmlOutput += "		<dynamic-content>" + x(entry.getContain()) +"</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		xmlOutput += "	<dynamic-element name=\"name\" type=\"text\" index-type=\"keyword\">";
		xmlOutput += "		<dynamic-content>" + x(entry.getName()) + "</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		xmlOutput += "	<dynamic-element name=\"material\" type=\"text\" index-type=\"text\">";
		xmlOutput += "		<dynamic-content>" + x(entry.getMaterial()) + "</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		xmlOutput += "	<dynamic-element name=\"weight\" type=\"text\" index-type=\"keyword\">";
		xmlOutput += "		<dynamic-content>" + x(entry.getWeight()) + "</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		xmlOutput += "	<dynamic-element name=\"color\" type=\"text\" index-type=\"keyword\">";
		xmlOutput += "		<dynamic-content>" + x(entry.getColor()) + "</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		xmlOutput += "	<dynamic-element name=\"indoorOutdoor\" type=\"text\" index-type=\"keyword\">";
		xmlOutput += "		<dynamic-content>" + x(entry.getIndoorOutdoorRecommended()) + "</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		xmlOutput += "	<dynamic-element name=\"hangingMaterial\" type=\"text\" index-type=\"text\">";
		xmlOutput += "		<dynamic-content>" + x(entry.getHangingMaterial()) + "</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		xmlOutput += "	<dynamic-element name=\"feature\" type=\"text\" index-type=\"text\">";
		xmlOutput += "		<dynamic-content>" + x(entry.getFeature()) + "</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		xmlOutput += "	<dynamic-element name=\"port\" type=\"text\" index-type=\"text\">";
		xmlOutput += "		<dynamic-content>" + x(entry.getLoadingPort()) + "</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		if (false) {
		xmlOutput += "	<dynamic-element name=\"other\" type=\"text_box\" index-type=\"text\">";
		xmlOutput += "		<dynamic-content>" + "</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		}
		for( int i = 1; i < imageURLs.size(); i++) {
		xmlOutput += "	<dynamic-element name=\"picture\" type=\"document_library\" index-type=\"\">";
		xmlOutput += "		<dynamic-content>" + x(imageURLs.get(i)) + "</dynamic-content>";
		xmlOutput += "	</dynamic-element>";
		}
		xmlOutput += "</root> ";
		return xmlOutput;
	}

	private String x(String val) {
		return StringEscapeUtils.escapeXml(val);
	}
}
