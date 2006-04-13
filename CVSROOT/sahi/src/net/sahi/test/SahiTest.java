package com.sahi.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Logger;

import com.sahi.config.Configuration;

class SahiTest {
	private final String scriptName;
	private final String startURL;
	private int randomInt;
	private final String sessionId;
	Process process;
	private final String browser;
	private static Logger logger = Configuration
	.getLogger("com.sahi.test.SahiTest");	

	SahiTest(String scriptName, String startURL, String browser, String sessionId) {
		this.scriptName = scriptName;
		this.startURL = startURL;
		this.browser = browser;
		this.sessionId = sessionId;
	}

	public void execute() {
		randomInt = getRandomInt();
		String url = addSessionId(getURL());
		process = openURL(url);
	}

	String getURL() {
		String cmd = null;
		try {
			cmd = "http://www.sahidomain.com/_s_/dyn/Player_auto?file=" + URLEncoder.encode(scriptName, "UTF8") + "&startUrl="
			+ URLEncoder.encode(startURL, "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.fine("cmd=" + cmd);
		return cmd;
	}

	private int getRandomInt() {
		return (int) (10000 * Math.random());
	}

	private String addSessionId(String url) {
		return url + "&sahisid=" + sessionId + "sahix" + randomInt + "x";
	}


	private Process openURL(String url) {
		String cmd = escapeCommandStringForPlatform(url);
		logger.fine("cmd=" + cmd);
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return process;
	}

	private String escapeCommandStringForPlatform(String url) {
		if (isWindows())
			return "\"" + browser + "\" \"" + url + "\"";
		else
			return browser.replaceAll("[ ]+", "\\ ")
			    + " " + url.replaceAll("&", "\\&");
	}

	private boolean isWindows() {
		return System.getProperty("os.name").toLowerCase()
		    .startsWith("windows");
	}

	public void stop() {
		logger.fine("Killing " + scriptName);		
		if (process != null) {
//			if (isFirefox() && isWindows()) {
//				try {
//					Runtime.getRuntime().exec("taskkill /PID "+process.toString());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
			process.destroy();		
		}
	}

//	private boolean isFirefox() {
//		return browser.indexOf("firefox")!=-1;
//	}
}