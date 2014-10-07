package de.maefvfis.gameoverlay.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import sun.security.ssl.Debug;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.ContainerWorkbench;

public class CraftingBenchManager {

	public ContainerWorkbench CraftingBench;
	public ItemStack[] recipes;
	public ItemStack resultStack;
	public Minecraft mc = Minecraft.getMinecraft();
	
	public int clickcounter = 0;
	
	public CraftingBenchManager(ContainerWorkbench CraftingBench2, ItemStack[] recipes2, ItemStack resultStack2) {
		this.CraftingBench = CraftingBench2;
		this.recipes = recipes2;
		this.resultStack = resultStack2;
	}
	
	public void stackcraft() {
		this.click(0, mouseAction.shiftLeftClick);
		this.clearCraftingBench();
		int amount = this.countMaxCrafting();
		int space = countInventorySpaceForItem(this.resultStack);
		
		if(amount > space) {
			amount = space;
		}
		if(amount == 0) { return; }
		Debug.println("Anzahl Herstellen", amount+"");
		
		for(int j = 0; j < 9; j++) {
			
			ItemStack recipe = recipes[j];
			if(recipe == null) {
				continue;
			}
			this.getItemStackFromInventory(recipe, amount);
			this.click(j+1, mouseAction.leftClick);
		}
		Debug.println("Anzahl Clicks", clickcounter + "");
		
	}
	
	public void craft() {
		
		this.clearCraftingBench();
		for(int i = 0; i < 36; i++) {
			int amount = this.countMaxCrafting();
			int space = countInventorySpaceForItem(this.resultStack);
			if(amount > space) {
				amount = space;
			}
			if(amount == 0) { break; }
			
			Debug.println("Anzahl Herstellen", amount+"");
			for(int j = 0; j < 9; j++) {
				
    			ItemStack recipe = recipes[j];
    			if(recipe == null) {
    				continue;
    			}
    			this.getItemStackFromInventory(recipe, amount);
    			this.click(j+1, mouseAction.leftClick);
			}
			this.click(0, mouseAction.shiftLeftClick);
			
		}
		this.clearCraftingBench();
		Debug.println("Anzahl Clicks", clickcounter + "");
	}
	
	public void clearCraftingBench() {
		for(int j = 0; j < 9; j++) {
			this.click(j+1, mouseAction.shiftLeftClick);
		}
	}
	public int countInventorySpaceForItem(ItemStack ItemStack) {
		
		if(ItemStack.stackSize == 0) {
			Debug.println("StackSize Null",ItemStack.getDisplayName());
			return 0;
		}
		
		int returner = 0;
		
		for(int i = 10; i < 46; i++) { 
			Slot inventorySlot = (Slot) this.CraftingBench.inventorySlots.get(i);
			
			if(!inventorySlot.getHasStack()) {
				returner = returner + (ItemStack.getMaxStackSize() / ItemStack.stackSize);
				continue;
			}
			
			if(isSameItem(inventorySlot.getStack(),ItemStack)) {
				returner = returner + ((ItemStack.getMaxStackSize() - inventorySlot.getStack().stackSize) / ItemStack.stackSize);
				continue;
			}
		}
		Debug.println("Inventory Space", returner+"");
		return returner;
		
	}
	
