package com.nyagosu.chickengenes.tileentity;

import com.nyagosu.chickengenes.ChickenGenesCore;
import com.nyagosu.chickengenes.block.BlockChickenGeneProcessor;
import com.nyagosu.chickengenes.entity.GeneData;
import com.nyagosu.chickengenes.item.ItemChickenCell;
import com.nyagosu.chickengenes.item.ItemChickenGene;
import com.nyagosu.chickengenes.util.Randory;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGeneProcessor extends TileEntity implements ISidedInventory {
	
	public static String INV_NAME = "Chicken Gene Processor";
	
	public int burnTime;
	public int currentItemBurnTime;
	
	public int cookTime;
	public static int SMELT_TIME = 100;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {1, 2 , 3};
	private static final int[] slots_sides = new int[] {1};
	
	private String field_145958_o;
	
	public ItemStack[] itemStacks = new ItemStack[4];
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items",10);
		this.itemStacks = new ItemStack[this.getSizeInventory()];
		
		for (int i = 0; i < nbttaglist.tagCount(); ++i){
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");
			if (b0 >= 0 && b0 < this.itemStacks.length){
				this.itemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		this.burnTime = par1NBTTagCompound.getShort("BurnTime");
		this.cookTime = par1NBTTagCompound.getShort("CookTime");
		this.currentItemBurnTime = getItemBurnTime(this.itemStacks[1]);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound){
		super.writeToNBT(par1NBTTagCompound);
		
		par1NBTTagCompound.setShort("BurnTime", (short)this.burnTime);
		par1NBTTagCompound.setShort("CookTime", (short)this.cookTime);
		NBTTagList nbttaglist = new NBTTagList();
 		for (int i = 0; i < this.itemStacks.length; ++i){
			if (this.itemStacks[i] != null){
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.itemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		par1NBTTagCompound.setTag("Items", nbttaglist);
	}
	
	/*
	 * パケット系
	 */
	@Override
	public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
	}
 
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }
	
	
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		return this.cookTime * par1 / SMELT_TIME;
	}
	 
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1)
	{
		if (this.currentItemBurnTime == 0)
		{
			this.currentItemBurnTime = SMELT_TIME;
		}
 
		return this.burnTime * par1 / this.currentItemBurnTime;
	}
 
	public boolean isBurning()
	{
		return this.burnTime > 0;
	}
	
	//更新時に呼び出される
	//かまどの処理
	public void updateEntity(){
		boolean flag = this.burnTime > 0;
		boolean flag1 = false;
 
		if (this.burnTime > 0){
			--this.burnTime;
		}
 
		if (!this.worldObj.isRemote){
			
			if (this.burnTime == 0 && this.canGeneProcess()){
				this.currentItemBurnTime = this.burnTime = getItemBurnTime(this.itemStacks[1]);
				if (this.burnTime > 0){
					flag1 = true;
					if (this.itemStacks[1] != null){
						--this.itemStacks[1].stackSize;
						if (this.itemStacks[1].stackSize == 0){
							this.itemStacks[1] = this.itemStacks[1].getItem().getContainerItem(this.itemStacks[1]);
						}
					}
				}
			}
 
			if (this.isBurning() && this.canGeneProcess()){
				++this.cookTime;
				if (this.cookTime == SMELT_TIME){
					this.cookTime = 0;
					//this.smeltItem();
					this.processItem();
					flag1 = true;
				}
			}else{
				this.cookTime = 0;
			}
 
			if (flag != this.burnTime > 0){
				flag1 = true;
				BlockChickenGeneProcessor.updateFurnaceBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}
 
		if (flag1){
			this.markDirty();
		}
	}
	
	public void func_145951_a(String p_145951_1_){
        this.field_145958_o = p_145951_1_;
    }
	
	private boolean canGeneProcess(){
		if (this.itemStacks[0] == null){
			return false;
		}else{
			if(
					!(this.itemStacks[0].getItem() instanceof ItemChickenCell) && 
					!(this.itemStacks[0].getItem() instanceof ItemChickenGene)
					){
				return false;
			}
			if (this.itemStacks[2] == null) return true;
			return false;
		}
	}
	
	public void processItem(){
		
		if(!this.canGeneProcess())return;
		ItemStack from_itemstack = this.itemStacks[0];
		
		if(this.itemStacks[0].getItem() instanceof ItemChickenCell) {
			this.processTypeCell(from_itemstack);
		}else if(this.itemStacks[0].getItem() instanceof ItemChickenGene){
			this.processTypeGene(from_itemstack);
		}
		
	}
	
	public void processTypeGene(ItemStack from_itemstack){
		
		Randory rand = new Randory();
		rand.addRate(0,50);
		rand.addRate(1,50);
		ItemStack itemstack = null;
		
		if(rand.getValue() == 1){
			//Success
			itemstack = new ItemStack(ChickenGenesCore.itemChickenGeneMutation,1);
			
			if (this.itemStacks[2] == null){
				this.itemStacks[2] = itemstack.copy();
			}else if (this.itemStacks[2].isItemEqual(itemstack)){
				this.itemStacks[2].stackSize += itemstack.stackSize;
			}
			
		}else{
			//Failure
			itemstack = new ItemStack(ChickenGenesCore.itemChickenGeneBroken,1);
			
			if (this.itemStacks[3] == null){
				this.itemStacks[3] = itemstack.copy();
			}else if (this.itemStacks[3].isItemEqual(itemstack)){
				this.itemStacks[3].stackSize += itemstack.stackSize;
			}
			
		}
		
		--this.itemStacks[0].stackSize;
		if (this.itemStacks[0].stackSize <= 0){
			this.itemStacks[0] = null;
		}
	}
	
	public void processTypeCell(ItemStack from_itemstack){
		
		NBTTagCompound from_nbt = from_itemstack.getTagCompound();
		String gene_data_string = from_nbt.getString("GeneData");
		
		ItemStack itemstack = new ItemStack(ChickenGenesCore.itemChickenGene,1);
		ItemChickenGene gene = (ItemChickenGene)itemstack.getItem();
		GeneData gene_data = new GeneData(gene_data_string);
		gene.setGeneData(itemstack,gene_data);
		
		if (this.itemStacks[2] == null){
			this.itemStacks[2] = itemstack.copy();
		}else if (this.itemStacks[2].isItemEqual(itemstack)){
			this.itemStacks[2].stackSize += itemstack.stackSize;
		}
		--this.itemStacks[0].stackSize;
		if (this.itemStacks[0].stackSize <= 0){
			this.itemStacks[0] = null;
		}
	}
	
    public static int getItemBurnTime(ItemStack p_145952_0_){
        if (p_145952_0_ == null){
            return 0;
        }else{
        	int moddedBurnTime = net.minecraftforge.event.ForgeEventFactory.getFuelBurnTime(p_145952_0_);
        	if (moddedBurnTime >= 0) return moddedBurnTime;
            Item item = p_145952_0_.getItem();
            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air){
                Block block = Block.getBlockFromItem(item);
                if (block == Blocks.wooden_slab){
                    return 150;
                }
                if (block.getMaterial() == Material.wood){
                    return 300;
                }
                if (block == Blocks.coal_block){
                    return 16000;
                }
            }
            if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemHoe && ((ItemHoe)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item == Items.stick) return 100;
            if (item == Items.coal) return 1600;
            if (item == Items.lava_bucket) return 20000;
            if (item == Item.getItemFromBlock(Blocks.sapling)) return 100;
            if (item == Items.blaze_rod) return 2400;
            return GameRegistry.getFuelValue(p_145952_0_);
        }
    }
    
  	public static boolean isItemFuel(ItemStack par0ItemStack)
  	{
  		return getItemBurnTime(par0ItemStack) > 0;
  	}
   
  	@Override
  	public int getSizeInventory() {
  		return this.itemStacks.length;
  	}
   
  	@Override
  	public ItemStack getStackInSlot(int par1) {
  		return this.itemStacks[par1];
  	}
  	
  	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.itemStacks[par1] != null){
			ItemStack itemstack;
			if (this.itemStacks[par1].stackSize <= par2){
				itemstack = this.itemStacks[par1];
				this.itemStacks[par1] = null;
				return itemstack;
			}else{
				itemstack = this.itemStacks[par1].splitStack(par2);
				if (this.itemStacks[par1].stackSize == 0){
					this.itemStacks[par1] = null;
				}
				return itemstack;
			}
		}else{
			return null;
		}
	}
  	
  	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.itemStacks[par1] != null){
			ItemStack itemstack = this.itemStacks[par1];
			this.itemStacks[par1] = null;
			return itemstack;
		}else{
			return null;
		}
	}
  	
 	@Override
 	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
 		this.itemStacks[par1] = par2ItemStack;
 		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()){
 			par2ItemStack.stackSize = this.getInventoryStackLimit();
 		}
 	}
 	
 	public String getInvName() {
 		return INV_NAME;
 	}
 	
 	public boolean isInvNameLocalized() {
 		return false;
 	}
  
 	@Override
 	public int getInventoryStackLimit() {
 		return 64;
 	}
 	
 	@Override
 	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
 		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
 	}
 	
 	@Override
 	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
 		return par1 == 2 ? false : (par1 == 1 ? this.isItemFuel(par2ItemStack) : true);
 	}
  
 	@Override
 	public int[] getAccessibleSlotsFromSide(int par1) {
 		return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
 	}
 	
 	@Override
 	public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) {
 		return this.isItemValidForSlot(par1, par2ItemStack);
 	}
  
 	@Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_){
        return p_102008_3_ != 0 || p_102008_1_ != 1 || p_102008_2_.getItem() == Items.bucket;
    }

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}
}
