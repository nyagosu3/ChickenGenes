package com.nyagosu.chickengenes;

import com.nyagosu.chickengenes.block.BlockGeneProcessor;
import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.gui.GuiHandler;
import com.nyagosu.chickengenes.item.ItemChickenBell;
import com.nyagosu.chickengenes.item.ItemChickenBook;
import com.nyagosu.chickengenes.item.ItemChickenCell;
import com.nyagosu.chickengenes.item.ItemChickenContainer;
import com.nyagosu.chickengenes.item.ItemChickenDopingSyringe;
import com.nyagosu.chickengenes.item.ItemChickenEgg;
import com.nyagosu.chickengenes.item.ItemChickenKnife;
import com.nyagosu.chickengenes.item.ItemChickenLoupe;
import com.nyagosu.chickengenes.item.ItemSpawnEgg;
import com.nyagosu.chickengenes.item.ItemSweetSeed;
import com.nyagosu.chickengenes.proxy.ServerProxy;
import com.nyagosu.chickengenes.tileentity.TileEntityGeneProcessor;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.util.ResourceLocation;

@Mod(
		modid = "ChickenGenes",
		name = "ChickenGenes",
		version = "0.0.0",
		dependencies = "required-after:Forge@[10.13.4.1614,)",
		useMetadata = true
	)

public class ChickenGenesCore {
	
	@Instance("ChickenGenes")
    public static ChickenGenesCore instance;
	
	@SidedProxy(clientSide="com.nyagosu.chickengenes.proxy.ClientProxy", serverSide="com.nyagosu.chickengenes.proxy.ServerProxy")
	public static ServerProxy proxy;
	
	public static ResourceLocation ChickenGenesTexture = new ResourceLocation("ChickenGenes","textures/entity/genechicken.png");
	
	public static final int CHICKENLOUPE_GUI_ID = 0;
	public static final int GENEPROCESSOR_GUI_ID = 1;
	
	
	public static ItemSpawnEgg itemSpawnEgg;
	public static ItemChickenBook itemChickenBook;
	public static ItemChickenLoupe itemChickenLoupe;
	public static ItemChickenBell itemChickenBell;
	public static ItemSweetSeed itemSweetSeed;
	public static ItemChickenKnife itemChickenKnife;
	public static ItemChickenCell itemChickenCell;
	public static ItemChickenDopingSyringe itemChickenDopingSyringe;
	public static ItemChickenContainer itemChickenContainer;
	public static ItemChickenEgg itemChickenEgg;
	
	public static BlockGeneProcessor blockGeneProcessor;
	public static BlockGeneProcessor lit_blockGeneProcessor;
	
	
	@Mod.EventHandler
    public void preInit(FMLPostInitializationEvent event) {
		EntityRegistry.registerModEntity(EntityGeneChicken.class, "GeneChicken",1, this, 64, 2, true);
		
		blockGeneProcessor = new BlockGeneProcessor(false);
		lit_blockGeneProcessor = new BlockGeneProcessor(true);
		
		GameRegistry.registerBlock(blockGeneProcessor, "blockGeneProcessor");
		GameRegistry.registerBlock(lit_blockGeneProcessor, "lit_blockGeneProcessor");
		
    }
	
	@Mod.EventHandler
    public void init(FMLInitializationEvent event) {
		
		
		
		itemSpawnEgg = new ItemSpawnEgg(0x00FF0000,0x00FF0000);
		GameRegistry.registerItem(itemSpawnEgg, "itemSpawnEgg");
		
		itemChickenBook = new ItemChickenBook();
		GameRegistry.registerItem(itemChickenBook, "itemChickenBook");
		
		itemChickenLoupe = new ItemChickenLoupe();
		GameRegistry.registerItem(itemChickenLoupe, "itemChickenLoupe");
		
		itemChickenBell = new ItemChickenBell();
		GameRegistry.registerItem(itemChickenBell, "itemChickenBell");
		
		itemSweetSeed = new ItemSweetSeed();
		GameRegistry.registerItem(itemSweetSeed, "itemSweetSeed");
		
		itemChickenKnife = new ItemChickenKnife();
		GameRegistry.registerItem(itemChickenKnife, "itemChickenKnife");
		
		itemChickenCell = new ItemChickenCell();
		GameRegistry.registerItem(itemChickenCell, "itemChickenCell");
		
		itemChickenDopingSyringe = new ItemChickenDopingSyringe();
		GameRegistry.registerItem(itemChickenDopingSyringe, "itemChickenDopingSyringe");
		
		itemChickenContainer = new ItemChickenContainer();
		GameRegistry.registerItem(itemChickenContainer, "itemChickenContainer");
		
		itemChickenEgg = new ItemChickenEgg();
		GameRegistry.registerItem(itemChickenEgg, "itemChickenEgg");
		
		GameRegistry.registerTileEntity(TileEntityGeneProcessor.class, "TileEntityGeneProcessor");
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event) {
		proxy.registerRenderThings();
		ChickenGenesRecipes.regist();
    }
	
}
