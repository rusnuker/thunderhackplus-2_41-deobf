//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.awt.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Timer extends Module
{
    public static long lastUpdateTime;
    public static double value;
    public final Setting<ColorSetting> color;
    public final Setting<ColorSetting> color2;
    public final Setting<Integer> slices;
    public final Setting<Integer> slices1;
    public final Setting<Integer> slices2;
    public final Setting<Integer> slices3;
    public final Setting<Integer> yyy;
    private final Setting<Mode> mode;
    public Setting<Float> speed;
    public Setting<Boolean> smart;
    public Setting<Boolean> noMove;
    public Setting<Boolean> autoDisable;
    public Setting<Boolean> indicator;
    public Setting<Integer> maxTicks;
    public Setting<Float> shiftTicks;
    
    public Timer() {
        super("Timer", "Timer", Category.MOVEMENT);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color1", (T)new ColorSetting(-2013233153)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("Color2", (T)new ColorSetting(-2001657727)));
        this.slices = (Setting<Integer>)this.register(new Setting("colorOffset1", (T)125, (T)10, (T)500));
        this.slices1 = (Setting<Integer>)this.register(new Setting("colorOffset2", (T)211, (T)10, (T)500));
        this.slices2 = (Setting<Integer>)this.register(new Setting("colorOffset3", (T)162, (T)10, (T)500));
        this.slices3 = (Setting<Integer>)this.register(new Setting("colorOffset4", (T)60, (T)10, (T)500));
        this.yyy = (Setting<Integer>)this.register(new Setting("Y", (T)180, (T)10, (T)500));
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.NORMAL));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)2.0f, (T)0.1f, (T)10.0f, v -> this.mode.getValue() == Mode.NORMAL));
        this.smart = (Setting<Boolean>)this.register(new Setting("Smart", (T)true, v -> this.mode.getValue() == Mode.NORMAL));
        this.noMove = (Setting<Boolean>)this.register(new Setting("NoMove", (T)true, v -> this.smart.getValue() && this.mode.getValue() == Mode.NORMAL));
        this.autoDisable = (Setting<Boolean>)this.register(new Setting("AutoDisable", (T)true, v -> this.smart.getValue() && this.mode.getValue() == Mode.NORMAL));
        this.indicator = (Setting<Boolean>)this.register(new Setting("Indicator", (T)false, v -> this.smart.getValue() && this.mode.getValue() == Mode.NORMAL));
        this.maxTicks = (Setting<Integer>)this.register(new Setting("Bound", (T)0, (T)0, (T)15, v -> this.mode.getValue() == Mode.NORMAL));
        this.shiftTicks = (Setting<Float>)this.register(new Setting("ShiftTicks", (T)10.0f, (T)1.0f, (T)40.0f, v -> this.mode.getValue() == Mode.ReallyWorld));
    }
    
    public static Color TwoColoreffect(final Color cl1, final Color cl2, final double speed) {
        final double thing = speed / 4.0 % 1.0;
        final float val = MathHelper.clamp((float)Math.sin(18.84955592153876 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
        return new Color(lerp(cl1.getRed() / 255.0f, cl2.getRed() / 255.0f, val), lerp(cl1.getGreen() / 255.0f, cl2.getGreen() / 255.0f, val), lerp(cl1.getBlue() / 255.0f, cl2.getBlue() / 255.0f, val));
    }
    
    public static float lerp(final float a, final float b, final float f) {
        return a + f * (b - a);
    }
    
    public int getMin() {
        return -(15 - this.maxTicks.getValue());
    }
    
    @Override
    public void onUpdate() {
        if (this.mode.getValue() == Mode.ReallyWorld) {
            return;
        }
        if (!MovementUtil.isMoving() && Timer.mc.player.onGround && this.noMove.getValue()) {
            return;
        }
        if (!this.smart.getValue() || this.canEnableTimer(this.speed.getValue() + 0.2f)) {
            Thunderhack.TICK_TIMER = Math.max(this.speed.getValue() + ((Timer.mc.player.ticksExisted % 2 == 0) ? -0.2f : 0.2f), 0.1f);
        }
        else {
            if (this.autoDisable.getValue()) {
                this.toggle();
            }
            Thunderhack.TICK_TIMER = 1.0f;
        }
    }
    
    @SubscribeEvent
    public void onPostPlayerUpdate(final PostPlayerUpdateEvent event) {
        if (this.mode.getValue() == Mode.ReallyWorld) {
            final int status = (int)((10.0 - Timer.value) / (Math.abs(this.getMin()) + 10) * 100.0);
            if (status < 90) {
                Command.sendMessage("\u041f\u0435\u0440\u0435\u0434 \u043f\u043e\u0432\u0442\u043e\u0440\u043d\u044b\u043c \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435\u043c \u043d\u0435\u043e\u0431\u0445\u043e\u0434\u0438\u043c\u043e \u043f\u043e\u0441\u0442\u043e\u044f\u0442\u044c \u043d\u0430 \u043c\u0435\u0441\u0442\u0435!");
                this.toggle();
                return;
            }
            event.setCanceled(true);
            event.setIterations(this.shiftTicks.getValue().intValue());
            this.toggle();
        }
    }
    
    @Override
    public void onDisable() {
        Thunderhack.TICK_TIMER = 1.0f;
    }
    
    public boolean canEnableTimer(final float speed) {
        final double predictVl = (50.0 - 50.0 / speed) / 50.0;
        return predictVl + Timer.value < 10.0f - this.speed.getValue();
    }
    
    public void m() {
        final long now = System.currentTimeMillis();
        final long timeElapsed = now - Timer.lastUpdateTime;
        Timer.lastUpdateTime = now;
        Timer.value += (50.0 - timeElapsed) / 50.0;
        Timer.value -= 0.001;
        Timer.value = MathHelper.clamp(Timer.value, (double)this.getMin(), 25.0);
    }
    
    public enum Mode
    {
        NORMAL, 
        ReallyWorld;
    }
}
