package com.fobcode.mf;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.fobcode.mf.config.Settings;
import com.fobcode.mf.utils.Common;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class MemeFine extends JavaPlugin{
	private static Economy econ = null;
	private static MemeFine instance;
	public void onEnable() {
		instance = this;
		if (!setupEconomy() ) {
			getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		Settings.init();
	}
	public void onDisable() {
		instance = null;
	}
	public static MemeFine getInstance() {
		return instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("fine")) {
			if(sender.hasPermission("memefine.fine")) {
				if(args.length > 0) {
					Player p = (Player) Bukkit.getServer().getPlayerExact(args[0]);
					if(args.length > 1) {
						double fine = Double.parseDouble(args[1]);
						if(args.length > 2) {
							String reason = "";
							for(int i =2; i< args.length; i++) {
								reason += " " + args[i];
							}
							if(p !=null) {
								double bal = econ.getBalance(p);
								if(fine > bal) {
									Common.tell(sender, Settings.NOTENOUGHMONEY);
									return true;
								}
								econ.withdrawPlayer(p, fine);
								Common.tell(p, replaceConfig(Settings.FINEDMESSAGE, fine, p.getName(), reason, sender.getName()));
								Common.tell(sender, replaceConfig(Settings.FINESUCCESS, fine, p.getName(), reason, sender.getName()));
								return true;
							}
							Common.tell(sender, Settings.PLAYERDOESNTEXIST);

							return true;
						}
						Common.tell(sender, Settings.NOREASON);
						return true;

					}
					Common.tell(sender, Settings.NOFINEAMOUNT);
					return true;
				}
				Common.tell(sender, Settings.NOUSER);
				return true;
			}
			Common.tell(sender, Settings.NOPERMISSION);

			return true;
		}
		return false;
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	public static Economy getEconomy() {
		return econ;
	}
	public String replaceConfig(String s, double fine, String p, String reason, String punisher) {
		s.replaceAll("%amount%", fine+"");
		s.replaceAll("%player%", p);
		s.replaceAll("%reason%", reason);
		s.replaceAll("%punisher%", punisher);
		s.replaceAll("%units%", econ.currencyNamePlural());
		return s;
	}

}
