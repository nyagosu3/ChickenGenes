package com.nyagosu.chickengenes.item;

import java.util.List;
import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.entity.GeneData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemChickenSyringeDoping extends Item {
	
	public ItemChickenSyringeDoping(){
		this.setUnlocalizedName("ItemChickenSyringeDoping");
		this.setTextureName("ChickenGenes:chickensyringedoping");
		this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.maxStackSize = 64;
	}
	
    public boolean itemInteractionForEntity(ItemStack p_111207_1_, EntityPlayer p_111207_2_, EntityLivingBase p_111207_3_){
    	if(!p_111207_3_.worldObj.isRemote && p_111207_3_ instanceof EntityGeneChicken){
    		EntityGeneChicken entity = (EntityGeneChicken)p_111207_3_;
    		GeneData gene = entity.getGeneData();
    		switch(p_111207_1_.getItemDamage()){
    		case 0:gene.maxhealth += 1;		break;
    		case 1:gene.attack += 1;		break;
    		case 2:gene.defense += 1;		break;
    		case 3:gene.eggspeed += 100;		break;
    		case 4:gene.efficiency += 100;	break;
    		case 5:gene.growspeed += 1;		break;
    		case 6:gene.movespeed += 1;		break;
    		};
    		entity.setGeneData(gene);
    	}
    	return false;
    }

    public String getItemStackDisplayName(ItemStack par1ItemStack){
    	String str = "";
    	switch(par1ItemStack.getItemDamage()){
    	case 0:str = "MaxHealth UP";		break;
    	case 1:str = "Attack UP";			break;
    	case 2:str = "Defense UP";			break;
    	case 3:str = "EggSpeed UP";			break;
    	case 4:str = "Efficiency UP";		break;
    	case 5:str = "GrowSpeed UP";		break;
    	case 6:str = "MoveSpeed UP";		break;
    	};
        return str;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2, List par3){
    	par3.add(new ItemStack(par1, 1, 0));
    	par3.add(new ItemStack(par1, 1, 1));
    	par3.add(new ItemStack(par1, 1, 2));
    	par3.add(new ItemStack(par1, 1, 3));
    	par3.add(new ItemStack(par1, 1, 4));
    	par3.add(new ItemStack(par1, 1, 5));
    	par3.add(new ItemStack(par1, 1, 6));
    }
}
