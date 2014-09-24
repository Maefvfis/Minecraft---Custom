package de.maefvfis.gameoverlay.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.maefvfis.gameoverlay.handler.ConfigurationHandler;
import de.maefvfis.gameoverlay.objects.Output;
import de.maefvfis.gameoverlay.reference.EntityGridOptions;
import sun.security.ssl.Debug;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;

public class Grid2D  extends GuiScreen {
	
	public int gridsize;
	public int chunksize = 10;
	
	public Grid2D(int size) {
		//
		gridsize = size;
		st();

	}
	
	public void st() {
		
		
		
		
		mc = mc.getMinecraft();
		ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int screenwidth = scaledresolution.getScaledWidth();
		int screenheight = scaledresolution.getScaledHeight();
		int k = scaledresolution.getScaledWidth();
		int fullgridsizex = (chunksize*gridsize) + (chunksize - 1)+2;
		int fullgridsizey = (chunksize*gridsize) + (chunksize - 1)+2;
		
		
		float transfX = screenwidth - (float)(Math.sqrt((2 * (fullgridsizex * fullgridsizex))) / 4);
		float transfY = (float)Math.sqrt((2 * (fullgridsizex * fullgridsizex))) / 4;
		
	    GL11.glTranslatef(transfX, transfY, 0);
	    GL11.glRotatef(-this.mc.thePlayer.rotationYaw + 180, 0, 0, 1);
	    
	    
	    
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glScalef(0.25F,0.25F,0.25F);
	    mc.renderEngine.bindTexture(new ResourceLocation("gameoverlay","textures/map_bg.png"));
	    drawTexturedModalRect(-128, -128, 0, 0, 256, 256);
	    GL11.glScalef(4.0F,4.0F,4.0F);
	    
	    int EntityCount;
		EntityCount = 0;
		Chunk chunk;
		List <String> player_names = new ArrayList();
		ChunckViewer CViewer = new ChunckViewer();
		int X = MathHelper.floor_double(mc.thePlayer.posX);
		int Z = MathHelper.floor_double(mc.thePlayer.posZ);
		int color = 0xBB000000;
		int tempcolor = 0x000000;
		
		
		
	    GL11.glScalef(0.5F,0.5F,0.5F);
	    for (int i1 = 0; i1 <= gridsize; i1++)
		{
			for (int i2 = 0; i2 <= gridsize; i2++)
			{
				EntityCount = 0;
				chunk = mc.theWorld.getChunkFromBlockCoords(X + this.XOffset(i1), Z + this.ZOffset(i2));
			
				EntityCount = CViewer.countEntity(EntityGridOptions.ActiveEntity.EntityClass, chunk, EntityGridOptions.ActiveEntity.WitherSkelett);
				player_names.addAll(CViewer.ListPlayers(chunk));
				
				tempcolor = 0x002200 * EntityCount;

				if(EntityCount == 0) {
				} else if(EntityCount <= 5){
					tempcolor = 0xdab900;
				} else if(EntityCount > 5){
					tempcolor = 0xff3333;
				}
				tempcolor = tempcolor + color;
				
				drawRect(((i1*chunksize)+1)-(fullgridsizex / 2) , (i2*chunksize)+1-(fullgridsizey / 2), ((i1*chunksize)+chunksize)-(fullgridsizex / 2), ((i2*chunksize)+chunksize)-(fullgridsizey / 2), tempcolor);
			}
		}
	    GL11.glScalef(2.0F,2.0F,2.0F);
	    
	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glScalef(0.25F,0.25F,0.25F);
	    mc.renderEngine.bindTexture(new ResourceLocation("gameoverlay","textures/map.png"));
	    drawTexturedModalRect(-128, -128, 0, 0, 256, 256);
	    GL11.glScalef(4.0F,4.0F,4.0F);

	    
	    GL11.glRotatef(this.mc.thePlayer.rotationYaw - 180, 0, 0, 1);
	    
	    
	    
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glScalef(0.0625F,0.0625F,0.0625F);
	    mc.renderEngine.bindTexture(new ResourceLocation("gameoverlay","textures/map_arrow.png"));
	    drawTexturedModalRect(-130, -130, 0, 0, 256, 256);
	    GL11.glScalef(16.0F,16.0F,16.0F);
	    
	    GL11.glTranslatef(-transfX, -transfY, 0);
	    
	    chunk = mc.theWorld.getChunkFromBlockCoords(X, Z);
	    int current_y_playerlist = 130;
	    FontRenderer fontRender =  mc.fontRenderer;
	    List <Output> EntityList = new ArrayList<Output>();
	    List whitelist = Arrays.asList(ConfigurationHandler.PlayerGridWhitelist.split(","));
	    
	    
	    if(!EntityGridOptions.ActiveEntity.EntityName.equals("Players")) {
	    	EntityList = CViewer.ListEntity(EntityGridOptions.ActiveEntity.EntityClass, chunk, EntityGridOptions.ActiveEntity.WitherSkelett);
	    }

		
	    GL11.glScalef(0.75F,0.75F,0.75F);
		Output OutputString;
		
		
	    if(EntityGridOptions.ActiveEntity.EntityName.equals("Players") && ConfigurationHandler.myConfigShowEntityPosition) {
			Collections.sort(player_names);
			for(int i = 0; i < player_names.size(); i++) {
				
				if(!whitelist.contains(player_names.get(i))) {
					fontRender.drawStringWithShadow((String) player_names.get(i),(int) (k * 1.3) - fontRender.getStringWidth((String) player_names.get(i)) - 2, current_y_playerlist, 0xffffff);
					current_y_playerlist += 10;
				}
			}
		}
		
		if(!EntityGridOptions.ActiveEntity.EntityName.equals("Players") && ConfigurationHandler.myConfigShowEntityPosition) {
			Collections.sort(EntityList);
			
			for(int i = 0; i < EntityList.size(); i++) {
				OutputString = EntityList.get(i);
				fontRender.drawStringWithShadow(OutputString.get_coordinaten(),(int) (k * 1.3) - fontRender.getStringWidth(OutputString.get_coordinaten()) - 2, current_y_playerlist, 0xffffff);
				current_y_playerlist += 10;
			}
			
		}
		GL11.glScalef(1.35F,1.35F,1.35F);
		
	}
	
	public int ZOffset(int count) {
		if(count < gridsize / 2) return ((gridsize / 2) - count) * -16;
		if(count > gridsize / 2) return (count - (gridsize / 2)) * 16;
		return 0;
	}

	public int XOffset(int count) {
		if(count < gridsize / 2) return ((gridsize / 2) - count) * -16;
		if(count > gridsize / 2) return (count - (gridsize / 2)) * 16;
		return 0;
	}
}
