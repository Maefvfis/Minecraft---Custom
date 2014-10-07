package de.maefvfis.gameoverlay.client.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.maefvfis.gameoverlay.handler.ConfigurationHandler;
import de.maefvfis.gameoverlay.objects.ChunkImage;
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

public class CopyOfCopyOfGrid2D  extends GuiScreen {
	
	public int gridsize = Integer.valueOf(ConfigurationHandler.myGridSize);
	public int blocksize = 1;
	public int colorAlpha = 0xFF000000;
	public int padding = 0;
	public int chunksize = blocksize * 16;
	public Minecraft mc = Minecraft.getMinecraft();
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
	public float transfY = radius + padding;
	public Chunk chunk;
	public int chunkX;
	public int chunkY;
	public boolean fullyInCircle;
	public int blockY;
	
	
	public CopyOfCopyOfGrid2D() {
		test();
	}
	
	
	
	public void test() {
		GL11.glTranslatef(transfX, transfY, 0);
	    GL11.glRotatef(-this.mc.thePlayer.rotationYaw + 180, 0, 0, 1);
		
	    
	    
	    //GL11.glScalef(0.8F,0.8F,0.8F);
	    for (int i1 = 0; i1 <= gridsize; i1++)
		{
			for (int i2 = 0; i2 <= gridsize; i2++)
			{
				
				
				
				
				fullyInCircle = false;
				
				if(!in_radius2(radius + 8,radius + 8,radius,(i1*chunksize) + chunksize,(i2*chunksize) + chunksize)) {
					continue;
				}
				
				if(in_radius2(radius + 8,radius + 8,radius - 32,(i1*chunksize) + chunksize,(i2*chunksize) + chunksize)) {
					fullyInCircle = true;
				}
				
				chunk = mc.theWorld.getChunkFromBlockCoords(X + Offset(i1), Z + Offset(i2));
				chunkX = ((i1*chunksize))-radius;
				chunkY = (i2*chunksize)-radius;
				
				mc.renderEngine.bindTexture(new ResourceLocation("gameoverlay","textures/map.png"));
				
				BufferedImage bImage = ChunkImage.getMapImage(chunk);
				if(bImage != null) {
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, TextureUtil.uploadTextureImage(0, bImage));
				}
				for(int i3 = 0; i3 <= 15; i3++) {
					
					for(int i4 = 0; i4 <= 15; i4++) {
						
						
						if(!fullyInCircle) {
							
							if(!in_radius2(radius,radius,radius - chunksize,(i1*chunksize) + (i3*blocksize) - xRel +8 ,((i2*chunksize) + (i4*blocksize)- zRel +8 ))) {	
								continue;
							}
							
						}
						
						
						
						
						blockY = chunk.getHeightValue(i3, i4);
						if(!mc.theWorld.blockExists(i3, blockY, i4)) { Debug.println("Map Error", "kein Block gefunden"); continue; }
						Block Block = chunk.getBlock(i3,blockY, i4);
						MapColor MapColor = Block.getMapColor(0);
						if(MapColor.colorValue == 0) {
							Block = chunk.getBlock(i3,blockY-1, i4);
							MapColor = Block.getMapColor(0);
						}
						
						drawRect(chunkX  - xRel + (i3*blocksize) + 8, chunkY - zRel + (i4*blocksize) +8 , chunkX - xRel + (i3*blocksize) + blocksize +8 , chunkY - zRel + (i4*blocksize) + blocksize +8 , colorAlpha + MapColor.colorValue);
					}
				}
			}
		}
	    //Debug.println(""+ chunk.xPosition, "");
	    drawRect(0,0,1,1,0xFFFF0000);
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
