package de.maefvfis.gameoverlay.client.gui;




import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import sun.security.ssl.Debug;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.maefvfis.gameoverlay.objects.ChordsToPlayer;
import de.maefvfis.gameoverlay.objects.GuiPlayerInfoSorter;
import de.maefvfis.gameoverlay.reference.EntityGridOptions.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.Slot;

public class PlayerList  extends GuiScreen {

	private Minecraft mc;
	public static boolean isPlayerList;
	public List<ChordsToPlayer> ChordsToPlayerList = new ArrayList<ChordsToPlayer>();
	public static GuiPlayerInfoSorter playerInfoSorter = new GuiPlayerInfoSorter();
	
	public PlayerList(Minecraft mc) {
		//super();
		this.mc = mc;	
		//drawUserList();
	}
	
	@Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
		String Player = ChordsToPlayer.getPlayerName_fromChords(p_73864_1_, p_73864_2_, ChordsToPlayerList);
		if(Player != null) {
			GuiChatCustom GuiChat = new GuiChatCustom();
			this.mc.displayGuiScreen(GuiChat);
			GuiChat.setinputfield("/msg " + Player + " ");
		}
    }
	
	@Override
	public boolean doesGuiPauseGame()
    {
        return false;
    }
	@Override
	 public void drawScreen(int x, int y, float f) {
			
		ChordsToPlayerList.clear();
			//double guiscale = 1.25;
			//GL11.glScalef(0.8F,0.8F,0.8F);
		
			isPlayerList = true;
		
			
			
			ScaledResolution scaledRes = new ScaledResolution(mc,mc.displayWidth, mc.displayHeight);
			int width = (int) (scaledRes.getScaledWidth());
			int height = (int) (scaledRes.getScaledHeight());
			
			
			ScoreObjective scoreobjective = mc.theWorld.getScoreboard().func_96539_a(0);
			NetHandlerPlayClient handler = mc.thePlayer.sendQueue;
			List players = new ArrayList(handler.playerInfoList);
			Collections.sort(players, playerInfoSorter);
			
			
			int maxPlayers = handler.currentServerMaxPlayers;
			int rows = maxPlayers;
			int columns = 1;
			int columnWidth;
			byte border = 10;
			
			maxPlayers = 138;
			rows = 23;
			
			
			columns = (int) maxPlayers / rows;
			
			int listWidth = Math.min(700, (int) ((0.9 * width)));
			columnWidth = Math.min(150, listWidth / columns);
			int left = (width - columns * columnWidth) / 2;
			
			drawRect(left - 1, border - 1, left + columnWidth * columns, border + 9 * rows, Integer.MIN_VALUE);
			
			for (int i = 0; i < maxPlayers; i++) {
				
				int xPos = left + i % columns * columnWidth;
				int yPos = border + i / columns * 9;
				drawRect(xPos, yPos, xPos + columnWidth - 1, yPos + 8, 553648127);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				
				if (i < players.size()) {
					
					GuiPlayerInfo player = (GuiPlayerInfo)players.get(i);
					String displayName = player.name;
					
					// if show all players
					int displayChars = displayName.length();
					String subDisplayName = displayName;
					
					for (displayChars = displayName.length(); displayChars > 1; displayChars--) {
						
						subDisplayName = displayName.substring(0, displayChars);
						
						if (subDisplayName.endsWith("\u00a7")) {
							continue;
						}
						
						int displaySize = mc.fontRenderer.getStringWidth(subDisplayName);
						
						if (displaySize <= columnWidth) {
							break;
						}
						
					}
					
					if (displayChars < displayName.length())
						displayName = player.name.substring(0, displayChars); 
					
					ScorePlayerTeam team = mc.theWorld.getScoreboard().getPlayersTeam(player.name);
					String displayNameString = ScorePlayerTeam.formatPlayerName(team, displayName);
					ChordsToPlayerList.add(new ChordsToPlayer(getUnformatedText(player.name), xPos, yPos,xPos + columnWidth - 1 ,yPos +8));
					mc.fontRenderer.drawStringWithShadow(displayNameString, xPos, yPos, 16777215);
					
					if (scoreobjective != null) {
						
						int endX = xPos + mc.fontRenderer.getStringWidth(displayName) + 5;
						int maxX = xPos + columnWidth - 12 - 5;
						
						if (maxX - endX > 5) {
							
							Score score = scoreobjective.getScoreboard().func_96529_a(player.name, scoreobjective);
							String scoreDisplay = EnumChatFormatting.YELLOW + "" + score.getPlayerName(); //    score.func_96652_c();
							mc.fontRenderer.drawStringWithShadow(scoreDisplay, maxX - mc.fontRenderer.getStringWidth(scoreDisplay), yPos, 16777215);
						}
						
					}
					
					
					
				}
								
			}		
			//GL11.glScalef(1.25F,1.25F,1.25F);
	}
	
	public static String getUnformatedText(String PlayerName) {

		return EnumChatFormatting.getTextWithoutFormattingCodes(PlayerName);
		
	}

}
	

