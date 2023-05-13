//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import org.lwjgl.input.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.client.entity.*;

public class MiddleClick extends Module
{
    public Setting<Boolean> fm;
    public Setting<Boolean> friend;
    public Setting<Boolean> rocket;
    public Setting<Boolean> ep;
    public Setting<Boolean> silentPearl;
    public Setting<Integer> swapDelay;
    public Setting<Boolean> xp;
    public Setting<Boolean> feetExp;
    public Setting<Boolean> silent;
    public Setting<Boolean> whileEating;
    public Setting<Boolean> pickBlock;
    public Timer timr;
    private int lastSlot;
    
    public MiddleClick() {
        super("MiddleClick", "\u0434\u0435\u0439\u0441\u0442\u0432\u0438\u044f \u043d\u0430 \u043a\u043e\u043b\u0435\u0441\u0438\u043a\u043e-\u043c\u044b\u0448\u0438", Category.MISC);
        this.fm = (Setting<Boolean>)this.register(new Setting("FriendMessage", (T)true));
        this.friend = (Setting<Boolean>)this.register(new Setting("Friend", (T)true));
        this.rocket = (Setting<Boolean>)this.register(new Setting("Rocket", (T)false));
        this.ep = (Setting<Boolean>)this.register(new Setting("Pearl", (T)true));
        this.silentPearl = (Setting<Boolean>)this.register(new Setting("SilentPearl", (T)true, v -> this.ep.getValue()));
        this.swapDelay = (Setting<Integer>)this.register(new Setting("SwapDelay", (T)100, (T)0, (T)1000));
        this.xp = (Setting<Boolean>)this.register(new Setting("XP", (T)false));
        this.feetExp = (Setting<Boolean>)this.register(new Setting("FeetXP", (T)false));
        this.silent = (Setting<Boolean>)this.register(new Setting("SilentXP", (T)true));
        this.whileEating = (Setting<Boolean>)this.register(new Setting("WhileEating", (T)true));
        this.pickBlock = (Setting<Boolean>)this.register(new Setting("CancelMC", (T)true));
        this.timr = new Timer();
        this.lastSlot = -1;
    }
    
