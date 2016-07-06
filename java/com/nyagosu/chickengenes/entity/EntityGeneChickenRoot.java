package com.nyagosu.chickengenes.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.nyagosu.chickengenes.item.ItemSweetSeed;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityGeneChickenRoot extends EntityTameable {
	
	public float field_70886_e;
    public float destPos;
    public float field_70884_g;
    public float field_70888_h;
    
    public float field_70889_i = 1.0F;
    
	public boolean field_152118_bv;
	public int timeUntilNextEgg;
	
	public ItemStack lastSeed;
    
	public static final int DW_GENEDATA = 25;
    public static final String NBT_GENEDATA = "GENE_DATA";

	public EntityGeneChickenRoot(World p_i1682_1_){
    	super(p_i1682_1_);
    }

	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) {
		return null;
	}

    protected void applyEntityAttributes(){
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }
    
    protected void entityInit(){
        super.entityInit();
        this.dataWatcher.addObject(18, new Float(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte)0));
        this.dataWatcher.addObject(20, new Byte((byte)BlockColored.func_150032_b(1)));
        this.dataWatcher.addObject(DW_GENEDATA,"");
    }
    
    protected double getGeneMaxHealth(){
    	double base = 4.0D;
    	return base + (double)this.getGeneData().maxhealth;
    }
    
    public void setGeneDataInit(GeneData gene){
    	this.setGeneData(gene);
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getGeneMaxHealth());
    	this.setHealth((float)this.getGeneMaxHealth());
    }
	
	public void writeEntityToNBT(NBTTagCompound p_70014_1_){
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setBoolean("Angry", this.isAngry());
        p_70014_1_.setByte("CollarColor", (byte)this.getCollarColor());
        p_70014_1_.setBoolean("IsChickenJockey", this.field_152118_bv);
        p_70014_1_.setString(NBT_GENEDATA, this.dataWatcher.getWatchableObjectString(DW_GENEDATA));
    }
    
    public void readEntityFromNBT(NBTTagCompound p_70037_1_){
        super.readEntityFromNBT(p_70037_1_);
        this.field_152118_bv = p_70037_1_.getBoolean("IsChickenJockey");
        this.setAngry(p_70037_1_.getBoolean("Angry"));
        if (p_70037_1_.hasKey("CollarColor", 99))
        {
            this.setCollarColor(p_70037_1_.getByte("CollarColor"));
        }
        this.dataWatcher.updateObject(DW_GENEDATA, p_70037_1_.getString(NBT_GENEDATA));
    }
	
	public boolean isAIEnabled(){
        return true;
    }
	
	public GeneData getGeneData(){
    	GeneData gene = new GeneData(this.dataWatcher.getWatchableObjectString(DW_GENEDATA));
    	return gene;
    }
    
    public void setGeneData(GeneData gene){
    	this.dataWatcher.updateObject(DW_GENEDATA,gene.getDataString());
    }
    
    protected void fall(float p_70069_1_) {}
    
    protected String getLivingSound()
    {
        return "mob.chicken.say";
    }
    
    protected String getHurtSound()
    {
        return "mob.chicken.hurt";
    }
    
    protected String getDeathSound()
    {
        return "mob.chicken.hurt";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.chicken.step", 0.15F, 1.0F);
    }
	
	public boolean isGeneSuccess(int c){
    	ArrayList<Integer> rates = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0,0,0,0));
    	for(int i = 0;i < c;i++)rates.set(i,1);
    	Collections.shuffle(rates);
    	return (rates.get((int) (Math.random()*rates.size()))) == 1;
    }
    
    public int getArrangeRate(int p,int m){
    	int[] rates = {4,6,8,10};
    	return (rates[p] + rates[m])/2;
    }
    
    public int arrangeValue(int a,int b)
    {
    	return ( a + b ) / 2 + ( (int)Math.random() * 50 + 50 );
    }
	
	public boolean isBreedingItem(ItemStack p_70877_1_)
    {
        return p_70877_1_ != null && p_70877_1_.getItem() instanceof ItemSweetSeed;
    }
    
    protected int getExperiencePoints(EntityPlayer p_70693_1_)
    {
        return this.func_152116_bZ() ? 10 : super.getExperiencePoints(p_70693_1_);
    }
    
//    protected boolean canDespawn()
//    {
//        return this.func_152116_bZ() && this.riddenByEntity == null;
//    }
    
    protected boolean canDespawn()
    {
        return !this.isTamed() && this.ticksExisted > 2400;
    }
    
    public void updateRiderPosition()
    {
        super.updateRiderPosition();
        float f = MathHelper.sin(this.renderYawOffset * (float)Math.PI / 180.0F);
        float f1 = MathHelper.cos(this.renderYawOffset * (float)Math.PI / 180.0F);
        float f2 = 0.1F;
        float f3 = 0.0F;
        this.riddenByEntity.setPosition(this.posX + (double)(f2 * f), this.posY + (double)(this.height * 0.5F) + this.riddenByEntity.getYOffset() + (double)f3, this.posZ - (double)(f2 * f1));
        
        if (this.riddenByEntity instanceof EntityLivingBase)
        {
            ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
        }
    }

    public boolean func_152116_bZ()
    {
        return this.field_152118_bv;
    }

    public void func_152117_i(boolean p_152117_1_)
    {
        this.field_152118_bv = p_152117_1_;
    }
	
	public void setAttackTarget(EntityLivingBase p_70624_1_)
    {
        super.setAttackTarget(p_70624_1_);

        if (p_70624_1_ == null)
        {
            this.setAngry(false);
        }
        else if (!this.isTamed())
        {
            this.setAngry(true);
        }
    }
    
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            Entity entity = p_70097_1_.getEntity();
            this.aiSit.setSitting(false);

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
            {
                p_70097_2_ = (p_70097_2_ + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(p_70097_1_, p_70097_2_);
        }
    }
	
	public boolean attackEntityAsMob(Entity p_70652_1_)
    {
        int i = this.isTamed() ? 4 : 2;
        return p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), (float)i);
    }
    
    public int getCollarColor()
    {
        return this.dataWatcher.getWatchableObjectByte(20) & 15;
    }
    
    public void setAngry(boolean p_70916_1_)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        if (p_70916_1_)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 2)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -3)));
        }
    }
    
    public boolean isAngry()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }
    
    public void setCollarColor(int p_82185_1_)
    {
        this.dataWatcher.updateObject(20, Byte.valueOf((byte)(p_82185_1_ & 15)));
    }

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
