package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.entity.GeneData;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemChickenSyringe extends Item {
	public ItemChickenSyringe(){
        this.maxStackSize = 64;
        this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.setUnlocalizedName("Chicken Syringe");
        this.setTextureName("ChickenGenes:chickensyringe");
    }
}