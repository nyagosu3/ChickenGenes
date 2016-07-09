package com.nyagosu.chickengenes.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemChickenSyringe extends Item {
	public ItemChickenSyringe()
    {
        this.maxStackSize = 64;
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setUnlocalizedName("Chicken Syringe");
        this.setTextureName("ChickenGenes:chickensyringe");
    }
}
