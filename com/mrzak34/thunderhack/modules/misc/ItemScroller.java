//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;

public class ItemScroller extends Module
{
    public Setting<Integer> delay;
    
    public ItemScroller() {
        super("ItemScroller", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0431\u044b\u0441\u0442\u0440\u043e-\u043f\u0435\u0440\u0435\u043a\u043b\u0430\u0434\u044b\u0432\u0430\u0442\u044c-\u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b", Category.MISC);
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)100, (T)0, (T)1000));
    }
}
