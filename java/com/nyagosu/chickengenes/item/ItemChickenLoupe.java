package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.gui.GuiScreenChickenLoupe;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemChickenLoupe extends Item{
	public ItemChickenLoupe(){
		this.setHasSubtypes(true);
		this.setUnlocalizedName("ItemChickenLoupe");
		this.setTextureName("chickengenes:chickenloupe");
        this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	/**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack p_111207_1_, EntityPlayer p_111207_2_, EntityLivingBase p_111207_3_)
    {
    	
    	if (p_111207_3_.worldObj.isRemote)
        {
            return false;
        }
    	
    	if(p_111207_3_ instanceof EntityGeneChicken){
    		EntityGeneChicken entity = (EntityGeneChicken)p_111207_3_;
    		Minecraft.getMinecraft().displayGuiScreen(new GuiScreenChickenLoupe(p_111207_2_,entity));
    	}
    	
    	if(!p_111207_3_.worldObj.isRemote){
    		
    		
    		
    		
    		
//    		NBTTagCompound c = p_111207_3_.getEntityData();
//    		float a = p_111207_3_.getHealth();
//    		DebugTool.print(String.valueOf(a));
    		

    		//EntityMyGolem entity = (EntityMyGolem)p_111207_3_;
    		
    		//entity.setTest(10);
    		
//    		Date date = new Date();
//        	DebugTool.print(date.toString());
        	//Minecraft.getMinecraft().displayGuiScreen(new GuiScreenTest(p_111207_2_,p_111207_1_,false));
    	}
        return false;
    }
}
