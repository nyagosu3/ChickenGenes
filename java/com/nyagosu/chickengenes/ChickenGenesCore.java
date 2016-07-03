package com.nyagosu.chickengenes;

import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.item.ItemChickenBell;
import com.nyagosu.chickengenes.item.ItemChickenBook;
import com.nyagosu.chickengenes.item.ItemChickenLoupe;
import com.nyagosu.chickengenes.item.ItemSpawnEgg;
import com.nyagosu.chickengenes.proxy.ServerProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.util.ResourceLocation;

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
	
	public static ResourceLocation ChickenGenesTexture = new ResourceLocation("chickengenes","textures/entity/genechicken.png");
	
	public static final int CHICKENLOUPE_GUI_ID = 0;
	
	public static ItemSpawnEgg itemSpawnEgg;
	public static ItemChickenBook itemChickenBook;
	public static ItemChickenLoupe itemChickenLoupe;
	public static ItemChickenBell itemChickenBell;
	
	
	@EventHandler
    public void preInit(FMLPostInitializationEvent event) {
		EntityRegistry.registerModEntity(EntityGeneChicken.class, "GeneChicken",1, this, 64, 2, true);
    }
	
	@EventHandler
    public void init(FMLInitializationEvent event) {
		itemSpawnEgg = new ItemSpawnEgg(0x00FF0000,0x00FF0000);
		GameRegistry.registerItem(itemSpawnEgg, "itemSpawnEgg");
		
		itemChickenBook = new ItemChickenBook();
		GameRegistry.registerItem(itemChickenBook, "itemChickenBook");
		
		itemChickenLoupe = new ItemChickenLoupe();
		GameRegistry.registerItem(itemChickenLoupe, "itemChickenLoupe");
		
		itemChickenBell = new ItemChickenBell();
		GameRegistry.registerItem(itemChickenBell, "itemChickenBell");
		
    }
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event) {
		proxy.registerRenderThings();
    }
	
}
