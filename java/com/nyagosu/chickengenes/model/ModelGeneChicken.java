package com.nyagosu.chickengenes.model;

import org.lwjgl.opengl.GL11;

import com.nyagosu.chickengenes.entity.EntityGeneChicken;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelGeneChicken extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer rightWing;
    public ModelRenderer leftWing;
    public ModelRenderer bill;
    public ModelRenderer chin;
    public ModelRenderer crest;
    
    public ModelGeneChicken(int type){
    	int chin_x;
    	int chin_y;
    	int crest_x;
    	int crest_y;
    	chin_x = crest_x = 14;
		chin_y = crest_y = 4;
    	switch(type){
    	case 0:
    		chin_x = crest_x = 14;
    		chin_y = crest_y = 4;
    		break;
    	case 1:
    		chin_x = crest_x = 28;
    		chin_y = crest_y = 8;
    		break;
    	case 2:
    		chin_x = crest_x = 38;
    		chin_y = crest_y = 0;
    		break;
    	case 3:
    		chin_x = crest_x = 38;
    		chin_y = crest_y = 5;
    		break;
    	case 4:
    		chin_x = crest_x = 38;
    		chin_y = crest_y = 10;
    		break;
    	case 5:
    		chin_x = crest_x = 38;
    		chin_y = crest_y = 15;
    		break;
    	case 6:
    		chin_x = crest_x = 38;
    		chin_y = crest_y = 20;
    		break;
    	case 7:
    		chin_x = crest_x = 38;
    		chin_y = crest_y = 25;
    		break;
    	case 8:
    		chin_x = crest_x = 48;
    		chin_y = crest_y = 0;
    		break;
    	case 9:
    		chin_x = crest_x = 48;
    		chin_y = crest_y = 5;
    		break;
    	}
    	
        byte b0 = 16;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
        this.head.setRotationPoint(0.0F, (float)(-1 + b0), -4.0F);
        this.bill = new ModelRenderer(this, 14, 0);
        this.bill.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
        this.bill.setRotationPoint(0.0F, (float)(-1 + b0), -4.0F);
        this.chin = new ModelRenderer(this, chin_x, chin_y);
        this.chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
        this.chin.setRotationPoint(0.0F, (float)(-1 + b0), -4.0F);
        this.body = new ModelRenderer(this, 0, 9);
        this.body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
        this.body.setRotationPoint(0.0F, (float)b0, 0.0F);
        this.rightLeg = new ModelRenderer(this, 26, 0);
        this.rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
        this.rightLeg.setRotationPoint(-2.0F, (float)(3 + b0), 1.0F);
        this.leftLeg = new ModelRenderer(this, 26, 0);
        this.leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
        this.leftLeg.setRotationPoint(1.0F, (float)(3 + b0), 1.0F);
        this.rightWing = new ModelRenderer(this, 24, 13);
        this.rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
        this.rightWing.setRotationPoint(-4.0F, (float)(-3 + b0), 0.0F);
        this.leftWing = new ModelRenderer(this, 24, 13);
        this.leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
        this.leftWing.setRotationPoint(4.0F, (float)(-3 + b0), 0.0F);
        this.crest= new ModelRenderer(this, crest_x, crest_y);
        this.crest.addBox(-1F, -7F, -1F, 2, 2, 3);
        this.crest.setRotationPoint(0F, 15F, -4F);
        this.crest.setTextureSize(64, 32);
        this.crest.mirror = true;
    }
    
    public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_){
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);

        EntityGeneChicken entity = (EntityGeneChicken)p_78088_1_;
        
        if (entity.isChild()){
            float f6 = 2.0F;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 5.0F * p_78088_7_, 2.0F * p_78088_7_);
            this.head.render(p_78088_7_);
            this.bill.render(p_78088_7_);
            this.chin.render(p_78088_7_);
            this.crest.render(p_78088_7_);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
            GL11.glTranslatef(0.0F, 24.0F * p_78088_7_, 0.0F);
            this.body.render(p_78088_7_);
            this.rightLeg.render(p_78088_7_);
            this.leftLeg.render(p_78088_7_);
            this.rightWing.render(p_78088_7_);
            this.leftWing.render(p_78088_7_);
            GL11.glPopMatrix();
        }else{
            this.head.render(p_78088_7_);
            this.bill.render(p_78088_7_);
            this.chin.render(p_78088_7_);
            this.body.render(p_78088_7_);
            this.rightLeg.render(p_78088_7_);
            this.leftLeg.render(p_78088_7_);
            this.rightWing.render(p_78088_7_);
            this.leftWing.render(p_78088_7_);
            this.crest.render(p_78088_7_);
        }
    }

    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_){
        this.head.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);
        this.head.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
        this.bill.rotateAngleX = this.head.rotateAngleX;
        this.bill.rotateAngleY = this.head.rotateAngleY;
        this.chin.rotateAngleX = this.head.rotateAngleX;
        this.chin.rotateAngleY = this.head.rotateAngleY;
        this.crest.rotateAngleX = this.head.rotateAngleX;
        this.crest.rotateAngleY = this.head.rotateAngleY;
        this.body.rotateAngleX = ((float)Math.PI / 2F);
        this.rightLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
        this.leftLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 1.4F * p_78087_2_;
        this.rightWing.rotateAngleZ = p_78087_3_;
        this.leftWing.rotateAngleZ = -p_78087_3_;
    }
    
    public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_){
        EntityGeneChicken chicken = (EntityGeneChicken)p_78086_1_;
        if (chicken.isSitting()){
        	this.head.offsetY = 0.3F;
        	this.bill.offsetY = 0.3F;
        	this.chin.offsetY = 0.3F;
        	this.crest.offsetY = 0.3F;
        	this.body.offsetY = 0.3F;
        	this.rightLeg.showModel = false;
        	this.leftLeg.showModel = false;
        	this.rightWing.offsetY = 0.3F;
            this.leftWing.offsetY = 0.3F;
        }else{
        	this.head.offsetY = 0.0F;
        	this.bill.offsetY = 0.0F;
        	this.chin.offsetY = 0.0F;
        	this.crest.offsetY = 0.0F;
        	this.body.offsetY = 0.0F;
        	this.rightLeg.showModel = true;
        	this.leftLeg.showModel = true;
        	this.rightWing.offsetY = 0.0F;
            this.leftWing.offsetY =  0.0F;
        }
    }
}