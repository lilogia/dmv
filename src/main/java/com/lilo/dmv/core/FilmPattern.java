package com.lilo.dmv.core;

public class FilmPattern {

	final public static String SEARCH_EPISODE_LINK_PATTERN_TUDOU = "http://www.soku.com/t/nisearch/%s"; // need to be converted to ASCII before requesting
	final public static String EPISODE_LINK_PATTERN_TUDOU = "http://www.tudou.com/albumplay/[0-9A-Za-z\\-_]+/[0-9A-Za-z\\-_]+.html";

	final public static String SEARCH_TO_DOWNLOAD_SITE_FLVCD = "http://www.flvcd.com/"; //FLVCD_SEARCH_PATTERN_PREFIX
	final public static String SEARCH_TO_DOWNLOAD_PATTERN_NORMAL_FLVCD = "http://www.flvcd.com/parse.php?format=&kw=%s"; //FLVCD_SEARCH_PATTERN_NORMAL
	//	String startLink = "http://www.flvcd.com/parse.php?kw=http://www.tudou.com/albumplay/U1p8GFBVzKo.html&flag=one&format=super"; 
	final public static String SEARCH_TO_DOWNLOAD_PATTERN_SUPER_FLVCD = "parse.php\\?kw=http\\%3A\\%2F\\%2F[0-9A-Za-z\\.\\-\\%_]+.html\\&flag=one\\&format=super"; 
	//FLVCD_SEARCH_PATTERN_SUPER
	// http://www.flvcd.com/parse.php?kw=http%3A%2F%2Fv.youku.com%2Fv_show%2Fid_XNTkwNTEyNjUy.html&flag=one&format=super

	final public static String FILE_TO_DOWNLOAD_PATTERN_FLVCD = "http://f.youku.com/player/getFlvPath/sid/00_00/st/flv/fileid/[0-9A-Z\\-]+\\?K=[0-9a-z]+"; //FLVCD_PATTERN
	final public static String FILENAME_PATTERN = "%1$02d-%2$s_%3$s.flv";	
	final public static String FILENAME_PATTERN_SUFFIX = "%02d";
	
	public static String DOWNLOAD_CMD_IDMAN = "IDMan /n /d \"%1$s\" /p %2$s /f %3$s /q ";
	
	final public static int DRAMA = 1;
	final public static int MOVIE = 2;
}
