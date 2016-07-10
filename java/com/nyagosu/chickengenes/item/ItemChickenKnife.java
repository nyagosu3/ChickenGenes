package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.entity.EntityGeneChicken;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemChickenKnife extends Item {
	
	public ItemChickenKnife(){
		this.setHasSubtypes(true);
		this.setUnlocalizedName("ItemChickenKnife");
		this.setTextureName("ChickenGenes:chickenknife");
		this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.maxStackSize = 1;
	}
	
    public boolean itemInteractionForEntity(ItemStack p_111207_1_, EntityPlayer p_111207_2_, EntityLivingBase p_111207_3_){
    	if(!p_111207_3_.worldObj.isRemote && p_111207_3_ instanceof EntityGeneChicken){
    		EntityGeneChicken entity = (EntityGeneChicken)p_111207_3_;
    		ItemStack itemstack = new ItemStack(ChickenGenesCore.itemChickenCell,1);
    		ItemChickenCell cell = (ItemChickenCell)itemstack.getItem();
    		cell.setGeneData(itemstack ,entity.getGeneData());
    		entity.entityDropItem(itemstack, 1.0F);
    		entity.setHealth(entity.getHealth() - 1.0F);
    	}
        return false;
    }
}
