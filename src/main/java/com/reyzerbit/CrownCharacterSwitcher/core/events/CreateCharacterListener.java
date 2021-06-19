package com.reyzerbit.CrownCharacterSwitcher.core.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.reyzerbit.CrownCharacterSwitcher.core.SwapLogic;
import com.reyzerbit.CrownCore.core.events.CreateCharacterEvent;

import net.md_5.bungee.api.ChatColor;

public class CreateCharacterListener implements Listener {
	
	@EventHandler
	public void onCreateCharacter(CreateCharacterEvent event) {
		
		Bukkit.getPlayer(event.getPlayerUUID()).sendMessage(ChatColor.GREEN + "New character creation detected! Creating new inventory and balance.");
		
		SwapLogic.createInventory(event.getCharacter());
		SwapLogic.createBalance(event.getCharacter(), 0);
		
	}

}
