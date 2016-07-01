package com.nyagosu.chickengenes;

import com.nyagosu.chickengenes.proxy.ServerProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(
		modid = "chickengenes",
		name = "ChickenGenes",
		version = "0.0.0",
		dependencies = "required-after:Forge@[10.13.4.1614,)",
		useMetadata = true
	)

public class ChickenGenesCore {
	
	@Mod.Instance("ChickenGenes")
    public static ChickenGenesCore INSTANCE;
	
	@SidedProxy(clientSide="com.nyagosu.chickengenes.proxy.ClientProxy", serverSide="com.nyagosu.chickengenes.proxy.ServerProxy")
	public static ServerProxy proxy;
	
	@EventHandler
    public void preInit(FMLPostInitializationEvent event) {
		
    }
	
	@EventHandler
    public void init(FMLInitializationEvent event) {
		
    }
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event) {
		
    }
	
}
