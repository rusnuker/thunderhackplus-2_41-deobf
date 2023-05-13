//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;

public class KeepSprint extends Module
{
    public final Setting<Boolean> sprint;
    public final Setting<Float> motion;
    
    public KeepSprint() {
        super("KeepSprint", "\u041d\u0435 \u0441\u0431\u0438\u0432\u0430\u0442\u044c \u0441\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u043f\u0440\u0438 \u0443\u0434\u0430\u0440\u0435", Module.Category.MOVEMENT);
        this.sprint = (Setting<Boolean>)this.register(new Setting("Sprint", (T)true));
        this.motion = (Setting<Float>)this.register(new Setting("motion", (T)1.0f, (T)0.0f, (T)1.0f));
    }
}
