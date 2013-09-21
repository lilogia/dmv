package com.lilo.dmv.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.lilo.dmv.util.Parsing;


public class TudouEpisode extends Episode {

	public TudouEpisode(int index, String onlineLink) {
		super(index, onlineLink);
	}

	@Override
	public boolean createDownloadLinks() {	
		boolean isSuccess = false;
		String normalLink = String.format(FilmPattern.SEARCH_TO_DOWNLOAD_PATTERN_NORMAL_FLVCD, Parsing.convertLinkToQuery(super.getOnlineLink()));
		String superLink = null;
		try {
			superLink = Parsing.getSuperLink(new URL(normalLink), FilmPattern.SEARCH_TO_DOWNLOAD_PATTERN_SUPER_FLVCD);

			String parseLink;
			if (superLink != null) {
				parseLink = FilmPattern.SEARCH_TO_DOWNLOAD_SITE_FLVCD + superLink;
			}else {
				parseLink = normalLink;
			}
			
			List<String> downloadLinks = Parsing.getListToDownload(new URL(parseLink), FilmPattern.FILE_TO_DOWNLOAD_PATTERN_FLVCD);
			if (downloadLinks != null && !downloadLinks.isEmpty()) {
				isSuccess = true;
				setDownloadLinks(downloadLinks);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return isSuccess;
		
	}

}
