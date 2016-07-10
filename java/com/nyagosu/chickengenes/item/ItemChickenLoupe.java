package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.gui.GuiScreenChickenLoupe;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemChickenLoupe extends Item{
	public ItemChickenLoupe(){
		this.setHasSubtypes(true);
		this.setUnlocalizedName("ItemChickenLoupe");
		this.setTextureName("ChickenGenes:chickenloupe");
		this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.maxStackSize = 1;
	}
	
    public boolean itemInteractionForEntity(ItemStack p_111207_1_, EntityPlayer p_111207_2_, EntityLivingBase p_111207_3_){
    	if (p_111207_3_.worldObj.isRemote){
            return false;
        }
    	if(p_111207_3_ instanceof EntityGeneChicken){
    		EntityGeneChicken entity = (EntityGeneChicken)p_111207_3_;
    		Minecraft.getMinecraft().displayGuiScreen(new GuiScreenChickenLoupe(p_111207_2_,entity));
    	}
        return false;
    }
}
