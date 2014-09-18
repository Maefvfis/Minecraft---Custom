package de.maefvfis.gameoverlay.client.gui;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import sun.security.ssl.Debug;
import de.maefvfis.gameoverlay.handler.ConfigurationHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.chunk.Chunk;
import de.maefvfis.gameoverlay.objects.Output;
public class EntityGrid extends GuiScreen {
	
	public int[][] result;
	public int GridSize;
	private String color = "FFFFFF";

	private final Minecraft mc = Minecraft.getMinecraft();
	public int X = MathHelper.floor_double(mc.thePlayer.posX);
	public int Z = MathHelper.floor_double(mc.thePlayer.posZ);
	
	private final FontRenderer fontRender =  mc.fontRenderer;
	private final ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
	private final int k = scaledresolution.getScaledWidth();
	private final int l = scaledresolution.getScaledHeight();
	private final String gridchar = String.valueOf(Character.toChars(Integer.parseInt("2588", 16)));
	private final int gridchar_width = fontRender.getStringWidth(gridchar);
	private List whitelist = Arrays.asList(ConfigurationHandler.PlayerGridWhitelist.split(","));
	private int current_y_playerlist;
	
	
	public EntityGrid(int size) {
		// Size must be even
		if ((size%2)!=0) {
			size++;
		}
		result = new int[size][size];
		GridSize = size;
		current_y_playerlist = gridchar_width * (size + 2);
		this.BuildGrid();
	}
	
	public void BuildGrid() {
		
		int EntityCount;
		Chunk chunk;
		List <String> player_names = new ArrayList();
		ChunckViewer CViewer = new ChunckViewer();
		
		for (int i1 = 0; i1 <= GridSize; i1++)
		{
			for (int i2 = 0; i2 <= GridSize; i2++)
			{
				EntityCount = 0;
				chunk = mc.theWorld.getChunkFromBlockCoords(X + this.XOffset(i1), Z + this.ZOffset(i2));
				
				if(ConfigurationHandler.myConfigGridType.equals("Witherskeletts")) {
					EntityCount = CViewer.countEntity(EntitySkeleton.class, chunk, true);
				} else if(ConfigurationHandler.myConfigGridType.equals("Mobs")) {
					EntityCount = CViewer.countEntity(EntityMob.class, chunk);
				} else if(ConfigurationHandler.myConfigGridType.equals("Animals")) {
					EntityCount = CViewer.countEntity(EntityAnimal.class, chunk);
				} else if(ConfigurationHandler.myConfigGridType.equals("Slimes")) {
					EntityCount = CViewer.countEntity(EntitySlime.class, chunk);
				} else if(ConfigurationHandler.myConfigGridType.equals("Players")) {
					EntityCount = CViewer.countEntity(EntityPlayer.class, chunk);
					player_names.addAll(CViewer.ListPlayers(chunk));
				}
				
				if(EntityCount == 0) {
					color = "1c73c4";
				} else if(EntityCount <= 5){
					color = "dab900";
				} else if(EntityCount > 5){
					color = "ff3333";
				}
				
				double grid_alpha = ConfigurationHandler.myConfigGridColor;
				grid_alpha = ((grid_alpha / 100) * 239) + 16;
				String Hex = (String)Integer.toHexString((int)grid_alpha) + (String)color;
				BigInteger bigInt = new BigInteger(Hex, 16);
				
				fontRender.drawString(gridchar, k - gridchar_width - 2 - (i1*gridchar_width), 1 + (i2*gridchar_width), bigInt.intValue() );

			}
		}
		
		
		chunk = mc.theWorld.getChunkFromBlockCoords(X, Z);
		List <Output> EntityList = new ArrayList<Output>();
		if(ConfigurationHandler.myConfigGridType == "Players") {
			Collections.sort(player_names);
			for(int i = 0; i < player_names.size(); i++) {
				
				if(!whitelist.contains(player_names.get(i))) {
					fontRender.drawStringWithShadow((String) player_names.get(i),k - fontRender.getStringWidth((String) player_names.get(i)) - 2, current_y_playerlist, 0xffffff);
					current_y_playerlist += 10;
				}
			}
		}
		if(ConfigurationHandler.myConfigShowEntityPosition) {
			if(ConfigurationHandler.myConfigGridType.equals("Witherskeletts")) {
				EntityList = CViewer.ListEntity(EntitySkeleton.class, chunk, true);
			} else if(ConfigurationHandler.myConfigGridType.equals("Mobs")) {
				EntityList = CViewer.ListEntity(EntityMob.class, chunk);
			} else if(ConfigurationHandler.myConfigGridType.equals("Animals")) {
				EntityList = CViewer.ListEntity(EntityAnimal.class, chunk);
			} else if(ConfigurationHandler.myConfigGridType.equals("Slimes")) {
				EntityList = CViewer.ListEntity(EntitySlime.class, chunk);
			}
		}
		
		
		Output OutputString;
		if(!ConfigurationHandler.myConfigGridType.equals("Players")) {
			Collections.sort(EntityList);
			
			for(int i = 0; i < EntityList.size(); i++) {
				OutputString = EntityList.get(i);
				fontRender.drawStringWithShadow(OutputString.get_coordinaten(),k - fontRender.getStringWidth(OutputString.get_coordinaten()) - 2, current_y_playerlist, 0xffffff);
				current_y_playerlist += 10;
			}
			
		}
		
		int lookdirection = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		String looking = "";
		if(lookdirection == 2) {
			looking = "25B2";
		} else if(lookdirection == 3) {
			looking = "25B6";
		} else if(lookdirection == 0) {
			looking = "25BC";
		} else if(lookdirection == 1) {
			looking = "25C0";
		}
		String lookchar = String.valueOf(Character.toChars(Integer.parseInt(looking, 16)));
		fontRender.drawString(lookchar, k - (((GridSize / 2)+1)*gridchar_width) + (gridchar_width / 2) - 2 - 1, 1 + ((GridSize / 2)*gridchar_width), 0xFFFFFF);
		
		
		
		
		
		//
		int screenwidth = scaledresolution.getScaledWidth();
		int screenheight = scaledresolution.getScaledHeight();
	    GL11.glTranslatef(0, 100, 0);
	    GL11.glRotatef(this.mc.thePlayer.rotationYaw, 0, 0, 1);
	    drawRect(0, 0, 50, 50, 0xAA000000);
	    drawRect(0, 50,50, 100, 0xAA000000);   

	    GL11.glRotatef(0, 0, 0, 0);
		
		
	    
	    GL11.glTranslatef(0, -100, 0);
	    GL11.glRotatef(-this.mc.thePlayer.rotationYaw, 0, 0, 1);
		
	}
	
	public int ZOffset(int count) {
		if(count < GridSize / 2) return ((GridSize / 2) - count) * -16;
		if(count > GridSize / 2) return (count - (GridSize / 2)) * 16;
		return 0;
	}

	public int XOffset(int count) {
		if(count < GridSize / 2) return ((GridSize / 2) - count) * 16;
		if(count > GridSize / 2) return (count - (GridSize / 2)) * -16;
		return 0;
	}
}