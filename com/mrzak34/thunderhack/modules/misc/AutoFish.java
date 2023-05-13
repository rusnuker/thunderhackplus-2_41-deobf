//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class AutoFish extends Module
{
    public Setting<Boolean> rodSave;
    public Setting<Boolean> changeRod;
    public Setting<Boolean> autoSell;
    public Setting<Boolean> autoLeave;
    private int rodSlot;
    private final Timer timeout;
    
    public AutoFish() {
        super("AutoFish", "\u043f\u0440\u0438\u0437\u043d\u0430\u0439\u0441\u044f \u0437\u0430\u0445\u043e\u0442\u0435\u043b", Category.MISC);
        this.rodSave = (Setting<Boolean>)this.register(new Setting("RodSave", (T)true));
        this.changeRod = (Setting<Boolean>)this.register(new Setting("ChangeRod", (T)false));
        this.autoSell = (Setting<Boolean>)this.register(new Setting("AutoSell", (T)false));
        this.autoLeave = (Setting<Boolean>)this.register(new Setting("AutoLeave", (T)false));
        this.rodSlot = -1;
        this.timeout = new Timer();
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            this.toggle();
            return;
        }
        this.rodSlot = InventoryUtil.findItem(ItemFishingRod.class);
    }
    
    @Override
    public void onUpdate() {
        if (AutoFish.mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod && AutoFish.mc.player.getHeldItemMainhand().getItemDamage() > 52) {
            if (this.rodSave.getValue() && !this.changeRod.getValue()) {
                Command.sendMessage("Saving rod...");
                this.toggle();
            }
            else if (this.changeRod.getValue() && InventoryUtil.getRodSlot() != -1) {
                Command.sendMessage("Swapped to a new rod");
                AutoFish.mc.player.inventory.currentItem = InventoryUtil.getRodSlot();
            }
            else {
                Command.sendMessage("Saving rod...");
                this.toggle();
            }
        }
        if (this.timeout.passedMs(60000L)) {
            if (this.rodSlot == -1) {
                this.rodSlot = InventoryUtil.findItem(ItemFishingRod.class);
            }
            if (this.rodSlot != -1) {
                final int startSlot = AutoFish.mc.player.inventory.currentItem;
                AutoFish.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.rodSlot));
                AutoFish.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                AutoFish.mc.player.swingArm(EnumHand.MAIN_HAND);
                AutoFish.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                AutoFish.mc.player.swingArm(EnumHand.MAIN_HAND);
                if (startSlot != -1) {
                    AutoFish.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(startSlot));
                }
                this.timeout.reset();
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityAdded(final EntityAddedEvent event) {
        if (this.autoLeave.getValue() && !Thunderhack.friendManager.isFriend(event.entity.getName())) {
            this.toggle();
            AutoFish.mc.player.connection.handleDisconnect(new SPacketDisconnect((ITextComponent)new TextComponentString("AutoFish (log)")));
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (packet.getCategory() == SoundCategory.NEUTRAL && packet.getSound() == SoundEvents.ENTITY_BOBBER_SPLASH) {
                if (this.rodSlot == -1) {
                    this.rodSlot = InventoryUtil.findItem(ItemFishingRod.class);
                }
                if (this.rodSlot != -1) {
                    final int startSlot = AutoFish.mc.player.inventory.currentItem;
                    AutoFish.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.rodSlot));
                    AutoFish.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    AutoFish.mc.player.swingArm(EnumHand.MAIN_HAND);
                    AutoFish.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    AutoFish.mc.player.swingArm(EnumHand.MAIN_HAND);
                    if (startSlot != -1) {
                        AutoFish.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(startSlot));
                    }
                    if (this.autoSell.getValue() && this.timeout.passedMs(1000L)) {
                        AutoFish.mc.player.sendChatMessage("/sellfish");
                    }
                    this.timeout.reset();
                }
            }
        }
    }
}
