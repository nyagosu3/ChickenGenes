package com.nyagosu.chickengenes.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.item.ItemChickenSyringeGene;
import com.nyagosu.chickengenes.item.ItemSweetSeed;
import com.nyagosu.chickengenes.util.Randory;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityGeneChickenRoot extends EntityTameable {
	
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
    
	public EntityGeneChickenRoot(World p_i1682_1_){
    	super(p_i1682_1_);
    }
	
	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_){
		return null;
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
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.base_movement_speed + (double)gene.movespeed * 0.001D);
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
         * for ChickenBell
         */
        if (this.isTamed()){
        	if(
        			itemstack != null && 
        			this.func_152114_e(p_70085_1_) && 
        			!this.worldObj.isRemote && 
        			itemstack.getItem() == ChickenGenesCore.itemChickenBell
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
        		itemstack.getItem() == ChickenGenesCore.itemChickenBell && 
        		!this.isAngry()
        		){
        	this.setTamed(true);
            this.setPathToEntity((PathEntity)null);
            this.setAttackTarget((EntityLivingBase)null);
            this.setHealth(20.0F);
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
        		this.addStamina(20.0F);
            	this.heal(2.0F);
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
         * Doping
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
         * insert Gene
         */
        if (
        		itemstack != null && 
        		itemstack.getItem() == ChickenGenesCore.itemChickenSyringeGene
        		){
        	if(this.getInsertGeneResult()){
        		for (int i = 0; i < 7; i++){
            		float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
            		float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
            		this.worldObj.spawnParticle("happyVillager",this.posX + (double)f1,this.posY + 0.8D,this.posZ + (double)f2,this.motionX + f1 + f2,this.motionY + f1 + f2,this.motionZ + f1 + f2);
            	}
            	GeneData gene = ((ItemChickenSyringeGene)itemstack.getItem()).getGeneData(itemstack);
        		GeneData new_gene = gene.mix(this.getGeneData());
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
        		for (int i = 0; i < 7; i++){
            		float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
            		float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
            		this.worldObj.spawnParticle("mobSpell",this.posX + (double)f1,this.posY + 0.8D,this.posZ + (double)f2,this.motionX + f1 + f2,this.motionY + f1 + f2,this.motionZ + f1 + f2);
            	}
            	GeneData gene = new GeneData();
        		GeneData new_gene = gene.mix(this.getGeneData());
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
    	Randory rand = new Randory();
    	rand.addRate(0,100-count);
		rand.addRate(1,count);
		return rand.getValue() == 0;
    }
    
    public float getEggStamina(){
    	float base = 5.0F;
    	float v = base - (float) (this.getGeneData().efficiency * 0.05);
    	if(v < 0.01F)v = 0.01F;
    	return v * -1;
    }
    
    public int getEggTime(){
    	int time = this.rand.nextInt(6000) + 6000 - this.getGeneData().eggspeed * 20;
    	if(time < 20)time = 20;
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
    }
    
	public boolean isUniteSuccessGene(int c){
    	ArrayList<Integer> rates = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0,0,0,0));
    	for(int i = 0;i < c;i++)rates.set(i,1);
    	Collections.shuffle(rates);
    	return (rates.get((int) (Math.random()*rates.size()))) == 1;
    }
    
    public int getUniteSuccessRate(int p,int m){
    	int[] rates = {4,6,8,10};
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
            	p_70097_2_ = (float) (p_70097_2_ - (gene.defense * 0.025));
            	
            	if(p_70097_2_ < 0){
            		Random rand = new Random();
            		int r = rand.nextInt(2);
            		if(r == 1)p_70097_2_ = 1.0F;
            	}
            }
            return super.attackEntityFrom(p_70097_1_, p_70097_2_);
        }
    }
    
	public boolean attackEntityAsMob(Entity p_70652_1_){
        float i = 2.0F;
		GeneData gene = this.getGeneData();
		i = (float) (i + gene.attack * 0.1);
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
