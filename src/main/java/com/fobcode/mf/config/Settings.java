package com.fobcode.mf.config;

public class Settings extends SimpleConfig{
	private Settings(String fileName) {
		super(fileName);
	}
	
	public static String NOTENOUGHMONEY;
	public static String PLAYERDOESNTEXIST;
	public static String GENERALERROR;
	public static String NOPERMISSION;
	public static String NOUSER;
	public static String NOFINEAMOUNT;
	public static String NOREASON;
	public static String FINEDMESSAGE;
	public static String FINESUCCESS;
	public static String RELOADCONFIG;
	
	private void onLoad() {

		NOTENOUGHMONEY = getString("notenoughmoney");
		PLAYERDOESNTEXIST = getString("playerdoesntexist");
		GENERALERROR = getString("generalerror");
		NOPERMISSION = getString("nopermission");
		NOUSER = getString("nouser");
		NOFINEAMOUNT = getString("nofineamount");
		NOREASON = getString("noreason");
		FINEDMESSAGE = getString("finedmessage");
		FINESUCCESS = getString("finesuccess");
		RELOADCONFIG = getString("reloadconfig");
	}
	public static void init() {
		new Settings("settings.yml").onLoad();
	}
}