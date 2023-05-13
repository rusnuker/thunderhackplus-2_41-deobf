//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.command.*;
import java.nio.charset.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AntiBot extends Module
{
    public static ArrayList<EntityPlayer> bots;
    public Setting<Boolean> remove;
    public Setting<Boolean> onlyAura;
    private final Setting<Mode> mode;
    public Setting<Integer> checkticks;
    private final Timer timer;
    private int botsNumber;
    private int ticks;
    
    public AntiBot() {
        super("AntiBot", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u0431\u043e\u0442\u043e\u0432", Category.COMBAT);
        this.remove = (Setting<Boolean>)this.register(new Setting("Remove", (T)false));
        this.onlyAura = (Setting<Boolean>)this.register(new Setting("OnlyAura", (T)true));
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.MotionCheck));
        this.checkticks = (Setting<Integer>)this.register(new Setting("checkTicks", (T)3, (T)0, (T)10, v -> this.mode.getValue() == Mode.MotionCheck));
        this.timer = new Timer();
        this.botsNumber = 0;
        this.ticks = 0;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPre(final EventSync e) {
        if (!this.onlyAura.getValue()) {
            for (final EntityPlayer player : AntiBot.mc.world.playerEntities) {
                if (this.mode.getValue() == Mode.MotionCheck) {
                    if (player == null) {
                        continue;
                    }
                    final double speed = (player.posX - player.prevPosX) * (player.posX - player.prevPosX) + (player.posZ - player.prevPosZ) * (player.posZ - player.prevPosZ);
                    if (player == AntiBot.mc.player || speed <= 0.5 || AntiBot.mc.player.getDistanceSq((Entity)player) > ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).attackDistance.getValue() * ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).attackDistance.getValue() || AntiBot.bots.contains(player) || AntiBot.bots.contains(player)) {
                        continue;
                    }
                    Command.sendMessage(player.getName() + " is a bot!");
                    ++this.botsNumber;
                    AntiBot.bots.add(player);
                }
                else {
                    if (!player.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName()).getBytes(StandardCharsets.UTF_8))) && player instanceof EntityOtherPlayerMP && !AntiBot.bots.contains(player)) {
                        Command.sendMessage(player.getName() + " is a bot!");
                        ++this.botsNumber;
                        AntiBot.bots.add(player);
                    }
                    if (player.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName()).getBytes(StandardCharsets.UTF_8))) || !player.isInvisible() || !(player instanceof EntityOtherPlayerMP) || AntiBot.bots.contains(player)) {
                        continue;
                    }
                    Command.sendMessage(player.getName() + " is a bot!");
                    ++this.botsNumber;
                    AntiBot.bots.add(player);
                }
            }
        }
        else if (Aura.target != null && Aura.target instanceof EntityPlayer) {
            if (this.mode.getValue() == Mode.MotionCheck) {
                final double speed2 = (Aura.target.posX - Aura.target.prevPosX) * (Aura.target.posX - Aura.target.prevPosX) + (Aura.target.posZ - Aura.target.prevPosZ) * (Aura.target.posZ - Aura.target.prevPosZ);
                if (speed2 > 0.5 && !AntiBot.bots.contains(Aura.target)) {
                    if (this.ticks >= this.checkticks.getValue()) {
                        Command.sendMessage(Aura.target.getName() + " is a bot!");
                        ++this.botsNumber;
                        AntiBot.bots.add((EntityPlayer)Aura.target);
                    }
                    ++this.ticks;
                }
            }
            else {
                if (!Aura.target.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + Aura.target.getName()).getBytes(StandardCharsets.UTF_8))) && Aura.target instanceof EntityOtherPlayerMP) {
                    Command.sendMessage(Aura.target.getName() + " is a bot!");
                    ++this.botsNumber;
                    AntiBot.bots.add((EntityPlayer)Aura.target);
                }
                if (!Aura.target.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + Aura.target.getName()).getBytes(StandardCharsets.UTF_8))) && Aura.target.isInvisible() && Aura.target instanceof EntityOtherPlayerMP) {
                    Command.sendMessage(Aura.target.getName() + " is a bot!");
                    ++this.botsNumber;
                    AntiBot.bots.add((EntityPlayer)Aura.target);
                }
            }
        }
        for (final EntityPlayer bot : AntiBot.bots) {
            if (this.remove.getValue()) {
                try {
                    AntiBot.mc.world.removeEntity((Entity)bot);
                }
                catch (Exception ex) {}
            }
        }
        if (this.timer.passedMs(10000L)) {
            AntiBot.bots.clear();
            this.botsNumber = 0;
            this.timer.reset();
            this.ticks = 0;
        }
    }
    
    @Override
    public String getDisplayInfo() {
        return String.valueOf(this.botsNumber);
    }
    
    static {
        AntiBot.bots = new ArrayList<EntityPlayer>();
    }
    
    public enum Mode
    {
        UUIDCheck, 
        MotionCheck;
    }
}
