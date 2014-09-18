package de.maefvfis.gameoverlay.client.renderer;

import de.maefvfis.gameoverlay.client.renderer.vector.Vector4i;
import de.maefvfis.gameoverlay.handler.ConfigurationHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import org.lwjgl.input.Keyboard;

import sun.security.ssl.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Events {
	public static final List<Vector4i> SPAWN_LIST = new ArrayList<Vector4i>();

	private final Minecraft minecraft = Minecraft.getMinecraft();
	private final Frustrum frustrum = new Frustrum();
	private final AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);

	private int ticks = -1;

	@SubscribeEvent
	public void tick(ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			onTick();
		}
	}


	public boolean onTick() {
		this.minecraft.mcProfiler.startSection("msh");

		if (--this.ticks < 0) {
			this.ticks = ConfigurationHandler.updateRate;

			if (this.minecraft.theWorld != null && ConfigurationHandler.renderSpawns) {
				SPAWN_LIST.clear();

				this.frustrum.setPosition(PlayerPosition.x, PlayerPosition.y, PlayerPosition.z);

				World world = this.minecraft.theWorld;

				int lowX, lowY, lowZ, highX, highY, highZ, x, y, z;

				lowX = (int) (Math.floor(PlayerPosition.x) - ConfigurationHandler.renderRangeXZ);
				highX = (int) (Math.floor(PlayerPosition.x) + ConfigurationHandler.renderRangeXZ);
				lowY = (int) (Math.floor(PlayerPosition.y) - ConfigurationHandler.renderRangeYBellow);
				highY = (int) (Math.floor(PlayerPosition.y) + ConfigurationHandler.renderRangeYAbove);
				lowZ = (int) (Math.floor(PlayerPosition.z) - ConfigurationHandler.renderRangeXZ);
				highZ = (int) (Math.floor(PlayerPosition.z) + ConfigurationHandler.renderRangeXZ);

				for (y = lowY; y <= highY; y++) {
					for (x = lowX; x <= highX; x++) {
						for (z = lowZ; z <= highZ; z++) {
							if (!this.frustrum.isBoundingBoxInFrustum(this.boundingBox.setBounds(x, y, z, x + 1, y + 1, z + 1))) {
								continue;
							}

							if (getCanSpawnHere(world, x, y, z)) {
								SPAWN_LIST.add(new Vector4i(x, y, z, 1));
							}
						}
					}
				}
			}
		}

		this.minecraft.mcProfiler.endSection();

		return true;
	}


	private boolean getCanSpawnHere(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y - 1, z);

		if (block == null || block == Blocks.air || block.getMaterial().isLiquid() || !block.canCreatureSpawn(EnumCreatureType.monster, world, x, y, z)) {
			return false;
		}

		Chunk chunk = Minecraft.getMinecraft().theWorld.getChunkFromBlockCoords(x, z);
		if(chunk.getSavedLightValue(EnumSkyBlock.Block, x & 15, y+1, z & 15) >= 8) {
			return false;
		}

		return true;
	}

}
