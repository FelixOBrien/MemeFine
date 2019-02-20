package com.fobcode.mf.config;

public class Settings extends SimpleConfig{
	private Settings(String fileName) {
		super(fileName);
	}
	
	public static String notEnoughMoney;
	public static String playerDoesntExist;
	public static String generalError;
	public static String noPermission;
	public static String noUser;
	public static String noFineAmount;
	public static String noReason;
	public static String finedMessage;
	public static String fineSuccess;
	public static String reloadConfig;
	
	private void onLoad() {

		notEnoughMoney = getString("notenoughmoney");
		playerDoesntExist = getString("playerdoesntexist");
		generalError = getString("generalerror");
		noPermission = getString("nopermission");
		noUser = getString("nouser");
		noFineAmount = getString("nofineamount");
		noReason = getString("noreason");
		finedMessage = getString("finedmessage");
		fineSuccess = getString("finesuccess");
		reloadConfig = getString("reloadconfig");
	}
	public static void init() {
		new Settings("settings.yml").onLoad();
	}
}