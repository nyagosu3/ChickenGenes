package com.nyagosu.chickengenes.ai;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class EntityAIMateCustom extends EntityAIBase {
	
	private EntityAnimal theAnimal;
    World theWorld;
    private EntityAnimal targetMate;
    int spawnBabyDelay;
    double moveSpeed;
    
    public EntityAIMateCustom(EntityAnimal p_i1619_1_, double p_i1619_2_){
        this.theAnimal = p_i1619_1_;
        this.theWorld = p_i1619_1_.worldObj;
        this.moveSpeed = p_i1619_2_;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute(){
        if (!this.theAnimal.isInLove()){
            return false;
        }else{
            this.targetMate = this.getNearbyMate();
            return this.targetMate != null;
        }
    }
    
    public boolean continueExecuting(){
        return this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
    }
    
    public void resetTask(){
        this.targetMate = null;
        this.spawnBabyDelay = 0;
    }
    
    public void updateTask(){
        this.theAnimal.getLookHelper().setLookPositionWithEntity(this.targetMate, 10.0F, (float)this.theAnimal.getVerticalFaceSpeed());
        this.theAnimal.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
        ++this.spawnBabyDelay;

        if (this.spawnBabyDelay >= 60 && this.theAnimal.getDistanceSqToEntity(this.targetMate) < 9.0D){
            this.spawnBaby();
        }
    }
    
    private EntityAnimal getNearbyMate(){
        float f = 8.0F;
        List list = this.theWorld.getEntitiesWithinAABB(this.theAnimal.getClass(), this.theAnimal.boundingBox.expand((double)f, (double)f, (double)f));
        double d0 = Double.MAX_VALUE;
        EntityAnimal entityanimal = null;
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            EntityAnimal entityanimal1 = (EntityAnimal)iterator.next();
            if (this.theAnimal.canMateWith(entityanimal1) && this.theAnimal.getDistanceSqToEntity(entityanimal1) < d0){
                entityanimal = entityanimal1;
                d0 = this.theAnimal.getDistanceSqToEntity(entityanimal1);
            }
        }
        return entityanimal;
    }
    
    private void spawnBaby(){
    	
        EntityAgeable entityageable = this.theAnimal.createChild(this.targetMate);
        
        if (entityageable != null){
            EntityPlayer entityplayer = this.theAnimal.func_146083_cb();

            if (entityplayer == null && this.targetMate.func_146083_cb() != null){
                entityplayer = this.targetMate.func_146083_cb();
            }
            
            if (entityplayer != null){
                entityplayer.triggerAchievement(StatList.field_151186_x);
                if (this.theAnimal instanceof EntityCow){
                    entityplayer.triggerAchievement(AchievementList.field_150962_H);
                }
            }
            
            this.theAnimal.setGrowingAge(6000);
            this.targetMate.setGrowingAge(6000);
            this.theAnimal.resetInLove();
            this.targetMate.resetInLove();
            entityageable.setGrowingAge(-24000);
            entityageable.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0F, 0.0F);
            this.theWorld.spawnEntityInWorld(entityageable);
            Random random = this.theAnimal.getRNG();
            
//            if (this.theWorld.getGameRules().getGameRuleBooleanValue("doMobLoot")){
//                this.theWorld.spawnEntityInWorld(new EntityXPOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, random.nextInt(7) + 1));
//            }
        }
    }    
}
