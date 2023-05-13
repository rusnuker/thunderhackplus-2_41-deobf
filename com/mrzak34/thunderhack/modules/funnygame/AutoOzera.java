//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;

public class AutoOzera extends Module
{
    private final Setting<Mode> mainMode;
    public Setting<Integer> delay;
    public Setting<Boolean> negativeLakeEff;
    public Timer timer;
    
    public AutoOzera() {
        super("AutoOzera", "\u041f\u044c\u0451\u0442 \u0420\u043e\u0434\u043d\u044b\u0435 \u041e\u0437\u0451\u0440\u0430", Category.FUNNYGAME);
        this.mainMode = (Setting<Mode>)this.register(new Setting("Version", (T)Mode.New));
        this.delay = (Setting<Integer>)this.register(new Setting("DelayOnUse", (T)200, (T)100, (T)2000));
        this.negativeLakeEff = (Setting<Boolean>)this.register(new Setting("RemoveEffects", (T)false));
        this.timer = new Timer();
    }
    
    @Override
    public void onUpdate() {
        if (this.timer.passedMs(this.delay.getValue()) && InventoryUtil.getOzeraAtHotbar(this.mainMode.getValue() == Mode.Old) != -1 && !AutoOzera.mc.player.isPotionActive(MobEffects.STRENGTH)) {
            final int hotbarslot = AutoOzera.mc.player.inventory.currentItem;
            AutoOzera.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(InventoryUtil.getOzeraAtHotbar(this.mainMode.getValue() == Mode.Old)));
            AutoOzera.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            AutoOzera.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(hotbarslot));
            this.timer.reset();
        }
        if (this.negativeLakeEff.getValue()) {
            if (AutoOzera.mc.player.isPotionActive(MobEffects.LEVITATION)) {
                AutoOzera.mc.player.removeActivePotionEffect(MobEffects.LEVITATION);
            }
            if (AutoOzera.mc.player.isPotionActive(MobEffects.NAUSEA)) {
                AutoOzera.mc.player.removeActivePotionEffect(MobEffects.NAUSEA);
            }
            if (AutoOzera.mc.player.isPotionActive(MobEffects.BLINDNESS)) {
                AutoOzera.mc.player.removeActivePotionEffect(MobEffects.BLINDNESS);
            }
        }
    }
    
    public enum Mode
    {
        Old, 
        New;
    }
}
