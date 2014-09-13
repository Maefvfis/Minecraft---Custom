package de.maefvfis.gameoverlay;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import de.maefvfis.gameoverlay.client.handler.GuiOpenHandler;
import de.maefvfis.gameoverlay.client.handler.KeyInputEventHandler;
import de.maefvfis.gameoverlay.client.handler.MainTick;
import de.maefvfis.gameoverlay.handler.ConfigurationHandler;
import de.maefvfis.gameoverlay.proxy.IProxy;
import de.maefvfis.gameoverlay.reference.Reference;
import de.maefvfis.gameoverlay.utility.LogHelper;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME,version = Reference.MOD_VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class GameOverlay {

    @Mod.Instance(Reference.MOD_ID)
    public static GameOverlay instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
        proxy.registerKeyBindings();
        MinecraftForge.EVENT_BUS.register(new MainTick());
        MinecraftForge.EVENT_BUS.register(new GuiOpenHandler());
        LogHelper.info("PRE ist durch .........................................");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());
        LogHelper.info("INIT ist durch .........................................");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LogHelper.info("POST ist durch .........................................");
    }
}
