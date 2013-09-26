package com.lilo.dmv;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import com.lilo.dmv.core.Film;
import com.lilo.dmv.core.FilmPattern;
import com.lilo.dmv.util.Handler;



public class DownloadMovie {

	public static void main(String[] args) {
		
		String inputProperties = args[0];
		if (inputProperties == null || inputProperties.equals("")) {
			showError();
		}
		
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(inputProperties));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(prop.toString());
		
		String title = prop.getProperty("TITLE");
		int type = Integer.parseInt(prop.getProperty("TYPE", "1"));
		String inputFilename = prop.getProperty("INPUT_FILENAME");
		String outputFilename = prop.getProperty("OUTPUT_FILENAME");
		String savePath = prop.getProperty("SAVE_PATH");
		int cmd = Integer.parseInt(prop.getProperty("CMD", "2"));
		String localSaveFile = "";
		if (cmd == 1) localSaveFile = prop.getProperty("FILENAME_PATTERN", "unknown");
		int startIndex = Integer.parseInt(prop.getProperty("START_INDEX", "1"));
		int endIndex = Integer.parseInt(prop.getProperty("END_INDEX", "0")); // 0 = get to the last episode
		
		String os = System.getProperty("os.name");
		boolean isUnix = true;
		if (os.startsWith("Windows")) isUnix = false;
		
		System.out.println("Start to create Film info ... " + startIndex + "," + endIndex );

		try {
			Film film = new Film(title,type);
			if (inputFilename != null && !inputFilename.equals("")) {
				film.createEpisodesUseTuDouFile(inputFilename);
			}else {
				System.out.println("Search link of episodes: " + film.createEpisodesUseTuDouSearch());
			}
		
			boolean flag = false;
			switch (cmd) {
				case 1:					
					System.out.println("- Create Runnable file ... ");
					film.reduceEpisodeList(startIndex, endIndex);
					flag = Handler.toRunnableFile(film, outputFilename, savePath, FilmPattern.DOWNLOAD_CMD_WGET, localSaveFile, isUnix);
					System.out.println("Done.");
					break;
				case 2:
					System.out.println("- Create Online link of episodes ... ");
					flag = Handler.toEpisodeOnlineLinkFile(film, outputFilename);
					System.out.println("Done. Please re-check manually with the number of episodes to make sure the number of online links is appropriate with the number of episodes.");
					break;
				case 3:
					System.out.println("- Create Download links of Episodes ... ");
					film.reduceEpisodeList(startIndex, endIndex);
					flag = Handler.toEpisodeDowndoadLinkFile(film, outputFilename);
					System.out.println("Done.");
					break;
			}
			
			if (flag) {
				System.out.println("Successful create outputFile: " + outputFilename);
			}else {
				System.out.println("Fail create outputFile: " + outputFilename);
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (URISyntaxException eu) {
			System.out.println("Error: " + eu.getMessage());
		}
		
	}
	
	public static void showError() {
		System.out.println("Missing Properties filename.");
	}
	
	public static void showErrorProperties() {
		System.out.println("Wrong properties !");
		System.out.println("See following sample values in properties file: ");
		System.out.println(
			"#TITLE=屋塔房王世子" + "\n" +
			"TITLE=\u5C4B\u5854\u623F\u738B\u4E16\u5B50" + "\n" +
			"# 1: Drama, 2: Movie" + "\n" +
			"TYPE=1" + "\n" +
			"SAVE_PATH=/Users/linhhong/Desktop" + "\n" +
			"OUTPUT_FILENAME=download.bat" + "\n" +
			"# List get from running CMD=2 below" + "\n" +
			"INPUT_FILENAME=rawlink.txt" + "\n" +
			"# 1: Get list of download file, 2: Episode raw list link, 3: Episode super list" + "\n" +
			"CMD=1" + "\n");
		System.exit(0);
	}

}
