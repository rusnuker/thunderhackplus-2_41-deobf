//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.item.*;

public class AutoGApple extends Module
{
    public static boolean stopAura;
    public final Setting<Boolean> fg;
    public final Setting<Integer> Delay;
    private final Setting<Float> health;
    private boolean isActive;
    private int antiLag;
    private final Timer useDelay;
    
    public AutoGApple() {
        super("AutoGApple", "AutoGApple", Module.Category.PLAYER);
        this.fg = (Setting<Boolean>)this.register(new Setting("FunnyGame", (T)false));
        this.Delay = (Setting<Integer>)this.register(new Setting("UseDelay", (T)200, (T)0, (T)2000));
        this.health = (Setting<Float>)this.register(new Setting("health", (T)20.0f, (T)1.0f, (T)36.0f));
        this.antiLag = 0;
        this.useDelay = new Timer();
    }
    
    @SubscribeEvent
    public void onUpdate(final PlayerUpdateEvent e) {
        if (fullNullCheck()) {
            return;
        }
        if (this.GapInOffHand()) {
            AutoGApple.stopAura = false;
            if (EntityUtil.getHealth((Entity)AutoGApple.mc.player) <= this.health.getValue() && this.useDelay.passedMs(this.Delay.getValue())) {
                AutoGApple.stopAura = true;
                this.isActive = true;
                ((IKeyBinding)AutoGApple.mc.gameSettings.keyBindUseItem).setPressed(true);
            }
            else if (this.isActive) {
                AutoGApple.stopAura = false;
                this.isActive = false;
                ((IKeyBinding)AutoGApple.mc.gameSettings.keyBindUseItem).setPressed(false);
                this.antiLag = 0;
            }
            else {
                AutoGApple.stopAura = false;
            }
            if (((IKeyBinding)AutoGApple.mc.gameSettings.keyBindUseItem).isPressed() && this.fg.getValue()) {
                ++this.antiLag;
                if (this.antiLag > 50) {
                    Command.sendMessage("AntiGapLAG");
                    ((IKeyBinding)AutoGApple.mc.gameSettings.keyBindUseItem).setPressed(false);
                    this.antiLag = 0;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onFinishEating(final FinishUseItemEvent e) {
        this.useDelay.reset();
    }
    
    public void onDisable() {
        AutoGApple.stopAura = false;
    }
    
    private boolean GapInOffHand() {
        return !AutoGApple.mc.player.getHeldItemOffhand().isEmpty() && AutoGApple.mc.player.getHeldItemOffhand().getItem() instanceof ItemAppleGold;
    }
    
    static {
        AutoGApple.stopAura = false;
    }
}
