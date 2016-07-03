package com.nyagosu.chickengenes.entity;


import java.util.HashMap;

import com.nyagosu.chickengenes.util.DebugTool;
import com.nyagosu.chickengenes.util.Randory;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
    
    public static final int DW_GENEDATA = 25;
    public static final String NBT_GENEDATA = "GENE_DATA";
    
    public EntityGeneChicken(World p_i1682_1_){
    	
        super(p_i1682_1_);
        this.setSize(0.3F, 0.7F);
        this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        this.setTamed(false);
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.wheat_seeds, false));
//		this.getNavigator().setAvoidsWater(true);
//		this.tasks.addTask(1, new EntityAISwimming(this));
//		this.tasks.addTask(2, this.aiSit);
//		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
//		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
//		this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
//		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
//		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
//		this.tasks.addTask(9, new EntityAILookIdle(this));
//		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
//		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
//		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
//		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheep.class, 200, false));
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
        p_70014_1_.setBoolean("IsChickenJockey", this.field_152118_bv);
        p_70014_1_.setString(NBT_GENEDATA, this.dataWatcher.getWatchableObjectString(DW_GENEDATA));
    }
    
    public void readEntityFromNBT(NBTTagCompound p_70037_1_){
        super.readEntityFromNBT(p_70037_1_);
        this.field_152118_bv = p_70037_1_.getBoolean("IsChickenJockey");
        this.dataWatcher.updateObject(DW_GENEDATA, p_70037_1_.getString(NBT_GENEDATA));
    }
    
    public GeneData getGeneData(){
    	GeneData gene = new GeneData(this.dataWatcher.getWatchableObjectString(DW_GENEDATA));
    	return gene;
    }
    
    public boolean interact(EntityPlayer p_70085_1_){
    	
    	ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();
    	
    	if(itemstack != null && itemstack.getItem() == Items.apple){
    		if(!p_70085_1_.worldObj.isRemote){
    			GeneData gene = getGeneData();
    			DebugTool.print(gene.getDataString4Debug());
    		}
    	}
        
        return super.interact(p_70085_1_);
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

    protected Item getDropItem()
    {
        return Items.feather;
    }
    
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + p_70628_2_);

        for (int k = 0; k < j; ++k)
        {
            this.dropItem(Items.feather, 1);
        }

        if (this.isBurning())
        {
            this.dropItem(Items.cooked_chicken, 1);
        }
        else
        {
            this.dropItem(Items.chicken, 1);
        }
    }

    public EntityGeneChicken createChild(EntityAgeable p_90011_1_)
    {
        return new EntityGeneChicken(this.worldObj);
    }
    
    public boolean isBreedingItem(ItemStack p_70877_1_)
    {
        return p_70877_1_ != null && p_70877_1_.getItem() instanceof ItemSeeds;
    }
    
    protected int getExperiencePoints(EntityPlayer p_70693_1_)
    {
        return this.func_152116_bZ() ? 10 : super.getExperiencePoints(p_70693_1_);
    }
    
    protected boolean canDespawn()
    {
        return this.func_152116_bZ() && this.riddenByEntity == null;
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
    
}
