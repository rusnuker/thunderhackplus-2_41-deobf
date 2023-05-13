//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.client;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.thundergui2.*;
import net.minecraft.client.gui.*;

public class ThunderHackGui extends Module
{
    private static ThunderHackGui INSTANCE;
    public final Setting<ColorSetting> onColor1;
    public final Setting<ColorSetting> onColor2;
    public Setting<Float> scrollSpeed;
    
    public ThunderHackGui() {
        super("ThunderGui", "\u043d\u043e\u0432\u044b\u0439 \u043a\u043b\u0438\u043a \u0433\u0443\u0438", Category.CLIENT);
        this.onColor1 = (Setting<ColorSetting>)this.register(new Setting("OnColor1", (T)new ColorSetting(new Color(71, 0, 117, 255).getRGB())));
        this.onColor2 = (Setting<ColorSetting>)this.register(new Setting("OnColor2", (T)new ColorSetting(new Color(32, 1, 96, 255).getRGB())));
        this.scrollSpeed = (Setting<Float>)this.register(new Setting("ScrollSpeed", (T)0.2f, (T)0.1f, (T)1.0f));
        this.setInstance();
    }
    
    public static ThunderHackGui getInstance() {
        if (ThunderHackGui.INSTANCE == null) {
            ThunderHackGui.INSTANCE = new ThunderHackGui();
        }
        return ThunderHackGui.INSTANCE;
    }
    
    private void setInstance() {
        ThunderHackGui.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        Util.mc.displayGuiScreen((GuiScreen)ThunderGui2.getThunderGui());
        this.disable();
    }
    
    public Color getColorByTheme(final int id) {
        switch (id) {
            case 0: {
                return new Color(37, 27, 41, 250);
            }
            case 1: {
                return new Color(50, 35, 60, 250);
            }
            case 2: {
                return new Color(-1);
            }
            case 3: {
                return new Color(6645093);
            }
            case 4: {
                return new Color(50, 35, 60, 178);
            }
            case 5: {
                return new Color(133, 93, 162, 178);
            }
            case 6: {
                return new Color(88, 64, 107, 178);
            }
            case 7: {
                return new Color(25, 20, 30, 255);
            }
            case 8: {
                return new Color(6645093);
            }
            case 9: {
                return new Color(50, 35, 60, 178);
            }
            default: {
                return new Color(37, 27, 41, 250);
            }
        }
    }
    
    static {
        ThunderHackGui.INSTANCE = new ThunderHackGui();
    }
}
