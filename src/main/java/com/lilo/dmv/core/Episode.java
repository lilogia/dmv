package com.lilo.dmv.core;

import java.util.List;

public abstract class Episode {
	private int index; // The order of episode
	private String onlineLink; // see the film online link
	List<String> downloadLinks; // this can be multi-links to download multi parts or only one link

	public Episode(int index, String onlineLink) {
		this.index = index;
		this.onlineLink = onlineLink;		
	}
	
	public abstract boolean createDownloadLinks();
	

	public int getIndex() {
		return index;
	}	
	
	public String getOnlineLink() {
		return onlineLink;
	}
	
	public List<String> getDownloadLinks() {
		return downloadLinks;
	}
	
	public void addDownloadLink(String downloadLink) {
		downloadLinks.add(downloadLink);
	}
	public void setDownloadLinks(List<String> downloadLinks) {
		this.downloadLinks = downloadLinks;
	}
	
	public String toString() {
		String s = "Episode " + index + "," + onlineLink + ":";
		for (String link : downloadLinks) {
			s += "\n-- " + link; 
		}
		return s;
	}
}
