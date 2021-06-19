package com.reyzerbit.CrownCharacterSwitcher.core;

import org.bukkit.entity.Player;

import com.reyzerbit.CrownCharacterSwitcher.CCS;
import com.reyzerbit.CrownCharacterSwitcher.core.structures.InventoryStorage;
import com.reyzerbit.CrownCore.core.structures.CrownCharacter;

public class SwapLogic {
	
	public static void createBalance(CrownCharacter character, double bal) {
		
		CCS.balanceMap.put(character, 0.0);
		
	}
	
	public static void setBalance(Player p, CrownCharacter character) {
		
		CCS.econ.withdrawPlayer(p, CCS.econ.getBalance(p));
		CCS.econ.depositPlayer(p, CCS.balanceMap.get(character));
		
	}
	
	public static void createInventory(CrownCharacter character) {
		
		CCS.inventoryMap.put(character, new InventoryStorage());
		
	}
	
	public static void setInventory(Player p, CrownCharacter character) {
		
		InventoryStorage storedInv = CCS.inventoryMap.get(character);
		
		p.getInventory().setHelmet(storedInv.getHelmet());
		p.getInventory().setLeggings(storedInv.getLeggings());
		p.getInventory().setChestplate(storedInv.getChestplate());
		p.getInventory().setBoots(storedInv.getBoots());
		
		p.getInventory().setItemInMainHand(storedInv.getMainHand());
		p.getInventory().setItemInOffHand(storedInv.getOffHand());
		
		p.getInventory().setStorageContents(storedInv.getStorage());
		
	}

	public static void saveInventory(Player p, CrownCharacter character) {
		
		CCS.inventoryMap.put(character, Conversion.convertToInvStorage(p.getInventory()));
		
	}
	
	public static void saveBalance(Player p, CrownCharacter character) {
		
		CCS.balanceMap.put(character, CCS.econ.getBalance(p));
		
	}
	
}
