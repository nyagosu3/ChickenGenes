package com.nyagosu.chickengenes.recipes;

import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.entity.GeneData;
import com.nyagosu.chickengenes.item.ItemChickenSyringe;
import com.nyagosu.chickengenes.item.ItemChickenSyringeGene;
import com.nyagosu.chickengenes.util.TooCon;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ChickenGenesSyringeRecipe implements IRecipe {
	
	
	public ChickenGenesSyringeRecipe() {}
	public boolean matches(InventoryCrafting inventory, World worldIn){
		if(inventory == null)return false;
		ItemStack itemstack_s = null;
		ItemStack itemstack_g = null;
		for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
			ItemStack currentStack = inventory.getStackInSlot(slot);
			if(currentStack != null){
				System.out.println(currentStack.getItem().toString());
				if(currentStack.getItem() == ChickenGenesCore.itemChickenSyringe)itemstack_s = currentStack;
				if(currentStack.getItem() == ChickenGenesCore.itemChickenGene)itemstack_g = currentStack;
			}
		}
		if(itemstack_s == null || itemstack_g == null)return false;
		return true;
    }
    
    public ItemStack getCraftingResult(InventoryCrafting inventory){
    	if(inventory == null)return null;
		ItemStack itemstack_s = null;
		ItemStack itemstack_g = null;
		for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
			ItemStack currentStack = inventory.getStackInSlot(slot);
			if(currentStack != null){
				if(currentStack.getItem() == ChickenGenesCore.itemChickenSyringe)itemstack_s = currentStack;
				if(currentStack.getItem() == ChickenGenesCore.itemChickenGene)itemstack_g = currentStack;
			}
		}
		
		if(itemstack_s == null || itemstack_g == null)return null;
		
		NBTTagCompound nbt = itemstack_g.getTagCompound();
    	String gene_data_string = nbt.getString("GeneData");
    	
    	ItemStack new_itemstack = getRecipeOutput().copy();
    	ItemChickenSyringeGene item = (ItemChickenSyringeGene) new_itemstack.getItem();
    	item.setGeneData(new_itemstack, new GeneData(gene_data_string));
		
        return new_itemstack;
    }

    public int getRecipeSize(){
        return 1;
    }
 
    public ItemStack getRecipeOutput(){
        return new ItemStack(ChickenGenesCore.itemChickenSyringeGene,1);
    }
}
