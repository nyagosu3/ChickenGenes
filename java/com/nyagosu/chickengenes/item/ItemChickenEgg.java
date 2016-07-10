package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;
import net.minecraft.item.Item;

public class ItemChickenEgg extends Item {
	public ItemChickenEgg(){
        this.maxStackSize = 64;
        this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.setUnlocalizedName("Chicken Egg");
        this.setTextureName("ChickenGenes:chickenegg");
    }
}
