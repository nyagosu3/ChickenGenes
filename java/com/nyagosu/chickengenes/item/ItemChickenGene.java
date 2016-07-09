package com.nyagosu.chickengenes.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemChickenGene extends Item {
	public ItemChickenGene()
    {
        this.maxStackSize = 64;
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setUnlocalizedName("Chicken Gene");
        this.setTextureName("ChickenGenes:chickengene");
    }
}
