package com.nyagosu.chickengenes.item;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemSpawnEgg extends ItemMonsterPlacer {
	
	public final int primaryColor;
	public final int secondaryColor;
	
    public static Class[] spawnableEntities = {
    		EntityGeneChicken.class,
    };
    
    public ItemSpawnEgg(int par1, int par2){
    	this.setUnlocalizedName("ItemSpawnEgg");
        this.setHasSubtypes(true);
        this.primaryColor = par1;
        this.secondaryColor = par2;
        this.setTextureName("spawn_egg");
        this.setCreativeTab(ChickenGenesCore.tabChickenGenes);
        this.maxStackSize = 64;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2){
        return par2 == 0 ? primaryColor : secondaryColor;
    }
    
    public String getItemStackDisplayName(ItemStack par1ItemStack){
        String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        Class c = spawnableEntities[par1ItemStack.getItemDamage()];
        String s1 = (String) EntityList.classToStringMapping.get(c);
        if (s1 != null){
            s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
        }
        return s;
    }
    
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10){
        if (par3World.isRemote){
            return true;
        }else{
            Block block = par3World.getBlock(par4, par5, par6);
            par4 += Facing.offsetsXForSide[par7];
            par5 += Facing.offsetsYForSide[par7];
            par6 += Facing.offsetsZForSide[par7];
            double d0 = 0.0D;
 
            if (par7 == 1 && block.getRenderType() == 11){
                d0 = 0.5D;
            }
            Entity entity = spawnCreature(par3World, par1ItemStack.getItemDamage(), (double)par4 + 0.5D, (double)par5 + d0, (double)par6 + 0.5D);
            if (entity != null){
                if (entity instanceof EntityLivingBase && par1ItemStack.hasDisplayName()){
                    ((EntityLiving)entity).setCustomNameTag(par1ItemStack.getDisplayName());
                }
                if (!par2EntityPlayer.capabilities.isCreativeMode){
                    --par1ItemStack.stackSize;
                }
            }
            return true;
        }
    }
    
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
        if (par2World.isRemote){
            return par1ItemStack;
        }else{
        	MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
            if (movingobjectposition == null){
                return par1ItemStack;
            }else{
                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK){
                	
                    int i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;
 
                    if (!par2World.canMineBlock(par3EntityPlayer, i, j, k)){
                        return par1ItemStack;
                    }
 
                    if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, par1ItemStack)){
                        return par1ItemStack;
                    }
 
                    if (par2World.getBlock(i, j, k) instanceof BlockLiquid){
                        Entity entity = spawnCreature(par2World, par1ItemStack.getItemDamage(), (double)i, (double)j, (double)k);
 
                        if (entity != null){
                            if (entity instanceof EntityLivingBase && par1ItemStack.hasDisplayName()){
                                ((EntityLiving)entity).setCustomNameTag(par1ItemStack.getDisplayName());
                            }
 
                            if (!par3EntityPlayer.capabilities.isCreativeMode){
                                --par1ItemStack.stackSize;
                            }
                        }
                    }
                }
 
                return par1ItemStack;
            }
        }
    }
    
    public static Entity spawnCreature(World par0World, int par1, double par2, double par4, double par6)
    {
        Class c = spawnableEntities[par1];
        Entity entity = null;
        try {
        	
        	
        	
            entity = (Entity)c.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par0World});
 
            EntityLiving entityliving = (EntityLiving)entity;
            entity.setLocationAndAngles(par2, par4, par6, MathHelper.wrapAngleTo180_float(par0World.rand.nextFloat() * 360.0F), 0.0F);
            entityliving.rotationYawHead = entityliving.rotationYaw;
            entityliving.renderYawOffset = entityliving.rotationYaw;
            entityliving.onSpawnWithEgg((IEntityLivingData)null);
            par0World.spawnEntityInWorld(entity);
            entityliving.playLivingSound();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
 
        return entity;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2, List par3){
        for(int i = 0; i < spawnableEntities.length; ++i) {
            par3.add(new ItemStack(par1, 1, i));
        }
    }
    
}
