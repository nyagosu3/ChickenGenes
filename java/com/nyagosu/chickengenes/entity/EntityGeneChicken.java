package com.nyagosu.chickengenes.entity;


import java.util.Random;

import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.ai.EntityAIMateCustom;
import com.nyagosu.chickengenes.item.ItemSweetSeed;

import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;

public class EntityGeneChicken extends EntityGeneChickenRoot{
	
    public EntityGeneChicken(World p_i1682_1_){
    	super(p_i1682_1_);
    	this.setGeneDataInit(new GeneData());
    	this.init();
    }
    
    public EntityGeneChicken(World p_i1682_1_ , boolean flag){
    	super(p_i1682_1_);
    	if(flag)this.setGeneDataInit(new GeneData());
    	this.init();
    }
    
    public void init(){
        this.setSize(0.3F, 0.7F);
        this.timeUntilNextEgg = this.getEggTime();
        this.setTamed(false);
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.wheat_seeds, false));
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
		this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(6, new EntityAIMateCustom(this, 1.0D));
//		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheep.class, 200, false));
    }
    
    public EntityGeneChicken createChild(EntityAgeable p_90011_1_){
    	
    	EntityGeneChicken p = (EntityGeneChicken)p_90011_1_;
    	
    	int p_seed = (p.lastSeed != null)?p.lastSeed.getItemDamage():0;
    	int m_seed = (this.lastSeed != null)?this.lastSeed.getItemDamage():0;
    	
    	GeneData new_gene = null;
    	GeneData p_gene = p.getGeneData();
    	GeneData m_gene = this.getGeneData();
    	
    	int c = getUniteSuccessRate(p_seed,m_seed);
    	if(this.isUniteSuccessGene(c)){
    		new_gene = m_gene.mix(p_gene);
    	}else{
    		Random r = new Random();
    		new_gene = (r.nextInt(2) == 0)?m_gene:p_gene;
    	}
    	
    	p_gene.mate_flag = 1;
    	m_gene.mate_flag = 1;
    	p.setGeneData(p_gene);
    	this.setGeneData(m_gene);
    	
    	EntityGeneChicken chicken = new EntityGeneChicken(this.worldObj,true);
    	chicken.setGeneDataInit(new_gene);
    	
    	return chicken;
    }
    
    public boolean interact(EntityPlayer p_70085_1_){
        ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();
        
        if (this.isTamed()){
            if (itemstack != null){
                if (itemstack.getItem() instanceof ItemFood){
                	
                    ItemFood itemfood = (ItemFood)itemstack.getItem();

                    if (itemfood.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < 20.0F){
                        if (!p_70085_1_.capabilities.isCreativeMode){
                            --itemstack.stackSize;
                        }
                        this.heal((float)itemfood.func_150905_g(itemstack));
                        if (itemstack.stackSize <= 0){
                            p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, (ItemStack)null);
                        }
                        return true;
                    }
                }
            }
            
            if (this.func_152114_e(p_70085_1_) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack)){
            	if(!(itemstack != null && itemstack.getItem() == ChickenGenesCore.itemChickenLoupe)){
            		this.aiSit.setSitting(!this.isSitting());
                    this.isJumping = false;
                    this.setPathToEntity((PathEntity)null);
                    this.setTarget((Entity)null);
                    this.setAttackTarget((EntityLivingBase)null);
            	}
            }
        }else if (
        		itemstack != null && 
        		itemstack.getItem() == ChickenGenesCore.itemChickenBell && 
        		!this.isAngry()
        		){
        	if (!this.worldObj.isRemote){
            	this.setTamed(true);
                this.setPathToEntity((PathEntity)null);
                this.setAttackTarget((EntityLivingBase)null);
                this.setHealth(20.0F);
                this.func_152115_b(p_70085_1_.getUniqueID().toString());
                this.playTameEffect(true);
                this.worldObj.setEntityState(this, (byte)7);                
            }
            return true;
        }else if (itemstack != null && itemstack.getItem() instanceof ItemSweetSeed && !this.isAngry()){
        	this.lastSeed = itemstack;
        }else if (itemstack != null && itemstack.getItem() == Items.apple ){
        	this.setGrowingAge(100);
        }
        return super.interact(p_70085_1_);
    }
    
    public boolean canMateWith(EntityAnimal p_70878_1_){
        if(p_70878_1_ == this ? false : (p_70878_1_.getClass() != this.getClass() ? false : this.isInLove() && p_70878_1_.isInLove())){
        	GeneData my_gene = this.getGeneData();
        	EntityGeneChicken target = (EntityGeneChicken)p_70878_1_;
        	GeneData target_gene = target.getGeneData();
        	return (my_gene.sex != target_gene.sex);
        }else{
        	return false;
        }
    }
}
