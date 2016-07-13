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
    		case 1:gene.maxhealth += 10;	break;
    		case 2:gene.maxhealth += 100;	break;
    		case 3:gene.maxhealth -= 1;		break;
    		case 4:gene.maxhealth -= 10;	break;
    		case 5:gene.maxhealth -= 100;	break;
    		
    		case 10:gene.attack += 1;		break;
    		case 11:gene.attack += 10;		break;
    		case 12:gene.attack += 100;		break;
    		case 13:gene.attack -= 1;		break;
    		case 14:gene.attack -= 10;		break;
    		case 15:gene.attack -= 100;		break;
    		
    		case 20:gene.defense += 1;		break;
    		case 21:gene.defense += 10;		break;
    		case 22:gene.defense += 100;	break;
    		case 23:gene.defense -= 1;		break;
    		case 24:gene.defense -= 10;		break;
    		case 25:gene.defense -= 100;	break;
    		
    		case 30:gene.eggspeed += 1;		break;
    		case 31:gene.eggspeed += 10;	break;
    		case 32:gene.eggspeed += 100;	break;
    		case 33:gene.eggspeed -= 1;		break;
    		case 34:gene.eggspeed -= 10;	break;
    		case 35:gene.eggspeed -= 100;	break;
    		
    		case 40:gene.efficiency += 1;	break;
    		case 41:gene.efficiency += 10;	break;
    		case 42:gene.efficiency += 100;	break;
    		case 43:gene.efficiency -= 1;	break;
    		case 44:gene.efficiency -= 10;	break;
    		case 45:gene.efficiency -= 100;	break;
    		
    		case 50:gene.growspeed += 1;	break;
    		case 51:gene.growspeed += 10;	break;
    		case 52:gene.growspeed += 100;	break;
    		case 53:gene.growspeed -= 1;	break;
    		case 54:gene.growspeed -= 10;	break;
    		case 55:gene.growspeed -= 100;	break;
    		
    		case 60:gene.movespeed += 1;	break;
    		case 61:gene.movespeed += 10;	break;
    		case 62:gene.movespeed += 100;	break;
    		case 63:gene.movespeed -= 1;	break;
    		case 64:gene.movespeed -= 10;	break;
    		case 65:gene.movespeed -= 100;	break;
    		};
    		entity.setGeneData(gene);
    	}
    	return false;
    }

    public String getItemStackDisplayName(ItemStack par1ItemStack){
    	String str = "";
    	switch(par1ItemStack.getItemDamage()){
    	case 0:		str = "MaxHealth UP + 1";		break;
    	case 1:		str = "MaxHealth UP + 10";		break;
    	case 2:		str = "MaxHealth UP + 100";		break;
    	case 3:		str = "MaxHealth DOWN - 1";		break;
    	case 4:		str = "MaxHealth DOWN - 10";	break;
    	case 5:		str = "MaxHealth DOWN - 100";	break;

    	case 10:	str = "Attack UP + 1";			break;
    	case 11:	str = "Attack UP + 10";			break;
    	case 12:	str = "Attack UP + 100";		break;
    	case 13:	str = "Attack DOWN - 1";		break;
    	case 14:	str = "Attack DOWN - 10";		break;
    	case 15:	str = "Attack DOWN - 100";		break;
    	
    	case 20:	str = "Defense UP + 1";			break;
    	case 21:	str = "Defense UP + 10";		break;
    	case 22:	str = "Defense UP + 100";		break;
    	case 23:	str = "Defense DOWN - 1";		break;
    	case 24:	str = "Defense DOWN - 10";		break;
    	case 25:	str = "Defense DOWN - 100";		break;
    	
    	case 30:	str = "EggSpeed UP + 1";		break;
    	case 31:	str = "EggSpeed UP + 10";		break;
    	case 32:	str = "EggSpeed UP + 100";		break;
    	case 33:	str = "EggSpeed DOWN - 1";		break;
    	case 34:	str = "EggSpeed DOWN - 10";		break;
    	case 35:	str = "EggSpeed DOWN - 100";	break;
    	
    	case 40:	str = "Efficiency UP + 1";		break;
    	case 41:	str = "Efficiency UP + 10";		break;
    	case 42:	str = "Efficiency UP + 100";	break;
    	case 43:	str = "Efficiency DOWN - 1";	break;
    	case 44:	str = "Efficiency DOWN - 10";	break;
    	case 45:	str = "Efficiency DOWN - 100";	break;
    	
    	case 50:	str = "GrowSpeed UP + 1";		break;
    	case 51:	str = "GrowSpeed UP + 10";		break;
    	case 52:	str = "GrowSpeed UP + 100";		break;
    	case 53:	str = "GrowSpeed DOWN - 1";		break;
    	case 54:	str = "GrowSpeed DOWN - 10";	break;
    	case 55:	str = "GrowSpeed DOWN - 100";	break;
    	
    	case 60:	str = "MoveSpeed UP + 1";		break;
    	case 61:	str = "MoveSpeed UP + 10";		break;
    	case 62:	str = "MoveSpeed UP + 100";		break;
    	case 63:	str = "MoveSpeed DOWN - 1";		break;
    	case 64:	str = "MoveSpeed DOWN - 10";	break;
    	case 65:	str = "MoveSpeed DOWN - 100";	break;
    		
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
    	par3.add(new ItemStack(par1, 1, 10));
    	par3.add(new ItemStack(par1, 1, 11));
    	par3.add(new ItemStack(par1, 1, 12));
    	par3.add(new ItemStack(par1, 1, 13));
    	par3.add(new ItemStack(par1, 1, 14));
    	par3.add(new ItemStack(par1, 1, 15));
    	par3.add(new ItemStack(par1, 1, 20));
    	par3.add(new ItemStack(par1, 1, 21));
    	par3.add(new ItemStack(par1, 1, 22));
    	par3.add(new ItemStack(par1, 1, 23));
    	par3.add(new ItemStack(par1, 1, 24));
    	par3.add(new ItemStack(par1, 1, 25));
    	par3.add(new ItemStack(par1, 1, 30));
    	par3.add(new ItemStack(par1, 1, 31));
    	par3.add(new ItemStack(par1, 1, 32));
    	par3.add(new ItemStack(par1, 1, 33));
    	par3.add(new ItemStack(par1, 1, 34));
    	par3.add(new ItemStack(par1, 1, 35));
    	par3.add(new ItemStack(par1, 1, 40));
    	par3.add(new ItemStack(par1, 1, 41));
    	par3.add(new ItemStack(par1, 1, 42));
    	par3.add(new ItemStack(par1, 1, 43));
    	par3.add(new ItemStack(par1, 1, 44));
    	par3.add(new ItemStack(par1, 1, 45));
    	par3.add(new ItemStack(par1, 1, 50));
    	par3.add(new ItemStack(par1, 1, 51));
    	par3.add(new ItemStack(par1, 1, 52));
    	par3.add(new ItemStack(par1, 1, 53));
    	par3.add(new ItemStack(par1, 1, 54));
    	par3.add(new ItemStack(par1, 1, 55));
    	par3.add(new ItemStack(par1, 1, 60));
    	par3.add(new ItemStack(par1, 1, 61));
    	par3.add(new ItemStack(par1, 1, 62));
    	par3.add(new ItemStack(par1, 1, 63));
    	par3.add(new ItemStack(par1, 1, 64));
    	par3.add(new ItemStack(par1, 1, 65));
    }
}
