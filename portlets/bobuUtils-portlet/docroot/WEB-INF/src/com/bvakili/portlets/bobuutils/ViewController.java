package com.bvakili.portlets.bobuutils;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller(value="viewController")
@RequestMapping(value="VIEW")
public class ViewController {
	@Autowired
	BobuWebConvertUtil bobuWebConvertUtil;

	@ActionMapping
	public void convert(ActionRequest request, ActionResponse response) {
		ThemeDisplay themeDisplay =
				(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		bobuWebConvertUtil.convert("/var/root/Documents/work/amir/", themeDisplay);
	}

	@RenderMapping
	public String view(RenderResponse response) {
		return "view";
	}
	public void setBobuWebConvertUtil(BobuWebConvertUtil bobuWebConvertUtil) {
		this.bobuWebConvertUtil = bobuWebConvertUtil;
	}

	public BobuWebConvertUtil getBobuWebConvertUtil() {
		return this.bobuWebConvertUtil;
	}
}
