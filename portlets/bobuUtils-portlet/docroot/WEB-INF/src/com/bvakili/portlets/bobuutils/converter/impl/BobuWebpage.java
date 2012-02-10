package com.bvakili.portlets.bobuutils.converter.impl;

import com.bvakili.portlets.bobuutils.converter.WebpageConverter;
import com.bvakili.portlets.bobuutils.model.BobuProductEntry;
import com.liferay.portlet.journal.model.JournalArticle;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.lang.Exception;

public class BobuWebpage implements WebpageConverter{
	public BobuProductEntry convertToWebArticle(File file, String templateId, String structureId) {
		BobuProductEntry retVal = null;
		try {
			retVal = processFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	private BobuProductEntry processFile(File file) throws Exception {
		BobuProductEntry retVal = processDetailFile(file);
		retVal = processToGetSmallImageURL(file, retVal);
		return retVal;
	}
	private BobuProductEntry processToGetSmallImageURL(File file, BobuProductEntry entry) throws Exception {
		File parentDir = file.getParentFile();
		if (file.getName().startsWith("products_view")) {
		for (File sibling : parentDir.listFiles()) {
			if (sibling.getName().startsWith("Products") && !sibling.getName().startsWith("products_view")) {
				List <String> lines = FileUtils.readLines(sibling, "UTF-8");
				System.out.println("File is " + file.getName());
				System.out.println("Sibling File is " + sibling.getName());
				for (String line : lines) {
					String filenameRegexp = file.getName().replace('?', '.');
					String regexp = "(?is)href=\"" + filenameRegexp  + "[^\"]*\".*?src=['\"]([^'\"]*)['\"].*";
					java.util.regex.Pattern imagePattern = java.util.regex.Pattern.compile(regexp);
					java.util.regex.Matcher imageMatcher = imagePattern.matcher(line);
					boolean found = imageMatcher.find();
					String imageString = "";
					/**
					if (line.contains(file.getName())){
						System.out.println("");
						System.out.print("Found ? : " + found);
						System.out.print("Regexp is : " + regexp);
						System.out.println(" Line is : " + line);
					}
					*/
					if (found) {
						imageString = imageMatcher.group(1);
						File imageFile = new File(file.getParent() + File.separator + imageString);
						entry.setSmallImage(FileUtils.readFileToByteArray(imageFile));
						return entry;
					}
				}
			}
			
		}
		}
		return entry;
	}
	private BobuProductEntry processDetailFile(File file) throws Exception {
		BobuProductEntry retVal = null;
		String name = file.getName();
		ConcurrentHashMap<String,List<String>> productDetails = new ConcurrentHashMap<String,List<String>>();
		if (name.startsWith("products_view")) {
			//System.out.println(name);
			List <String> lines = FileUtils.readLines(file, "UTF-8");
			int count = -1;
			String currentKey = "";
			for (String line : lines) {
				java.util.regex.Pattern imagePattern = java.util.regex.Pattern.compile("(?is).*?src[^=]*=[^\"']*['\"](uploadfile.201004[^'\"]*)['\"].*?");
				java.util.regex.Matcher imageMatcher = imagePattern.matcher(line);
				boolean found = imageMatcher.find();
				String imageString = "";
				while (found) {
					imageString = imageMatcher.group(1);
					List<String> imageEntries = productDetails.get("images");
					if (imageEntries == null) {
						imageEntries = new ArrayList<String>();
					}
					imageEntries.add(imageString);
					productDetails.put("images", imageEntries);
					found = imageMatcher.find();
				}
				if (line.matches("^<P>.*<.P>.*$")) {
					line = line.replaceAll("^(<P>.*?</P>).*$","$1");
					String[] parts = line.split(":");
					if (parts.length == 1) {
						List<String> value = new ArrayList<String>();
						value = (List<String>) productDetails.get(currentKey);
						if (value == null) {
							value = new ArrayList<String>();
						}
						String newLine = "";
						if (value.size() == 1) {
							newLine = value.get(0);
							newLine = line + newLine;
							value.set(0, newLine);
							//System.out.println(currentKey + " : " + newLine);
						} else {
							value.add(line);
							//System.out.println(currentKey + " : " + line);
						}
						productDetails.put(currentKey, value);
						continue;
					}
					if (parts[1].trim() == "") {
						continue;
					}
					currentKey = parts[0];
					List<String> values = productDetails.get(currentKey);
					if (values == null) {
						values = new ArrayList<String>();
					}
					if (values.size() == 1) {
						String newLine = values.get(0);
						newLine += parts[1];
						values.set(0, newLine);
					} else {
						values.add(parts[1]);
					}
					productDetails.put(currentKey, values);
				} 
			}
//			}
			BobuProductEntry bobuProductEntry = new BobuProductEntry();
			for (Enumeration e = productDetails.keys() ; e.hasMoreElements() ;) {
				String key = e.nextElement().toString();
				List<String> values = productDetails.get(key);
				if (values == null) {
					values = new ArrayList<String>();
				}
				int size = values.size();
				String val = "";
				for (int i = 0; i < size; i++) {
					val = values.get(i);
					val = val.replaceAll("<[^>]*>","");
					val = val.replaceAll("(?i)[&]amp;","&");
					val = val.replaceAll("(?i)[&]\\s*n\\s*b\\s*s\\s*p\\s*;"," ");
					val = val.replaceAll("(?i)[&]nbsp;"," ");
					values.set(i, val);
					//System.out.println("\n\t" + key + "\t" + val );
				}
				key = key.replaceAll("<[^>]*>","");
				//fields.put(key,"");
				bobuProductEntry = setEntryKeyValue(bobuProductEntry, key, values, file);
			}
			retVal = bobuProductEntry;
		}
		return retVal;
	}

	private BobuProductEntry setEntryKeyValue(BobuProductEntry p, String k, List<String> vs, File curFile) {
		if (vs  == null) {
			return p;
		}
		String v = "";
		if (vs.size() == 1) {
			v = vs.get(0);
			if (v != null) {
				v = v.trim();
			}
		}
		if (k.toLowerCase().trim().startsWith("weight")) {
			p.setWeight(v);
		}
		if (k.toLowerCase().trim().startsWith("loading")) {
			p.setLoadingPort(v);
		}
		if (k.toLowerCase().trim().startsWith("extra")) {
			p.setExtraProductInformation(v);
		}
		if (k.toLowerCase().trim().startsWith("material")) {
			p.setMaterial(v);
		}
		if (k.toLowerCase().trim().startsWith("color")) {
			p.setColor(v);
		}
		if (k.toLowerCase().trim().startsWith("measurement")) {
			p.setMeasurement(v);
		}
		if (k.toLowerCase().trim().startsWith("contain")) {
			p.setContain(v);
		}
		if (k.toLowerCase().trim().startsWith("name")) {
			v = v.replaceAll("[^0-9a-zA-Z_\\.\\s-]","");
			p.setName(v);
		}
		if (k.toLowerCase().trim().startsWith("hanging")) {
			p.setHangingMaterial(v);
		}
		if (k.toLowerCase().trim().startsWith("indoor")) {
			p.setIndoorOutdoorRecommended(v);
		}
		if (k.toLowerCase().trim().startsWith("feature")) {
			p.setFeature(v);
		}
		try {
		if (k.toLowerCase().trim().startsWith("images")) {
			for (String val : vs) {
				List<byte[]> images = p.getImages();
				List<String> imageNames = p.getImageNames();
				if (images == null) {
					images = new ArrayList<byte[]>();
				}
				if (imageNames == null) {
					imageNames = new ArrayList<String>();
				}
				File file = new File(curFile.getParent() + File.separator +  val);
				images.add(FileUtils.readFileToByteArray(file));
				imageNames.add(file.getName());
				p.setImages(images);
				p.setImageNames(imageNames);
			}
		}
		} catch(IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return p;
	}
}
