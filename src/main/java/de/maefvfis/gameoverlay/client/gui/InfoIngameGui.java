package de.maefvfis.gameoverlay.client.gui;

import org.lwjgl.opengl.GL11;

import sun.security.ssl.Debug;
import de.maefvfis.gameoverlay.objects.DayTime;
import de.maefvfis.gameoverlay.objects.Mondphasen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;





import cpw.mods.fml.client.config.GuiButtonExt;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.gui.GuiIngameMenu;





import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class InfoIngameGui extends GuiScreen {

	private final Minecraft mc = Minecraft.getMinecraft();
	private int X = MathHelper.floor_double(mc.thePlayer.posX); // j3
	private int Y = MathHelper.floor_double(mc.thePlayer.posY); // k3
	private int Z = MathHelper.floor_double(mc.thePlayer.posZ); // l3
	
	
	private final ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
	private final int screenwidth = scaledresolution.getScaledWidth();
	private final int screenheight = scaledresolution.getScaledHeight();
	
	private int currentY = 10;
	private final Chunk chunk = mc.theWorld.getChunkFromBlockCoords(X, Z);
	
	public InfoIngameGui() {
		
		GL11.glScalef(0.75F,0.75F,0.75F);
		
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
		currentY += 10;
		// Zeiten#
		//worldInfo.this
		
		//fontRender.drawStringWithShadow(DayTime.getTimeString(Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldTime()) + " | " + Mondphasen.getPhase(Minecraft.getMinecraft().theWorld.getMoonPhase()),5, currentY, 0xffffff);
		
		
		GL11.glScalef(1.25F,1.25F,1.25F);
		
		
		 //draw your Gui here, only thing you need to change is the path
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glScalef(0.5F,0.5F,0.5F);
	    mc.renderEngine.bindTexture(new ResourceLocation("gameoverlay","textures/clock_bg.png"));
	    
	    //mc.renderEngine.
	    //mc.renderGlobal.d
	    //drawTexturedModalRect(200, 200, 0, 0, 200, 200);
	    //drawRect(200, 200, 0, 0, 0x10000000);
	    //this.mc.getTextureManager().bindTexture(pageTextures);
	    //drawRect((screenwidth / 2)-35, 5, (screenwidth / 2)+35, 45, 0xAA000000);
	    drawTexturedModalRect(screenwidth - 50 , 5, 0, 0, 140, 80);
		
	    mc.renderEngine.bindTexture(new ResourceLocation("gameoverlay","textures/moon_phases.png"));
	    
	    int[] MoonTextOff = Mondphasen.getPhase_iconoffset(Minecraft.getMinecraft().theWorld.getMoonPhase());
	    drawTexturedModalRect(screenwidth - 45 , 10, MoonTextOff[0] / 2, MoonTextOff[1] / 2, 50, 50);
	    
	    
	    GL11.glScalef(2.0F,2.0F,2.0F);
	    GL11.glScalef(0.75F,0.75F,0.75F);
	    
		
	    
	    
	    
	    fontRender.drawStringWithShadow(DayTime.getTimeString(Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldTime()),(int) ((screenwidth / 1.41) - fontRender.getStringWidth(DayTime.getTimeString(Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldTime())) / 2), 10, 0xffffff);
		
	    GL11.glScalef(1.42F,1.42F,1.42F);


	}
}
