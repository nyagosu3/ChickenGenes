package com.nyagosu.chickengenes.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.ai.EntityAIMateCustom;
import com.nyagosu.chickengenes.item.ItemChickenSyringeGene;
import com.nyagosu.chickengenes.item.ItemSweetSeed;
import com.nyagosu.chickengenes.util.Randory;

import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
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
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;

public class EntityGeneChicken extends EntityTameable {
	
	public double base_max_health = 4.0D;
	public double base_movement_speed = 0.25D;
	public float field_70886_e;
    public float destPos;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;
	public int timeUntilNextEgg = 0;
	public ItemStack lastSeed;
    
	public static final int DW_GENEDATA = 25;
    public static final String NBT_GENEDATA = "GENE_DATA";
    
    public static final int DW_CHICKEN_STAMINA = 26;
    public static final String NBT_CHICKEN_STAMINA = "CHICKEN_STAMINA";
    
    public static final int DW_CHICKEN_IN_GENE_COUNT = 27;
    public static final String NBT_CHICKEN_IN_GENE_COUNT = "CHICKEN_IN_GENE_COUNT";
	
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
//		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheep.class, 200, false));
    }
    
    protected void applyEntityAttributes(){
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.base_max_health);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.base_movement_speed);
    }
    
    public boolean isAIEnabled(){
        return true;
    }
    
    protected void entityInit(){
        super.entityInit();
        this.dataWatcher.addObject(18, new Float(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte)0));
        this.dataWatcher.addObject(DW_GENEDATA,"");
        this.dataWatcher.addObject(DW_CHICKEN_STAMINA,0.0F);
        this.dataWatcher.addObject(DW_CHICKEN_IN_GENE_COUNT,0);
    }
    
    public void setGeneDataInit(GeneData gene){
    	this.setGeneData(gene);
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getGeneMaxHealth());
    	this.setHealth((float)this.getGeneMaxHealth());
    	this.timeUntilNextEgg = this.getEggTime();
    }
	
    public void changeGeneState(GeneData gene){
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getGeneMaxHealth());
    	this.timeUntilNextEgg = this.getEggTime();
    	if( this.getHealth() > this.getGeneMaxHealth())
    		this.setHealth((int)this.getGeneMaxHealth());
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.base_movement_speed + (double)gene.movespeed * ChickenGenesCore.GeneMoveSpeedFactorValue);
    }
    
    public EntityGeneChicken createChild(EntityAgeable p_90011_1_){
    	
    	EntityGeneChicken p = (EntityGeneChicken)p_90011_1_;
    	
    	int p_seed = (p.lastSeed != null)?p.lastSeed.getItemDamage():0;
    	int m_seed = (this.lastSeed != null)?this.lastSeed.getItemDamage():0;
    	
    	GeneData new_gene = null;
    	GeneData p_gene = p.getGeneData();
    	GeneData m_gene = this.getGeneData();
    	
    	if(this.isUniteSuccessGene(this.getUniteSuccessRate(p_seed,m_seed))){
    		//success
    		new_gene = m_gene.mix(p_gene);
    		for(int i = 0;i < 3;i++)
    			this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, this.rand.nextInt(2) + 1));
    	}else{
    		new_gene = (this.rand.nextInt(2) == 0)?m_gene:p_gene;
    	}
    	
    	new_gene.generation = Math.max(m_gene.generation, p_gene.generation) + 1;
    	
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
        
        if (!this.worldObj.isRemote && isChild()){
        	GeneData gene = this.getGeneData();
        	
    		if(gene.growspeed != 0 && Math.random() < ChickenGenesCore.GeneGrowSpeedAdjustRate){
    			int age = (int) (this.getGrowingAge() + gene.growspeed * ChickenGenesCore.GeneGrowSpeedFactorValue);
    			if(age > 0)age = 0;
    			this.setGrowingAge(age);
    		}
        }
        
        if (
        		!this.worldObj.isRemote && 
        		!this.isChild() &&
        		this.getStamina() >= ChickenGenesCore.NeedSpawnEggStamina
        		){
        	if(this.timeUntilNextEgg -1 <= 0){
        		this.playSound("mob.chicken.plop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                this.entityDropItem(new ItemStack(Items.egg,1),0.0F);
                this.timeUntilNextEgg = this.getEggTime();
                this.addStamina(this.getEggStamina());
        	}else{
        		--this.timeUntilNextEgg;
        	}
        }	
    }
    
    public void setGrowingAge(int p_70873_1_){
    	if(p_70873_1_ > ChickenGenesCore.EntityGeneChickenGrowSpeedMax)p_70873_1_ = ChickenGenesCore.EntityGeneChickenGrowSpeedMax;
        this.dataWatcher.updateObject(12, Integer.valueOf(p_70873_1_));
        this.setScaleForAge(this.isChild());
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
    	str += String.valueOf(this.getHealth()) + "|";
    	str += String.valueOf(this.timeUntilNextEgg) + "|";
    	str += String.valueOf(this.getGeneData().getDataString()) + "";
    	return str;
    }
    
    public void setChickenDataString(String chicken_data_string){
    	String[] values = chicken_data_string.split("|");
    	this.setHealth(Float.valueOf(values[0]));
    	this.timeUntilNextEgg = Integer.parseInt(values[1]);
    	this.setGeneData(new GeneData(values[2]));
    }
    
	public GeneData getGeneData(){
    	return new GeneData(this.dataWatcher.getWatchableObjectString(DW_GENEDATA));
    }
    
    public void setGeneData(GeneData gene){
    	this.dataWatcher.updateObject(DW_GENEDATA,gene.getDataString());
    	this.changeGeneState(gene);
    }
    
    protected double getGeneMaxHealth(){
    	return this.base_max_health + (double)this.getGeneData().maxhealth;
    }
    
    public float getStamina(){
    	return this.dataWatcher.getWatchableObjectFloat(DW_CHICKEN_STAMINA);
    }
    
    public void setStamina(float stamina){
    	this.dataWatcher.updateObject(DW_CHICKEN_STAMINA,stamina);
    }
    
    public void addStamina(float value){
    	float stamina = this.getStamina() + value;
    	if(stamina > 100.0F)stamina = 100.0F;
    	this.setStamina(stamina);
    }
    
    public int getInGeneCount(){
    	return this.dataWatcher.getWatchableObjectInt(DW_CHICKEN_IN_GENE_COUNT);
    }
    
    public void setInGeneCount(int value){
    	this.dataWatcher.updateObject(DW_CHICKEN_IN_GENE_COUNT,value);
    }
    
    public void addInGeneCount(){
    	this.setInGeneCount(this.getInGeneCount() + 1);
    }
    
    public boolean interact(EntityPlayer p_70085_1_){
        ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();
        
        /*
         * for ChickenWhistle
         */
        if (this.isTamed()){
        	if(
        			itemstack != null && 
        			this.func_152114_e(p_70085_1_) && 
        			!this.worldObj.isRemote && 
        			itemstack.getItem() == ChickenGenesCore.itemChickenWhistle
        			){
        		this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity((PathEntity)null);
                this.setTarget((Entity)null);
                this.setAttackTarget((EntityLivingBase)null);
        	}
        }else if (
        		itemstack != null && 
				!this.worldObj.isRemote &&
        		itemstack.getItem() == ChickenGenesCore.itemChickenWhistle && 
        		!this.isAngry()
        		){
        	this.setTamed(true);
            this.setPathToEntity((PathEntity)null);
            this.setAttackTarget((EntityLivingBase)null);
            this.func_152115_b(p_70085_1_.getUniqueID().toString());
            this.playTameEffect(true);
            this.worldObj.setEntityState(this, (byte)7);
            return true;
            
        }
        
        if (
        		itemstack != null && 
        		itemstack.getItem() instanceof ItemSweetSeed && 
        		!this.isAngry()
        		){
        	this.lastSeed = itemstack;
        }
        
        /*
         * Stamina and Health
         */
        if (
        		itemstack != null && 
        		itemstack.getItem() == Items.wheat_seeds
        		){
        	if(this.getStamina() < 100.0F || this.getHealth() < this.getGeneMaxHealth()){
        		this.addStamina(ChickenGenesCore.AddStaminaValueBySeed);
            	this.heal(ChickenGenesCore.AddHealthValueBySeed);
            	float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                this.worldObj.spawnParticle("note",this.posX + (double)f1,this.posY + 0.8D,this.posZ + (double)f2,this.motionX + f1,this.motionY + f1,this.motionZ + f1);
                if (!p_70085_1_.capabilities.isCreativeMode){
                    --itemstack.stackSize;
                }
            	if (itemstack.stackSize <= 0){
            		p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, (ItemStack)null);
    			}
        	}
        }
        
        
        /*
         * for ChickenContainer
         */
        if (
        		itemstack != null && 
        		itemstack.getItem() == ChickenGenesCore.itemChickenContainer
        		){
        	
    		NBTTagCompound nbt = itemstack.getTagCompound();
        	if(nbt == null){
        		itemstack.setTagCompound(new NBTTagCompound());
        		nbt = itemstack.getTagCompound();	
        	}
        	
        	if(!nbt.hasKey("ChickenContainerState") || nbt.getInteger("ChickenContainerState") == 0){
        		this.writeEntityToNBT(nbt);
    			nbt.setString("ChickenData", EntityList.getEntityString(this));
    			nbt.setString("GeneData", this.getGeneData().getDataString());
    			if(this.hasCustomNameTag())
    				nbt.setString("ChickenName",this.getCustomNameTag());
    			nbt.setInteger("ChickenContainerState", 1);
    			itemstack.setTagCompound(nbt);
    			this.setDead();
        	}
        }
        
        /*
         * for Drug
         */
        if (
        		itemstack != null && 
        		itemstack.getItem() == ChickenGenesCore.itemChickenSyringeDoping
        		){
        	for (int i = 0; i < 7; i++){
        		float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
        		float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
        		this.worldObj.spawnParticle("happyVillager",this.posX + (double)f1,this.posY + 0.8D,this.posZ + (double)f2,this.motionX + f1 + f2,this.motionY + f1 + f2,this.motionZ + f1 + f2);
        	}
        	if (!p_70085_1_.capabilities.isCreativeMode){
                --itemstack.stackSize;
            }
        }
        
        /*
         * add Gene
         */
        if (
        		itemstack != null && 
        		itemstack.getItem() == ChickenGenesCore.itemChickenSyringeGene
        		){
        	if(this.getInsertGeneResult()){
        		for (int i = 0; i < 4; i++){
            		float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
            		float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
            		this.worldObj.spawnParticle("happyVillager",this.posX + (double)f1,this.posY + 0.8D,this.posZ + (double)f2,this.motionX + f1 + f2,this.motionY + f1 + f2,this.motionZ + f1 + f2);
            	}
            	GeneData add_gene = ((ItemChickenSyringeGene)itemstack.getItem()).getGeneData(itemstack);
            	if(add_gene == null)add_gene = new GeneData();
            	GeneData own_gene = this.getGeneData();
        		GeneData new_gene = own_gene.addGene(add_gene);
        		this.setGeneData(new_gene);
            	this.addInGeneCount();
        	}else{
        		this.setHealth(0);
        	}
        	if (!p_70085_1_.capabilities.isCreativeMode){
                --itemstack.stackSize;
            }
        }
        
        /*
         * insert Mutanted Gene
         */
        if (
        		itemstack != null && 
        		itemstack.getItem() == ChickenGenesCore.itemChickenSyringeMutation
        		){
        	if(this.getInsertGeneResult()){
        		for (int i = 0; i < 4; i++){
            		float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
            		float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
            		this.worldObj.spawnParticle("mobSpell",this.posX + (double)f1,this.posY + 0.8D,this.posZ + (double)f2,this.motionX + f1 + f2,this.motionY + f1 + f2,this.motionZ + f1 + f2);
            	}
            	GeneData gene = new GeneData();
            	GeneData own_gene = this.getGeneData();
        		GeneData new_gene = own_gene.addGene(gene);
        		this.setGeneData(new_gene);
            	this.addInGeneCount();
        	}else{
        		this.setHealth(0);
        	}
        	if (!p_70085_1_.capabilities.isCreativeMode){
                --itemstack.stackSize;
            }
        }
        
        return super.interact(p_70085_1_);
    }
    
    public boolean getInsertGeneResult(){
    	int count = this.getInGeneCount();
    	if(count == 0)return true;
    	float val = (float) (count*0.01);
    	return (Math.random() > val);
    }
    
    public float getEggStamina(){
    	float base = 5.0F;
    	float v = base - (float) (this.getGeneData().efficiency * ChickenGenesCore.GeneEfficiencyFactorValue);
    	if(v < 0.01F)v = 0.01F;
    	return v * -1;
    }
    
    public int getEggTime(){
    	int time = this.rand.nextInt(6000) + 6000 - this.getGeneData().eggspeed * ChickenGenesCore.GeneEggSpeedFactorValue;
    	if(time < 20)time = 20;
    	if(time > ChickenGenesCore.EntityGeneChickenTimeUntilNextEggMax)time = ChickenGenesCore.EntityGeneChickenTimeUntilNextEggMax;
    	return time;
    }
    
	public void writeEntityToNBT(NBTTagCompound p_70014_1_){
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setBoolean("Angry", this.isAngry());
        p_70014_1_.setString(NBT_GENEDATA, this.dataWatcher.getWatchableObjectString(DW_GENEDATA));
        p_70014_1_.setFloat(NBT_CHICKEN_STAMINA, this.dataWatcher.getWatchableObjectFloat(DW_CHICKEN_STAMINA));
        p_70014_1_.setInteger(NBT_CHICKEN_IN_GENE_COUNT, this.dataWatcher.getWatchableObjectInt(DW_CHICKEN_IN_GENE_COUNT));
    }
    
    public void readEntityFromNBT(NBTTagCompound p_70037_1_){
        super.readEntityFromNBT(p_70037_1_);
        this.setAngry(p_70037_1_.getBoolean("Angry"));
        this.dataWatcher.updateObject(DW_GENEDATA, p_70037_1_.getString(NBT_GENEDATA));
        this.dataWatcher.updateObject(DW_CHICKEN_STAMINA, p_70037_1_.getFloat(NBT_CHICKEN_STAMINA));
        this.dataWatcher.updateObject(DW_CHICKEN_IN_GENE_COUNT, p_70037_1_.getInteger(NBT_CHICKEN_IN_GENE_COUNT));
        this.timeUntilNextEgg = this.getEggTime();
    }
    
	public boolean isUniteSuccessGene(float rate){
		return Math.random() < rate;
    }
    
    public float getUniteSuccessRate(int p,int m){
    	float[] rates = {
    			0.4F,	//sweet seed level 1
    			0.6F,	//sweet seed level 2
    			0.8F,	//sweet seed level 3
    			1.0F	//sweet seed level 4
    			};
    	return (rates[p] + rates[m])/2;
    }
    
	public boolean isBreedingItem(ItemStack p_70877_1_){
        return p_70877_1_ != null && p_70877_1_.getItem() instanceof ItemSweetSeed;
    }
    
    protected int getExperiencePoints(EntityPlayer p_70693_1_){
        return super.getExperiencePoints(p_70693_1_);
    }

    protected boolean canDespawn(){
        return !this.isTamed() && this.ticksExisted > 2400;
    }
	
	public void setAttackTarget(EntityLivingBase p_70624_1_){
        super.setAttackTarget(p_70624_1_);
        if (p_70624_1_ == null){
            this.setAngry(false);
        }else if (!this.isTamed()){
            this.setAngry(true);
        }
    }
    
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_){
        if (this.isEntityInvulnerable()){
            return false;
        }else{
            Entity entity = p_70097_1_.getEntity();
            this.aiSit.setSitting(false);
            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)){
                p_70097_2_ = (p_70097_2_ + 1.0F) / 2.0F;
            }
            GeneData gene = this.getGeneData();
            if(gene.defense != 0){
            	p_70097_2_ = (float) (p_70097_2_ - (gene.defense * ChickenGenesCore.GeneDefenseFactorValue));
            	if(p_70097_2_ < 0){
            		Random rand = new Random();
            		if(Math.random() < ChickenGenesCore.GeneDefenseAdjustRate){
            			p_70097_2_ = 1.0F;
            		}
            	}
            }
            return super.attackEntityFrom(p_70097_1_, p_70097_2_);
        }
    }
    
	public boolean attackEntityAsMob(Entity p_70652_1_){
        float i = 2.0F;
		GeneData gene = this.getGeneData();
		i = (float) (i + gene.attack * ChickenGenesCore.GeneAttackFactorValue);
		return p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), (float)i);
    }
    
    public void setAngry(boolean p_70916_1_){
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        if (p_70916_1_){
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 2)));
        }else{
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -3)));
        }
    }
    
    public boolean isAngry(){
    	return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }
    
    protected void fall(float p_70069_1_) {}
    
    protected String getLivingSound(){
        return "mob.chicken.say";
    }
    
    protected String getHurtSound(){
        return "mob.chicken.hurt";
    }
    
    protected String getDeathSound(){
        return "mob.chicken.hurt";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_){
        this.playSound("mob.chicken.step", 0.15F, 1.0F);
    }


//  public void updateRiderPosition()
//  {
//      super.updateRiderPosition();
//      float f = MathHelper.sin(this.renderYawOffset * (float)Math.PI / 180.0F);
//      float f1 = MathHelper.cos(this.renderYawOffset * (float)Math.PI / 180.0F);
//      float f2 = 0.1F;
//      float f3 = 0.0F;
//      this.riddenByEntity.setPosition(this.posX + (double)(f2 * f), this.posY + (double)(this.height * 0.5F) + this.riddenByEntity.getYOffset() + (double)f3, this.posZ - (double)(f2 * f1));
//      
//      if (this.riddenByEntity instanceof EntityLivingBase)
//      {
//          ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
//      }
//  }
//  
//  protected Item getDropItem()
//  {
//      return Items.feather;
//  }
//  
//  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
//  {
//      int j = this.rand.nextInt(3) + this.rand.nextInt(1 + p_70628_2_);
//
//      for (int k = 0; k < j; ++k)
//      {
//          this.dropItem(Items.feather, 1);
//      }
//      
//      if (this.isBurning())
//      {
//          this.dropItem(Items.cooked_chicken, 1);
//      }
//      else
//      {
//          this.dropItem(Items.chicken, 1);
//      }
//  }
    
}
