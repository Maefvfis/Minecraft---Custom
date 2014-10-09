package de.maefvfis.gameoverlay.client.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import de.maefvfis.gameoverlay.client.gui.ChunckViewer.choords;
import de.maefvfis.gameoverlay.client.settings.Keybindings;
import de.maefvfis.gameoverlay.handler.ConfigurationHandler;
import de.maefvfis.gameoverlay.objects.ChunkImage;
import de.maefvfis.gameoverlay.objects.DayTime;
import de.maefvfis.gameoverlay.objects.Mondphasen;
import de.maefvfis.gameoverlay.objects.Output;
import de.maefvfis.gameoverlay.reference.EntityGridOptions;
import sun.security.ssl.Debug;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;

public class CopyOfGrid2D  extends GuiScreen {
	
	public int gridsize = Integer.valueOf(ConfigurationHandler.myGridSize);
	public int blocksize = 1;
	public int colorAlpha = 0xFF000000;
	public int scaleFactor = 16;
	public int texturesize = 314;
	public int texturecenterX = 173;
	public int texturecenterY = 186;
	public int mapTopOffset = 20;
	public int padding = 0;
	public int chunksize = blocksize * 16;
	public Minecraft mc = Minecraft.getMinecraft();
	public FontRenderer fontRender =  mc.fontRenderer;
	public ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
	public int screenwidth = scaledresolution.getScaledWidth();
	public int screenheight = scaledresolution.getScaledHeight();
	public int X = MathHelper.floor_double(mc.thePlayer.posX);
	public int Z = MathHelper.floor_double(mc.thePlayer.posZ);
	public int xRel = Integer.valueOf(X & 15);
	public int zRel = Integer.valueOf(Z & 15);
	public int fullgridsizex = (chunksize*gridsize) + chunksize;
	public int radius = (fullgridsizex/2);
	public float transfX = screenwidth - radius - padding;
	public float transfY = radius + padding + mapTopOffset;
	public Chunk chunk;
	public int chunkX;
	public int chunkY;
	public boolean fullyInCircle;
	public int blockY;
	public ChunckViewer CViewer = new ChunckViewer();
	public List<choords> choordsList = new ArrayList<choords>();
	
	public CopyOfGrid2D() {
		render_map();
	}
	
	public void render_map() {
		
		GL11.glTranslatef(transfX, transfY, 0);
	    GL11.glRotatef(-this.mc.thePlayer.rotationYaw + 180, 0, 0, 1);

	    GL11.glAlphaFunc(GL11.GL_GREATER, 0.0f);
	    
		mc.renderEngine.bindTexture(new ResourceLocation("gameoverlay","textures/mask9.png"));
		drawTexturedModalRect(-radius+9, -radius+9, 0, 0, 16*(gridsize), 16*(gridsize));
		
		GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_ALPHA,GL11.GL_DST_ALPHA);


	    GL11.glScalef(0.0625F,0.0625F,0.0625F);
	    for (int i1 = 0; i1 <= gridsize; i1++)
		{
			for (int i2 = 0; i2 <= gridsize; i2++)
			{
				chunk = mc.theWorld.getChunkFromBlockCoords(X + Offset(i1), Z + Offset(i2));
				chunkX = ((i1*chunksize))-radius;
				chunkY = (i2*chunksize)-radius;
				
				// Add EntityPositions to List
				List<choords> EntityCount = CViewer.ListEntitysOnChunkChoords(EntityGridOptions.ActiveEntity.EntityClass, chunk, EntityGridOptions.ActiveEntity.WitherSkelett);
				for(choords choord: EntityCount) {
					choordsList.add(choord);
				}
				
				int textureID = ChunkImage.getMapImage(chunk);
				if(textureID != -1) {
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
					drawTexturedModalRect((((i1*chunksize)-radius - xRel + 8) * scaleFactor), (((i2*chunksize)-radius - zRel + 8) * scaleFactor) , 0, 0, 16*scaleFactor, 16*scaleFactor);
				}	
			}
		}
	    
	    drawRect(0,0,1,1,0xFFFF0000);

