//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.nbt.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.notification.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.multiplayer.*;
import java.util.*;
import java.io.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.command.*;

public class StashLogger extends Module
{
    public Setting<Boolean> chests;
    public Setting<Integer> chestsAmount;
    public Setting<Boolean> shulker;
    public Setting<Integer> shulkersAmount;
    public Setting<Boolean> saveCoords;
    
    public StashLogger() {
        super("StashFinder", "\u0438\u0449\u0435\u0442 \u0441\u0442\u0435\u0448\u0438 \u0432 \u0437\u043e\u043d\u0435-\u043f\u0440\u043e\u0433\u0440\u0443\u0437\u043a\u0438", Category.MISC);
        this.chests = (Setting<Boolean>)this.register(new Setting("Chests", (T)true));
        this.chestsAmount = (Setting<Integer>)this.register(new Setting("ChestsAmount", (T)5, (T)1, (T)100));
        this.shulker = (Setting<Boolean>)this.register(new Setting("Shulkers", (T)true));
        this.shulkersAmount = (Setting<Integer>)this.register(new Setting("ShulkersAmount", (T)5, (T)1, (T)100));
        this.saveCoords = (Setting<Boolean>)this.register(new Setting("SaveCoords", (T)true));
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getPacket() instanceof SPacketChunkData) {
            final SPacketChunkData l_Packet = (SPacketChunkData)event.getPacket();
            int l_ChestsCount = 0;
            int shulkers = 0;
            for (final NBTTagCompound l_Tag : l_Packet.getTileEntityTags()) {
                final String l_Id = l_Tag.getString("id");
                if (l_Id.equals("minecraft:chest") && this.chests.getValue()) {
                    ++l_ChestsCount;
                }
                else {
                    if (!l_Id.equals("minecraft:shulker_box")) {
                        continue;
                    }
                    if (!this.shulker.getValue()) {
                        continue;
                    }
                    ++shulkers;
                }
            }
            if (l_ChestsCount >= this.chestsAmount.getValue()) {
                this.SendMessage(String.format("%s chests located at X: %s, Z: %s", l_ChestsCount, l_Packet.getChunkX() * 16, l_Packet.getChunkZ() * 16), true);
                if (((NotificationManager)Thunderhack.moduleManager.getModuleByClass((Class)NotificationManager.class)).isOn()) {
                    NotificationManager.publicity(String.format("%s chests located at X: %s, Z: %s", l_ChestsCount, l_Packet.getChunkX() * 16, l_Packet.getChunkZ() * 16), 5, Notification.Type.SUCCESS);
                }
            }
            if (shulkers >= this.shulkersAmount.getValue()) {
                this.SendMessage(String.format("%s shulker boxes at X: %s, Z: %s", shulkers, l_Packet.getChunkX() * 16, l_Packet.getChunkZ() * 16), true);
                if (((NotificationManager)Thunderhack.moduleManager.getModuleByClass((Class)NotificationManager.class)).isOn()) {
                    NotificationManager.publicity(String.format("%s shulker boxes at X: %s, Z: %s", shulkers, l_Packet.getChunkX() * 16, l_Packet.getChunkZ() * 16), 5, Notification.Type.SUCCESS);
                }
            }
        }
    }
    
    private void SendMessage(final String message, final boolean save) {
        final String server = StashLogger.mc.isSingleplayer() ? "SINGLEPLAYER" : Objects.requireNonNull(StashLogger.mc.getCurrentServerData()).serverIP;
        if (this.saveCoords.getValue() && save) {
            try {
                final FileWriter writer = new FileWriter("ThunderHack/misc/stashlogger.txt", true);
                writer.write("[" + server + "]: " + message + "\n");
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        StashLogger.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f));
        Command.sendMessage(ChatFormatting.GREEN + message);
    }
}
