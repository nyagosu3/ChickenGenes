package com.nyagosu.chickengenes.container;

import com.nyagosu.chickengenes.item.ItemChickenCell;
import com.nyagosu.chickengenes.item.ItemChickenGene;
import com.nyagosu.chickengenes.tileentity.TileEntityGeneProcessor;
import com.nyagosu.chickengenes.util.TooCon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGeneProcessor extends Container {
	
	private TileEntityGeneProcessor tileentity;
	 
	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;
	
	public ContainerGeneProcessor(EntityPlayer player, TileEntityGeneProcessor par2TileEntity) {
		this.tileentity = par2TileEntity;
 
		this.addSlotToContainer(new Slot(this.tileentity, 0, 56, 17));
		this.addSlotToContainer(new Slot(this.tileentity, 1, 56, 53));
		this.addSlotToContainer(new Slot(this.tileentity, 2, 116, 35));
		this.addSlotToContainer(new Slot(this.tileentity, 3, 142, 35));
		int i;
 
		for (i = 0; i < 3; ++i){
			for (int j = 0; j < 9; ++j){
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
 
		for (i = 0; i < 9; ++i){
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}
	}
	
	public void addCraftingToCrafters(ICrafting par1ICrafting){
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.tileentity.cookTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.tileentity.burnTime);
		par1ICrafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime);
	}
	
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
 
		for (int i = 0; i < this.crafters.size(); ++i){
			ICrafting icrafting = (ICrafting)this.crafters.get(i);
 
			if (this.lastCookTime != this.tileentity.cookTime){
				icrafting.sendProgressBarUpdate(this, 0, this.tileentity.cookTime);
			}
 
			if (this.lastBurnTime != this.tileentity.burnTime){
				icrafting.sendProgressBarUpdate(this, 1, this.tileentity.burnTime);
			}
 
			if (this.lastItemBurnTime != this.tileentity.currentItemBurnTime){
				icrafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime);
			}
		}
 
		this.lastCookTime = this.tileentity.cookTime;
		this.lastBurnTime = this.tileentity.burnTime;
		this.lastItemBurnTime = this.tileentity.currentItemBurnTime;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2){
		if (par1 == 0){
			this.tileentity.cookTime = par2;
		}
		if (par1 == 1){
			this.tileentity.burnTime = par2;
		}
		if (par1 == 2){
			this.tileentity.currentItemBurnTime = par2;
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.tileentity.isUseableByPlayer(par1EntityPlayer);
	}
	
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2){
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);
 
		if (slot != null && slot.getHasStack()){
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			TooCon.log(String.valueOf(par2));
			
			if (par2 == 0 || par2 == 1 || par2 == 2 || par2 == 3){
				if (!this.mergeItemStack(itemstack1, 4, 39, true)){
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}else if (
					par2 != 0 &&
					par2 != 1 &&
					par2 != 2 &&
					par2 != 3
					){
				if (this.isMaterialItem(itemstack1)){
					if (!this.mergeItemStack(itemstack1, 0, 1, false)){
						return null;
					}
				}else if (TileEntityGeneProcessor.isItemFuel(itemstack1)){
					if (!this.mergeItemStack(itemstack1, 1, 2, false)){
						return null;
					}
				}else if (par2 >= 4 && par2 < 30){
					if (!this.mergeItemStack(itemstack1, 30, 39, false)){
						return null;
					}
				}else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemstack1, 4, 30, false)){
					return null;
				}
			}else if (!this.mergeItemStack(itemstack1, 4, 39, false)){
				return null;
			}
			if (itemstack1.stackSize == 0){
				slot.putStack((ItemStack)null);
			}else{
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize){
				return null;
			}
			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}
	
	public boolean isMaterialItem(ItemStack itemstack){
		if(itemstack.getItem() instanceof ItemChickenCell)return true;
		if(itemstack.getItem() instanceof ItemChickenGene)return true;
		return false;
	}
}
