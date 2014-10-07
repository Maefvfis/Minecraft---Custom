package de.maefvfis.gameoverlay.objects;

import java.util.Comparator;

import net.minecraft.client.gui.GuiPlayerInfo;

public class GuiPlayerInfoSorter implements Comparator<GuiPlayerInfo> {
	
	private int getPlayerRank(String playerName) {
		
		if (playerName.startsWith("\u00a76")) {
			
			return 2;
			
		} else if (playerName.startsWith("\u00a79")) {
			
			return 1;
			
		} else
			
			return 0;			
	}
	

	@Override
	public int compare(GuiPlayerInfo arg1, GuiPlayerInfo arg2) {
		
		String name1 = arg1.name;
		String name2 = arg2.name;
		String sName1 = name1.replaceAll("\u00a7.", "");
		String sName2 = name2.replaceAll("\u00a7.", "");

		int rank1 = getPlayerRank(name1);
		int rank2 = getPlayerRank(name2);
		int diff = rank2 - rank1;
		if (diff == 0) {
			
			return sName1.compareToIgnoreCase(sName2);
			
		} else {
			
			return diff;
			
		}
		
	}

}
