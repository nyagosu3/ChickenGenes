package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;

public class ItemChickenGeneMutation extends ItemChickenBaseGene {
	public ItemChickenGeneMutation(){
		this.setUnlocalizedName("ItemChickenGeneMutation");
        this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.setTextureName("ChickenGenes:chickengenemutation");
        this.maxStackSize = 64;
    }
}
