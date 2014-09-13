package de.maefvfis.gameoverlay.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;

public class InfoIngameGui {

	private final Minecraft mc = Minecraft.getMinecraft();
	private int X = MathHelper.floor_double(mc.thePlayer.posX); // j3
	private int Y = MathHelper.floor_double(mc.thePlayer.posY); // k3
	private int Z = MathHelper.floor_double(mc.thePlayer.posZ); // l3
	private int currentY = 10;
	private final Chunk chunk = mc.theWorld.getChunkFromBlockCoords(X, Z);
	
	public InfoIngameGui() {
		
		FontRenderer fontRender =  mc.fontRenderer;
		ChunckViewer CViewer = new ChunckViewer();
		// Entity Counts
		fontRender.drawStringWithShadow("In Chunk (Ani/Vil/Gol/Mon): " + CViewer.countEntity(EntityAnimal.class, chunk) + "/" + CViewer.countEntity(EntityVillager.class, chunk) + "/" + CViewer.countEntity(EntityGolem.class, chunk)+ "/" + CViewer.countEntity(EntityMob.class, chunk), 5, currentY, 0xffffff);
		currentY += 10;
		
		// Chunk / ChunkBiome
		fontRender.drawStringWithShadow("Chunk: " + Integer.valueOf(X >> 4) + " (" + Integer.valueOf(X & 15) + "), " + Integer.valueOf(Z >> 4) + " (" + Integer.valueOf(Z & 15) + ")" + " | " + chunk.getBiomeGenForWorldCoords(X & 0xf, X & 0xf,mc.theWorld.getWorldChunkManager()).biomeName, 5, currentY, 0xffffff);
		currentY += 10;
		
		// Chords
		fontRender.drawStringWithShadow(Integer.valueOf(X) + ", " + Integer.valueOf(Z) + " (" + Integer.valueOf(Y) + ")",5, currentY, 0xffffff);
		currentY += 10;
		
		// light level
		if (mc.theWorld != null && mc.theWorld.blockExists(X, Y, Z))
        {
			fontRender.drawStringWithShadow("Light: " + chunk.getSavedLightValue(EnumSkyBlock.Block, X & 15, Y, Z & 15) + " / " + chunk.getSavedLightValue(EnumSkyBlock.Sky, X & 15, Y, Z & 15), 5,currentY, 0xffffff);
			currentY += 10;
        }
		
		// FPS
		String[] splits = mc.debug.split(",");
		fontRender.drawStringWithShadow(splits[0], 5, currentY,0xffffff);
	}
}
