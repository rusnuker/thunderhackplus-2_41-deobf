//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.init.*;

public class AutoLeave extends Module
{
    public Setting<Float> health;
    public Setting<Boolean> leaveOnEnable;
    
    public AutoLeave() {
        super("AutoLeave", "\u043b\u0438\u0432\u0430\u0435\u0442 \u0435\u0441\u043b\u0438 \u0442\u0432\u043e\u0435 \u0445\u0432\u0445-\u043f\u043e\u0434\u0445\u043e\u0434\u0438\u0442 \u043a \u043a\u043e\u043d\u0446\u0443", Category.FUNNYGAME);
        this.health = (Setting<Float>)this.register(new Setting("health", (T)4.0f, (T)0.0f, (T)10.0f));
        this.leaveOnEnable = (Setting<Boolean>)this.register(new Setting("LeaveOnEnable", (T)true));
    }
    
    @Override
    public void onEnable() {
        if (AutoLeave.mc.player != null && AutoLeave.mc.world != null && this.leaveOnEnable.getValue()) {
            for (int i = 0; i < 1000; ++i) {
                AutoLeave.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoLeave.mc.player.posX + 100.0, AutoLeave.mc.player.posY + 100.0, AutoLeave.mc.player.posZ + 100.0, false));
            }
            this.toggle();
        }
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        if (AutoLeave.mc.player.getHealth() <= this.health.getValue() && AutoLeave.mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING && AutoLeave.mc.player.getHeldItemMainhand().getItem() != Items.TOTEM_OF_UNDYING) {
            for (int i = 0; i < 1000; ++i) {
                AutoLeave.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoLeave.mc.player.posX + 100.0, AutoLeave.mc.player.posY + 100.0, AutoLeave.mc.player.posZ + 100.0, false));
            }
            this.toggle();
        }
    }
}
