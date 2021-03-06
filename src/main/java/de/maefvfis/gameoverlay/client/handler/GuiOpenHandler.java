package de.maefvfis.gameoverlay.client.handler;

import sun.security.ssl.Debug;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.CreativeCrafting;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraftforge.client.event.GuiOpenEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.maefvfis.gameoverlay.client.gui.CraftingBench;
import de.maefvfis.gameoverlay.client.gui.CreativeInv;
import de.maefvfis.gameoverlay.client.gui.GuiChatCustom;

public class GuiOpenHandler {

	
    @SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)


    public void onEvent(GuiOpenEvent event)
    {
    	if(event.gui instanceof GuiChat && !(event.gui instanceof GuiChatCustom)) {
        	event.setCanceled(true);
        	GuiChatCustom blubb = new GuiChatCustom();
        	net.minecraftforge.client.event.GuiOpenEvent event2 = new net.minecraftforge.client.event.GuiOpenEvent(blubb);
        	Minecraft.getMinecraft().displayGuiScreen(blubb);
        	net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event2);
        } 
    	
    	if(event.gui instanceof GuiCrafting && !(event.gui instanceof CraftingBench)) {
        	event.setCanceled(true);
        	//((GuiCrafting)event).
        	CraftingBench blubb = new CraftingBench(Minecraft.getMinecraft().thePlayer.inventory, Minecraft.getMinecraft().theWorld, 0, 0, 0);
        	net.minecraftforge.client.event.GuiOpenEvent event2 = new net.minecraftforge.client.event.GuiOpenEvent(blubb);
        	Minecraft.getMinecraft().displayGuiScreen(blubb);
        	net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event2);
        } 
    	
    	
    	
    	
    }  
}
