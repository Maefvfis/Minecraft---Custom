package de.maefvfis.gameoverlay.client.settings;


import de.maefvfis.gameoverlay.reference.Names;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class Keybindings {
    public static KeyBinding menu = new KeyBinding(Names.Keys.MENU, Keyboard.KEY_K, Names.Keys.CATEGORY);
}
