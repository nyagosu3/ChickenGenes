package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;
import net.minecraft.item.Item;

public class ItemChickenBell extends Item {
	public ItemChickenBell(){
		this.setHasSubtypes(true);
		this.setUnlocalizedName("ItemChickenBell");
		this.setTextureName("ChickenGenes:chickenbell");
        this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.maxStackSize = 1;
	}
}
