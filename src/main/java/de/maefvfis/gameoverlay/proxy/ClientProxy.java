package de.maefvfis.gameoverlay.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import de.maefvfis.gameoverlay.client.settings.Keybindings;
import net.minecraft.client.settings.KeyBinding;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerKeyBindings() {
        ClientRegistry.registerKeyBinding(Keybindings.menu);
    }
}
