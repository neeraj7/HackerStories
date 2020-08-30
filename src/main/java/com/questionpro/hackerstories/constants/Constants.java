package com.questionpro.hackerstories.constants;

public class Constants {

	public static final String BASE_URL = "https://hacker-news.firebaseio.com/";

	public static final String V0 = "v0";

	public static final String ITEM = "item";

	public static final String SLASH = "/";

	public static final String ITEM_URL = BASE_URL + V0 + SLASH + ITEM + SLASH;
	
	public static final String USER = "user";
	
	public static final String USER_URL = BASE_URL + V0 + SLASH + USER + SLASH;
	
	public static final String DOT_JSON = ".json";
	
	public static final String BEST_STORIES = "beststories";
	
	public static final String BEST_STORIES_URL = BASE_URL + V0 + SLASH + BEST_STORIES + DOT_JSON ;

}
