package com.nyagosu.chickengenes.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.entity.GeneData;
import com.nyagosu.chickengenes.util.DebugTool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiScreenChickenLoupe extends GuiScreen {
    private static final ResourceLocation bookGuiTextures = new ResourceLocation("ChickenGenes:textures/gui/chickenloupe.png");
    private int bookImageWidth = 248;
    private int bookImageHeight = 167;
    private GuiButton buttonDone;
    private static final String __OBFID = "CL_00000744";
    private EntityGeneChicken chicken;
    private GeneData gene;
    
    private int offset_x;
    private int separate_offset_x;
    private int value_offset_x;
    private int value_offset_base_y;
    private int value_line_height;
	
	public GuiScreenChickenLoupe(EntityPlayer p_i1080_1_,EntityGeneChicken chicken){
		this.chicken = chicken;
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
        value_line_height = 12;
        
        this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 - 100, 4 + this.bookImageHeight, 200, 20, I18n.format("gui.done", new Object[0])));
    }
    
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        if(this.mc != null)this.mc.getTextureManager().bindTexture(bookGuiTextures);
        this.drawTexturedModalRect(offset_x, 2, 0, 0, this.bookImageWidth, this.bookImageHeight);
        
        if(this.fontRendererObj == null)return;
        
        String str_title = "Chicken GeneData";
        int ss = this.fontRendererObj.getStringWidth(str_title);
        this.fontRendererObj.drawString(str_title,(this.width / 2 - ss / 2 ),12,0);
        
        String sex_str = gene.sex == 0 ? "ZZ" : "ZW";
        this.drawDataValue("Sex", sex_str, 0);
        
        String mate_flag_str = gene.mate_flag == 0 ? "Still" : "Done";
        this.drawDataValue("Mate", mate_flag_str, 1);
        
        this.drawDataValue("MaxHealth",gene.maxhealth,2);
        this.drawDataValue("Attack", gene.attack,3);
        this.drawDataValue("Defense", gene.defense,4);
        this.drawDataValue("EggSpeed", gene.eggspeed,5);
        this.drawDataValue("Efficiency", gene.efficiency,6);
        this.drawDataValue("GrowSpeed", gene.growspeed,7);
        this.drawDataValue("MoveSpeed", gene.movespeed,8);
        this.drawDataValue("Stamina", this.chicken.getStamina(),9);
        this.drawDataValue("Time", this.chicken.timeUntilNextEgg,10);
        
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    private void drawDataValue(String name, String value, int line_num){
    	int y = value_offset_base_y + value_line_height * line_num;
    	this.fontRendererObj.drawString(name,offset_x + 14,y,0);
        this.fontRendererObj.drawString("|",separate_offset_x,y,0);
        this.fontRendererObj.drawString(value,value_offset_x,y,0);
    }
    
    private void drawDataValue(String name, int value, int line_num){
    	int color_code = (value == 0)?0x000000:(value > 0)?0x00FF00:0xFF0000;
    	String value_string = ((value > 0)?"+":"") + String.valueOf(value);
    	int y = value_offset_base_y + value_line_height * line_num;
    	this.fontRendererObj.drawString(name,offset_x + 14,y,0);
        this.fontRendererObj.drawString("|",separate_offset_x,y,0);
        this.fontRendererObj.drawString(value_string,value_offset_x,y,color_code);
    }
    
    private void drawDataValue(String name, float value, int line_num){
    	//int color_code = (value == 0)?0x000000:(value > 0)?0x00FF00:0xFF0000;
    	String value_string = ((value > 0)?"+":"") + String.valueOf(value) + "%";
    	int y = value_offset_base_y + value_line_height * line_num;
    	this.fontRendererObj.drawString(name,offset_x + 14,y,0);
        this.fontRendererObj.drawString("|",separate_offset_x,y,0);
        this.fontRendererObj.drawString(value_string,value_offset_x,y,0);
    }
    
    protected void keyTyped(char p_73869_1_, int p_73869_2_){
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
