package edu.android.podcast_listener.rss;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.sax.*;
import android.util.Log;
import android.util.Xml;

public class RSSHandler extends DefaultHandler {

	private Channel channel;
	private Items items;
	private Item item;
	
	public RSSHandler() {
		items = new Items();
	}
	
	public Channel parse(InputStream input) {
		RootElement root = new RootElement("rss");
		Element channelElement = root.getChild("channel");
		Element channelTitle = root.getChild("title");
		Element channelLink = root.getChild("link");
		Element channelDesc = root.getChild("description");
		Element channelLanguage = root.getChild("language");
		
		Element channelItem = channelElement.getChild("item");
		Element itemTitle = channelItem.getChild("title");
		Element itemDesc = channelItem.getChild("description");
		Element itemLink = channelItem.getChild("guid");
		
		channelElement.setStartElementListener(new StartElementListener() {
			public void start(Attributes attrbutes) {
				channel = new Channel();
			}
		});
		
		channelTitle.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                channel.setTitle(body);
            }
        });
		
		channelLink.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                channel.setLink(body);
            }
        });
		
		channelDesc.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                channel.setDescription(body);
            }
        });
		
		channelLanguage.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                channel.setLanguage(body);
            }
        });
		
		channelItem.setStartElementListener(new StartElementListener() {
            public void start(Attributes attributes) {
                item = new Item();
            }
        });
		
		channelItem.setEndElementListener(new EndElementListener() {
            public void end() {
                items.add(item);
            }
        });
		
		itemTitle.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                item.setTitle(body);
            }
        });
		
		itemDesc.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                item.setDescription(body);
            }
        });
		
		itemLink.setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                item.setGuid(body);
            }
        });

		try {
            Xml.parse(input, Xml.Encoding.UTF_8, root.getContentHandler());
            return channel;
        } catch (SAXException e) {
        	Log.e("RSSHandler", "An issue occured while parsing XML: " + e.getMessage());
        } catch (IOException e) {
        	Log.e("RSSHandler", "An Issue occured with the input stream: " + e.getMessage());
        }
		
		return null;
	}
	
	public Channel getParsedChannelData() {
		return this.channel;
	}
}
