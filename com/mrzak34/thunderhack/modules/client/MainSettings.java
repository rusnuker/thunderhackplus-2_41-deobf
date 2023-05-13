//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.client;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;

public class MainSettings extends Module
{
    public Setting<Boolean> showcapes;
    public Setting<Boolean> DownloadCapes;
    public Setting<Boolean> notifyToggles;
    public Setting<Boolean> mainMenu;
    public Setting<Boolean> renderRotations;
    public Setting<Boolean> eatAnim;
    public Setting<ShaderModeEn> shaderMode;
    public Setting<Language> language;
    
    public MainSettings() {
        super("ClientSettings", "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u043a\u043b\u0438\u0435\u043d\u0442\u0430", Category.CLIENT);
        this.showcapes = (Setting<Boolean>)this.register(new Setting("Capes", (T)true));
        this.DownloadCapes = (Setting<Boolean>)this.register(new Setting("DownloadCapes", (T)true));
        this.notifyToggles = (Setting<Boolean>)this.register(new Setting("NotifyToggles", (T)false));
        this.mainMenu = (Setting<Boolean>)this.register(new Setting("MainMenu", (T)true));
        this.renderRotations = (Setting<Boolean>)this.register(new Setting("RenderRotations", (T)true));
        this.eatAnim = (Setting<Boolean>)this.register(new Setting("BedrockEatAnim", (T)false));
        this.shaderMode = (Setting<ShaderModeEn>)this.register(new Setting("ShaderMode", (T)ShaderModeEn.Smoke));
        this.language = (Setting<Language>)this.register(new Setting("Language", (T)Language.RU));
    }
    
    public enum ShaderModeEn
    {
        Smoke, 
        WarThunder, 
        Dicks;
    }
    
    public enum Language
    {
        RU, 
        ENG;
    }
}
