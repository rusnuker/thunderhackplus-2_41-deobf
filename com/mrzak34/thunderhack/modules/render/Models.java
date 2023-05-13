//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;

public class Models extends Module
{
    public Setting<Boolean> onlySelf;
    public Setting<Boolean> friends;
    public Setting<Boolean> friendHighlight;
    public Setting<mode> Mode;
    public Setting<ColorSetting> eyeColor;
    public Setting<ColorSetting> bodyColor;
    public Setting<ColorSetting> legsColor;
    
    public Models() {
        super("Models", "Models", Module.Category.RENDER);
        this.onlySelf = (Setting<Boolean>)this.register(new Setting("onlySelf", (T)false));
        this.friends = (Setting<Boolean>)this.register(new Setting("friends", (T)false));
        this.friendHighlight = (Setting<Boolean>)this.register(new Setting("friendHighLight", (T)false));
        this.Mode = (Setting<mode>)this.register(new Setting("Mode", (T)mode.Freddy));
        this.eyeColor = (Setting<ColorSetting>)this.register(new Setting("eyeColor", (T)new ColorSetting(-2009289807)));
        this.bodyColor = (Setting<ColorSetting>)this.register(new Setting("bodyColor", (T)new ColorSetting(-2009289807)));
        this.legsColor = (Setting<ColorSetting>)this.register(new Setting("legsColor", (T)new ColorSetting(-2009289807)));
    }
    
    public enum mode
    {
        Amogus, 
        Rabbit, 
        Freddy;
    }
}
