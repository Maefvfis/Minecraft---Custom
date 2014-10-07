package de.maefvfis.gameoverlay.objects;

import java.util.List;

import de.maefvfis.gameoverlay.reference.EntityGridOptions.Entity;

public class ChordsToPlayer {
	
	public String PlayerName;
	public int x1;
	public int x2;
	public int y1;
	public int y2;
	
	public ChordsToPlayer(String iPlayerName, int ix1, int iy1, int ix2, int iy2) {
		this.PlayerName = iPlayerName;
		this.x1 = ix1;
		this.x2 = ix2;
		this.y1 = iy1;
		this.y2 = iy2;
	}
	
	public static String getPlayerName_fromChords(int x, int y, List<ChordsToPlayer> ChordsToPlayerList) {
		for (ChordsToPlayer Player: ChordsToPlayerList) {
			if(Player.getIsPlayerOnChords(x, y)) {
				return Player.PlayerName;
			}
		}
		return null;
	}
	
	public boolean getIsPlayerOnChords(int x, int y) {
		if(x > this.x1 && x < this.x2 && y > this.y1 &&  y < this.y2) {
			return true;
		}
		return false;
	}
}
