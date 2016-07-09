package com.nyagosu.chickengenes.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;
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

public class ItemChickenCell extends Item {
	public ItemChickenCell(){
		this.setHasSubtypes(true);
		this.setUnlocalizedName("ItemChickenCell");
		this.setTextureName("ChickenGenes:chickencell");
//        this.setCreativeTab(CreativeTabs.tabMisc);
        this.maxStackSize = 1;
	}
	
	public void setGeneData(ItemStack itemstack ,GeneData gene){
		NBTTagCompound nbt = itemstack.getTagCompound();
		if(nbt == null)
		{
			nbt = new NBTTagCompound();
			itemstack.setTagCompound(nbt);
		}
		nbt.setString("GeneData",gene.getDataString());
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
	
	public GeneData getGeneData(ItemStack itemstack){
		NBTTagCompound nbt = itemstack.getTagCompound();
		if(nbt == null)return null;
		String gene_data_string = nbt.getString("GeneData");
		GeneData gene = new GeneData(gene_data_string);
		return gene;
	}

	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advanced)
	{
		GeneData gene = this.getGeneData(itemStack);
		list.add("[ GeneData ]");
        list.add(this.getToolChipValue("MaxHealth",gene.maxhealth));
        list.add(this.getToolChipValue("Attack",gene.attack));
        list.add(this.getToolChipValue("Defense",gene.defense));
        list.add(this.getToolChipValue("EggSpeed",gene.eggspeed));
        list.add(this.getToolChipValue("Efficiency",gene.efficiency));
        list.add(this.getToolChipValue("GrowSpeed",gene.growspeed));
        list.add(this.getToolChipValue("MoveSpeed",gene.movespeed));
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