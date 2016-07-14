package com.nyagosu.chickengenes;

import com.nyagosu.chickengenes.recipes.ChickenGenesSyringeRecipe;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ChickenGenesRecipes {
	
	public static void regist(){
		
		ItemStack itemChickenKnife = new ItemStack(ChickenGenesCore.itemChickenKnife);
		ItemStack itemDiamond = new ItemStack(Items.diamond);
		
		ItemStack itemChickenBell = new ItemStack(ChickenGenesCore.itemChickenBell);
		ItemStack itemGoldNugget = new ItemStack(Items.gold_nugget);
		
		ItemStack itemChickenLoupe = new ItemStack(ChickenGenesCore.itemChickenLoupe);
		ItemStack itemBrick = new ItemStack(Items.brick);
		
		ItemStack itemChickenContainer = new ItemStack(ChickenGenesCore.itemChickenContainer);
		ItemStack itemStick = new ItemStack(Items.stick);
		
		ItemStack itemSyringe = new ItemStack(ChickenGenesCore.itemChickenSyringe,1,0);
		ItemStack itemIronIngot = new ItemStack(Items.iron_ingot);
		ItemStack itemGlassPane = new ItemStack(Blocks.glass_pane);
		
		ItemStack wheatSeeds = new ItemStack(Items.wheat_seeds);
		ItemStack itemSweetSeed1 = new ItemStack(ChickenGenesCore.itemSweetSeed,1,0);
		ItemStack itemSweetSeed2 = new ItemStack(ChickenGenesCore.itemSweetSeed,1,1);
		ItemStack itemSweetSeed3 = new ItemStack(ChickenGenesCore.itemSweetSeed,1,2);
		ItemStack itemSweetSeed4 = new ItemStack(ChickenGenesCore.itemSweetSeed,1,3);
		
		ItemStack itemChickenSyringe = new ItemStack(ChickenGenesCore.itemChickenSyringe,1);
		ItemStack itemChickenSyringeMutation = new ItemStack(ChickenGenesCore.itemChickenSyringeMutation,1);
		ItemStack itemChickenGeneMutation = new ItemStack(ChickenGenesCore.itemChickenGeneMutation,1);
		
		ItemStack itemDrugMaxHealthPlus1 	= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,0);
		ItemStack itemDrugMaxHealthMinus1 	= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,3);
		ItemStack itemDrugAttackPlus1		= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,10);
		ItemStack itemDrugAttackMinus1		= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,13);
		ItemStack itemDrugDefensePlus1		= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,20);
		ItemStack itemDrugDefenseMinus1		= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,23);
		ItemStack itemDrugEggSpeedPlus1		= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,30);
		ItemStack itemDrugEggSpeedMinus1	= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,33);
		ItemStack itemDrugEfficiencyPlus1	= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,40);
		ItemStack itemDrugEfficiencyMinus1	= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,43);
		ItemStack itemDrugGrowSpeedPlus1	= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,50);
		ItemStack itemDrugGrowSpeedMinus1	= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,53);
		ItemStack itemDrugMoveSpeedPlus1	= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,60);
		ItemStack itemDrugMoveSpeedMinus1	= new ItemStack(ChickenGenesCore.itemChickenSyringeDoping,1,63);
		
		ItemStack blockGeneProcessor			= new ItemStack(ChickenGenesCore.blockChickenGeneProcessor);
		ItemStack blockFurnace					= new ItemStack(Blocks.furnace);
		
		GameRegistry.addRecipe(
				blockGeneProcessor,
				new Object[] {
	            		"iii",
	    				"bfb",
	    				"iii",
	    				'f',blockFurnace,
	    				'i',itemIronIngot,
	    				'b',itemBrick
	                });
		
		GameRegistry.addRecipe(
				itemDrugMaxHealthPlus1,
				new Object[] {
	            		"d i",
	    				"isi",
	    				"iii",
	    				'd',itemDiamond,
	    				'i',itemSweetSeed4,
	    				's',itemSyringe
	                });
		GameRegistry.addRecipe(itemDrugMaxHealthMinus1,	new Object[] {"d",'d',itemDrugMaxHealthPlus1});
		GameRegistry.addRecipe(itemDrugMaxHealthPlus1,	new Object[] {"d",'d',itemDrugMaxHealthMinus1});
		
		GameRegistry.addRecipe(
				itemDrugAttackPlus1,
				new Object[] {
	            		"di ",
	    				"isi",
	    				"iii",
	    				'd',itemDiamond,
	    				'i',itemSweetSeed4,
	    				's',itemSyringe
	                });
		GameRegistry.addRecipe(itemDrugAttackMinus1,	new Object[] {"d",'d',itemDrugAttackPlus1});
		GameRegistry.addRecipe(itemDrugAttackPlus1,		new Object[] {"d",'d',itemDrugAttackMinus1});
		
		GameRegistry.addRecipe(
				itemDrugDefensePlus1,
				new Object[] {
	            		"dii",
	    				" si",
	    				"iii",
	    				'd',itemDiamond,
	    				'i',itemSweetSeed4,
	    				's',itemSyringe
	                });
		GameRegistry.addRecipe(itemDrugDefenseMinus1,	new Object[] {"d",'d',itemDrugDefensePlus1});
		GameRegistry.addRecipe(itemDrugDefensePlus1,	new Object[] {"d",'d',itemDrugDefenseMinus1});
		
		GameRegistry.addRecipe(
				itemDrugEggSpeedPlus1,
				new Object[] {
	            		"dii",
	    				"is ",
	    				"iii",
	    				'd',itemDiamond,
	    				'i',itemSweetSeed4,
	    				's',itemSyringe
	                });
		GameRegistry.addRecipe(itemDrugEggSpeedMinus1,	new Object[] {"d",'d',itemDrugEggSpeedPlus1});
		GameRegistry.addRecipe(itemDrugEggSpeedPlus1,	new Object[] {"d",'d',itemDrugEggSpeedMinus1});
		
		GameRegistry.addRecipe(
				itemDrugEfficiencyPlus1,
				new Object[] {
	            		"dii",
	    				"isi",
	    				" ii",
	    				'd',itemDiamond,
	    				'i',itemSweetSeed4,
	    				's',itemSyringe
	                });
		GameRegistry.addRecipe(itemDrugEfficiencyMinus1,new Object[] {"d",'d',itemDrugEfficiencyPlus1});
		GameRegistry.addRecipe(itemDrugEfficiencyPlus1,	new Object[] {"d",'d',itemDrugEfficiencyMinus1});
		
		GameRegistry.addRecipe(
				itemDrugGrowSpeedPlus1,
				new Object[] {
	            		"dii",
	    				"isi",
	    				"i i",
	    				'd',itemDiamond,
	    				'i',itemSweetSeed4,
	    				's',itemSyringe
	                });
		GameRegistry.addRecipe(itemDrugGrowSpeedMinus1,	new Object[] {"d",'d',itemDrugGrowSpeedPlus1});
		GameRegistry.addRecipe(itemDrugGrowSpeedPlus1,	new Object[] {"d",'d',itemDrugGrowSpeedMinus1});
		
		GameRegistry.addRecipe(
				itemDrugMoveSpeedPlus1,
				new Object[] {
	            		"dii",
	    				"isi",
	    				"ii ",
	    				'd',itemDiamond,
	    				'i',itemSweetSeed4,
	    				's',itemSyringe
	                });
		GameRegistry.addRecipe(itemDrugMoveSpeedMinus1,	new Object[] {"d",'d',itemDrugMoveSpeedPlus1});
		GameRegistry.addRecipe(itemDrugMoveSpeedPlus1,	new Object[] {"d",'d',itemDrugMoveSpeedMinus1});
		
		GameRegistry.addRecipe(
				itemChickenKnife,
				new Object[] {
	            		"d  ",
	    				" i ",
	    				"  i",
	    				'd',itemDiamond,
	    				'i',itemIronIngot
	                });
		
		GameRegistry.addRecipe(
				itemChickenBell,
				new Object[] {
	            		" g ",
	    				"g g",
	    				"gig",
	    				'g',itemGoldNugget,
	    				'i',itemIronIngot
	                });
		
		GameRegistry.addRecipe(
				itemChickenLoupe,
				new Object[] {
	            		" b ",
	    				"bgb",
	    				" bs",
	    				'g',itemGlassPane,
	    				'b',itemBrick,
	    				's',itemStick
	                });
		
		GameRegistry.addRecipe(
				itemChickenContainer,
				new Object[] {
	            		"isi",
	    				"s s",
	    				"isi",
	    				'i',itemIronIngot,
	    				's',itemStick
	                });
		
		GameRegistry.addRecipe(
				itemSyringe,
				new Object[] {
	            		"ig ",
	    				"g g",
	    				" g ",
	    				'i',itemIronIngot,
	    				'g',itemGlassPane
	                });
		
		GameRegistry.addSmelting(wheatSeeds,itemSweetSeed1,0.0f);
		
		GameRegistry.addRecipe(
				itemSweetSeed2,
	            new Object[] {
            		"eee",
    				"eee",
    				"eee",
    				'e',itemSweetSeed1
	                } );
		
		GameRegistry.addRecipe(
				itemSweetSeed3,
	            new Object[] {
            		"eee",
    				"eee",
    				"eee",
    				'e',itemSweetSeed2
	                } );
		
		GameRegistry.addRecipe(
				itemSweetSeed4,
	            new Object[] {
            		"eee",
    				"eee",
    				"eee",
    				'e',itemSweetSeed3
	                } );
		
		GameRegistry.addShapelessRecipe(
				itemChickenSyringeMutation,
					itemChickenSyringe,
					itemChickenGeneMutation
			);
		
		GameRegistry.addRecipe(new ChickenGenesSyringeRecipe());
	}
}
