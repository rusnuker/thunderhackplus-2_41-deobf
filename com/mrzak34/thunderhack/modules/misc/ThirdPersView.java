//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;

public class ThirdPersView extends Module
{
    public Setting<Integer> x;
    public Setting<Integer> y;
    public Setting<Float> z;
    
    public ThirdPersView() {
        super("ThirdPersView", "ThirdPersView", Category.MISC);
        this.x = (Setting<Integer>)this.register(new Setting("x", (T)0, (T)(-180), (T)180));
        this.y = (Setting<Integer>)this.register(new Setting("y", (T)0, (T)(-180), (T)180));
        this.z = (Setting<Float>)this.register(new Setting("z", (T)1.0f, (T)0.1f, (T)5.0f));
    }
}
