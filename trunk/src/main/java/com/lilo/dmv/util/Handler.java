package com.lilo.dmv.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.lilo.dmv.core.Episode;
import com.lilo.dmv.core.Film;
import com.lilo.dmv.core.FilmPattern;


public class Handler {
	final static String line_separator = System.getProperty("line.separator");
	
	public static boolean toRunnableFile(Film film, String outputFile, String savePath, String patternCmd, String partShortName, boolean isUnix) {
		boolean isSuccess = false;		
		
		FileWriter out;
		try {
			out = new FileWriter(outputFile);
			List<Episode> episodes = film.getEpisodes();
			
			for (Episode ep : episodes) {
				if (ep.getDownloadLinks() == null) {
					if (!ep.createDownloadLinks()) {
						break;
					}
				}
				
				out.write(line_separator);
				if (isUnix) {
					out.write("# ");
				} else { //isWindows
					out.write("REM "); 
				}				
				out.write("Episode " + ep.getIndex() + "|| " + ep.getOnlineLink());
			
				out.write(line_separator);
				
				List<String> downloadLinks = ep.getDownloadLinks();
			
				int partIndex = 0;
				for (String link : downloadLinks) {
					partIndex ++;
					String partFilename = String.format(FilmPattern.FILENAME_PATTERN, ep.getIndex(), partShortName, partIndex);
					out.write(String.format(patternCmd, link, savePath, partFilename));
					out.write(line_separator);
				}
			}
			out.close();
			isSuccess = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}

	public static boolean toEpisodeOnlineLinkFile(Film film, String outputFile) {
		boolean isSuccess = false;
		
		FileWriter out;
		try {
			out = new FileWriter(outputFile);
			List<Episode> episodes = film.getEpisodes();	
			for (Episode ep : episodes) {												
				out.write(ep.getOnlineLink());
				out.write(line_separator);
			}
			out.close();
			isSuccess = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	public static boolean toEpisodeDowndoadLinkFile(Film film, String outputFile) {
		boolean isSuccess = false;
		
		FileWriter out;
		try {
			out = new FileWriter(outputFile);
			List<Episode> episodes = film.getEpisodes();
				
			for (Episode ep : episodes) {	
				if (ep.getDownloadLinks() == null) {
					System.out.print("--- Create Download Links for episode #" + ep.getIndex() + " ... ");
					if (!ep.createDownloadLinks()) {
						break;
					}
					System.out.println("OK.Done.");
				}
				out.write(ep.toString());
				out.write(line_separator);
			}
			out.close();
			isSuccess = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}
}
