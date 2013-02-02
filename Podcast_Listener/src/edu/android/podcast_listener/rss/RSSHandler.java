package edu.android.podcast_listener.rss;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.util.Log;
import android.util.Xml;

public class RSSHandler extends DefaultHandler {
	String elementValue = null;
    Boolean elementOn = false;
	private Channel channel;
	private Items items;
	private Item item;
	
	public RSSHandler() {
		items = new Items();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
		if (localName.equalsIgnoreCase("channel")) {
			
		} else if (localName.equalsIgnoreCase("item")) {
			
		}
	}
	
	@Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
		elementOn = false;
		
		if (localName.equalsIgnoreCase("title")) {
			
		}
	}
	
	@Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (elementOn) {
            elementValue = new String(ch, start, length);
            elementOn = false;
        }
    }
	
	public Channel parse(InputStream input) {
//		RootElement root = new RootElement("rss");
//		Element channelElement = root.getChild("channel");
//		Element channelTitle = root.getChild("title");
//		Element channelLink = root.getChild("link");
//		Element channelDesc = root.getChild("description");
//		Element channelLanguage = root.getChild("language");
//		
//		Element channelItem = channelElement.getChild("item");
//		Element itemTitle = channelItem.getChild("title");
//		Element itemDesc = channelItem.getChild("description");
//		Element itemLink = channelItem.getChild("guid");
		
		return null;
	}
	
	public Channel getParsedChannelData() {
		return this.channel;
	}
}
