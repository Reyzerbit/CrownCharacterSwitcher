package com.reyzerbit.CrownCharacterSwitcher.core;

import org.bukkit.inventory.PlayerInventory;

import com.reyzerbit.CrownCharacterSwitcher.core.structures.InventoryStorage;

public class Conversion {
	
	public static InventoryStorage convertToInvStorage(PlayerInventory pInv) {
		
		return new InventoryStorage(pInv.getHelmet(), pInv.getChestplate(), pInv.getLeggings(), pInv.getBoots(), pInv.getItemInMainHand(), pInv.getItemInOffHand(), pInv.getStorageContents());
		
	}

}
