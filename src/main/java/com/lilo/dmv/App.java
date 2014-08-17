package com.lilo.dmv;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.lilo.dmv.core.Film;
import com.lilo.dmv.core.FilmPattern;
import com.lilo.dmv.util.Handler;
import com.lilo.dmv.util.Parsing;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
    	//testParsing();
    	
    	runDownload();
    }
    
    
    public static void testParsing() {
    	String pOrgTxt 
    	//= "http://k.youku.com/player/getFlvPath/sid/540785733914512002d6b_00/st/flv/fileid/0300020600537A2E6F3FE803B99CC8FBE8B76C-BDCB-3997-6ED1-0309E13BC40C?K=e9d57bc7fa65b5b3261dd850&amp;ctype=12&amp;ev=1&amp;oip=1887191773&amp;token=7394&amp;ep=dyaUHUGIUc0D7CLbjT8bZyzgIHFdXP4J9h%2BFg9JkALshS%2B%2FOm0vXsZ7GO%2FdCFYsQe1F1GJ2D3tiVbUZnYYI32hoQ3EehPvrmi4Ti5a1QxJl1ZxpEAcnXx1SZQDn1";
    	//"http://k.youku.com/player/getFlvPath/sid/540785733914512002d6b_00/st/flv/fileid/0300020600537A2E6F3FE803B99CC8FBE8B76C-BDCB-3997-6ED1-0309E13BC40C?K=e9d57bc7fa65b5b3261dd850&amp;ctype=12&amp;ev=1&amp;oip=1887191773&amp;token=7394&amp;ep=dyaUHUGIUc0D7CLbjT8bZyzgIHFdXP4J9h%2BFg9JkALshS%2B%2FOm0vXsZ7GO%2FdCFYsQe1F1GJ2D3tiVbUZnYYI32hoQ3EehPvrmi4Ti5a1QxJl1ZxpEAcnXx1SZQDn1";
    	= "http://k.youku.com/player/getFlvPath/sid/1407857361907122d9ac6_00/st/flv/fileid/0300020800530F0A676A6A032DBBC752EA802C-6570-BB55-5E24-07F21402B8C3?K=5312bd311ce5aaf22411e837&ctype=12&ev=1&oip=2043096855&token=5090&ep=cyaUHUGIUc0G5Crfjz8bZXjrJSQJXP4J9h%2BFg9JqALshS%2BjJmU%2FXwJvBSI5CFfttAFB1F%2B7z3qHvakJnYfZGrmgQrTytPPrli%2FLn5a1UspIBYhk0AMWkt1SbQznx";
    	String pPattern 
    	//= "http://k.youku.com/player/getFlvPath/sid/[0-9A-Za-z\\-]+_[0-9]+/st/flv/fileid/[0-9A-Z\\-]+\\?K=[0-9a-zA-Z;?=&%]+"; //FLVCD_PATTERN
    	= FilmPattern.FILE_TO_DOWNLOAD_PATTERN_FLVCD;
    	
    	List<String> list = Parsing.getURLs(pOrgTxt, pPattern);
    	
    	if (list.isEmpty()) System.out.println("nothing");
    	for (String link:list) {
    		System.out.println("-" + link);
    	}
    	
    	System.out.println("done.");
    	
    }
    
    public static void runDownload() {
    	boolean isUnix = true;
    	//String title = "天上女子";
    	String title = "你是谁";

		int type = 1;

		String theName = "whoareyou"; //"angle";
		String savePath = "/Users/linhhong/Downloads/temp";
		
		String onlineLinkFilename = savePath + File.separator + theName + "_online.txt";
		String episodeSuperListFilename = savePath + File.separator + theName + "_superlist";
		String runableFilename = savePath + File.separator + theName + "_run.bat";		
		
		int cmd = 1;
		
		int startIndex = 1;
		int endIndex = 1; // 0 = get to the last episode
		
		try {
			Film film = new Film(title,type);
		
			boolean flag = false;
			String outputFile = "";
			switch (cmd) {
				case 1:					
					System.out.println("- Create Runnable file ... ");
					File inputFile = new File(onlineLinkFilename);
					if (inputFile.exists()) {
						film.createEpisodesUseTuDouFile(onlineLinkFilename);
					}else {
						System.out.println("You should run cmd=2 first to create online Link filename & check if they are correct.");
						System.out.println("Anyway we will try to create from directly searching.");
						System.out.println("Search link of episodes: " + film.createEpisodesUseTuDouSearch());
					}
					film.reduceEpisodeList(startIndex, endIndex);
					flag = Handler.toRunnableFile(film, runableFilename, savePath, FilmPattern.DOWNLOAD_CMD_WGET, theName, isUnix);
					outputFile = runableFilename;
					System.out.println("Done.");
					break;
				case 2:
					System.out.println("- Create Online link of episodes ... ");
					film.createEpisodesUseTuDouSearch();
					flag = Handler.toEpisodeOnlineLinkFile(film, onlineLinkFilename);
					outputFile = onlineLinkFilename;
					System.out.println("Done. Please re-check manually with the number of episodes to make sure the number of online links is appropriate with the number of episodes.");
					break;
				case 3:
					System.out.println("- Create Download links of Episodes ... ");
					film.reduceEpisodeList(startIndex, endIndex);
					flag = Handler.toEpisodeDowndoadLinkFile(film, episodeSuperListFilename);
					outputFile = episodeSuperListFilename;
					System.out.println("Done.");
					break;
			}
			
			if (flag) {
				System.out.println("Successful create outputFile: " + outputFile);
			}else {
				System.out.println("Fail create outputFile: " + outputFile);
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (URISyntaxException eu) {
			System.out.println("Error: " + eu.getMessage());
		}
		
    }
}
