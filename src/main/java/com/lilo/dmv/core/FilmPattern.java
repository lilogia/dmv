package com.lilo.dmv.core;

import java.io.File;

public class FilmPattern {

	final public static String SEARCH_EPISODE_LINK_PATTERN_TUDOU = "http://www.soku.com/t/nisearch/%s"; // need to be converted to ASCII before requesting
	final public static String EPISODE_LINK_PATTERN_TUDOU = "http://www.tudou.com/albumplay/[0-9A-Za-z\\-_]+/[0-9A-Za-z\\-_]+.html";

	final public static String SEARCH_TO_DOWNLOAD_SITE_FLVCD = "http://www.flvcd.com/"; //FLVCD_SEARCH_PATTERN_PREFIX
	final public static String SEARCH_TO_DOWNLOAD_PATTERN_NORMAL_FLVCD = "http://www.flvcd.com/parse.php?format=&kw=%s"; //FLVCD_SEARCH_PATTERN_NORMAL
	//	String startLink = "http://www.flvcd.com/parse.php?kw=http://www.tudou.com/albumplay/U1p8GFBVzKo.html&flag=one&format=super"; 
	final public static String SEARCH_TO_DOWNLOAD_PATTERN_SUPER_FLVCD = "parse.php\\?kw=http\\%3A\\%2F\\%2F[0-9A-Za-z\\.\\-\\%_]+.html\\&flag=one\\&format=super"; 
	//FLVCD_SEARCH_PATTERN_SUPER
	// http://www.flvcd.com/parse.php?kw=http%3A%2F%2Fv.youku.com%2Fv_show%2Fid_XNTkwNTEyNjUy.html&flag=one&format=super

	final public static String FILE_TO_DOWNLOAD_PATTERN_FLVCD 
		= "http://k.youku.com/player/getFlvPath/sid/[0-9A-Za-z\\-]+_[0-9]+/st/flv/fileid/[0-9A-Z\\-]+\\?K=[0-9a-zA-Z;?=&%]+"; //FLVCD_PATTERN
		// before 17-Aug-2014 "http://f.youku.com/player/getFlvPath/sid/00_00/st/flv/fileid/[0-9A-Z\\-]+\\?K=[0-9a-z]+"; //FLVCD_PATTERN
	
	/* sample at 17-Aug-2014
	 * http://k.youku.com/player/getFlvPath/sid/540785733914512002d6b_00/st/flv/fileid/0300020600537A2E6F3FE803B99CC8FBE8B76C-BDCB-3997-6ED1-0309E13BC40C?K=e9d57bc7fa65b5b3261dd850&amp;ctype=12&amp;ev=1&amp;oip=1887191773&amp;token=7394&amp;ep=dyaUHUGIUc0D7CLbjT8bZyzgIHFdXP4J9h%2BFg9JkALshS%2B%2FOm0vXsZ7GO%2FdCFYsQe1F1GJ2D3tiVbUZnYYI32hoQ3EehPvrmi4Ti5a1QxJl1ZxpEAcnXx1SZQDn1
	 * http://k.youku.com/player/getFlvPath/sid/540785733914512002d6b_01/st/flv/fileid/0300020601537A2E6F3FE803B99CC8FBE8B76C-BDCB-3997-6ED1-0309E13BC40C?K=1cd2b076054fbcd82411e830&amp;ctype=12&amp;ev=1&amp;oip=1887191773&amp;token=7394&amp;ep=dyaUHUGIUc0D7CLbjT8bZyzgIHFdXP4J9h%2BFg9JkALohS%2B%2FOm0vXsZ7GO%2FdCFYsQe1F1GJ2D3tiVbUZnYYI32hoQ3EehPvrmi4Ti5a1QxJl1ZxpEAcnXx1SZQDn1
	 */
	
	final public static String FILENAME_PATTERN = "%1$03d-%2$s_%3$02d.flv";	
	final public static String FILENAME_PATTERN_SUFFIX = "%02d";
	
	public static String DOWNLOAD_CMD_IDMAN = "IDMan /n /d \"%1$s\" /p %2$s /f %3$s /q ";
	public static String DOWNLOAD_CMD_WGET = "wget -U firefox -O \"%2$s" + File.separatorChar + "%3$s %1$s\""; 
	
	final public static int DRAMA = 1;
	final public static int MOVIE = 2;
}
