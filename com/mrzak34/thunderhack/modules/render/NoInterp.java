//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;

public class NoInterp extends Module
{
    private static NoInterp INSTANCE;
    public Setting<Boolean> lowIQ;
    
    public NoInterp() {
        super("NoInterp", "\u0443\u0431\u0438\u0440\u0430\u0435\u0442 \u0438\u043d\u0442\u0435\u0440\u043f\u043e\u043b\u044f\u0446\u0438\u044e-\u0441 \u0438\u0433\u0440\u043e\u043a\u043e\u0432", Module.Category.RENDER);
        this.lowIQ = (Setting<Boolean>)this.register(new Setting("LowIQ", (T)true));
        this.setInstance();
    }
    
    public static NoInterp getInstance() {
        if (NoInterp.INSTANCE == null) {
            NoInterp.INSTANCE = new NoInterp();
        }
        return NoInterp.INSTANCE;
    }
    
    private void setInstance() {
        NoInterp.INSTANCE = this;
    }
    
    static {
        NoInterp.INSTANCE = new NoInterp();
    }
}
