package de.maefvfis.gameoverlay.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.chunk.Chunk;
import de.maefvfis.gameoverlay.handler.ConfigurationHandler;
import de.maefvfis.gameoverlay.objects.Output;

public class ChunckViewer {
	
	private List whitelist = Arrays.asList(ConfigurationHandler.PlayerGridWhitelist.split(","));

	public ChunckViewer() {
		
	}
	
	public List ListPlayers(Chunk chunk) {
		List <String> result = new ArrayList();
		for (List entityList : chunk.entityLists) {
			for (Object o : entityList) {
				if (o instanceof EntityPlayer) {
					if(!whitelist.contains(((EntityPlayer) o).getCommandSenderName())) {
						result.add(((EntityPlayer) o).getCommandSenderName());
					}
				}
			}
		}
		return result;
	}
	public List<Output> ListEntity(Class<?> instance, Chunk chunk, Boolean IsWither) {
		List <Output> result = new ArrayList<Output>();
		for (List entityList : chunk.entityLists) {
			for (Object o : entityList) {
				if (instance.isInstance(o)) {
					if(IsWither && instance.isInstance(o)) {
						if(((EntitySkeleton) o).getSkeletonType() == 1) {
							result.add(new Output(((Entity) o).getEntityId(),String.valueOf(MathHelper.floor_double(((Entity) o).posX))+", "+String.valueOf(MathHelper.floor_double(((Entity) o).posZ))+" ("+String.valueOf(MathHelper.floor_double(((Entity) o).posY))+")"));
							
						}
					} else if(o instanceof EntityPlayer) {
						if(!whitelist.contains(((EntityPlayer) o).getCommandSenderName())) {
							
						}
					} else {
						result.add(new Output(((Entity) o).getEntityId(),String.valueOf(MathHelper.floor_double(((Entity) o).posX))+", "+String.valueOf(MathHelper.floor_double(((Entity) o).posZ))+" ("+String.valueOf(MathHelper.floor_double(((Entity) o).posY))+")"));
					}
				} 
			}
		}
		return result;
	}
	public List ListEntity(Class<?> instance, Chunk chunk) {
		return ListEntity(instance,chunk,false);
	}
	
	public int countEntity(Class<?> instance, Chunk chunk, Boolean IsWither) {
		int result = 0;
		for (List entityList : chunk.entityLists) {
			for (Object o : entityList) {
				if (instance.isInstance(o)) {
					if(IsWither && instance.isInstance(o)) {
						if(((EntitySkeleton) o).getSkeletonType() == 1) {
							result++;
						}
					} else if(o instanceof EntityPlayer) {
						if(!whitelist.contains(((EntityPlayer) o).getCommandSenderName())) {
							result++;
						}
					} else {
						result++;
					}
				} 
			}
		}
		return result;
	}
	public int countEntity(Class<?> instance, Chunk chunk) {
		return countEntity(instance,chunk,false);
	}
		
}
