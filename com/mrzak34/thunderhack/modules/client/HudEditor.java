//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.client;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.hud.elements.*;
import net.minecraft.client.gui.*;

public class HudEditor extends Module
{
    private static HudEditor INSTANCE;
    
    public HudEditor() {
        super("HudEditor", "\u0445\u0443\u0434 \u0438\u0437\u043c\u0435\u043d\u044f\u0442\u044c \u0434\u0430", Category.CLIENT);
        this.setInstance();
    }
    
    public static HudEditor getInstance() {
        if (HudEditor.INSTANCE == null) {
            HudEditor.INSTANCE = new HudEditor();
        }
        return HudEditor.INSTANCE;
    }
    
    private void setInstance() {
        HudEditor.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        Util.mc.displayGuiScreen((GuiScreen)HudEditorGui.getHudGui());
        this.toggle();
    }
    
    static {
        HudEditor.INSTANCE = new HudEditor();
    }
}
