package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;

public class ItemChickenGeneMutation extends ItemChickenBaseGene {
	public ItemChickenGeneMutation(){
		this.maxStackSize = 64;
        this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.setUnlocalizedName("Chicken Gene Mutation");
        this.setTextureName("ChickenGenes:chickengenemutation");
    }
}
