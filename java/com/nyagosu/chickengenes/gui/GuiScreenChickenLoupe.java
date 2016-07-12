package com.nyagosu.chickengenes.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.entity.GeneData;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiScreenChickenLoupe extends GuiScreen {
    private static final ResourceLocation bookGuiTextures = new ResourceLocation("ChickenGenes:textures/gui/chickenloupe.png");
    private int bookImageWidth = 248;
    private int bookImageHeight = 167;
    private GuiButton buttonDone;
    private EntityGeneChicken chicken;
    private GeneData gene;
    
    private int offset_x;
    private int separate_offset_x;
    private int value_offset_x;
    private int value_offset_base_y;
    private int value_line_height;
    
    private int left_offset_x;
    private int left_offset_y;
    private int relative_separate_offset_x;
    private int relative_value_offset_x;
    private int line_height;
    
    private int base_x;
    private int array_pos_offset_x[] = new int[2];
    private int array_pos_offset_y[] = new int[2];
    private static int BASE_FONT_COLOR = 0x333333;
	
	public GuiScreenChickenLoupe(EntityPlayer p_i1080_1_,EntityGeneChicken chicken){
		this.chicken = chicken;
        this.gene = chicken.getGeneData();
    }
	
    public void initGui()
    {
    	this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        offset_x = (this.width - this.bookImageWidth) / 2;
        separate_offset_x = offset_x + 84;
        value_offset_x = offset_x + 90;
        value_offset_base_y = 40;
        
        base_x = (this.width - this.bookImageWidth) / 2;
        array_pos_offset_x[0] = base_x + 20;
        array_pos_offset_x[1] = base_x + 136;
        array_pos_offset_y[0] = 49;
        array_pos_offset_y[1] = 49;
        value_line_height = 17;
        
        this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 - 100, 4 + this.bookImageHeight, 200, 20, I18n.format("gui.done", new Object[0])));
    }
    
    public void onGuiClosed(){
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
        this.fontRendererObj.drawString(str_title,(this.width / 2 - ss / 2 ),20,0x333333);
        
        String sex_str = gene.sex == 0 ? "ZZ" : "ZW";
        put("Sex",sex_str,0,0);
        String mate_flag_str = gene.mate_flag == 0 ? "Still" : "Done";
        put("Mate",mate_flag_str,0,1);
        put("MaxHealth", gene.maxhealth, 0, 2);
        put("Attack", gene.attack, 0, 3);
        put("Defense", gene.defense, 0, 4);
        put("EggSpeed", gene.eggspeed, 0, 5);
        put("Efficiency", gene.efficiency, 0, 6);
        put("GrowSpeed", gene.growspeed, 1, 0);
        put("MoveSpeed", gene.movespeed, 1, 1);
        put("Stamina", String.format("%.2f",this.chicken.getStamina()) + "%", 1, 2);
        put("NextEgg", this.chicken.timeUntilNextEgg, 1, 3);
        put("GrowingAge", this.chicken.getGrowingAge(), 1, 4);
        
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    private void put(String name, int value, int no, int line_num){
    	int color = (value == 0)?BASE_FONT_COLOR:(value > 0)?0x00FF00:0xFF0000;
    	String value_string = ((value > 0)?"+":"") + String.valueOf(value);
    	int s = this.fontRendererObj.getStringWidth(value_string);
    	int y = array_pos_offset_y[no] + value_line_height * line_num;
    	this.fontRendererObj.drawString(name,array_pos_offset_x[no],y,BASE_FONT_COLOR);
    	this.fontRendererObj.drawString(value_string,array_pos_offset_x[no] + 94 - s,y,color);
    }
    
    private void put(String name, String value, int no , int line_num){
    	put(name,value,no,line_num,BASE_FONT_COLOR);
    }
    
    private void put(String name, String value, int no , int line_num ,int color){
    	int s = this.fontRendererObj.getStringWidth(value);
    	int y = array_pos_offset_y[no] + value_line_height * line_num;
    	this.fontRendererObj.drawString(name,array_pos_offset_x[no],y,BASE_FONT_COLOR);
    	this.fontRendererObj.drawString(value,array_pos_offset_x[no] + 94 - s,y,color);
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
