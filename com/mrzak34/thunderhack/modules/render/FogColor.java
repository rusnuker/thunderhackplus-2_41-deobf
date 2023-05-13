//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;

public class FogColor extends Module
{
    public final Setting<ColorSetting> color;
    public Setting<Float> distance;
    
    public FogColor() {
        super("FogColor", "\u043c\u0435\u043d\u044f\u0435\u0442 \u0446\u0432\u0435\u0442 \u0442\u0443\u043c\u0430\u043d\u0430", Module.Category.RENDER);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.distance = (Setting<Float>)this.register(new Setting("Distance", (T)1.0f, (T)0.0f, (T)10.0f));
    }
}
