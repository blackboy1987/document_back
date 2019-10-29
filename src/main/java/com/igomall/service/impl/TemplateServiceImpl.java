
package com.igomall.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.igomall.TemplateConfig;
import com.igomall.service.TemplateService;
import com.igomall.util.SystemUtils;

/**
 * Service - 模板
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class TemplateServiceImpl implements TemplateService {

	@Value("${template.loader_path}")
	private String templateLoaderPath;

	@Autowired
	private ServletContext servletContext;

	@Override
	public String read(String templateConfigId) {
		Assert.hasText(templateConfigId,"");

		TemplateConfig templateConfig = SystemUtils.getTemplateConfig(templateConfigId);
		return read(templateConfig);
	}

	@Override
	public String read(TemplateConfig templateConfig) {
		Assert.notNull(templateConfig,"");

		try {
			String templatePath = servletContext.getRealPath(templateLoaderPath + templateConfig.getTemplatePath());
			File templateFile = new File(templatePath);
			return FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void write(String templateConfigId, String content) {
		Assert.hasText(templateConfigId,"");

		TemplateConfig templateConfig = SystemUtils.getTemplateConfig(templateConfigId);
		write(templateConfig, content);
	}

	@Override
	public void write(TemplateConfig templateConfig, String content) {
		Assert.notNull(templateConfig,"");

		try {
			String templatePath = servletContext.getRealPath(templateLoaderPath + templateConfig.getTemplatePath());
			File templateFile = new File(templatePath);
			FileUtils.writeStringToFile(templateFile, content, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}