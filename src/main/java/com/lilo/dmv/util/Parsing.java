package com.lilo.dmv.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lilo.dmv.core.Episode;
import com.lilo.dmv.core.TudouEpisode;


public class Parsing {
	
	/*
	 * Get the first match string
	 */
	public static String getURL(String pOrgTxt, String pPattern) {		
		
		Pattern pattern = Pattern.compile(pPattern);
		Matcher matcher = pattern.matcher(pOrgTxt);		
		
		if (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			return pOrgTxt.substring(start, end);
		}
		return null;
	}
	
	/*
	 * Get all match strings
	 */
	public static List<String> getURLs(String pOrgTxt, String pPattern) {
		List<String> list = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile(pPattern);
		Matcher matcher = pattern.matcher(pOrgTxt);		
		
		while (matcher.find()) {
			
			int start = matcher.start();
			int end = matcher.end();
			list.add(pOrgTxt.substring(start,end));
			
		}
		return list;
	}

	/*
	 * Get list of episode links from a search result page of tudow.com website
	 * TODO: Need to improve the algorithm, find out the way to get exactly the list
	 */	
	public static List<Episode> getListEpisodes(URL searchURL, String pattern) throws IOException, URISyntaxException {
		HttpURLConnection httpConnect = (HttpURLConnection)searchURL.openConnection();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(httpConnect.getInputStream()));
		List<Episode> listOfEpisode = getListEpisodes(in, pattern);
		in.close();
		
		return listOfEpisode;
	}

	/*
	 * Get list of episode links from a file which is created by manually copy & paste from tudou.com website
	 */
	public static List<Episode> getListEpisodes(String textFile, String pattern) throws IOException, URISyntaxException {
		
		BufferedReader in = new BufferedReader(new FileReader(textFile));
		List<Episode> listOfEpisode = getListEpisodes(in, pattern);
		in.close();
		
		return listOfEpisode;
	}
	
	
	private static List<Episode> getListEpisodes(BufferedReader in, String pPattern) throws IOException, URISyntaxException {
		
		List<Episode> listOfEpisode = new ArrayList<Episode>();
		
		String line;	
		int index = 0;
		
		while ((line = in.readLine()) != null) {
			
			List<String> episodeLinks = getURLs(line, pPattern);
			if (episodeLinks != null && !episodeLinks.isEmpty()) {	
				
				for (String link:episodeLinks) {
					if (existedOnlineLinkOfEpisode(listOfEpisode, link)) {
						break;
					}
					
					index++;
					Episode ep = new TudouEpisode(index, link);					
					listOfEpisode.add(ep);
							
				}
			}
		}
		
		return listOfEpisode;
	}	
	
	private static boolean existedOnlineLinkOfEpisode(List<Episode> listE, String link) {
		boolean flag = false;
		for (Episode ep:listE) {
			if (link != null && link.equals(ep.getOnlineLink())) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}

	/*
	 * Convert special character before inserting into query
	 * : ~ %3A
	 * / ~ %2F 
	 */
	public static String convertLinkToQuery(String pOrgTxt) {
		String s = pOrgTxt.replaceAll("\\:", "%3A");
		s = s.replaceAll("\\/", "%2F");
		return s;
	}	

	/*
	 * Query the normal link, search on the page to get super link
	 */
	public static String getSuperLink(URL pLink, String pPattern) throws IOException {
		String superLink = null;
		
		HttpURLConnection httpConnect = (HttpURLConnection)pLink.openConnection();
		
		BufferedReader in = new BufferedReader(
								new InputStreamReader(httpConnect.getInputStream()));
		String line;
		
		while ((line = in.readLine()) != null) {
			superLink = getURL(line, pPattern);
			if (superLink != null) {
				break;
			}
		}	
		in.close();
		
		return superLink;		
	}	

	/*
	 * Get list of link of sub files of one episode/a movie (flv) to download 
	 */
	public static List<String> getListToDownload(URL parseLink, String pattern) throws IOException {
		List<String> downloadLinks = new ArrayList<String>();
		
		HttpURLConnection httpConnect = (HttpURLConnection)parseLink.openConnection();
		
		BufferedReader in = new BufferedReader(
								new InputStreamReader(httpConnect.getInputStream()));
		String line;

		while ((line = in.readLine()) != null) {
			List<String> links = getURLs(line, pattern);
			if (links != null && !links.isEmpty()) {

				for (String link:links) {
					if (downloadLinks.contains(link)) {
						break;
					}					
					downloadLinks.add(link);
				}

			}
		}	
		in.close();
		
		return downloadLinks;
	}	
	

}
