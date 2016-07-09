package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.gui.GuiScreenChickenLoupe;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemChickenKnife extends Item {
	
	public ItemChickenKnife(){
		this.setHasSubtypes(true);
		this.setUnlocalizedName("ItemChickenKnife");
		this.setTextureName("ChickenGenes:chickenknife");
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.maxStackSize = 1;
	}
	
	/**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack p_111207_1_, EntityPlayer p_111207_2_, EntityLivingBase p_111207_3_)
    {
    	if(!p_111207_3_.worldObj.isRemote && p_111207_3_ instanceof EntityGeneChicken)
    	{
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
