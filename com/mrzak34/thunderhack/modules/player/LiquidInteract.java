//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;

public class LiquidInteract extends Module
{
    private static LiquidInteract INSTANCE;
    
    public LiquidInteract() {
        super("LiquidInteract", "\u0441\u0442\u0430\u0432\u0438\u0442\u044c \u0431\u043b\u043e\u043a\u0438 \u043d\u0430 \u0432\u043e\u0434\u0443", Module.Category.PLAYER);
        this.setInstance();
    }
    
    public static LiquidInteract getInstance() {
        if (LiquidInteract.INSTANCE == null) {
            LiquidInteract.INSTANCE = new LiquidInteract();
        }
        return LiquidInteract.INSTANCE;
    }
    
    private void setInstance() {
        LiquidInteract.INSTANCE = this;
    }
    
    static {
        LiquidInteract.INSTANCE = new LiquidInteract();
    }
}
