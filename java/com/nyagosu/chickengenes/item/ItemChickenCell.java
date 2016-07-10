package com.nyagosu.chickengenes.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.entity.GeneData;
import com.nyagosu.chickengenes.util.DebugTool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemChickenCell extends ItemChickenBaseGene {
	
	public ItemChickenCell(){
		super();
		this.setUnlocalizedName("ItemChickenCell");
		this.setTextureName("ChickenGenes:chickencell");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer){
		if(!world.isRemote){
			NBTTagCompound nbt = itemstack.getTagCompound();
			if(nbt == null)
				return itemstack;
			String gene_data_string = nbt.getString("GeneData");
			GeneData gene = new GeneData(gene_data_string);
		}
		return itemstack;
	}
}