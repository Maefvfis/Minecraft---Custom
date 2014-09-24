package de.maefvfis.gameoverlay.client.gui;

import org.lwjgl.opengl.GL11;

import de.maefvfis.gameoverlay.client.crafting.CustomContainerWorkbench;
import sun.security.ssl.Debug;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CraftingBench extends GuiContainer  {

    private static final ResourceLocation craftingTableGuiTextures = new ResourceLocation("textures/gui/container/crafting_table.png");
    private static final String __OBFID = "CL_00000750";

    public CraftingBench(InventoryPlayer p_i1084_1_, World p_i1084_2_, int p_i1084_3_, int p_i1084_4_, int p_i1084_5_)
    {
        super(new CustomContainerWorkbench(p_i1084_1_, p_i1084_2_, p_i1084_3_, p_i1084_4_, p_i1084_5_));
    }
    
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 28, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
        //this.buttonList.add(new GuiButton(id, x, y, w, h, string));
        int buttonw = 80;
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(999, this.guiLeft + this.xSize - buttonw, this.guiTop, buttonw, 20, "test"));
        
    }
    
    public void actionPerformed(GuiButton button)
    {
	    switch(button.id)
	    {
		    case 999: 
		    {
		    	Debug.println("Button5", "Button");
		    	break;
		    }
		    
	    }
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
    	ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    	int screenwidth = scaledresolution.getScaledWidth();
    	
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(craftingTableGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(this.guiLeft, l, 0, 0, this.xSize, this.ySize);
        
        //mc.displayGuiScreen(new CreativeInv(Minecraft.getMinecraft().thePlayer));
    }

}
