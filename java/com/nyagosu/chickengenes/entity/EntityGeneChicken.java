package com.nyagosu.chickengenes.entity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.item.ItemSweetSeed;
import com.nyagosu.chickengenes.util.DebugTool;
import com.nyagosu.chickengenes.util.Randory;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityGeneChicken extends EntityTameable {
	
	public float field_70886_e;
    public float destPos;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;
    /** The time until the next egg is spawned. */
    public int timeUntilNextEgg;
    public boolean field_152118_bv;
    private static final String __OBFID = "CL_00001639";
    
    private EntityPlayer field_146084_br;
    private int inLove;
    
    private ItemStack lastSeed;
    
    public static final int DW_GENEDATA = 25;
    public static final String NBT_GENEDATA = "GENE_DATA";
    
    public EntityGeneChicken(World p_i1682_1_){
    	super(p_i1682_1_);
    	this.init();
    }
    
    public EntityGeneChicken(World p_i1682_1_, GeneData gene){
    	super(p_i1682_1_);
    	this.setGeneData(gene);
    	this.init();
    }
    
    public void init(){
        this.setSize(0.3F, 0.7F);
        this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        this.setTamed(false);
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.wheat_seeds, false));
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
		this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
//		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheep.class, 200, false));
    }
    
    public boolean isAIEnabled(){
        return true;
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
        
        GeneData gene = new GeneData();
        this.dataWatcher.addObject(DW_GENEDATA,gene.getDataString());
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
    
    public GeneData getGeneData(){
    	GeneData gene = new GeneData(this.dataWatcher.getWatchableObjectString(DW_GENEDATA));
    	return gene;
    }
    
    public void setGeneData(GeneData gene){
    	this.dataWatcher.updateObject(DW_GENEDATA,gene.getDataString());
    }
    
    public boolean isAngry()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        
        if (this.destPos < 0.0F)
        {
            this.destPos = 0.0F;
        }

        if (this.destPos > 1.0F)
        {
            this.destPos = 1.0F;
        }

        if (!this.onGround && this.field_70889_i < 1.0F)
        {
            this.field_70889_i = 1.0F;
        }

        this.field_70889_i = (float)((double)this.field_70889_i * 0.9D);

        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }

        this.field_70886_e += this.field_70889_i * 2.0F;

        if (!this.worldObj.isRemote && !this.isChild() && !this.func_152116_bZ() && --this.timeUntilNextEgg <= 0)
        {
            this.playSound("mob.chicken.plop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(Items.egg, 1);
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        }
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

//    protected Item getDropItem()
//    {
//        return Items.feather;
//    }
//    
//    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
//    {
//        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + p_70628_2_);
//
//        for (int k = 0; k < j; ++k)
//        {
//            this.dropItem(Items.feather, 1);
//        }
//        
//        if (this.isBurning())
//        {
//            this.dropItem(Items.cooked_chicken, 1);
//        }
//        else
//        {
//            this.dropItem(Items.chicken, 1);
//        }
//    }

    public EntityGeneChicken createChild(EntityAgeable p_90011_1_)
    {
    	EntityGeneChicken p = (EntityGeneChicken)p_90011_1_;
    	
    	int p_seed_damage = (p.lastSeed != null)?p.lastSeed.getItemDamage():0;
    	int m_seed_damage = (this.lastSeed != null)?this.lastSeed.getItemDamage():0;
    	int c = getArrangeRate(p_seed_damage,m_seed_damage);
    	
    	DebugTool.print("成功率 : " + String.valueOf(c * 10) + "%");
    	
    	GeneData new_gene = null;
    	GeneData p_gene = p.getGeneData();
    	GeneData m_gene = this.getGeneData();
    	p_gene.mate_flag = 1;
    	m_gene.mate_flag = 1;
    	if(this.isGeneSuccess(c)){
    		//success
    		DebugTool.print("success");
    		new_gene = m_gene.mix(p_gene);
    	}else{
    		//failure
    		DebugTool.print("sippai");
    		new_gene = new GeneData();
    	}
    	p.setGeneData(p_gene);
    	this.setGeneData(m_gene);
    	
    	return new EntityGeneChicken(this.worldObj,new_gene);
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
    
    public boolean canMateWith(EntityAnimal p_70878_1_)
    {
        if(p_70878_1_ == this ? false : (p_70878_1_.getClass() != this.getClass() ? false : this.isInLove() && p_70878_1_.isInLove()))
        {
        	GeneData my_gene = this.getGeneData();
        	EntityGeneChicken target = (EntityGeneChicken)p_70878_1_;
        	GeneData target_gene = target.getGeneData();
        	
        	return (my_gene.sex != target_gene.sex);
        }
        else
        {
        	return false;
        }
    }
    
    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer p_70085_1_)
    {
        ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();
        
        if (this.isTamed())
        {
            if (itemstack != null)
            {
                if (itemstack.getItem() instanceof ItemFood)
                {
                    ItemFood itemfood = (ItemFood)itemstack.getItem();

                    if (itemfood.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < 20.0F)
                    {
                        if (!p_70085_1_.capabilities.isCreativeMode)
                        {
                            --itemstack.stackSize;
                        }

                        this.heal((float)itemfood.func_150905_g(itemstack));

                        if (itemstack.stackSize <= 0)
                        {
                            p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, (ItemStack)null);
                        }

                        return true;
                    }
                }
                else if (itemstack.getItem() == Items.dye)
                {
                    int i = BlockColored.func_150032_b(itemstack.getItemDamage());
                    if (!p_70085_1_.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
                    {
                        p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, (ItemStack)null);
                    }

                    return true;
                }
            }
            
            if (this.func_152114_e(p_70085_1_) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack))
            {
            	if(!(itemstack != null && itemstack.getItem() == ChickenGenesCore.itemChickenLoupe)){
            		this.aiSit.setSitting(!this.isSitting());
                    this.isJumping = false;
                    this.setPathToEntity((PathEntity)null);
                    this.setTarget((Entity)null);
                    this.setAttackTarget((EntityLivingBase)null);
            	}
            }
        }
        else if (itemstack != null && itemstack.getItem() == ChickenGenesCore.itemChickenBell && !this.isAngry())
        {
        	if (!this.worldObj.isRemote)
            {
            	this.setTamed(true);
                this.setPathToEntity((PathEntity)null);
                this.setAttackTarget((EntityLivingBase)null);
                this.setHealth(20.0F);
                this.func_152115_b(p_70085_1_.getUniqueID().toString());
                this.playTameEffect(true);
                this.worldObj.setEntityState(this, (byte)7);                
            }
            return true;
        }
        else if (itemstack != null && itemstack.getItem() instanceof ItemSweetSeed && !this.isAngry()){
        	this.lastSeed = itemstack;
        }
        else if (itemstack != null && itemstack.getItem() == Items.apple ){
        	this.setGrowingAge(100);
        	
        }
        
        return super.interact(p_70085_1_);
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
    
    public void setCollarColor(int p_82185_1_)
    {
        this.dataWatcher.updateObject(20, Byte.valueOf((byte)(p_82185_1_ & 15)));
    }
}
