//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;

public class ShiftInterp extends Module
{
    private static ShiftInterp INSTANCE;
    public Setting<Boolean> sleep;
    public Setting<Boolean> aboba;
    
    public ShiftInterp() {
        super("ShiftInterp", "\u0432\u0441\u0435 \u0438\u0433\u0440\u043e\u043a\u0438 \u0431\u0443\u0434\u0443\u0442-\u043d\u0430 \u0448\u0438\u0444\u0442\u0435", Module.Category.RENDER);
        this.sleep = (Setting<Boolean>)this.register(new Setting("Sleep", (T)false));
        this.aboba = (Setting<Boolean>)this.register(new Setting("aboba", (T)false));
        this.setInstance();
    }
    
    public static ShiftInterp getInstance() {
        if (ShiftInterp.INSTANCE == null) {
            ShiftInterp.INSTANCE = new ShiftInterp();
        }
        return ShiftInterp.INSTANCE;
    }
    
    private void setInstance() {
        ShiftInterp.INSTANCE = this;
    }
    
    static {
        ShiftInterp.INSTANCE = new ShiftInterp();
    }
}
