package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;

public class ItemChickenGeneBroken extends ItemChickenBaseGene {
	public ItemChickenGeneBroken(){
		this.maxStackSize = 64;
        this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.setUnlocalizedName("Chicken Gene Broken");
        this.setTextureName("ChickenGenes:chickengenebroken");
    }
}
