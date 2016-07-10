package com.nyagosu.chickengenes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ChickenGenesTab extends CreativeTabs {
	
	private ItemStack field_151245_t;
	
	public ChickenGenesTab(String label) {
		super(label);
	}
 
	@Override
	public Item getTabIconItem() {
		return ChickenGenesCore.itemChickenContainer;
	}
	
	@SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack()
    {
        if (this.field_151245_t == null)
        {
            this.field_151245_t = new ItemStack(this.getTabIconItem(), 1, 1);
        }

        return this.field_151245_t;
    }
}
