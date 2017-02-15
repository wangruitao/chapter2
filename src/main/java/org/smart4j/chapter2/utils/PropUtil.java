package org.smart4j.chapter2.utils;


import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropUtil {

	private final static Logger logger = LoggerFactory.getLogger(PropUtil.class);
	
	public static Properties getProperties() {
		Properties pro = new Properties();
		InputStream in= Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");

		return pro;
	}
}
