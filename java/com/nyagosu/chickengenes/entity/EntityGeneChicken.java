package com.nyagosu.chickengenes.entity;


import java.util.Random;

import com.nyagosu.chickengenes.ai.EntityAIMateCustom;

import net.minecraft.world.World;
import net.minecraft.entity.EntityAgeable;
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
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
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
    
    public void onLivingUpdate(){
        super.onLivingUpdate();
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        
        if (this.destPos < 0.0F)this.destPos = 0.0F;
        
        if (this.destPos > 1.0F)this.destPos = 1.0F;
        
        if (!this.onGround && this.field_70889_i < 1.0F)this.field_70889_i = 1.0F;
        
        this.field_70889_i = (float)((double)this.field_70889_i * 0.9D);
        
        if (!this.onGround && this.motionY < 0.0D)this.motionY *= 0.6D;
        
        this.field_70886_e += this.field_70889_i * 2.0F;
        
        if (
        		!this.worldObj.isRemote && 
        		!this.isChild() &&
        		this.getStamina() >= 30.0F
        		){
        	if(this.timeUntilNextEgg -1 <= 0){
        		this.playSound("mob.chicken.plop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                this.dropItem(Items.egg, 1);
                this.timeUntilNextEgg = this.getEggTime();
                this.addStamina(this.getEggStamina());
        	}else{
        		--this.timeUntilNextEgg;
        	}
        }
        
//        if(!this.worldObj.isRemote){
//        	DebugTool.print(String.valueOf(this.timeUntilNextEgg));
//        }
        	
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
    
    public String getChickenDataString(){
    	String str = "";
    	str += String.valueOf(this.getHealth()) + "|";		//health
    	str += String.valueOf(this.timeUntilNextEgg) + "|";	//timeUntilNextEgg
    	str += String.valueOf(this.getGeneData().getDataString()) + "";	//gene
    	return str;
    }
    
    public void setChickenDataString(String chicken_data_string){
    	String[] values = chicken_data_string.split("|");
    	this.setHealth(Float.valueOf(values[0]));
    	this.timeUntilNextEgg = Integer.parseInt(values[1]);
    	this.setGeneData(new GeneData(values[2]));
    }
}
