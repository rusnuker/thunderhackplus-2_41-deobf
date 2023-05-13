//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.client;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.clickui.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ClickGui extends Module
{
    private static ClickGui INSTANCE;
    public final Setting<ColorSetting> hcolor1;
    public final Setting<ColorSetting> acolor;
    public final Setting<ColorSetting> plateColor;
    public final Setting<ColorSetting> catColor;
    public final Setting<ColorSetting> downColor;
    public Setting<Integer> colorSpeed;
    public Setting<Boolean> showBinds;
    private final Setting<colorModeEn> colorMode;
    private final Setting<Moderator> shader;
    
    public ClickGui() {
        super("ClickGui", "\u043a\u043b\u0438\u043a\u0433\u0443\u0438", Category.CLIENT);
        this.hcolor1 = (Setting<ColorSetting>)this.register(new Setting("MainColor", (T)new ColorSetting(-6974059)));
        this.acolor = (Setting<ColorSetting>)this.register(new Setting("MainColor2", (T)new ColorSetting(-8365735)));
        this.plateColor = (Setting<ColorSetting>)this.register(new Setting("PlateColor", (T)new ColorSetting(-14474718)));
        this.catColor = (Setting<ColorSetting>)this.register(new Setting("CategoryColor", (T)new ColorSetting(-15395563)));
        this.downColor = (Setting<ColorSetting>)this.register(new Setting("DownColor", (T)new ColorSetting(-14474461)));
        this.colorSpeed = (Setting<Integer>)this.register(new Setting("ColorSpeed", (T)18, (T)2, (T)54));
        this.showBinds = (Setting<Boolean>)this.register(new Setting("ShowBinds", (T)true));
        this.colorMode = (Setting<colorModeEn>)this.register(new Setting("ColorMode", (T)colorModeEn.Static));
        this.shader = (Setting<Moderator>)this.register(new Setting("shader", (T)Moderator.none));
        this.setInstance();
    }
    
    public static ClickGui getInstance() {
        if (ClickGui.INSTANCE == null) {
            ClickGui.INSTANCE = new ClickGui();
        }
        return ClickGui.INSTANCE;
    }
    
    public Color getColor(final int count) {
        final int index = count;
        switch (this.colorMode.getValue()) {
            case Sky: {
                return ColorUtil.skyRainbow((int)this.colorSpeed.getValue(), index);
            }
            case LightRainbow: {
                return ColorUtil.rainbow((int)this.colorSpeed.getValue(), index, 0.6f, 1.0f, 1.0f);
            }
            case Rainbow: {
                return ColorUtil.rainbow((int)this.colorSpeed.getValue(), index, 1.0f, 1.0f, 1.0f);
            }
            case Fade: {
                return ColorUtil.fade((int)this.colorSpeed.getValue(), index, this.hcolor1.getValue().getColorObject(), 1.0f);
            }
            case DoubleColor: {
                return ColorUtil.interpolateColorsBackAndForth((int)this.colorSpeed.getValue(), index, this.hcolor1.getValue().getColorObject(), Colors.ALTERNATE_COLOR, true);
            }
            case Analogous: {
                final int val = 1;
                final Color analogous = ColorUtil.getAnalogousColor(this.acolor.getValue().getColorObject())[val];
                return ColorUtil.interpolateColorsBackAndForth((int)this.colorSpeed.getValue(), index, this.hcolor1.getValue().getColorObject(), analogous, true);
            }
            default: {
                return this.hcolor1.getValue().getColorObject();
            }
        }
    }
    
    @Override
    public void onEnable() {
        Util.mc.displayGuiScreen((GuiScreen)ClickUI.getClickGui());
    }
    
    private void setInstance() {
        ClickGui.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        if (this.shader.getValue() != Moderator.none) {
            if (OpenGlHelper.shadersSupported && ClickGui.mc.getRenderViewEntity() instanceof EntityPlayer) {
                if (ClickGui.mc.entityRenderer.getShaderGroup() != null) {
                    ClickGui.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
                }
                try {
                    ClickGui.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/" + this.shader.getValue() + ".json"));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (ClickGui.mc.entityRenderer.getShaderGroup() != null && ClickGui.mc.currentScreen == null) {
                ClickGui.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent event) {
    }
    
    @Override
    public void onDisable() {
        if (ClickGui.mc.entityRenderer.getShaderGroup() != null) {
            ClickGui.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
    
    @Override
    public void onTick() {
        if (!(ClickGui.mc.currentScreen instanceof ClickUI)) {
            this.disable();
        }
    }
    
    static {
        ClickGui.INSTANCE = new ClickGui();
    }
    
    public enum colorModeEn
    {
        Static, 
        Sky, 
        LightRainbow, 
        Rainbow, 
        Fade, 
        DoubleColor, 
        Analogous;
    }
    
    public enum Moderator
    {
        none, 
        notch, 
        antialias, 
        art, 
        bits, 
        blobs, 
        blobs2, 
        blur, 
        bumpy, 
        color_convolve, 
        creeper, 
        deconverge, 
        desaturate, 
        flip, 
        fxaa, 
        green, 
        invert, 
        ntsc, 
        pencil, 
        phosphor, 
        sobel, 
        spider, 
        wobble;
    }
}
