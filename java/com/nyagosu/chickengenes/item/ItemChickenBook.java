package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemChickenBook extends Item {
	public ItemChickenBook(){
		this.setHasSubtypes(true);
		this.setUnlocalizedName("ItemChickenBook");
		this.setTextureName("ChickenGenes:chickenbook");
		this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
	}
}