    @SubscribeEvent
    public void onPreMotion(final EventSync event) {
        if (MiddleClick.mc.player == null || MiddleClick.mc.world == null) {
            return;
        }
        if (this.feetExp.getValue() && Mouse.isButtonDown(2)) {
            if (!this.xp.getValue()) {
                return;
            }
            MiddleClick.mc.player.rotationPitch = 90.0f;
        }
        if (this.friend.getValue() && MiddleClick.mc.objectMouseOver != null && MiddleClick.mc.objectMouseOver.entityHit != null) {
            if (!Mouse.isButtonDown(2)) {
                return;
            }
            final Entity entity = MiddleClick.mc.objectMouseOver.entityHit;
            if (entity instanceof EntityPlayer && this.timr.passedMs(2500L)) {
                if (Thunderhack.friendManager.isFriend(entity.getName())) {
                    Thunderhack.friendManager.removeFriend(entity.getName());
                    Command.sendMessage("Removed §b" + entity.getName() + "§r as a friend!");
                }
                else {
                    Thunderhack.friendManager.addFriend(entity.getName());
                    if (this.fm.getValue()) {
                        MiddleClick.mc.player.sendChatMessage("/w " + entity.getName() + " i friended u at ThunderHackPlus");
                    }
                    Command.sendMessage("Added §b" + entity.getName() + "§r as a friend!");
                }
                this.timr.reset();
                return;
            }
        }
        if (this.rocket.getValue() && this.findRocketSlot() != -1 && this.timr.passedMs(500L)) {
            if (!Mouse.isButtonDown(2)) {
                return;
            }
            final int rocketSlot = this.findRocketSlot();
            final int originalSlot = MiddleClick.mc.player.inventory.currentItem;
            if (rocketSlot != -1) {
                MiddleClick.mc.player.inventory.currentItem = rocketSlot;
                MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(rocketSlot));
                MiddleClick.mc.playerController.processRightClick((EntityPlayer)MiddleClick.mc.player, (World)MiddleClick.mc.world, EnumHand.MAIN_HAND);
                MiddleClick.mc.player.inventory.currentItem = originalSlot;
                MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(originalSlot));
                this.timr.reset();
                return;
            }
        }
        if (this.ep.getValue() && this.timr.passedMs(500L) && MiddleClick.mc.currentScreen == null) {
            if (!Mouse.isButtonDown(2)) {
                return;
            }
            if (this.silentPearl.getValue()) {
                final int epSlot = this.findEPSlot();
                final int originalSlot = MiddleClick.mc.player.inventory.currentItem;
                if (epSlot != -1) {
                    MiddleClick.mc.player.inventory.currentItem = epSlot;
                    MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(epSlot));
                    ((IMinecraft)MiddleClick.mc).invokeRightClick();
                    MiddleClick.mc.player.inventory.currentItem = originalSlot;
                    MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(originalSlot));
                }
            }
            else {
                final int epSlot = this.findEPSlot();
                final int originalSlot = MiddleClick.mc.player.inventory.currentItem;
                if (epSlot != -1) {
                    new PearlThread(MiddleClick.mc.player, epSlot, originalSlot, this.swapDelay.getValue()).start();
                }
            }
            this.timr.reset();
        }
    }
    
    @SubscribeEvent
    public void onPostMotion(final EventPostSync event) {
        if (this.xp.getValue()) {
            if (Mouse.isButtonDown(2) && (this.whileEating.getValue() || !(MiddleClick.mc.player.getActiveItemStack().getItem() instanceof ItemFood))) {
                final int slot = InventoryUtil.findItemAtHotbar(Items.EXPERIENCE_BOTTLE);
                if (slot != -1) {
                    final int lastSlot = MiddleClick.mc.player.inventory.currentItem;
                    InventoryUtil.switchTo(slot);
                    MiddleClick.mc.playerController.processRightClick((EntityPlayer)MiddleClick.mc.player, (World)MiddleClick.mc.world, InventoryUtil.getHand(slot));
                    if (this.silent.getValue()) {
                        InventoryUtil.switchTo(lastSlot);
                    }
                }
                else if (this.lastSlot != -1) {
                    InventoryUtil.switchTo(this.lastSlot);
                    this.lastSlot = -1;
                }
            }
            else if (this.lastSlot != -1) {
                InventoryUtil.switchTo(this.lastSlot);
                this.lastSlot = -1;
            }
        }
    }
    
    private int findRocketSlot() {
        int rocketSlot = -1;
        if (MiddleClick.mc.player.getHeldItemMainhand().getItem() == Items.FIREWORKS) {
            rocketSlot = MiddleClick.mc.player.inventory.currentItem;
        }
        if (rocketSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (MiddleClick.mc.player.inventory.getStackInSlot(l).getItem() == Items.FIREWORKS) {
                    rocketSlot = l;
                    break;
                }
            }
        }
        return rocketSlot;
    }
    
    private int findEPSlot() {
        int epSlot = -1;
        if (MiddleClick.mc.player.getHeldItemMainhand().getItem() == Items.ENDER_PEARL) {
            epSlot = MiddleClick.mc.player.inventory.currentItem;
        }
        if (epSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (MiddleClick.mc.player.inventory.getStackInSlot(l).getItem() == Items.ENDER_PEARL) {
                    epSlot = l;
                    break;
                }
            }
        }
        return epSlot;
    }
    
    @SubscribeEvent
    public void onMiddleClick(final ClickMiddleEvent event) {
        if (!this.xp.getValue()) {
            return;
        }
        if (this.pickBlock.getValue()) {
            final int slot = InventoryUtil.findItemAtHotbar(Items.EXPERIENCE_BOTTLE);
            if (slot != -1 && slot != -2 && slot != MiddleClick.mc.player.inventory.currentItem) {
                event.setCanceled(true);
            }
        }
    }
    
    public class PearlThread extends Thread
    {
        public EntityPlayerSP player;
        int epSlot;
        int originalSlot;
        int delay;
        
        public PearlThread(final EntityPlayerSP entityPlayerSP, final int epSlot, final int originalSlot, final int delay) {
            this.player = entityPlayerSP;
            this.epSlot = epSlot;
            this.originalSlot = originalSlot;
            this.delay = delay;
        }
        
        @Override
        public void run() {
            Module.mc.player.inventory.currentItem = this.epSlot;
            Module.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.epSlot));
            try {
                Thread.sleep(this.delay);
            }
            catch (Exception ex) {}
            ((IMinecraft)Module.mc).invokeRightClick();
            try {
                Thread.sleep(this.delay);
            }
            catch (Exception ex2) {}
            Module.mc.player.inventory.currentItem = this.originalSlot;
            Module.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.originalSlot));
            super.run();
        }
    }
}
