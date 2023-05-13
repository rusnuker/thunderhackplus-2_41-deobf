//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.mixin.mixins.*;

public class Animations extends Module
{
    private static Animations INSTANCE;
    public Setting<Boolean> ed;
    public Setting<Boolean> auraOnly;
    public Setting<Float> fapSmooth;
    public Setting<Integer> slowValue;
    public Setting<rmode> rMode;
    public float shitfix;
    public boolean abobka228;
    
    public Animations() {
        super("Animations", "\u0430\u043d\u0438\u043c\u0430\u0446\u0438\u0438 \u0443\u0434\u0430\u0440\u0430", Module.Category.RENDER);
        this.ed = (Setting<Boolean>)this.register(new Setting("EquipDisable", (T)true));
        this.auraOnly = (Setting<Boolean>)this.register(new Setting("auraOnly", (T)false));
        this.fapSmooth = (Setting<Float>)this.register(new Setting("fapSmooth", (T)4.0f, (T)0.5f, (T)15.0f));
        this.slowValue = (Setting<Integer>)this.register(new Setting("SlowValue", (T)6, (T)1, (T)50));
        this.rMode = (Setting<rmode>)this.register(new Setting("SwordMode", (T)rmode.Swipe));
        this.shitfix = 1.0f;
        this.abobka228 = false;
        this.setInstance();
    }
    
    public static Animations getInstance() {
        if (Animations.INSTANCE == null) {
            Animations.INSTANCE = new Animations();
        }
        return Animations.INSTANCE;
    }
    
    private void setInstance() {
        Animations.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent e) {
        if (Animations.mc.world != null && Animations.mc.player != null) {
            this.shitfix = Animations.mc.player.getSwingProgress(Animations.mc.getRenderPartialTicks());
        }
    }
    
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        this.abobka228 = (((IItemRenderer)Animations.mc.getItemRenderer()).getEquippedProgressMainHand() < 1.0f);
        if (this.ed.getValue() && ((IItemRenderer)Animations.mc.getItemRenderer()).getEquippedProgressMainHand() >= 0.9) {
            ((IItemRenderer)Animations.mc.getItemRenderer()).setEquippedProgressMainHand(1.0f);
            ((IItemRenderer)Animations.mc.getItemRenderer()).setItemStackMainHand(Animations.mc.player.getHeldItemMainhand());
        }
    }
    
    static {
        Animations.INSTANCE = new Animations();
    }
    
    public enum rmode
    {
        Swipe, 
        Rich, 
        Glide, 
        Default, 
        New, 
        Oblique, 
        Fap, 
        Slow;
    }
}
