package de.maefvfis.gameoverlay.client.crafting.recipes;

import de.maefvfis.gameoverlay.client.crafting.CustomInventoryCrafting;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    boolean matches(CustomInventoryCrafting p_77569_1_, World p_77569_2_);
    
    
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    ItemStack[][] getRecipe(ItemStack Itemstack);
    
    
    /**
     * Returns an Item that is the result of this recipe
     */
    ItemStack getCraftingResult(CustomInventoryCrafting p_77572_1_);

    /**
     * Returns the size of the recipe area
     */
    int getRecipeSize();

    ItemStack getRecipeOutput();
}