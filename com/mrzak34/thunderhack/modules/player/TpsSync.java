//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.modules.misc.*;
import com.mrzak34.thunderhack.*;

public class TpsSync extends Module
{
    public TpsSync() {
        super("TpsSync", "\u0441\u0438\u043d\u0445\u0440\u043e\u043d\u0438\u0437\u0438\u0440\u0443\u0435\u0442 \u0438\u0433\u0440\u0443-\u0441 \u0442\u043f\u0441", Module.Category.PLAYER);
    }
    
    public void onUpdate() {
        if (((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).isEnabled()) {
            return;
        }
        if (Thunderhack.serverManager.getTPS() > 1.0f) {
            Thunderhack.TICK_TIMER = Thunderhack.serverManager.getTPS() / 20.0f;
        }
        else {
            Thunderhack.TICK_TIMER = 1.0f;
        }
    }
    
    public void onDisable() {
        Thunderhack.TICK_TIMER = 1.0f;
    }
}
