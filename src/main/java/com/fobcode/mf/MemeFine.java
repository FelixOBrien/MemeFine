package com.fobcode.mf;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import com.fobcode.mf.commands.Fine;
import com.fobcode.mf.config.Settings;
import net.milkbowl.vault.economy.Economy;


public class MemeFine extends JavaPlugin{
	public static Economy econ = null;
	private static MemeFine instance;
	public void onEnable() {
		instance = this;
		if (!setupEconomy() ) {
			getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		Settings.init();
		this.getCommand("fine").setExecutor(new Fine());
		this.getCommand("mfreload").setExecutor(new Fine());
	}
	public void onDisable() {
		instance = null;
	}
	public static MemeFine getInstance() {
		return instance;
	}

	public boolean setupEconomy() {
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


}
