package de.maefvfis.gameoverlay.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;

public class ShowItemUsage {

	private Minecraft mc = Minecraft.getMinecraft();
	
	public ShowItemUsage() {
		// Show item usage
		ItemStack currentItem = mc.thePlayer.inventory.getCurrentItem();
		if (currentItem != null) {
			
			if (currentItem.getMaxDamage() >= 29) {
				
				int usesLeft = currentItem.getMaxDamage() - currentItem.getItemDamage();
				long time = mc.theWorld.getWorldInfo().getWorldTime();
				int alertTime = ((int) (time % 32));
				if (alertTime >= 16) {
					alertTime = 32 - alertTime;
				}
				if (alertTime == 16) {
					alertTime = 15;
				}
				int color = 0xffffff;
				int alertColor = 0xff0000 + ((alertTime << 4) << 8);
				
				ScaledResolution scaledRes = new ScaledResolution(mc,mc.displayWidth, mc.displayHeight);
				int yPos = scaledRes.getScaledHeight();
				
				if (usesLeft < 15)
					color = alertColor;
				
				mc.fontRenderer.drawStringWithShadow("Item uses left: " + Integer.valueOf(usesLeft), 5, yPos - 10, color);
			}
			
		}
	}
}
