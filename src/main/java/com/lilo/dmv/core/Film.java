package com.lilo.dmv.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.lilo.dmv.util.Parsing;


public class Film {
	
	private String title;
	private int type;
	private List<Episode> episodes;
	
	public Film(String title, int type) {
		this.title = title;
		this.type = type;		
	}

	public String getTitle() {
		return title;
	}

	public int getType() {
		return type;
	}

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void addEpisode(Episode episode) {
		this.episodes.add(episode);
	}
	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}
	
	// Create Episode following multi-methods
	
	public String createEpisodesUseTuDouSearch() throws URISyntaxException, IOException {
		
		String searchLink = String.format(FilmPattern.SEARCH_EPISODE_LINK_PATTERN_TUDOU, title);
		URL searchURL = new URL((new URI(searchLink)).toASCIIString());
		episodes = Parsing.getListEpisodes(searchURL, FilmPattern.EPISODE_LINK_PATTERN_TUDOU);
		return searchURL.toString();
	}
	
	public void createEpisodesUseTuDouFile(String fileIncludeLinks) throws URISyntaxException, IOException {
		episodes = Parsing.getListEpisodes(fileIncludeLinks, FilmPattern.EPISODE_LINK_PATTERN_TUDOU);		
	}	
	
	// More features
	public void reduceEpisodeList(int startIndex, int endIndex) {
		endIndex = (endIndex == 0) ? episodes.size() : endIndex ;
		if (startIndex > 1 || endIndex < episodes.size()) {
			List<Episode> newList = new ArrayList<Episode>();
			for (int i=startIndex-1; i<endIndex; i++) {
				newList.add(episodes.get(i));
			}
			episodes = newList;

		}
		
	}
	
}