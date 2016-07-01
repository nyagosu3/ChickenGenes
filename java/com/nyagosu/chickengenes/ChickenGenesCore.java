package com.nyagosu.chickengenes;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@Mod(
		modid = "chickengenes",
		name = "ChickenGenes",
		version = "0.0.0",
		dependencies = "required-after:Forge@[10.13.4.1614,)",
		useMetadata = true
	)

public class ChickenGenesCore {
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event) {
		
    }
	
}
