package com.fobcode.mf.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fobcode.mf.MemeFine;
import com.fobcode.mf.config.Settings;
import com.fobcode.mf.utils.Common;

public class Fine implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
			if(!(sender.hasPermission("memefine.fine"))) {
				Common.tell(sender, Settings.NOPERMISSION);
				return true;
			}

			if(args.length <= 0) {
				Common.tell(sender, Settings.NOUSER);
				return true;
			}
			if(args.length < 2) {
				Common.tell(sender, Settings.NOFINEAMOUNT);
				return true;
			}
			if(args.length < 3) {
				Common.tell(sender, Settings.NOREASON);
				return true;
			}
			Player p = Bukkit.getServer().getPlayerExact(args[0]);
			if(p == null) {
				Common.tell(sender, Settings.PLAYERDOESNTEXIST);
				return true;
			}
			double fine = Double.parseDouble(args[1]);
			double bal = MemeFine.getEconomy().getBalance(p);
			if(fine > bal) {
				Common.tell(sender, Settings.NOTENOUGHMONEY);
				return true;
			}
			String reason = "";
			for(int i =2; i< args.length; i++) {
				reason += " " + args[i];
			}
			MemeFine.getEconomy().withdrawPlayer(p, fine);
			Common.tell(p, replaceConfig(Settings.FINEDMESSAGE, fine, p.getName(), reason, sender.getName()));
			Common.tell(sender, replaceConfig(Settings.FINESUCCESS, fine, p.getName(), reason, sender.getName()));
			return true;


		}

	
	
	private String replaceConfig(String s, double fine, String p, String reason, String punisher) {

		s = s.replaceAll("%amount%", fine+"");
		s = s.replaceAll("%player%", p);
		s = s.replaceAll("%reason%", reason);
		s = s.replaceAll("%punisher%", punisher);
		s = s.replaceAll("%units%", MemeFine.getEconomy().currencyNamePlural());
		s = s.replaceAll("  ", " ");
		return s;
	}
}
