package com.nyagosu.chickengenes.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemChickenEgg extends Item {
	public ItemChickenEgg()
    {
        this.maxStackSize = 64;
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setUnlocalizedName("Chicken Egg");
        this.setTextureName("ChickenGenes:chickenegg");
    }
}
