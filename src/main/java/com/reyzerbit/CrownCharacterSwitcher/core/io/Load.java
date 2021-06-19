package com.reyzerbit.CrownCharacterSwitcher.core.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.reyzerbit.CrownCharacterSwitcher.CCS;
import com.reyzerbit.CrownCharacterSwitcher.core.structures.InventoryStorage;
import com.reyzerbit.CrownCore.CrownCore;
import com.reyzerbit.CrownCore.core.structures.CrownCharacter;

public class Load {
	
	public static void load() {
		
		//YAML Filetype filter
		FilenameFilter filter = new FilenameFilter() {

			@Override
			public boolean accept(File f, String name) {

				return name.endsWith(".yml");

			}

		};
		
		if(CCS.playerDataDir.listFiles(filter) == null) {
			
			return;
			
		}

		//Gets all yaml files in directory playerDataDir
		List<File> playerSaves = Arrays.asList(CCS.playerDataDir.listFiles(filter));
		
		//Puts all FileConfigurations into playerSavesConfig map
	   	playerSaves.forEach(f -> {
	   		
	   		String playerUUIDString = f.getName().substring(0, f.getName().length() - 4);
	   		
	   		CrownCharacter character;
				
			if(playerUUIDString.length() == 36) {
				
				FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(f);
				Set<String> characterInventories = playerConfig.getKeys(false);
				
				for(String characterID : characterInventories) {
					
					character = CrownCore.playerData.get(UUID.fromString(playerUUIDString)).getCharacter(characterID);
					
					ConfigurationSection section = playerConfig.getConfigurationSection(characterID);
					
					if(section != null) {
						
						@SuppressWarnings("unchecked")
						List<ItemStack> storageContents = (List<ItemStack>) section.get("storage");
						
						ItemStack[] storageArray = storageContents.toArray(new ItemStack[0]);
						
						InventoryStorage inv = new InventoryStorage(section.getItemStack("helmet"), section.getItemStack("chestplate"), section.getItemStack("leggings"),
								section.getItemStack("boots"), section.getItemStack("mainhand"), section.getItemStack("offhand"), storageArray);
						
						CCS.inventoryMap.put(character, inv);
						CCS.balanceMap.put(character, section.getDouble("balance"));
						
					}
					
				}
	   			
	   		}
			
		});
		
	}

}
