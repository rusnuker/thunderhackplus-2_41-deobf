//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.command.*;

public class SilentBow extends Module
{
    public Setting<Boolean> bomb;
    private int prev_slot;
    private int ticks;
    
    public SilentBow() {
        super("SilentBow", "\u0421\u0442\u0440\u0435\u043b\u044f\u0435\u0442 \u0438\u0437 \u043b\u0443\u043a\u0430-\u0431\u0435\u0437 \u0441\u0432\u0430\u043f\u0430", "SilentBow", Category.FUNNYGAME);
        this.bomb = (Setting<Boolean>)this.register(new Setting("Bomb", (T)false));
        this.prev_slot = -2;
        this.ticks = 4;
    }
    
    @Override
    public void onEnable() {
        this.ticks = 4;
        final int bowslot = InventoryUtil.getBowAtHotbar();
        this.prev_slot = SilentBow.mc.player.inventory.currentItem;
        if (bowslot != -1) {
            SilentBow.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(bowslot));
            SilentBow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)SilentBow.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            SilentBow.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            if (this.bomb.getValue()) {
                for (int i = 0; i < 106; ++i) {
                    SilentBow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(SilentBow.mc.player.posX, SilentBow.mc.player.posY - 1.0E-10, SilentBow.mc.player.posZ, true));
                    SilentBow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(SilentBow.mc.player.posX, SilentBow.mc.player.posY + 1.0E-10, SilentBow.mc.player.posZ, false));
                }
            }
        }
        else {
            Command.sendMessage("\u0423 \u0442\u0435\u0431\u044f \u043b\u0443\u043a\u0430 \u0432 \u0445\u043e\u0442\u0431\u0430\u0440\u0435 \u043d\u0435\u043c\u0430, \u0434\u0443\u0440\u0430\u043d\u0447\u0435\u0443\u0441");
            this.toggle();
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.ticks > 0) {
            --this.ticks;
        }
        else if (this.prev_slot != -2) {
            SilentBow.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.prev_slot));
            this.prev_slot = -2;
            this.ticks = 4;
            this.toggle();
        }
    }
}
