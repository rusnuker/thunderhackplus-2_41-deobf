//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ViewModel extends Module
{
    private static ViewModel INSTANCE;
    public Setting<Settings> settings;
    public Setting<Boolean> noEatAnimation;
    public Setting<Float> eatX;
    public Setting<Float> eatY;
    public Setting<Boolean> doBob;
    public Setting<Boolean> XBob;
    public Setting<Float> zbobcorr;
    public Setting<Float> mainX;
    public Setting<Float> mainY;
    public Setting<Float> mainZ;
    public Setting<Float> offX;
    public Setting<Float> offY;
    public Setting<Float> offZ;
    public Setting<Float> mainRotX;
    public Setting<Float> mainRotY;
    public Setting<Float> mainRotZ;
    public Setting<Float> offRotX;
    public Setting<Float> offRotY;
    public Setting<Float> offRotZ;
    public Setting<Float> mainScaleX;
    public Setting<Float> mainScaleY;
    public Setting<Float> mainScaleZ;
    public Setting<Float> offScaleX;
    public Setting<Float> offScaleY;
    public Setting<Float> offScaleZ;
    public Timer timer2;
    public Setting<Boolean> killauraattack;
    public Setting<Float> kmainScaleX;
    public Setting<Float> kmainScaleY;
    public Setting<Float> kmainScaleZ;
    public Setting<Float> kmainRotX;
    public Setting<Float> kmainRotY;
    public Setting<Float> kmainRotZ;
    public Setting<Float> kmainX;
    public Setting<Float> kmainY;
    public Setting<Float> kmainZ;
    public Setting<Boolean> rotatexo;
    public Setting<Boolean> rotateyo;
    public Setting<Boolean> rotatezo;
    public Setting<Boolean> krotatex;
    public Setting<Boolean> krotatey;
    public Setting<Boolean> krotatez;
    public Setting<Boolean> rotatex;
    public Setting<Boolean> rotatey;
    public Setting<Boolean> rotatez;
    public Setting<Integer> animdelay;
    int negripidari;
    
    public ViewModel() {
        super("ViewModel", "Cool", Module.Category.RENDER);
        this.settings = (Setting<Settings>)this.register(new Setting("Settings", (T)Settings.TRANSLATE));
        this.noEatAnimation = (Setting<Boolean>)this.register(new Setting("NoEatAnimation", (T)false, v -> this.settings.getValue() == Settings.TWEAKS));
        this.eatX = (Setting<Float>)this.register(new Setting("EatX", (T)1.0f, (T)(-2.0f), (T)5.0f, v -> this.settings.getValue() == Settings.TWEAKS && !this.noEatAnimation.getValue()));
        this.eatY = (Setting<Float>)this.register(new Setting("EatY", (T)1.0f, (T)(-2.0f), (T)5.0f, v -> this.settings.getValue() == Settings.TWEAKS && !this.noEatAnimation.getValue()));
        this.doBob = (Setting<Boolean>)this.register(new Setting("ItemBob", (T)true, v -> this.settings.getValue() == Settings.TWEAKS));
        this.XBob = (Setting<Boolean>)this.register(new Setting("ZBob", (T)true, v -> this.settings.getValue() == Settings.TWEAKS));
        this.zbobcorr = (Setting<Float>)this.register(new Setting("ZBobCorr", (T)0.6f, (T)0.1f, (T)2.0f, v -> this.settings.getValue() == Settings.TWEAKS));
        this.mainX = (Setting<Float>)this.register(new Setting("MainX", (T)1.2f, (T)(-2.0f), (T)4.0f, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.mainY = (Setting<Float>)this.register(new Setting("MainY", (T)(-0.95f), (T)(-3.0f), (T)3.0f, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.mainZ = (Setting<Float>)this.register(new Setting("MainZ", (T)(-1.45f), (T)(-5.0f), (T)5.0f, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.offX = (Setting<Float>)this.register(new Setting("OffX", (T)1.2f, (T)(-2.0f), (T)4.0f, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.offY = (Setting<Float>)this.register(new Setting("OffY", (T)(-0.95f), (T)(-3.0f), (T)3.0f, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.offZ = (Setting<Float>)this.register(new Setting("OffZ", (T)(-1.45f), (T)(-5.0f), (T)5.0f, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.mainRotX = (Setting<Float>)this.register(new Setting("MainRotationX", (T)0.0f, (T)(-36.0f), (T)36.0f, v -> this.settings.getValue() == Settings.ROTATE));
        this.mainRotY = (Setting<Float>)this.register(new Setting("MainRotationY", (T)0.0f, (T)(-36.0f), (T)36.0f, v -> this.settings.getValue() == Settings.ROTATE));
        this.mainRotZ = (Setting<Float>)this.register(new Setting("MainRotationZ", (T)0.0f, (T)(-36.0f), (T)36.0f, v -> this.settings.getValue() == Settings.ROTATE));
        this.offRotX = (Setting<Float>)this.register(new Setting("OffRotationX", (T)0.0f, (T)(-36.0f), (T)36.0f, v -> this.settings.getValue() == Settings.ROTATE));
        this.offRotY = (Setting<Float>)this.register(new Setting("OffRotationY", (T)0.0f, (T)(-36.0f), (T)36.0f, v -> this.settings.getValue() == Settings.ROTATE));
        this.offRotZ = (Setting<Float>)this.register(new Setting("OffRotationZ", (T)0.0f, (T)(-36.0f), (T)36.0f, v -> this.settings.getValue() == Settings.ROTATE));
        this.mainScaleX = (Setting<Float>)this.register(new Setting("MainScaleX", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.settings.getValue() == Settings.SCALE));
        this.mainScaleY = (Setting<Float>)this.register(new Setting("MainScaleY", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.settings.getValue() == Settings.SCALE));
        this.mainScaleZ = (Setting<Float>)this.register(new Setting("MainScaleZ", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.settings.getValue() == Settings.SCALE));
        this.offScaleX = (Setting<Float>)this.register(new Setting("OffScaleX", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.settings.getValue() == Settings.SCALE));
        this.offScaleY = (Setting<Float>)this.register(new Setting("OffScaleY", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.settings.getValue() == Settings.SCALE));
        this.offScaleZ = (Setting<Float>)this.register(new Setting("OffScaleZ", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.settings.getValue() == Settings.SCALE));
        this.timer2 = new Timer();
        this.killauraattack = (Setting<Boolean>)this.register(new Setting("KillAura", (T)false));
        this.kmainScaleX = (Setting<Float>)this.register(new Setting("KMainScaleX", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.killauraattack.getValue()));
        this.kmainScaleY = (Setting<Float>)this.register(new Setting("KMainScaleY", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.killauraattack.getValue()));
        this.kmainScaleZ = (Setting<Float>)this.register(new Setting("KMainScaleZ", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.killauraattack.getValue()));
        this.kmainRotX = (Setting<Float>)this.register(new Setting("KMainRotationX", (T)0.0f, (T)(-36.0f), (T)36.0f, v -> this.killauraattack.getValue()));
        this.kmainRotY = (Setting<Float>)this.register(new Setting("KMainRotationY", (T)0.0f, (T)(-36.0f), (T)36.0f, v -> this.killauraattack.getValue()));
        this.kmainRotZ = (Setting<Float>)this.register(new Setting("kMainRotationZ", (T)0.0f, (T)(-36.0f), (T)36.0f, v -> this.killauraattack.getValue()));
        this.kmainX = (Setting<Float>)this.register(new Setting("KMainX", (T)1.2f, (T)(-2.0f), (T)4.0f, v -> this.killauraattack.getValue()));
        this.kmainY = (Setting<Float>)this.register(new Setting("KMainY", (T)(-0.95f), (T)(-3.0f), (T)3.0f, v -> this.killauraattack.getValue()));
        this.kmainZ = (Setting<Float>)this.register(new Setting("KMainZ", (T)(-1.45f), (T)(-5.0f), (T)5.0f, v -> this.killauraattack.getValue()));
        this.rotatexo = (Setting<Boolean>)this.register(new Setting("RotateX", (T)false));
        this.rotateyo = (Setting<Boolean>)this.register(new Setting("RotateY", (T)false));
        this.rotatezo = (Setting<Boolean>)this.register(new Setting("RotateZ", (T)false));
        this.krotatex = (Setting<Boolean>)this.register(new Setting("KRotateX", (T)false, v -> this.killauraattack.getValue()));
        this.krotatey = (Setting<Boolean>)this.register(new Setting("KRotateY", (T)false, v -> this.killauraattack.getValue()));
        this.krotatez = (Setting<Boolean>)this.register(new Setting("KRotateZ", (T)false, v -> this.killauraattack.getValue()));
        this.rotatex = (Setting<Boolean>)this.register(new Setting("RotateXOff", (T)false));
        this.rotatey = (Setting<Boolean>)this.register(new Setting("RotateYOff", (T)false));
        this.rotatez = (Setting<Boolean>)this.register(new Setting("RotateZOff", (T)false));
        this.animdelay = (Setting<Integer>)this.register(new Setting("RotateSpeed", (T)36, (T)1, (T)1200, v -> this.killauraattack.getValue() || this.rotatex.getValue() || this.rotatey.getValue() || this.rotatez.getValue()));
        this.negripidari = -180;
        this.setInstance();
    }
    
    public static ViewModel getInstance() {
        if (ViewModel.INSTANCE == null) {
            ViewModel.INSTANCE = new ViewModel();
        }
        return ViewModel.INSTANCE;
    }
    
    private void setInstance() {
        ViewModel.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onItemRender(final RenderItemEvent event) {
        event.setOffX(-this.offX.getValue());
        event.setOffY((float)this.offY.getValue());
        event.setOffZ((float)this.offZ.getValue());
        if (this.timer2.passedMs(1000 / this.animdelay.getValue())) {
            ++this.negripidari;
            if (this.negripidari > 180) {
                this.negripidari = -180;
            }
            this.timer2.reset();
        }
        if (!this.rotatex.getValue()) {
            event.setOffRotX(this.offRotX.getValue() * 5.0f);
        }
        else {
            event.setOffRotX((float)this.negripidari);
        }
        if (!this.rotatey.getValue()) {
            event.setOffRotY(this.offRotY.getValue() * 5.0f);
        }
        else {
            event.setOffRotY((float)this.negripidari);
        }
        if (!this.rotatez.getValue()) {
            event.setOffRotZ(this.offRotZ.getValue() * 5.0f);
        }
        else {
            event.setOffRotZ((float)this.negripidari);
        }
        event.setOffHandScaleX((float)this.offScaleX.getValue());
        event.setOffHandScaleY((float)this.offScaleY.getValue());
        event.setOffHandScaleZ((float)this.offScaleZ.getValue());
        if (this.killauraattack.getValue() && Aura.target != null) {
            event.setMainHandScaleX((float)this.kmainScaleX.getValue());
            event.setMainHandScaleY((float)this.kmainScaleY.getValue());
            event.setMainHandScaleZ((float)this.kmainScaleZ.getValue());
            if (!this.krotatex.getValue()) {
                event.setMainRotX(this.kmainRotX.getValue() * 5.0f);
            }
            else {
                event.setMainRotX((float)this.negripidari);
            }
            if (!this.krotatey.getValue()) {
                event.setMainRotY(this.kmainRotY.getValue() * 5.0f);
            }
            else {
                event.setMainRotY((float)this.negripidari);
            }
            if (!this.krotatez.getValue()) {
                event.setMainRotZ(this.kmainRotZ.getValue() * 5.0f);
            }
            else {
                event.setMainRotZ((float)this.negripidari);
            }
            event.setMainX((float)this.kmainX.getValue());
            event.setMainY((float)this.kmainY.getValue());
            event.setMainZ((float)this.kmainZ.getValue());
        }
        else {
            event.setMainHandScaleX((float)this.mainScaleX.getValue());
            event.setMainHandScaleY((float)this.mainScaleY.getValue());
            event.setMainHandScaleZ((float)this.mainScaleZ.getValue());
            if (!this.rotatexo.getValue()) {
                event.setMainRotX(this.mainRotX.getValue() * 5.0f);
            }
            else {
                event.setMainRotX((float)this.negripidari);
            }
            if (!this.rotateyo.getValue()) {
                event.setMainRotY(this.mainRotY.getValue() * 5.0f);
            }
            else {
                event.setMainRotY((float)this.negripidari);
            }
            if (!this.rotatezo.getValue()) {
                event.setMainRotZ(this.mainRotZ.getValue() * 5.0f);
            }
            else {
                event.setMainRotZ((float)this.negripidari);
            }
            event.setMainX((float)this.mainX.getValue());
            event.setMainY((float)this.mainY.getValue());
            event.setMainZ((float)this.mainZ.getValue());
        }
    }
    
    static {
        ViewModel.INSTANCE = new ViewModel();
    }
    
    private enum Settings
    {
        TRANSLATE, 
        ROTATE, 
        SCALE, 
        TWEAKS;
    }
}
