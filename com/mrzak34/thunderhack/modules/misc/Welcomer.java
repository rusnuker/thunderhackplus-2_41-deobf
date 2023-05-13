//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.network.*;
import java.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Welcomer extends Module
{
    public final Setting<Boolean> serverside;
    public final Setting<Boolean> global;
    private final String[] bb;
    private final String[] qq;
    private final Timer timer;
    private String string1;
    private final LinkedHashMap<UUID, String> nameMap;
    
    public Welcomer() {
        super("Welcomer", "\u041f\u0440\u0438\u0432\u0435\u0442\u0441\u0442\u0432\u0443\u0435\u0442 \u0438\u0433\u0440\u043e\u043a\u043e\u0432", Category.MISC);
        this.serverside = (Setting<Boolean>)this.register(new Setting("ServerSide", (T)false));
        this.global = (Setting<Boolean>)this.register(new Setting("Global", (T)false, v -> this.serverside.getValue()));
        this.bb = new String[] { "See you later, ", "Catch ya later, ", "See you next time, ", "Farewell, ", "Bye, ", "Good bye, ", "Later, " };
        this.qq = new String[] { "Good to see you, ", "Greetings, ", "Hello, ", "Howdy, ", "Hey, ", "Good evening, ", "Welcome to SERVERIP1D5A9E, " };
        this.timer = new Timer();
        this.string1 = "server";
        this.nameMap = new LinkedHashMap<UUID, String>();
    }
    
    @Override
    public void onDisable() {
        this.nameMap.clear();
    }
    
    @Override
    public void onUpdate() {
        if (this.timer.passedMs(15000L)) {
            for (final NetworkPlayerInfo b : Welcomer.mc.player.connection.getPlayerInfoMap()) {
                if (!this.nameMap.containsKey(b.getGameProfile().getId())) {
                    this.nameMap.put(b.getGameProfile().getId(), b.getGameProfile().getName());
                }
            }
            this.timer.reset();
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (e.getPacket() instanceof SPacketPlayerListItem) {
            final SPacketPlayerListItem pck = (SPacketPlayerListItem)e.getPacket();
            final int n = (int)Math.floor(Math.random() * this.bb.length);
            final int n2 = (int)Math.floor(Math.random() * this.qq.length);
            if (Welcomer.mc.getCurrentServerData() != null) {
                this.string1 = this.qq[n2].replace("SERVERIP1D5A9E", Welcomer.mc.getCurrentServerData().serverIP);
            }
            else {
                this.string1 = "server";
            }
            for (final SPacketPlayerListItem.AddPlayerData item : pck.getEntries()) {
                switch (pck.getAction()) {
                    case REMOVE_PLAYER: {
                        if (!this.nameMap.containsKey(item.getProfile().getId())) {
                            return;
                        }
                        if (this.antiBot(this.nameMap.get(item.getProfile().getId()))) {
                            return;
                        }
                        if (this.serverside.getValue()) {
                            Welcomer.mc.player.sendChatMessage((this.global.getValue() ? "!" : "") + this.bb[n] + this.nameMap.get(item.getProfile().getId()));
                        }
                        else {
                            Command.sendMessage(this.bb[n] + this.nameMap.get(item.getProfile().getId()));
                        }
                        this.nameMap.remove(item.getProfile().getId());
                        continue;
                    }
                    case ADD_PLAYER: {
                        if (this.antiBot(item.getProfile().getName())) {
                            return;
                        }
                        if (this.serverside.getValue()) {
                            Welcomer.mc.player.sendChatMessage((this.global.getValue() ? "!" : "") + this.string1 + item.getProfile().getName());
                        }
                        else {
                            Command.sendMessage(this.string1 + item.getProfile().getName());
                        }
                        this.nameMap.put(item.getProfile().getId(), item.getProfile().getName());
                        continue;
                    }
                    default: {}
                }
            }
        }
    }
    
    public boolean antiBot(final String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (Character.UnicodeBlock.of(s.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return true;
            }
        }
        return false;
    }
}
