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

public class AutoAmericano extends Module
{
    public Timer timer;
    private final Setting<Mode> mainMode;
    
    public AutoAmericano() {
        super("AutoAmericano", "AutoAmericano", Category.FUNNYGAME);
        this.timer = new Timer();
        this.mainMode = (Setting<Mode>)this.register(new Setting("Version", (T)Mode.New));
    }
    
    @Override
    public void onUpdate() {
        if (this.timer.passedMs(200L) && InventoryUtil.getAmericanoAtHotbar(this.mainMode.getValue() == Mode.Old) != -1 && !AutoAmericano.mc.player.isPotionActive(MobEffects.HASTE)) {
            final int hotbarslot = AutoAmericano.mc.player.inventory.currentItem;
            AutoAmericano.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(InventoryUtil.getAmericanoAtHotbar(this.mainMode.getValue() == Mode.Old)));
            AutoAmericano.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            AutoAmericano.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(hotbarslot));
            this.timer.reset();
        }
    }
    
    public enum Mode
    {
        Old, 
        New;
    }
}
