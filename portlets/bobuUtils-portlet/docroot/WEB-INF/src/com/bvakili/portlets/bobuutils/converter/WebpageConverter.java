package com.bvakili.portlets.bobuutils.converter;

//import com.liferay.portlet.journal.model.JournalArticle;
import com.bvakili.portlets.bobuutils.model.BobuProductEntry;
import java.io.File;

public interface WebpageConverter {
	public BobuProductEntry convertToWebArticle(File file, String templateId, String structureId);
}
