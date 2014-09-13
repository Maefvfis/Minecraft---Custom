package de.maefvfis.gameoverlay.handler;

import cpw.mods.fml.client.config.GuiConfigEntries.IConfigEntry;
import cpw.mods.fml.client.config.GuiConfigEntries.NumberSliderEntry;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.maefvfis.gameoverlay.reference.Reference;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {

    public static Configuration configuration;
    public static boolean myConfigShowGrid = true;
    public static boolean myConfigShowInfoIngameGui = true;
    public static boolean myConfigShowItemUsage = true;
    public static boolean myConfigShowEntityPosition = true;
    public static String myConfigGridType = "Mobs";
    public static int myConfigGridColor = 100;
    public static String myGridSize = "6";
    public static String PlayerGridWhitelist = "";
    
    public static void init(File configFile)
    {
        // Create the configuration object from the given configuration file
        if (configuration == null)
        {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration()
    {
        myConfigShowGrid = configuration.getBoolean("Show chunk grid", Configuration.CATEGORY_GENERAL, myConfigShowGrid, "If true: Shows the Chunk grid");
        myConfigShowInfoIngameGui = configuration.getBoolean("Show chunk info", Configuration.CATEGORY_GENERAL, myConfigShowInfoIngameGui, "If true: Shows the Chunk info");
        myConfigShowItemUsage = configuration.getBoolean("Show item usage", Configuration.CATEGORY_GENERAL, myConfigShowItemUsage, "If true: Shows the item usage");
        myConfigShowEntityPosition = configuration.getBoolean("Show Etity Position", Configuration.CATEGORY_GENERAL, myConfigShowEntityPosition, "If true: Shows the position of Etitys");
        
        myGridSize = configuration.getString("Grid size", Configuration.CATEGORY_GENERAL, myGridSize, "Sets the size of the grid",new String[] { "4", "6", "8", "10"});
        myConfigGridType = configuration.getString("Grid type", Configuration.CATEGORY_GENERAL, myConfigGridType, "Sets the color of the grid",new String[] { "Mobs", "Animals", "Slimes", "Players", "Witherskeletts" });
        myConfigGridColor = configuration.get(Configuration.CATEGORY_GENERAL, "Grid transparency", myConfigGridColor,"Grid transparency", 1, 100 ).setConfigEntryClass(getSliderClass()).getInt();
        PlayerGridWhitelist = configuration.getString("Grid ignore player", Configuration.CATEGORY_GENERAL, PlayerGridWhitelist, "Players in this list will not be shown. Seperate by ','");
        
        
        if (configuration.hasChanged())
            configuration.save();
    }
    
    public static Class<? extends IConfigEntry> getSliderClass()
	{
		return NumberSliderEntry.class;
	}
    
    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID))
            loadConfiguration();
    }

}
