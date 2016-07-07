package com.nyagosu.chickengenes.item;

import java.util.List;

import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.entity.GeneData;
import com.nyagosu.chickengenes.util.DebugTool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
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
	
    @Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
    	
    	Entity entity = EntityList.createEntityByName(par1ItemStack.getTagCompound().getString("ChickenData"), par3World);
		par1ItemStack.getTagCompound().removeTag("ChickenData");
		if(entity != null){
			if(entity instanceof EntityAnimal){
				
				EntityAnimal storedAnimal = (EntityAnimal)entity;
				
				storedAnimal.readEntityFromNBT(par1ItemStack.getTagCompound());
				double x = (double)par4 + 0.5D;
				double y = (double)par5 + 1.0D;
				double z = (double)par6 + 0.5D;
				
				entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(par2EntityPlayer.worldObj.rand.nextFloat() * 360.0F), 0.0F);
				storedAnimal.rotationYawHead = storedAnimal.rotationYaw;
				storedAnimal.renderYawOffset = storedAnimal.rotationYaw;
				storedAnimal.onSpawnWithEgg((IEntityLivingData)null);
				if(!par2EntityPlayer.worldObj.isRemote){
					par3World.spawnEntityInWorld(entity);
				}
				par1ItemStack.getTagCompound().setString("ChickenData", "");
				NBTTagCompound animalCompound = new NBTTagCompound();
				animalCompound.setInteger("ChickenContainerState", 0);
				par1ItemStack.setTagCompound(animalCompound);
			}
		}
		return false;
	}
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advanced){
    	NBTTagCompound nbt = itemStack.getTagCompound();
    	if(nbt == null)return ;
    	GeneData gene = new GeneData(nbt.getString("GeneData"));
    	list.add(gene.getDataString4Debug());
    }
}
