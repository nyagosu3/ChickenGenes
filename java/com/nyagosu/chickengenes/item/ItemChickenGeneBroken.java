package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;

public class ItemChickenGeneBroken extends ItemChickenBaseGene {
	public ItemChickenGeneBroken(){
		this.setUnlocalizedName("ItemChickenGeneBroken");
		this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.setTextureName("ChickenGenes:chickengenebroken");
        this.maxStackSize = 64;
    }
}
