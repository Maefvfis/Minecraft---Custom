package de.maefvfis.gameoverlay.reference;

import java.util.ArrayList;
import java.util.List;

import sun.security.ssl.Debug;
import de.maefvfis.gameoverlay.handler.ConfigurationHandler;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class EntityGridOptions {
	
	public static final String[] MobStrings = new String[] { "Mobs", "Animals", "Slimes", "Players", "Witherskeletts" };
	public static final Class<?>[] MobClasses = new Class<?>[] { EntityMob.class, EntityAnimal.class, EntitySlime.class, EntityPlayer.class, EntitySkeleton.class };
	public static final boolean [] MobIsWither = new boolean[] { false, false, false, false, true };
	
	
	public static List<Entity> EnitysObj = new ArrayList<Entity>();
	public static Entity[] EnitysObj2;
	public static Entity ActiveEntity;
	
	public EntityGridOptions() {
		for (int i = 0; i < MobStrings.length; i++) {
			EnitysObj.add(new Entity(MobStrings[i],MobClasses[i],MobIsWither[i]));
		}
		SetActiveEntity((String)ConfigurationHandler.myConfigGridType);
	}
	
	public static void init() {
		for (int i = 0; i < MobStrings.length; i++) {
			EnitysObj.add(new Entity(MobStrings[i],MobClasses[i],MobIsWither[i]));
		}
		SetActiveEntity((String)ConfigurationHandler.myConfigGridType);
	}
	
	public static void SetActiveEntity(String MobString) {
		for (Entity ent: EnitysObj) {
			if(ent.EntityName.equals(MobString)) {
				ActiveEntity = ent;
			}
		}
	}
	
	public static void SetActiveEntity(Class<?> MobClass) {
		for (Entity ent: EnitysObj) {
			if(ent.EntityClass == MobClass) {
				ActiveEntity = ent;
			}
		}
	}

	public static class Entity {
		public Class<?> EntityClass;
		public String EntityName;
		public boolean WitherSkelett = false;
		Entity(String setEntityName, Class<?> setEntityClass) {
			EntityName = setEntityName;
			EntityClass = setEntityClass;
		}
		Entity(String setEntityName, Class<?> setEntityClass, boolean Wither) {
			EntityName = setEntityName;
			EntityClass = setEntityClass;
			WitherSkelett = Wither;
		}
	}
}
