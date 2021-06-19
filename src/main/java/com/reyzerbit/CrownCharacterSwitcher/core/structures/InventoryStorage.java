package com.reyzerbit.CrownCharacterSwitcher.core.structures;

import org.bukkit.inventory.ItemStack;

public class InventoryStorage {
	
	ItemStack helmet;
	ItemStack chestplate;
	ItemStack leggings;
	ItemStack boots;
	
	ItemStack mainHand;
	ItemStack offHand;

	ItemStack[] storage;
	
	public InventoryStorage() {
		
		helmet = null;
		chestplate = null;
		leggings = null;
		boots = null;
		
		mainHand = null;
		offHand = null;
		
		storage = new ItemStack[] {};
		
	}
	
	public InventoryStorage(ItemStack helm, ItemStack chest, ItemStack legs, ItemStack boot, ItemStack main, ItemStack off, ItemStack[] cont) {
		
		helmet = helm;
		chestplate = chest;
		leggings = legs;
		boots = boot;
		
		mainHand = main;
		offHand = off;
		
		storage = cont;
		
	}

	public ItemStack getHelmet() {
		return helmet;
	}

	public void setHelmet(ItemStack helmet) {
		this.helmet = helmet;
	}

	public ItemStack getChestplate() {
		return chestplate;
	}

	public void setChestplate(ItemStack chestplate) {
		this.chestplate = chestplate;
	}

	public ItemStack getLeggings() {
		return leggings;
	}

	public void setLeggings(ItemStack leggings) {
		this.leggings = leggings;
	}

	public ItemStack getBoots() {
		return boots;
	}

	public void setBoots(ItemStack boots) {
		this.boots = boots;
	}

	public ItemStack getMainHand() {
		return mainHand;
	}

	public void setMainHand(ItemStack mainHand) {
		this.mainHand = mainHand;
	}

	public ItemStack getOffHand() {
		return offHand;
	}

	public void setOffHand(ItemStack offHand) {
		this.offHand = offHand;
	}

	public ItemStack[] getStorage() {
		return storage;
	}

	public void setStorage(ItemStack[] storage) {
		this.storage = storage;
	}

}
