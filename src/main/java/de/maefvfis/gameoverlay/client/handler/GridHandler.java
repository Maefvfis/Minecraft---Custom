package de.maefvfis.gameoverlay.client.handler;

import sun.security.ssl.Debug;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.maefvfis.gameoverlay.client.gui.EntityGrid;
import de.maefvfis.gameoverlay.client.gui.Grid2D;
import de.maefvfis.gameoverlay.handler.ConfigurationHandler;

public class GridHandler {
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Text event) {
        // Render Mob Grid
		if(ConfigurationHandler.myConfigShowGrid == true) {
			//new Grid2D(Integer.valueOf(ConfigurationHandler.myGridSize));
			
		}
	}
}
