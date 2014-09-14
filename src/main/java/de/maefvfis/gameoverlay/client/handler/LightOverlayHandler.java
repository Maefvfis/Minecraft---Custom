package de.maefvfis.gameoverlay.client.handler;

import sun.security.util.Debug;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class LightOverlayHandler {
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void catchEvent(RenderWorldLastEvent  evt) {
		
		
		
		//LigtLevelExtractBlockPowerdByGerhard renderGlobal = (LigtLevelExtractBlockPowerdByGerhard) Minecraft.getMinecraft().renderGlobal;
		//LightLevelOverlay.getInstance().render(new RenderBlocks(Minecraft.getMinecraft().theWorld), evt.partialTicks);
		//Debug.println("awd", "awdawd");
	}
	
}
