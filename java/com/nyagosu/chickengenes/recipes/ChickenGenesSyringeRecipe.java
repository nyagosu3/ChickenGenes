package com.nyagosu.chickengenes.recipes;

import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.entity.GeneData;
import com.nyagosu.chickengenes.item.ItemChickenSyringeGene;
import net.minecraft.inventory.InventoryCrafting;
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
		String gene_data_string = "";
		NBTTagCompound nbt = itemstack_g.getTagCompound();
		if(nbt == null)gene_data_string = new GeneData().getDataString();
		
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
