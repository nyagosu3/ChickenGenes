package com.nyagosu.chickengenes.gui;

import com.nyagosu.chickengenes.container.ContainerGeneProcessor;
import com.nyagosu.chickengenes.container.GuiContainerGeneProcessor;
import com.nyagosu.chickengenes.tileentity.TileEntityGeneProcessor;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;
		
		TileEntity tileentity = world.getTileEntity(x, y, z);
//		if(ID == DevelopMod.GUI_ID){
//			return new ContainerMyFurnace(player, (TileEntityMyFurnace) tileentity);
//		}
		if (tileentity instanceof TileEntityGeneProcessor) {
			return new ContainerGeneProcessor(player, (TileEntityGeneProcessor) tileentity);
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;
 
		TileEntity tileentity = world.getTileEntity(x, y, z);
//		if(ID == DevelopMod.GUI_ID){
//			return new GuiContainerMyFurnace(player, (TileEntityMyFurnace) tileentity);
//		}
		if (tileentity instanceof TileEntityGeneProcessor) {
			return new GuiContainerGeneProcessor(player, (TileEntityGeneProcessor) tileentity);
		}
		return null;
	}
}
