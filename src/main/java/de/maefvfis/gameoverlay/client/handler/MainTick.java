package de.maefvfis.gameoverlay.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;
import de.maefvfis.gameoverlay.client.gui.CopyOfGrid2D;
import de.maefvfis.gameoverlay.client.gui.EntityGrid;
import de.maefvfis.gameoverlay.client.gui.Grid2D;
import de.maefvfis.gameoverlay.client.gui.InfoIngameGui;
import de.maefvfis.gameoverlay.client.gui.ShowItemUsage;
import de.maefvfis.gameoverlay.handler.ConfigurationHandler;
import sun.security.util.Debug;
@SideOnly(Side.CLIENT)

public class MainTick {
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onRenderChunkViewer(RenderGameOverlayEvent.Text event) {
		if (mc.gameSettings.showDebugInfo || !mc.inGameHasFocus)
			return;
		

		// Render Chunk Info In game
		if(ConfigurationHandler.myConfigShowInfoIngameGui == true) {
			new InfoIngameGui();
		}
		
		// Render Chunk Info In game
		if(ConfigurationHandler.myConfigShowItemUsage == true) {
			ShowItemUsage.ShowUsage();
		}
		
        // Render Mob Grid
		if(ConfigurationHandler.myConfigShowGrid == true) {
			new CopyOfGrid2D();
		}
	}

}