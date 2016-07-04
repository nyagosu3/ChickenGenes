package com.nyagosu.chickengenes;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ChickenGenesRecipes {
	public static void regist()
	{
		ItemStack wheatSeeds = new ItemStack(Items.wheat_seeds);
		ItemStack itemSweetSees1 = new ItemStack(ChickenGenesCore.itemSweetSeed,1,0);
		ItemStack itemSweetSees2 = new ItemStack(ChickenGenesCore.itemSweetSeed,1,1);
		ItemStack itemSweetSees3 = new ItemStack(ChickenGenesCore.itemSweetSeed,1,2);
		ItemStack itemSweetSees4 = new ItemStack(ChickenGenesCore.itemSweetSeed,1,3);
		
		GameRegistry.addSmelting(wheatSeeds,itemSweetSees1,0.0f);
		
		GameRegistry.addRecipe(
				itemSweetSees2,
	            new Object[] {
            		"eee",
    				"eee",
    				"eee",
    				'e',itemSweetSees1
	                } );
		
		GameRegistry.addRecipe(
				itemSweetSees3,
	            new Object[] {
            		"eee",
    				"eee",
    				"eee",
    				'e',itemSweetSees2
	                } );
		
		GameRegistry.addRecipe(
				itemSweetSees4,
	            new Object[] {
            		"eee",
    				"eee",
    				"eee",
    				'e',itemSweetSees3
	                } );
	}
}
