package de.maefvfis.gameoverlay.client.gui;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;

import com.google.common.collect.Sets;

import sun.security.ssl.Debug;
import tv.twitch.chat.ChatUserInfo;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.stream.GuiTwitchUserMode;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;


@SideOnly(Side.CLIENT)
public class GuiChatCustom extends GuiChat 
{
	
	private static final Set field_152175_f = Sets.newHashSet(new String[] {"http", "https"});
    private static final Logger logger = LogManager.getLogger();
    private String field_146410_g = "";
    /**
     * keeps position of which chat message you will select when you press up, (does not increase for duplicated
     * messages sent immediately after each other)
     */
    private int sentHistoryCursor = -1;
    private boolean field_146417_i;
    private boolean field_146414_r;
    private int field_146413_s;
    private List field_146412_t = new ArrayList();
    /** used to pass around the URI to various dialogues and to the host os */
    private URI clickedURI;
    /** Chat entry field */
    //protected GuiTextField inputField;
    /**
     * is the text that appears when you press the chat key and the input box appears pre-filled
     */
    private String defaultInputFieldText = "";
    private static final String __OBFID = "CL_00000682";


	@Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {

		//Debug.println("Click", "Click");
        if (p_73864_3_ == 0 && this.mc.gameSettings.chatLinks)
        {
            IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().func_146236_a(Mouse.getX(), Mouse.getY());

            if (ichatcomponent != null)
            {
            	if (isShiftKeyDown())
                {
            		String Chattext = "/msg "+ichatcomponent.getUnformattedTextForChat().substring(2)+" ";
            		this.inputField.setText(Chattext);
            		//this.inputField.setCursorPosition(Chattext.length());
            		return;
                }
            	
                ClickEvent clickevent = ichatcomponent.getChatStyle().getChatClickEvent();

                if (clickevent != null)
                {
                	//this.inputField.writeText(ichatcomponent.getUnformattedTextForChat()+"awdawd");
                    if (isShiftKeyDown())
                    {
                        this.inputField.writeText(ichatcomponent.getUnformattedTextForChat());
                    }
                    else
                    {
                        URI uri;

                        if (clickevent.getAction() == ClickEvent.Action.OPEN_URL)
                        {
                            try
                            {
                                uri = new URI(clickevent.getValue());

                                if (!field_152175_f.contains(uri.getScheme().toLowerCase()))
                                {
                                    throw new URISyntaxException(clickevent.getValue(), "Unsupported protocol: " + uri.getScheme().toLowerCase());
                                }

                                if (this.mc.gameSettings.chatLinksPrompt)
                                {
                                    this.clickedURI = uri;
                                    this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, clickevent.getValue(), 0, false));
                                }
                                else
                                {
                                    this.func_146407_a(uri);
                                }
                            }
                            catch (URISyntaxException urisyntaxexception)
                            {
                                logger.error("Can\'t open url for " + clickevent, urisyntaxexception);
                            }
                        }
                        else if (clickevent.getAction() == ClickEvent.Action.OPEN_FILE)
                        {
                            uri = (new File(clickevent.getValue())).toURI();
                            this.func_146407_a(uri);
                        }
                        else if (clickevent.getAction() == ClickEvent.Action.SUGGEST_COMMAND)
                        {
                            this.inputField.setText(clickevent.getValue());
                        }
                        else if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
                        {
                            this.func_146403_a(clickevent.getValue());
                        }
                        else if (clickevent.getAction() == ClickEvent.Action.TWITCH_USER_INFO)
                        {
                            ChatUserInfo chatuserinfo = this.mc.func_152346_Z().func_152926_a(clickevent.getValue());

                            if (chatuserinfo != null)
                            {
                                this.mc.displayGuiScreen(new GuiTwitchUserMode(this.mc.func_152346_Z(), chatuserinfo));
                            }
                            else
                            {
                                logger.error("Tried to handle twitch user but couldn\'t find them!");
                            }
                        }
                        else
                        {
                            logger.error("Don\'t know how to handle " + clickevent);
                        }
                    }

                    return;
                }
            }
        }

        this.inputField.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }
    
	
	
	 
	
	
	
    private void func_146407_a(URI p_146407_1_)
    {
        try
        {
            Class oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {p_146407_1_});
        }
        catch (Throwable throwable)
        {
            logger.error("Couldn\'t open link", throwable);
        }
    }
}