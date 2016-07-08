package com.nyagosu.chickengenes.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.entity.GeneData;
import com.nyagosu.chickengenes.util.DebugTool;
import com.nyagosu.chickengenes.util.TooCon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemChickenContainer extends Item {
	
	private IIcon[] iicon = new IIcon[16];
	
	public ItemChickenContainer(){
		this.setUnlocalizedName("ItemChickenContainer");
		this.setTextureName("chickengenes:chickencontainer");
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.maxStackSize = 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iicon) {
		this.iicon[0] = iicon.registerIcon(this.getIconString() + "0");
		this.iicon[1] = iicon.registerIcon(this.getIconString() + "1");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack itemstack, int pass){
		return getIconIndex(itemstack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack itemstack){
		NBTTagCompound nbt = itemstack.getTagCompound();
		if (nbt == null){
			return this.iicon[0];
		}
		if (nbt.hasKey("ChickenContainerState")){
			return this.iicon[nbt.getInteger("ChickenContainerState")];
		}
		return this.iicon[0];
	}
	
	public EntityGeneChicken spawnChicken(ItemStack itemstack,EntityPlayer player, World world,double x, double y, double z){
		NBTTagCompound nbt = itemstack.getTagCompound();
		if(nbt == null){
    		return null;
    	}
    	if(!nbt.hasKey("ChickenContainerState") && !nbt.hasKey("ChickenData")){
    		return null;
    	}
    	
    	Entity entity = EntityList.createEntityByName(nbt.getString("ChickenData"), world);
    	
		if(entity != null){
			if(entity instanceof EntityGeneChicken){
				EntityGeneChicken storedAnimal = (EntityGeneChicken)entity;
				
				MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
	            if (movingobjectposition == null)return null;
	            if (movingobjectposition.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)return null;
            	
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;
                
                storedAnimal.readEntityFromNBT(itemstack.getTagCompound());
				entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(player.worldObj.rand.nextFloat() * 360.0F), 0.0F);
				storedAnimal.rotationYawHead = storedAnimal.rotationYaw;
				storedAnimal.renderYawOffset = storedAnimal.rotationYaw;
				storedAnimal.onSpawnWithEgg((IEntityLivingData)null);
				if(!player.worldObj.isRemote){
					if(world.spawnEntityInWorld(entity)){
						itemstack.getTagCompound().removeTag("ChickenData");
						itemstack.getTagCompound().removeTag("ChickenName");
						NBTTagCompound animalCompound = new NBTTagCompound();
						animalCompound.setInteger("ChickenContainerState", 0);
						itemstack.setTagCompound(animalCompound);
					}
				}
				
				return storedAnimal;
			}
		}
    	return null;
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
            Entity entity = spawnChicken(par1ItemStack, par2EntityPlayer, par3World ,(double)par4 + 0.5D, (double)par5 + d0, (double)par6 + 0.5D);
            if (entity != null){
            	if (entity instanceof EntityLivingBase && par1ItemStack.hasDisplayName()){
                    ((EntityLiving)entity).setCustomNameTag(par1ItemStack.getDisplayName());
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
                    	Entity entity = spawnChicken(par1ItemStack,par3EntityPlayer, par2World ,(double)i, (double)j, (double)k);
                    	if (entity != null){
                            if (entity instanceof EntityLivingBase && par1ItemStack.hasDisplayName()){
                                ((EntityLiving)entity).setCustomNameTag(par1ItemStack.getDisplayName());
                            }
                        }
                    }
                }
                return par1ItemStack;
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advanced){
    	NBTTagCompound nbt = itemStack.getTagCompound();
    	if(nbt == null)return ;
    	GeneData gene = new GeneData(nbt.getString("GeneData"));
    	
    	String title = "[ GeneData ]";
    	if(nbt.hasKey("ChickenName"))title += " Name : " + nbt.getString("ChickenName");
    	
    	list.add(title);
    	
    	Entity entity = EntityList.createEntityByName(nbt.getString("ChickenData"), player.worldObj);
    	EntityGeneChicken chicken = (EntityGeneChicken)entity;
    	
    	TooCon.log(itemStack.getDisplayName());
    	
    	
    	
        list.add(this.getToolChipValue("MaxHealth",gene.maxhealth));
        list.add(this.getToolChipValue("Attack",gene.attack));
        list.add(this.getToolChipValue("Defense",gene.defense));
        list.add(this.getToolChipValue("EggSpeed",gene.eggspeed));
        list.add(this.getToolChipValue("Efficiency",gene.efficiency));
        list.add(this.getToolChipValue("GrowSpeed",gene.growspeed));
        list.add(this.getToolChipValue("MoveSpeed",gene.movespeed));
    }
    
    private String getToolChipName(String code,String name){
    	String str = code + " | ";
    	return str + name;
    }
    
    private String getToolChipValue(String code,int value){
		String str = code + " | ";
		String value_str;
		if(value == 0)
		{
			value_str = String.valueOf(value);
		}
		else if(value > 0)
		{
			value_str = ChatFormatting.GREEN + String.valueOf(value);
		}
		else
		{
			value_str = ChatFormatting.RED + String.valueOf(value);
		}
		return str + value_str;
	}
}
