package de.maefvfis.gameoverlay.client.handler;


import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import de.maefvfis.gameoverlay.client.gui.CreativeInv;
import de.maefvfis.gameoverlay.client.gui.GuiChatCustom;
import de.maefvfis.gameoverlay.client.gui.ModGuiConfig;
import de.maefvfis.gameoverlay.client.gui.PlayerList;
import de.maefvfis.gameoverlay.client.settings.Keybindings;
import de.maefvfis.gameoverlay.objects.ChunkImage;
import de.maefvfis.gameoverlay.reference.EntityGridOptions;
import de.maefvfis.gameoverlay.reference.Key;
import de.maefvfis.gameoverlay.utility.LogHelper;

public class KeyInputEventHandler {

    private static Key getPressedKeybinding()
    {
        if (Keybindings.menu.isPressed())
        {
        	Minecraft  mc = Minecraft.getMinecraft();
            mc.displayGuiScreen(new ModGuiConfig(mc.currentScreen));
            return Key.MENU;
        }
        if (Keybindings.creativinv.isPressed())
        {
        	Minecraft  mc = Minecraft.getMinecraft();
            mc.displayGuiScreen(new CreativeInv(Minecraft.getMinecraft().thePlayer));
            return Key.CREATIVEINV;
        }
        
        if (Keybindings.maptoggle.isPressed()) {
        	//Toggle Map
        	EntityGridOptions.CycleActiveEntity();
        }
        
        
        if (Minecraft.getMinecraft().gameSettings.keyBindPlayerList.getIsKeyPressed())
        {
        	Minecraft.getMinecraft().gameSettings.keyBindPlayerList.unPressAllKeys();
        	Minecraft  mc = Minecraft.getMinecraft();
            mc.displayGuiScreen(new PlayerList(Minecraft.getMinecraft()));
            return Key.TEST;
        }
        
        
        return Key.UNKNOWN;
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
    {
   
        LogHelper.info(getPressedKeybinding());
    }
}