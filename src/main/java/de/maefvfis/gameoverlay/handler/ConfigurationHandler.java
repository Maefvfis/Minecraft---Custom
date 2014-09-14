package de.maefvfis.gameoverlay.handler;

import cpw.mods.fml.client.config.GuiConfigEntries.IConfigEntry;
import cpw.mods.fml.client.config.GuiConfigEntries.NumberSliderEntry;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.maefvfis.gameoverlay.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

import org.lwjgl.opengl.GL11;

public class ConfigurationHandler {

    public static Configuration configuration;
    
    
    // Light Overlay Options
    public static final int COLOR_MIN = 0x00;
	public static final int COLOR_MAX = 0xFF;
	
	public static final int COLORDAYRED_DEFAULT = COLOR_MIN;
	public static final int COLORDAYGREEN_DEFAULT = COLOR_MAX;
	public static final int COLORDAYBLUE_DEFAULT = COLOR_MIN;
	public static final int COLORNIGHTRED_DEFAULT = COLOR_MIN;
	public static final int COLORNIGHTGREEN_DEFAULT = COLOR_MIN;
	public static final int COLORNIGHTBLUE_DEFAULT = COLOR_MAX;
	public static final int COLORBOTHRED_DEFAULT = COLOR_MAX;
	public static final int COLORBOTHGREEN_DEFAULT = COLOR_MIN;
	public static final int COLORBOTHBLUE_DEFAULT = COLOR_MIN;
	public static final int RENDERRANGEXZ_DEFAULT = 20;
	public static final int RENDERRANGEYBELLOW_DEFAULT = 4;
	public static final int RENDERRANGEYABOVE_DEFAULT = 0;
	public static final int UPDATERATE_DEFAULT = 5;
	public static final float GUIDELENGTH_DEFAULT = 1.0f;
	public static final boolean RENDERSPAWNS_DEFAULT = true;

	public static int colorDayRed = COLORDAYRED_DEFAULT;
	public static int colorDayGreen = COLORDAYGREEN_DEFAULT;
	public static int colorDayBlue = COLORDAYBLUE_DEFAULT;
	public static int colorNightRed = COLORNIGHTRED_DEFAULT;
	public static int colorNightGreen = COLORNIGHTGREEN_DEFAULT;
	public static int colorNightBlue = COLORNIGHTBLUE_DEFAULT;
	public static int colorBothRed = COLORBOTHRED_DEFAULT;
	public static int colorBothGreen = COLORBOTHGREEN_DEFAULT;
	public static int colorBothBlue = COLORBOTHBLUE_DEFAULT;
	public static int renderRangeXZ = RENDERRANGEXZ_DEFAULT;
	public static int renderRangeYBellow = RENDERRANGEYBELLOW_DEFAULT;
	public static int renderRangeYAbove = RENDERRANGEYABOVE_DEFAULT;
	public static int updateRate = UPDATERATE_DEFAULT;
	public static float guideLength = GUIDELENGTH_DEFAULT;
	public static boolean renderSpawns = RENDERSPAWNS_DEFAULT;

	public static Property propColorDayRed;
	public static Property propColorDayGreen;
	public static Property propColorDayBlue;
	public static Property propColorNightRed;
	public static Property propColorNightGreen;
	public static Property propColorNightBlue;
	public static Property propColorBothRed;
	public static Property propColorBothGreen;
	public static Property propColorBothBlue;
	public static Property propRenderRangeXZ;
	public static Property propRenderRangeYBellow;
	public static Property propRenderRangeYAbove;
	public static Property propUpdateRate;
	public static Property propGuideLength;
	

	public static void glColorDay() {
		GL11.glColor4ub((byte) colorDayRed, (byte) colorDayGreen, (byte) colorDayBlue, (byte) 79);
	}

	public static void glColorNight() {
		GL11.glColor4ub((byte) colorNightRed, (byte) colorNightGreen, (byte) colorNightBlue, (byte) 79);
	}

	public static void glColorBoth() {
		GL11.glColor4ub((byte) colorBothRed, (byte) colorBothGreen, (byte) colorBothBlue, (byte) 79);
	}
	
	// Light Overlay Options ENde
	
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
        
        renderRangeXZ = configuration.get(Configuration.CATEGORY_GENERAL, "LightOverlayRender width:", renderRangeXZ,"LightOverlayRender width", 5, 50 ).setConfigEntryClass(getSliderClass()).getInt();
        updateRate = configuration.get(Configuration.CATEGORY_GENERAL, "LightOverlayRender UpdateRate:", updateRate,"LightOverlayRender UpdateRate", 5, 10 ).setConfigEntryClass(getSliderClass()).getInt();
        renderSpawns = configuration.getBoolean("Show LightOverlayRender", Configuration.CATEGORY_GENERAL, renderSpawns, "If true: Shows LightOverlay");
        
        
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
