package com.nyagosu.chickengenes;

import com.nyagosu.chickengenes.block.BlockChickenGeneProcessor;
import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.gui.GuiHandler;
import com.nyagosu.chickengenes.item.ItemChickenBell;
import com.nyagosu.chickengenes.item.ItemChickenBook;
import com.nyagosu.chickengenes.item.ItemChickenCell;
import com.nyagosu.chickengenes.item.ItemChickenContainer;
import com.nyagosu.chickengenes.item.ItemChickenSyringeDoping;
import com.nyagosu.chickengenes.item.ItemChickenSyringeGene;
import com.nyagosu.chickengenes.item.ItemChickenSyringeMutation;
import com.nyagosu.chickengenes.item.ItemChickenEgg;
import com.nyagosu.chickengenes.item.ItemChickenGene;
import com.nyagosu.chickengenes.item.ItemChickenGeneBroken;
import com.nyagosu.chickengenes.item.ItemChickenGeneMutation;
import com.nyagosu.chickengenes.item.ItemChickenKnife;
import com.nyagosu.chickengenes.item.ItemChickenLoupe;
import com.nyagosu.chickengenes.item.ItemChickenSyringe;
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
import net.minecraft.creativetab.CreativeTabs;
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
	
	public static CreativeTabs tabChickenGenes = new ChickenGenesTab("Chicken Genes");
	
	public static ResourceLocation ChickenGenesTexture = new ResourceLocation("chickengenes","textures/entity/genechicken.png");
	
	public static final int CHICKENLOUPE_GUI_ID = 0;
	public static final int GENEPROCESSOR_GUI_ID = 1;
	
	public static ItemSpawnEgg itemSpawnEgg;
	public static ItemChickenBook itemChickenBook;
	public static ItemChickenLoupe itemChickenLoupe;
	public static ItemChickenBell itemChickenBell;
	public static ItemSweetSeed itemSweetSeed;
	public static ItemChickenKnife itemChickenKnife;
	
	public static ItemChickenContainer itemChickenContainer;
	public static ItemChickenEgg itemChickenEgg;
	public static ItemChickenCell itemChickenCell;
	
	//gene
	public static ItemChickenGene itemChickenGene;
	public static ItemChickenGeneBroken itemChickenGeneBroken;
	public static ItemChickenGeneMutation itemChickenGeneMutation;
	
	//syringe
	public static ItemChickenSyringe itemChickenSyringe;
	public static ItemChickenSyringeGene itemChickenSyringeGene;
	public static ItemChickenSyringeDoping itemChickenSyringeDoping;
	public static ItemChickenSyringeMutation itemChickenSyringeMutation;
	
	public static BlockChickenGeneProcessor blockChickenGeneProcessor;
	public static BlockChickenGeneProcessor lit_blockChickenGeneProcessor;
	
	
	@Mod.EventHandler
    public void preInit(FMLPostInitializationEvent event) {
		
		EntityRegistry.registerModEntity(EntityGeneChicken.class, "GeneChicken",1, this, 64, 2, true);
		
		blockChickenGeneProcessor = new BlockChickenGeneProcessor(false);
		lit_blockChickenGeneProcessor = new BlockChickenGeneProcessor(true);
		
		GameRegistry.registerBlock(blockChickenGeneProcessor, "blockChickenGeneProcessor");
		GameRegistry.registerBlock(lit_blockChickenGeneProcessor, "lit_blockChickenGeneProcessor");
		
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
		
		itemChickenContainer = new ItemChickenContainer();
		GameRegistry.registerItem(itemChickenContainer, "itemChickenContainer");
		
		itemChickenEgg = new ItemChickenEgg();
		GameRegistry.registerItem(itemChickenEgg, "itemChickenEgg");
		
		itemChickenGene = new ItemChickenGene();
		GameRegistry.registerItem(itemChickenGene, "itemChickenGene");
		
		itemChickenGeneBroken = new ItemChickenGeneBroken();
		GameRegistry.registerItem(itemChickenGeneBroken, "itemChickenGeneBroken");
		
		itemChickenGeneMutation = new ItemChickenGeneMutation();
		GameRegistry.registerItem(itemChickenGeneMutation, "itemChickenGeneMutation");
		
		itemChickenSyringe = new ItemChickenSyringe();
		GameRegistry.registerItem(itemChickenSyringe, "itemChickenSyringe");
		
		itemChickenSyringeGene = new ItemChickenSyringeGene();
		GameRegistry.registerItem(itemChickenSyringeGene, "itemChickenSyringeGene");
		
		itemChickenSyringeDoping = new ItemChickenSyringeDoping();
		GameRegistry.registerItem(itemChickenSyringeDoping, "itemChickenSyringeDoping");
		
		itemChickenSyringeMutation = new ItemChickenSyringeMutation();
		GameRegistry.registerItem(itemChickenSyringeMutation, "itemChickenSyringeMutation");
		
		GameRegistry.registerTileEntity(TileEntityGeneProcessor.class, "TileEntityGeneProcessor");
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
    }
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event) {
		proxy.registerRenderThings();
		ChickenGenesRecipes.regist();
    }
	
}
