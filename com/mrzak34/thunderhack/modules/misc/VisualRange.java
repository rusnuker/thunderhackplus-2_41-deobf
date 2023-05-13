//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.notification.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class VisualRange extends Module
{
    private static final ArrayList<String> entities;
    public Setting<Boolean> leave;
    public Setting<Boolean> enter;
    public Setting<Boolean> friends;
    public Setting<Boolean> soundpl;
    public Setting<Boolean> funnyGame;
    public Setting<mode> Mode;
    
    public VisualRange() {
        super("VisualRange", "\u043e\u043f\u043e\u0432\u0435\u0449\u0430\u0435\u0442 \u043e \u0438\u0433\u0440\u043e\u043a\u0430\u0445-\u0432 \u0437\u043e\u043d\u0435 \u043f\u0440\u043e\u0433\u0440\u0443\u0437\u043a\u0438", Category.MISC);
        this.leave = (Setting<Boolean>)this.register(new Setting("Leave", (T)true));
        this.enter = (Setting<Boolean>)this.register(new Setting("Enter", (T)true));
        this.friends = (Setting<Boolean>)this.register(new Setting("Friends", (T)true));
        this.soundpl = (Setting<Boolean>)this.register(new Setting("Sound", (T)true));
        this.funnyGame = (Setting<Boolean>)this.register(new Setting("FunnyGame", (T)false));
        this.Mode = (Setting<mode>)this.register(new Setting("Mode", (T)mode.Notification));
    }
    
    @SubscribeEvent
    public void onEntityAdded(final EntityAddedEvent event) {
        if (this.funnyGame.getValue() && !VisualRange.mc.isSingleplayer() && Objects.equals(Minecraft.getMinecraft().getCurrentServerData().serverIP, "mcfunny.su")) {
            return;
        }
        if (!this.isValid(event.entity)) {
            return;
        }
        if (!VisualRange.entities.contains(event.entity.getName())) {
            VisualRange.entities.add(event.entity.getName());
            if (this.enter.getValue()) {
                this.notify(event.entity, true);
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityRemoved(final EntityRemovedEvent event) {
        if (!this.isValid(event.entity)) {
            return;
        }
        if (VisualRange.entities.contains(event.entity.getName())) {
            VisualRange.entities.remove(event.entity.getName());
            if (this.leave.getValue()) {
                this.notify(event.entity, false);
            }
        }
    }
    
    public void notify(final Entity entity, final boolean enter) {
        String message = "";
        if (Thunderhack.friendManager.isFriend(entity.getName())) {
            message = ChatFormatting.AQUA + entity.getName();
        }
        else {
            message = ChatFormatting.GRAY + entity.getName();
        }
        if (enter) {
            message = message + ChatFormatting.GREEN + " was found!";
        }
        else {
            message = message + ChatFormatting.RED + " left!";
        }
        if (this.Mode.getValue() == mode.Chat) {
            Command.sendMessage(message);
        }
        if (this.Mode.getValue() == mode.Notification) {
            NotificationManager.publicity(message, 2, Notification.Type.WARNING);
        }
        if (this.soundpl.getValue()) {
            try {
                if (enter) {
                    VisualRange.mc.world.playSound(PlayerUtils.getPlayerPos(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.AMBIENT, 150.0f, 10.0f, true);
                }
                else {
                    VisualRange.mc.world.playSound(PlayerUtils.getPlayerPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 150.0f, 10.0f, true);
                }
            }
            catch (Exception ex) {}
        }
    }
    
    public boolean isValid(final Entity entity) {
        return VisualRange.mc.player != null && entity instanceof EntityPlayer && !entity.isEntityEqual((Entity)VisualRange.mc.player) && (!Thunderhack.friendManager.isFriend(entity.getName()) || this.friends.getValue()) && !entity.getName().equals(VisualRange.mc.player.getName()) && entity.getEntityId() != -100;
    }
    
    static {
        entities = new ArrayList<String>();
    }
    
    public enum mode
    {
        Chat, 
        Notification;
    }
}
