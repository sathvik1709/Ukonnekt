package com.seteam3.events;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class EventsReader {
	
	private String rssUrl;
	public EventsReader(String rssUrl) {
		this.rssUrl = rssUrl;
	}

	public List<EventsItem> getItems() throws Exception {
		
		// SAX parse RSS data
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();

		EventsParseHandler handler = new EventsParseHandler();
		
		saxParser.parse(rssUrl, handler);

		return handler.getItems();		
	}
}