	    GL11.glRotatef(+mc.thePlayer.rotationYaw + 180, 0, 0, 1);
	    GL11.glEnable(GL11.GL_BLEND);
	    
	    
	    GL11.glScalef(0.5F,0.5F,0.5F);
	    mc.renderEngine.bindTexture(new ResourceLocation("gameoverlay","textures/mobmarker.png"));
	    for(choords choord: choordsList) {
	    	
	    	double angle = (-MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw) / (180 / Math.PI));
	    	int x2 =(int) ((X-choord.x) * Math.cos(angle) - (Z-choord.z) * Math.sin(angle));
	    	int z2 =(int) ((X-choord.x) * Math.sin(angle) + (Z-choord.z) * Math.cos(angle));

	    	
	    	if(in_radius2(0, 0, 64, x2, z2)) {
	    		drawTexturedModalRect((x2 - 4) * scaleFactor * 2, (z2 - 4) * scaleFactor * 2 , 0, 0, 16 * scaleFactor, 16* scaleFactor);
	    	}
	    }
	    GL11.glScalef(2F,2F,2F);
	    //
	    
	    GL11.glScalef(16F,16F,16F);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    
	    mc.renderEngine.bindTexture(new ResourceLocation("gameoverlay","textures/minimap.png"));
		drawTexturedModalRect(-(texturecenterX / 2) -1 , (-texturecenterY / 2) - 1 , 0, 0, 200, 200);
		
		// Uhrzeit
		String DayTimeString = DayTime.getTimeString(Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldTime());
		fontRender.drawString(DayTimeString,- fontRender.getStringWidth(DayTimeString) / 2, -81, 0xffffff);
		
		// Mond
		mc.renderEngine.bindTexture(new ResourceLocation("gameoverlay","textures/moon_phases.png"));
	    int[] MoonTextOff = Mondphasen.getPhase_iconoffset(Minecraft.getMinecraft().theWorld.getMoonPhase());
	    drawTexturedModalRect(-81, -52, MoonTextOff[0] / 2, MoonTextOff[1] / 2, 50, 50);
	    
	    // Weather
	    int weatheroffset = 0;
	    
	    if (!DayTime.getDaytime(Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldTime())) {
            weatheroffset = 96;
        }
	    if(mc.theWorld.isThundering()) {
	    	weatheroffset = 96*3;
	    } else if (mc.theWorld.isRaining()) {
	    	weatheroffset = 96 * 2;
	    }
	    
	    drawTexturedModalRect(-61 , -74, weatheroffset / 2, (96*2) / 2, 50, 50);
		
	    //Grid Type
	    drawTexturedModalRect(37, -73, ((EntityGridOptions.getActiveEntityIndex() * 96) / 2),(96*3) / 2 , 50 / 2, 50 / 2);
	    
	    //Directions North, South etc...
	    GL11.glRotatef(-mc.thePlayer.rotationYaw + 180, 0, 0, 1);
	    mc.renderEngine.bindTexture(new ResourceLocation("gameoverlay","textures/minimap_directions.png"));
		drawTexturedModalRect(-(texturecenterX / 2) -1 , (-texturecenterY / 2) - 1 , 0, 0, 200, 200);
		GL11.glRotatef(+mc.thePlayer.rotationYaw + 180, 0, 0, 1);
	    
		
	    GL11.glTranslatef(-transfX, -transfY, 0);
	}
	
	
	
	public static boolean in_radius( int c_x, int c_y, int r, int x, int y) {
		return Math.hypot(c_x-x, c_y-y) <= r;
	}
	
	public static boolean in_radius2( int c_x, int c_y, int r, int x, int y) {
		int a = Math.abs(c_x-x);
		int b = Math.abs(c_y-y);
		if(a > r || b > r) { return false; }
		if(a+b <= r) { return true; }
		if((a*a) + (b*b) < r*r) {
			return true;
		}
		return false;
	}

	public int Offset(int count) {
		if(count < gridsize / 2) return ((gridsize / 2) - count) * -16;
		if(count > gridsize / 2) return (count - (gridsize / 2)) * 16;
		return 0;
	}

}
