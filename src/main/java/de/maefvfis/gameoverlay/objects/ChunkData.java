package de.maefvfis.gameoverlay.objects;

import java.awt.image.BufferedImage;

public class ChunkData {

		public int x; 
		public int z; 
		public BufferedImage img;
		public int TextureID;
		
		public ChunkData(int x2, int z2, BufferedImage img2, int TextureID2) {
			this.x = x2;
			this.z = z2;
			this.img = img2;
			this.TextureID = TextureID2;
		}
}
