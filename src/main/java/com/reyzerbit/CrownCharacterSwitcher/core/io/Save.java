package com.reyzerbit.CrownCharacterSwitcher.core.io;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.reyzerbit.CrownCharacterSwitcher.CCS;
import com.reyzerbit.CrownCharacterSwitcher.core.SwapLogic;
import com.reyzerbit.CrownCharacterSwitcher.core.structures.InventoryStorage;
import com.reyzerbit.CrownCore.CrownCore;
import com.reyzerbit.CrownCore.core.structures.CrownCharacter;
import com.reyzerbit.CrownCore.core.structures.CrownPlayer;

public class Save {

	public static void save() {
		
		Bukkit.getLogger().log(Level.INFO, "[CCS] Saving data!");
		
		for(Map.Entry<UUID, CrownPlayer> entry : CrownCore.playerData.entrySet()) {
			
			File playerSave = new File(CCS.playerDataDir, entry.getKey().toString() + ".yml");
			
			if(playerSave.exists()) {
				
				saveLogic(playerSave, entry);
				
			} else {
				
				try {
					
					playerSave.getParentFile().mkdirs();
					playerSave.createNewFile();
					
					saveLogic(playerSave, entry);
					
				} catch (IOException e) {
					
					Bukkit.getLogger().log(Level.SEVERE, "[RPGCS] ERROR CREATING NEW FILE FOR PLAYER DATA! Please create a bug report and send stack trace to developer.");
					e.printStackTrace();
					
				}
				
			}
			
		}
		
	}
	
	private static void saveLogic(File f, Map.Entry<UUID, CrownPlayer> entry) {
		
		FileConfiguration playerSave = YamlConfiguration.loadConfiguration(f);
		
		for(CrownCharacter character : entry.getValue().getCharacters()) {
			
			if(playerSave.isConfigurationSection(character.getCharacterID())) {
			
				saveChecks(playerSave, character);
				
			} else {
				
				playerSave.createSection(character.getCharacterID());
				saveChecks(playerSave, character);
				
			}
			
		}
		
		try {
			
			playerSave.save(f);
			
		} catch (IOException e) {
			
			Bukkit.getLogger().log(Level.SEVERE, "[RPGCS] ERROR SAVING PLAYER DATA! Please create a bug report and send stack trace to developer.");
			e.printStackTrace();
			
		}
		
	}

	private static void saveChecks(FileConfiguration playerSave, CrownCharacter character) {
		
		if(CCS.inventoryMap.containsKey(character) && CCS.balanceMap.containsKey(character)) {
			
			// Save inventory and balance
			saveInventory(playerSave.getConfigurationSection(character.getCharacterID()), CCS.inventoryMap.get(character));
			saveBalance(playerSave.getConfigurationSection(character.getCharacterID()), CCS.balanceMap.get(character));
			
		} else if(CCS.inventoryMap.containsKey(character)) {
			
			// Create new balance and save balance + inventory
			SwapLogic.createBalance(character, 0);
			saveBalance(playerSave.getConfigurationSection(character.getCharacterID()), CCS.balanceMap.get(character));
			
			saveInventory(playerSave.getConfigurationSection(character.getCharacterID()), CCS.inventoryMap.get(character));
			
		} else if(CCS.balanceMap.containsKey(character)) {
			
			// Create new inventory and save inventory + balance
			SwapLogic.createInventory(character);
			saveInventory(playerSave.getConfigurationSection(character.getCharacterID()), CCS.inventoryMap.get(character));
			
			saveBalance(playerSave.getConfigurationSection(character.getCharacterID()), CCS.balanceMap.get(character));
			
		} else {
			
			// Create new inventory and balance and save both
			SwapLogic.createBalance(character, 0);
			saveBalance(playerSave.getConfigurationSection(character.getCharacterID()), CCS.balanceMap.get(character));

			SwapLogic.createInventory(character);
			saveInventory(playerSave.getConfigurationSection(character.getCharacterID()), CCS.inventoryMap.get(character));
			
		}
		
	}
	
	private static void saveInventory(ConfigurationSection cs, InventoryStorage pi) {
		
		cs.set("helmet", pi.getHelmet());
		cs.set("chestplate", pi.getChestplate());
		cs.set("leggings", pi.getLeggings());
		cs.set("boots", pi.getBoots());

		cs.set("mainhand", pi.getMainHand());
		cs.set("offhand", pi.getOffHand());

		cs.set("storage", pi.getStorage());
		
	}
	
	private static void saveBalance(ConfigurationSection cs, double bal) {
		
		cs.set("balance", bal);
		
	}
	
}
