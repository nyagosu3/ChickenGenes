package com.nyagosu.chickengenes.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemChickenBell extends Item {
	public ItemChickenBell(){
		this.setHasSubtypes(true);
		this.setUnlocalizedName("ItemChickenBell");
		this.setTextureName("ChickenGenes:chickenbell");
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.maxStackSize = 1;
	}
}
