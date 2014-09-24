package de.maefvfis.gameoverlay.client.crafting.recipes;


import de.maefvfis.gameoverlay.client.crafting.CustomInventoryCrafting;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RecipesMapCloning implements IRecipe
{
    private static final String __OBFID = "CL_00000087";

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(CustomInventoryCrafting p_77569_1_, World p_77569_2_)
    {
        int i = 0;
        ItemStack itemstack = null;

        for (int j = 0; j < p_77569_1_.getSizeInventory(); ++j)
        {
            ItemStack itemstack1 = p_77569_1_.getStackInSlot(j);

            if (itemstack1 != null)
            {
                if (itemstack1.getItem() == Items.filled_map)
                {
                    if (itemstack != null)
                    {
                        return false;
                    }

                    itemstack = itemstack1;
                }
                else
                {
                    if (itemstack1.getItem() != Items.map)
                    {
                        return false;
                    }

                    ++i;
                }
            }
        }

        return itemstack != null && i > 0;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(CustomInventoryCrafting p_77572_1_)
    {
        int i = 0;
        ItemStack itemstack = null;

        for (int j = 0; j < p_77572_1_.getSizeInventory(); ++j)
        {
            ItemStack itemstack1 = p_77572_1_.getStackInSlot(j);

            if (itemstack1 != null)
            {
                if (itemstack1.getItem() == Items.filled_map)
                {
                    if (itemstack != null)
                    {
                        return null;
                    }

                    itemstack = itemstack1;
                }
                else
                {
                    if (itemstack1.getItem() != Items.map)
                    {
                        return null;
                    }

                    ++i;
                }
            }
        }

        if (itemstack != null && i >= 1)
        {
            ItemStack itemstack2 = new ItemStack(Items.filled_map, i + 1, itemstack.getItemDamage());

            if (itemstack.hasDisplayName())
            {
                itemstack2.setStackDisplayName(itemstack.getDisplayName());
            }

            return itemstack2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return 9;
    }

    public ItemStack getRecipeOutput()
    {
        return null;
    }

	@Override
	public ItemStack[][] getRecipe(ItemStack Itemstack) {
		// TODO Auto-generated method stub
		return null;
	}
}