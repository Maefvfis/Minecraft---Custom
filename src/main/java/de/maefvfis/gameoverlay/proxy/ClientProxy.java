package de.maefvfis.gameoverlay.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import de.maefvfis.gameoverlay.client.renderer.Events;
import de.maefvfis.gameoverlay.client.renderer.Renderer;
import de.maefvfis.gameoverlay.client.settings.Keybindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerKeyBindings() {
        ClientRegistry.registerKeyBinding(Keybindings.menu);
        ClientRegistry.registerKeyBinding(Keybindings.creativinv);
        MinecraftForge.EVENT_BUS.register(new Renderer(Minecraft.getMinecraft()));
        FMLCommonHandler.instance().bus().register(new Events());
    }
    
    
}
