package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;
import net.minecraft.item.Item;

public class ItemChickenEgg extends Item {
	public ItemChickenEgg(){
		this.setUnlocalizedName("ItemChickenContainer");
        this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.setTextureName("ChickenGenes:chickenegg");
        this.maxStackSize = 64;
    }
}
