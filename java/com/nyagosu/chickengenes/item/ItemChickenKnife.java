package com.nyagosu.chickengenes.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemChickenKnife extends Item {
	public ItemChickenKnife(){
		this.setHasSubtypes(true);
		this.setUnlocalizedName("ItemChickenKnife");
		this.setTextureName("chickengenes:chickenknife");
        this.setCreativeTab(CreativeTabs.tabMisc);
	}
}
