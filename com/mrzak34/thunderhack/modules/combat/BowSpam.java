//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;

public class BowSpam extends Module
{
    private final Timer timer;
    public Setting<Mode> mode;
    public Setting<Boolean> allowOffhand;
    public Setting<Integer> ticks;
    public Setting<Integer> delay;
    private boolean offhand;
    
    public BowSpam() {
        super("BowSpam", "\u0421\u043f\u0430\u043c\u0438\u0442 \u0441\u0442\u0440\u0435\u043b\u0430\u043c\u0438", Category.COMBAT);
        this.timer = new Timer();
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.FAST));
        this.allowOffhand = (Setting<Boolean>)this.register(new Setting("Offhand", (T)Boolean.TRUE, v -> this.mode.getValue() != Mode.AUTORELEASE));
        this.ticks = (Setting<Integer>)this.register(new Setting("Ticks", (T)3, (T)0, (T)20, v -> this.mode.getValue() == Mode.FAST));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)50, (T)0, (T)500, v -> this.mode.getValue() == Mode.AUTORELEASE));
        this.offhand = false;
    }
    
    @SubscribeEvent
    public void onPlayerPre(final EventSync event) {
        if (this.mode.getValue() == Mode.FAST && (this.offhand || BowSpam.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) && BowSpam.mc.player.isHandActive() && BowSpam.mc.player.getItemInUseMaxCount() >= this.ticks.getValue()) {
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, BowSpam.mc.player.getHorizontalFacing()));
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(this.offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND));
            BowSpam.mc.player.stopActiveHand();
        }
    }
    
    @Override
    public void onUpdate() {
        this.offhand = (BowSpam.mc.player.getHeldItemOffhand().getItem() == Items.BOW && this.allowOffhand.getValue());
        if (this.mode.getValue() == Mode.AUTORELEASE) {
            if ((!this.offhand && !(BowSpam.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow)) || !this.timer.passedMs((int)(float)this.delay.getValue())) {
                return;
            }
            BowSpam.mc.playerController.onStoppedUsingItem((EntityPlayer)BowSpam.mc.player);
            this.timer.reset();
        }
    }
    
    public enum Mode
    {
        FAST, 
        AUTORELEASE;
    }
}
