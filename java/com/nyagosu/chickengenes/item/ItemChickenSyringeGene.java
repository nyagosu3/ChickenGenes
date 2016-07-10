package com.nyagosu.chickengenes.item;

import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.entity.GeneData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ItemChickenSyringeGene extends ItemChickenBaseGene {
	public ItemChickenSyringeGene(){
		super();
        this.setUnlocalizedName("Chicken Syringe Gene");
        this.setTextureName("ChickenGenes:chickensyringegene");
    }
	
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer p_111207_2_, EntityLivingBase p_111207_3_){
    	if(!p_111207_3_.worldObj.isRemote && p_111207_3_ instanceof EntityChicken){
    		
    		GeneData gene = ((ItemChickenSyringeGene)itemstack.getItem()).getGeneData(itemstack);
    		EntityChicken entity = (EntityChicken)p_111207_3_;
    		gene.randomSexValue();
    		double posx = entity.posX;
    		double posy = entity.posY;
    		double posz = entity.posZ;
    		entity.setDead();
    		
    		EntityGeneChicken chicken = new EntityGeneChicken(p_111207_3_.worldObj);
    		chicken.setGeneDataInit(gene);
    		chicken.setLocationAndAngles(posx,posy,posz, MathHelper.wrapAngleTo180_float(p_111207_3_.worldObj.rand.nextFloat() * 360.0F), 0.0F);
            chicken.rotationYawHead = chicken.rotationYaw;
            chicken.renderYawOffset = chicken.rotationYaw;
            chicken.onSpawnWithEgg((IEntityLivingData)null);
            p_111207_3_.worldObj.spawnEntityInWorld(chicken);
            chicken.playLivingSound();
            
            if (!p_111207_2_.capabilities.isCreativeMode){
                --itemstack.stackSize;
            }
    		
    	}
    	return false;
    }
}
