package net.sf.sahi.util;

import net.sf.sahi.util.URLParser;

import junit.framework.TestCase;

public class URLParserTest extends TestCase {
	final String uri = "/_s_/dyn/Log_highlight/sahi_demo_include.sah?n=2";

	public void testScriptFileNamefromURI() {
		assertEquals("../scripts/sahi_demo_include.sah", URLParser.scriptFileNamefromURI(uri, "/Log_highlight/"));
	}

	public void testScriptFileNamefromURI2() {
		final String uri2 = "/_s_/dyn/scripts/sahi_demo_include.sah?n=2";
		assertEquals("../scripts/sahi_demo_include.sah", URLParser.scriptFileNamefromURI(uri2, "/scripts/"));
	}
	
	public void testLogFileNamefromURI() {
		assertEquals("", URLParser.logFileNamefromURI("/_s_/logs/"));		
		assertEquals("", URLParser.logFileNamefromURI("/_s_/logs"));		
	}	
	
	public void testGetCommandFromUri() {
		assertEquals("Player_currentParsedScript", URLParser.getCommandFromUri("http://www.google.co.in/_s_/dyn/Player_currentParsedScript"));		
		assertEquals("Player_currentParsedScript", URLParser.getCommandFromUri("http://www.google.co.in/_s_/dyn/Player_currentParsedScript?a=b"));		
		assertEquals("Player_currentParsedScript", URLParser.getCommandFromUri("http://www.google.co.in/_s_/dyn/Player_currentParsedScript/xa/b"));		
		assertEquals("Player_currentParsedScript", URLParser.getCommandFromUri("http://www.google.co.in/_s_/dyn/Player_currentParsedScript/"));		
	}

}