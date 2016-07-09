package com.nyagosu.chickengenes.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemSweetSeed extends Item {
	
	private IIcon[] iicon = new IIcon[16];
	
	public ItemSweetSeed(){
		this.setHasSubtypes(true);
		this.setUnlocalizedName("ItemSweetSeed");
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setTextureName("ChickenGenes:sweetseed");
        this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iicon) {
		for (int i = 0; i < 4; i ++) {
			this.iicon[i] = iicon.registerIcon(this.getIconString() + i);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return iicon[meta];
	}
 
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
		for (int i = 0; i < 4; i ++) {
			list.add(new ItemStack(this, 1, i));
		}
	}
	
	@Override
	public int getMetadata(int meta) {
		return meta;
	}
 
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName() + "." + itemStack.getItemDamage();
	}
	
	
}
