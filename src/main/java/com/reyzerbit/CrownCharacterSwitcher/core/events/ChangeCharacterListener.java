package com.reyzerbit.CrownCharacterSwitcher.core.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.reyzerbit.CrownCharacterSwitcher.CCS;
import com.reyzerbit.CrownCharacterSwitcher.core.SwapLogic;
import com.reyzerbit.CrownCore.CrownCore;
import com.reyzerbit.CrownCore.core.events.SetActiveCharacterEvent;
import com.reyzerbit.CrownCore.core.structures.CrownCharacter;
import com.reyzerbit.CrownCore.core.structures.CrownPlayer;

import net.md_5.bungee.api.ChatColor;

public class ChangeCharacterListener implements Listener {
	
	@EventHandler
	public void onPlayerChangeCharacter(SetActiveCharacterEvent event) {
		
		CrownPlayer player = CrownCore.playerData.get(event.getPlayerUUID());
		CrownCharacter rpgC = player.getCharacter(event.getRpgCharacterId());
		
		SwapLogic.saveInventory(event.getPlayer(), player.getActiveCharacter());
		SwapLogic.saveBalance(event.getPlayer(), player.getActiveCharacter());
		
		//Set new inventory
		if(CCS.inventoryMap.containsKey(rpgC)) {
			
			SwapLogic.setInventory(event.getPlayer(), rpgC);
			
		} else {
			
			event.getPlayer().sendMessage(ChatColor.RED + "Attempted to set inventory for character " + event.getRpgCharacterId() + ", but none was found. Creating a new one...");
			
			SwapLogic.createInventory(rpgC);
			
			SwapLogic.setInventory(event.getPlayer(), rpgC);
			
		}
		
		//Set new balance
		if(CCS.balanceMap.containsKey(rpgC)) {
			
			//Set balance
			SwapLogic.setBalance(event.getPlayer(), rpgC);
			
		} else {
			
			event.getPlayer().sendMessage(ChatColor.RED + "Attempted to set balance for character " + event.getRpgCharacterId() + ", but none was found. Creating a new one...");
			
			SwapLogic.createBalance(rpgC, 0.0);

			//Set balance
			SwapLogic.setBalance(event.getPlayer(), rpgC);
			
		}
		
	}

}
