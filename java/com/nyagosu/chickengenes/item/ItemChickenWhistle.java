package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;
import net.minecraft.item.Item;

public class ItemChickenWhistle extends Item {
	public ItemChickenWhistle(){
		this.setUnlocalizedName("ItemChickenWhistle");
		this.setTextureName("ChickenGenes:chickenwhistle");
        this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.maxStackSize = 1;
	}
}