	public void getItemStackFromInventory(ItemStack ItemStack, int amount) {
		Debug.println("Amount Pickup", amount+"");
		for(int i = 10; i < 46; i++) { 
			
			Slot inventorySlot = (Slot) this.CraftingBench.inventorySlots.get(i);
			if(!inventorySlot.getHasStack()) {
				continue;
			}
			ItemStack invStack = inventorySlot.getStack().copy();
			if(isSameItem(invStack,ItemStack)) {

				if(invStack.stackSize == amount) {
					this.click(i, mouseAction.leftClick);
					return;
				}
				
				if(invStack.stackSize > amount) {
					int stackSize = invStack.stackSize;
					this.click(i, mouseAction.leftClick);
					
					for(int c = stackSize; c > amount; c--) {
						this.click(i, mouseAction.rightClick);
					}
					return;
				}
				
				if(invStack.stackSize < amount) {
					int nextslot = this.getNextSlot(i+1, ItemStack);
					this.click(nextslot, mouseAction.leftClick);
					this.click(i, mouseAction.leftClick);
					this.click(nextslot, mouseAction.leftClick);
					this.getItemStackFromInventory(ItemStack,amount);
					return;
				}
				Debug.println("ERROR", "Crafting Fehler 2");
			}
		}
	}
	
	
	public int getNextSlot(int c, ItemStack ItemStack) {
		for(int i = c; i < 46; i++) {
			Slot inventorySlot = (Slot) this.CraftingBench.inventorySlots.get(i);
			if(!inventorySlot.getHasStack()) {
				continue;
			}
			if(isSameItem(inventorySlot.getStack(),ItemStack)) {
				return i;
			}
		}
		Debug.println("getNextSlot", "Kein Slot Gefunden");
		return 0;
	}
	
	public int countMaxCrafting() {
		List<ItemStack> ItemList = this.getUniqueRecipeItemlist();
		int returner = this.getSmallestStackSize(ItemList);
		for(ItemStack RecipeItem: ItemList) {
			int count = this.countItemsInInventory(RecipeItem) / this.countItemOcurenceInRecipe(RecipeItem);
			if(count < returner) {
				returner = count;
			} 
		}
		return returner;
	}
	
	public int getSmallestStackSize(List<ItemStack> ItemList) {
		int returner = 0;
		for(ItemStack ItemStack: ItemList) {
			if(returner == 0 || ItemStack.getMaxStackSize() < returner) {
				returner = ItemStack.getMaxStackSize();
			}
		}
		return returner;
	}
	
	public int countItemsInInventory(ItemStack check) {
		int Returner = 0;
		for(int i = 10; i < 46; i++) { 
			Slot inventorySlot = (Slot) this.CraftingBench.inventorySlots.get(i);
			
			if(!inventorySlot.getHasStack()) {
				continue;
			}
			
			if(isSameItem(inventorySlot.getStack(),check)) {
				Returner = Returner + inventorySlot.getStack().stackSize;
			}
		}
		return Returner;
	}
	
	public List<ItemStack> getUniqueRecipeItemlist() {
		List<ItemStack> Returner = new ArrayList();
		Returner.clear();
		for(int i = 0; i < 9; i++) {
			if(!Returner.contains(this.recipes[i]) && this.recipes[i] != null) {
				Returner.add(this.recipes[i]);
			}
		}
		return Returner;
	}
	
	public int countItemOcurenceInRecipe(ItemStack check) {
		int Returner = 0;
		for(int i = 0; i < 9; i++) {
			if(isSameItem(this.recipes[i],check)) {
				Returner++;
			}
		}
		return Returner;
	}
	
	public boolean isSameItem(ItemStack Stack1, ItemStack Stack2) {
		if(Stack1 == null || Stack2 == null) {
			return false;
		}
		if(Stack1.getItem() == Stack2.getItem() && Stack1.getItemDamage() == Stack2.getItemDamage()) {
			return true;
		}
		return false;
	}
	
	public void click(int Slot, int[] Action) {
		mc.playerController.windowClick(this.CraftingBench.windowId, Slot, Action[0], Action[1], mc.thePlayer);
		clickcounter++;
	}
	
	public static class mouseAction {					// Key		    Mode	
		public static final int[] leftClick = 			{mouseKey.left,mouseMode.norm};
		public static final int[] rightClick = 			{mouseKey.right,mouseMode.norm};
		public static final int[] shiftLeftClick = 		{mouseKey.left,mouseMode.shift};
		public static final int[] shiftRightClick = 	{mouseKey.right,mouseMode.shift};
	}
	public class mouseKey {
		public static final int left = 0;
		public static final int right = 1;
	}
	public class mouseMode {
		public static final int norm = 0;
		public static final int shift = 1;
	}
}