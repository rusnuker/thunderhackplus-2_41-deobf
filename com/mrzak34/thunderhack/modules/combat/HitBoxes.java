//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;

public class HitBoxes extends Module
{
    public Setting<Float> expand;
    
    public HitBoxes() {
        super("HitBoxes", "\u0423\u0432\u0435\u043b\u0438\u0447\u0438\u0432\u0430\u0435\u0442 \u0445\u0438\u0442\u0431\u043e\u043a\u0441\u044b", Category.COMBAT);
        this.expand = (Setting<Float>)this.register(new Setting("Value", (T)0.0f, (T)0.0f, (T)5.0f));
    }
}
