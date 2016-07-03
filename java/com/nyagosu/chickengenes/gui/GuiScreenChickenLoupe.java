package com.nyagosu.chickengenes.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.entity.GeneData;
import com.nyagosu.chickengenes.util.DebugTool;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiScreenChickenLoupe extends GuiScreen {
    private static final ResourceLocation bookGuiTextures = new ResourceLocation("chickengenes:textures/gui/chickenloupe.png");
    private int bookImageWidth = 248;
    private int bookImageHeight = 167;
    private GuiButton buttonDone;
    private static final String __OBFID = "CL_00000744";
    private GeneData gene;
    
    private int offset_x;
    private int separate_offset_x;
    private int value_offset_x;
    private int value_offset_base_y;
    private int value_line_height;
	
	public GuiScreenChickenLoupe(EntityPlayer p_i1080_1_,EntityGeneChicken chicken)
    {
        this.gene = chicken.getGeneData();
        
    }
	
    public void initGui()
    {
    	this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        offset_x = (this.width - this.bookImageWidth) / 2;
        separate_offset_x = offset_x + 114;
        value_offset_x = offset_x + 120;
        value_offset_base_y = 32;
        value_line_height = 14;
        
        this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 - 100, 4 + this.bookImageHeight, 200, 20, I18n.format("gui.done", new Object[0])));
    }
    
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        TextureManager tm = this.mc.getTextureManager();
        if(tm != null)tm.bindTexture(bookGuiTextures);
        this.drawTexturedModalRect(offset_x, 2, 0, 0, this.bookImageWidth, this.bookImageHeight);
        
        String str_title = "Chicken GeneData";
        int ss = this.fontRendererObj.getStringWidth(str_title);
        this.fontRendererObj.drawString(str_title,(this.width / 2 - ss / 2 ),12,0);
        
        String sex_str = gene.sex == 0 ? "ZZ" : "ZW";
        this.drawDataValue("Sex", sex_str, 0);
        this.drawDataValue("MaxHealth",String.valueOf(gene.maxhealth),1);
        this.drawDataValue("Attack", String.valueOf(gene.attack),2);
        this.drawDataValue("Defense", String.valueOf(gene.defense),3);
        this.drawDataValue("EggSpeed", String.valueOf(gene.eggspeed),4);
        this.drawDataValue("Efficiency", String.valueOf(gene.efficiency),5);
        this.drawDataValue("GrowSpeed", String.valueOf(gene.growspeed),6);
        this.drawDataValue("MoveSpeed", String.valueOf(gene.movespeed),7);
        
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    private void drawDataValue(String name, String value, int line_num){
    	int y = value_offset_base_y + value_line_height * line_num;
    	this.fontRendererObj.drawString(name,offset_x + 14,y,0);
        this.fontRendererObj.drawString("|",separate_offset_x,y,0);
        this.fontRendererObj.drawString(value,value_offset_x,y,0);
    }
    
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        super.keyTyped(p_73869_1_, p_73869_2_);
        if(p_73869_2_ == 18){
        	this.mc.thePlayer.closeScreen();
        }
    }
    
    protected void actionPerformed(GuiButton p_146284_1_){
        if (p_146284_1_.enabled){
            if (p_146284_1_.id == 0){
                this.mc.displayGuiScreen((GuiScreen)null);
            }
        }
    }
}
