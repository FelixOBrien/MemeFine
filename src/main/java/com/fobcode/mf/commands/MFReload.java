package com.fobcode.mf.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.fobcode.mf.config.Settings;
import com.fobcode.mf.utils.Common;

public class MFReload implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
			if(!(sender.hasPermission("memefine.reload"))) {
				Common.tell(sender, Settings.NOPERMISSION);
				return true;
			}

			Settings.init();
			Common.tell(sender, Settings.RELOADCONFIG);
			return true;
		
	
	}
}
