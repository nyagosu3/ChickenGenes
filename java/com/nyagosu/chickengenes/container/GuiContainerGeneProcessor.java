package com.nyagosu.chickengenes.container;

import org.lwjgl.opengl.GL11;

import com.nyagosu.chickengenes.tileentity.TileEntityGeneProcessor;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiContainerGeneProcessor extends GuiContainer {
	
	private TileEntityGeneProcessor tileentity;
	//ResourceLocationの第一引数を付け足してドメインを指定することもできる
	//例:new ResourceLocation("sample", "textures/gui/container/furnace.png")
	private static final ResourceLocation GUITEXTURE = new ResourceLocation("textures/gui/container/furnace.png");
 
	public GuiContainerGeneProcessor(EntityPlayer player, TileEntityGeneProcessor par2TileEntity) {
		super(new ContainerGeneProcessor(player, par2TileEntity));
		this.tileentity = par2TileEntity;
	}
 
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		//インベントリ名の描画
		String s = this.tileentity.isInvNameLocalized() ? this.tileentity.getInvName() : StatCollector.translateToLocal(this.tileentity.getInvName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
 
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
 
		//テクスチャの指定
		this.mc.getTextureManager().bindTexture(GUITEXTURE);
 
		//かまど描画処理
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;
 
		if (this.tileentity.isBurning())
		{
			i1 = this.tileentity.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
		}
 
		i1 = this.tileentity.getCookProgressScaled(24);
		this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}
}
