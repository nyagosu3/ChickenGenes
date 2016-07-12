package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;
import net.minecraft.item.Item;

public class ItemChickenSyringe extends Item {
	public ItemChickenSyringe(){
		this.setUnlocalizedName("ItemChickenSyringe");
        this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.setTextureName("ChickenGenes:chickensyringe");
        this.maxStackSize = 64;
    }
}