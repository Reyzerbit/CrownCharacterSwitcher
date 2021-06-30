package com.reyzerbit.CrownCharacterSwitcher;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.reyzerbit.CrownCharacterSwitcher.core.events.ChangeCharacterListener;
import com.reyzerbit.CrownCharacterSwitcher.core.io.Load;
import com.reyzerbit.CrownCharacterSwitcher.core.io.Save;
import com.reyzerbit.CrownCharacterSwitcher.core.structures.InventoryStorage;
import com.reyzerbit.CrownCore.core.structures.CrownCharacter;

import net.milkbowl.vault.economy.Economy;

public class CCS extends JavaPlugin {
	
	//Playerdata
	public static Map<CrownCharacter, InventoryStorage> inventoryMap;
	public static Map<CrownCharacter, Double> balanceMap;
	
	//Vault economy
	public static Economy econ;
	
	//Configurations
	public static FileConfiguration config;
	public static  File configFile;
	public static File playerDataDir;
		
	//ConfigVals
	public static boolean pluginEnabled;
	
	// Valid CrownCore version
	public static boolean upToDate = true;

	@Override
	public void onEnable() {
		
		//congif.yml
    	configFile = new File(this.getDataFolder(), "config.yml");

    	//Generate files if missing
    	if(!configFile.exists()) {

    		saveResource("config.yml", false);
    		
    	}
    	
    	config = this.getConfig();
    	
    	playerDataDir = new File(this.getDataFolder(), "playerdata");
    	
    	pluginEnabled = config.getBoolean("enabled");
    	
    	inventoryMap = new HashMap<CrownCharacter, InventoryStorage>();
    	balanceMap = new HashMap<CrownCharacter, Double>();
    	
    	if(!pluginEnabled) {
    		
    		getLogger().log(Level.INFO, "Enabled value in config is set to false, disabling.", getDescription().getPrefix());
    		this.setEnabled(false);
    		return;
    		
    	}
    	
    	if(!playerDataDir.exists()) {

    		playerDataDir.mkdirs();
    		try {
				playerDataDir.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    	}
    	
    	if(getServer().getPluginManager().getPlugin("CrownCore") == null) {
    			
			getServer().getLogger().log(Level.SEVERE, "Unable to detect CrownCore! Disabling CrownCharacterSwitcher.");
    		getServer().getPluginManager().disablePlugin(this);
    		return;
    		
    	}
    	
    	double crownCoreVersion = Double.parseDouble(Bukkit.getPluginManager().getPlugin("CrownCore").getDescription().getVersion().substring(2));
    	
    	if(crownCoreVersion < 8.0) {
    		
    		upToDate = false;
    		
    		getServer().getLogger().log(Level.SEVERE, "[CCS] Detected CrownCore version 1." + crownCoreVersion + ", which is too old! Upgrade to version 1.8.0 or later! Disabling CrownCharacterSwitcher.");
    		getServer().getPluginManager().disablePlugin(this);
    		return;
    		
		}
    	
    	if (!setupEconomy()) {
    		
            Bukkit.getLogger().severe(String.format("[%s] Disabling due to no Vault dependency found!", getDescription().getPrefix()));
            getServer().getPluginManager().disablePlugin(this);
            return;
            
        }
    	
    	Load.load();
    	
    	getServer().getPluginManager().registerEvents(new ChangeCharacterListener(), this);
		
	}
	
	@Override
	public void onDisable() {
		
		if(pluginEnabled && upToDate) Save.save();
		
	}
	
	//Reload plugin
	public static void reload() {
		
		Save.save();
		
		config = YamlConfiguration.loadConfiguration(configFile);
		
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
	
}
