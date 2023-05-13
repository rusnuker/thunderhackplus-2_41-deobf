//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;

public class Reach extends Module
{
    private static Reach INSTANCE;
    public Setting<Float> add;
    
    public Reach() {
        super("Reach", "\u0423\u0432\u0435\u043b\u0438\u0447\u0438\u0432\u0430\u0435\u0442 \u0434\u0430\u043b\u044c\u043d\u043e\u0441\u0442\u044c-\u0432\u0437\u0430\u0438\u043c\u043e\u0434\u0435\u0439\u0441\u0442\u0432\u0438\u0439 (\u0438 \u0447\u043b\u0435\u043d)", Module.Category.PLAYER);
        this.add = (Setting<Float>)this.register(new Setting("Add", (T)3.0f, (T)0.0f, (T)7.0f));
        this.setInstance();
    }
    
    public static Reach getInstance() {
        if (Reach.INSTANCE == null) {
            Reach.INSTANCE = new Reach();
        }
        return Reach.INSTANCE;
    }
    
    private void setInstance() {
        Reach.INSTANCE = this;
    }
    
    public String getDisplayInfo() {
        return this.add.getValue().toString();
    }
    
    static {
        Reach.INSTANCE = new Reach();
    }
}
