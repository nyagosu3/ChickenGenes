package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;
import net.minecraft.item.Item;

public class ItemChickenSyringe extends Item {
	public ItemChickenSyringe(){
        this.maxStackSize = 64;
        this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.setUnlocalizedName("Chicken Syringe");
        this.setTextureName("ChickenGenes:chickensyringe");
    }
}