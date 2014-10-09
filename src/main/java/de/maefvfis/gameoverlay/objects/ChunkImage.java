package de.maefvfis.gameoverlay.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import sun.security.ssl.Debug;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.world.chunk.Chunk;



import net.minecraft.client.renderer.texture.TextureUtil;

public class ChunkImage {

	public static List<ChunkData> ChunkList = new ArrayList();
	public static int maxChunkBufferSize = 250;
	public static Minecraft mc = Minecraft.getMinecraft();
	public static int colorAlpha = 0xFF000000;
	

	public static int getMapImage(Chunk chunk) {
		
		checkBufferSize();
		if(!chunk.isChunkLoaded) { return -1;  }
		ChunkData cData = getChunkData(chunk.xPosition, chunk.zPosition);		
		if(cData == null) { CreateChunkData(chunk); return -1; }
		return cData.TextureID;
		
	}
	
	public static void CreateChunkData(Chunk chunk) {

		int blockY;
		Block Block;
		MapColor MapColor;
		BufferedImage Img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
		Graphics2D Graphic;
		ChunkData ChunkData;
		
		for(int i3 = 0; i3 <= 15; i3++) {
			for(int i4 = 0; i4 <= 15; i4++) {
				blockY = chunk.getHeightValue(i3, i4);
				if(!mc.theWorld.blockExists(i3, blockY, i4)) { Debug.println("Map Error", "kein Block gefunden"); continue; }
				if(!chunk.isChunkLoaded) { Debug.println("Map Error", "Chunk nicht geladen"); continue; }
				Block = chunk.getBlock(i3,blockY, i4);
				MapColor = Block.getMapColor(0);
				if(MapColor.colorValue == 0) {
					Block = chunk.getBlock(i3,blockY-1, i4);
					MapColor = Block.getMapColor(0);
				}
				Img.setRGB(i3, i4, MapColor.colorValue);
			}
		}
		
		
		int TextureID = TextureUtil.uploadTextureImage(TextureUtil.glGenTextures(), Img);
		
		ChunkList.add(new ChunkData(chunk.xPosition,chunk.zPosition,TextureID));
		
	}
	
	
	public static void checkBufferSize() {
		//Debug.println(maxChunkBufferSize+"", ChunkList.size()+"");
		if(ChunkList.size() > maxChunkBufferSize) {
			deleteBuffer();
		}
	}
	
	public static void deleteBuffer() {
		for(ChunkData ChunkD: ChunkList) {
			TextureUtil.deleteTexture(ChunkD.TextureID);
		}
		ChunkList.clear();
	}

	public static ChunkData getChunkData(int x, int z) {
		for(ChunkData TestChunk: ChunkList) {
			if(TestChunk.x == x && TestChunk.z == z ) {
				return TestChunk;
				
			}
		}
		return null;
	}

}
