package com.bvakili.portlets.bobuutils.model;

import java.util.List;
import java.util.ArrayList;
public class BobuProductEntry {
	public BobuProductEntry() {
	}

public String getWeight() {
	return weight;
}
public void setWeight(String val) {
	weight = val;
}

public String getLoadingPort() {
	return loadingPort;
}
public void setLoadingPort(String val) {
	loadingPort = val;
}

public String getExtraProductInformation() {
	return extraProductInformation;
}
public void setExtraProductInformation(String val) {
	extraProductInformation = val;
}

public String getMaterial() {
	return material;
}
public void setMaterial(String val) {
	material = val;
}

public String getColor() {
	return color;
}
public void setColor(String val) {
	color = val;
}

public String getMeasurement() {
	return measurement;
}
public void setMeasurement(String val) {
	measurement = val;
}

public String getContain() {
	return contain;
}
public void setContain(String val) {
	contain = val;
}

public String getName() {
	return name;
}
public void setName(String val) {
	name = val.trim();
}

public String getHangingMaterial() {
	return hangingMaterial;
}
public void setHangingMaterial(String val) {
	hangingMaterial = val;
}

public String getIndoorOutdoorRecommended() {
	return indoorOutdoorRecommended;
}
public void setIndoorOutdoorRecommended(String val) {
	indoorOutdoorRecommended = val;
}

public String getFeature() {
	return feature;
}
public void setFeature(String val) {
	feature = val;
}

public List<byte[]> getImages() {
	return images;
}
public void setImages(List<byte[]> val) {
	images = val;
}

public List<String> getImageNames() {
	return imageNames;
}
public void setImageNames(List<String> val) {
	imageNames = val;
}

public List<String> getImageURLs() {
	return imageURLs;
}
public void setImageURLs(List<String> val) {
	imageURLs = val;
}

public String getSmallImageURL() {
	return smallImageURL;
}

public void setSmallImageURL(String val) {
	smallImageURL = val;
}

public byte[] getSmallImage() {
	return smallImage;
}

public void setSmallImage(byte[] val) {
	smallImage = val;
}

public String toString() {
	String out = "";
	out += "weight : " + weight + "\n";
	out += "loadingPort : " + loadingPort + "\n";
	out += "extraProductInformation : " + extraProductInformation + "\n";
	out += "material : " + material + "\n";
	out += "color : " + color + "\n";
	out += "measurement : " + measurement + "\n";
	out += "contain : " + contain + "\n";
	out += "name : " + name + "\n";
	out += "hangingMaterial : " + hangingMaterial + "\n";
	out += "indoorOutdoorRecommended : " + indoorOutdoorRecommended + "\n";
	out += "feature : " + feature + "\n";
	if (images == null)
		images = new ArrayList<byte[]>();
	out += "number of images : " + images.size() + "\n";
	if (imageNames == null)
		imageNames = new ArrayList<String>();
	for (String imageName : imageNames) {
		out += "\n\t imageName : " + imageName + "\n";
	}
	if (imageURLs == null)
		imageURLs = new ArrayList<String>();
	for (String imageURL : imageURLs) {
		out += "\n\t imageURL : " + imageURL + "\n";
	}
	if (smallImage == null) {
		smallImage = new byte[0];
	}
	if (smallImageURL == null) {
		smallImageURL = "";
	}
	out += "smallImage : " + (smallImage.length > 0) + "\n";
	out += "smallImageURL : " + smallImageURL + "\n";
	return out;
}

private String weight;
private String loadingPort;
private String extraProductInformation;
private String material;
private String color;
private String measurement;
private String contain;
private String name;
private String hangingMaterial;
private String indoorOutdoorRecommended;
private String feature;
private List<byte[]> images;
private List<String> imageNames;
private List<String> imageURLs;
private byte[] smallImage;
private String smallImageURL;

}
