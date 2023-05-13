//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.network.*;
import net.minecraft.scoreboard.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import net.minecraftforge.fml.relauncher.*;
import com.google.common.collect.*;
import net.minecraft.world.*;

public class ClanInvite extends Module
{
    private static final Ordering<NetworkPlayerInfo> ENTRY_ORDERING;
    public Setting<Integer> delay;
    public Setting<Mode> b;
    Timer timer;
    ArrayList<String> playersNames;
    int aboba;
    
    public ClanInvite() {
        super("ClanInvite", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043f\u0440\u0438\u0433\u043b\u0430\u0448\u0430\u0435\u0442-\u0432 \u043a\u043b\u0430\u043d", Category.FUNNYGAME);
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)10, (T)1, (T)30));
        this.b = (Setting<Mode>)this.register(new Setting("Donate", (T)Mode.Creativ));
        this.timer = new Timer();
        this.playersNames = new ArrayList<String>();
        this.aboba = 0;
    }
    
    @Override
    public void onUpdate() {
        if (this.timer.passedS(this.delay.getValue())) {
            final NetHandlerPlayClient nethandlerplayclient = ClanInvite.mc.player.connection;
            final List<NetworkPlayerInfo> list = (List<NetworkPlayerInfo>)ClanInvite.ENTRY_ORDERING.sortedCopy((Iterable)nethandlerplayclient.getPlayerInfoMap());
            for (final NetworkPlayerInfo networkplayerinfo : list) {
                if (this.resolveDonate(this.getPlayerName(networkplayerinfo)) < this.resolveMode()) {
                    continue;
                }
                this.playersNames.add(this.getPlayerName(networkplayerinfo));
            }
            if (this.playersNames.size() > 1) {
                final int randomName = (int)Math.floor(Math.random() * this.playersNames.size());
                ClanInvite.mc.player.sendChatMessage("/c invite " + this.playersNames.get(randomName));
                this.playersNames.clear();
                this.timer.reset();
            }
        }
    }
    
    public String getPlayerName2(final NetworkPlayerInfo networkPlayerInfoIn) {
        return (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
    }
    
    public String getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn) {
        return networkPlayerInfoIn.getGameProfile().getName();
    }
    
    public int resolveMode() {
        return this.b.getValue().ordinal();
    }
    
    public int resolveDonate(final String nick) {
        final String donate = "null";
        final NetHandlerPlayClient nethandlerplayclient = ClanInvite.mc.player.connection;
        final List<NetworkPlayerInfo> list = (List<NetworkPlayerInfo>)ClanInvite.ENTRY_ORDERING.sortedCopy((Iterable)nethandlerplayclient.getPlayerInfoMap());
        for (final NetworkPlayerInfo networkplayerinfo : list) {
            if (this.getPlayerName2(networkplayerinfo).contains(nick)) {
                final String raw = this.getPlayerName2(networkplayerinfo);
                if (raw.contains("\u0412\u0438\u043f")) {
                    return 1;
                }
                if (raw.contains("\u041f\u0440\u0435\u043c\u0438\u0443\u043c")) {
                    return 2;
                }
                if (raw.contains("\u041a\u0440\u0435\u0430\u0442\u0438\u0432")) {
                    return 3;
                }
                if (raw.contains("\u0410\u0434\u043c\u0438\u043d")) {
                    return 4;
                }
                if (raw.contains("\u041b\u043e\u0440\u0434")) {
                    return 5;
                }
                if (raw.contains("\u0413\u043b.\u0410\u0434\u043c\u0438\u043d")) {
                    return 6;
                }
                if (raw.contains("\u0421\u043e\u0437\u0434\u0430\u0442\u0435\u043b\u044c")) {
                    return 7;
                }
                if (raw.contains("\u041e\u0441\u043d\u043e\u0432\u0430\u0442\u0435\u043b\u044c")) {
                    return 8;
                }
                if (raw.contains("\u0412\u043b\u0430\u0434\u0435\u043b\u0435\u0446")) {
                    return 9;
                }
                if (raw.contains("\u0426\u0435\u0437\u0430\u0440\u044c")) {
                    return 10;
                }
                if (raw.contains("\u041f\u0440\u0435\u0437\u0438\u0434\u0435\u043d\u0442")) {
                    return 11;
                }
                if (raw.contains("\u0411\u041e\u0413")) {
                    return 12;
                }
                if (raw.contains("\u0412\u043b\u0430\u0441\u0442\u0435\u043b\u0438\u043d")) {
                    return 13;
                }
                if (raw.contains("\u041f\u0420\u0410\u0412\u0418\u0422\u0415\u041b\u042c")) {
                    return 14;
                }
                if (raw.contains("\u0411\u0410\u0420\u041e\u041d")) {
                    return 15;
                }
                if (raw.contains("\u0412\u043b\u0430\u0434\u044b\u043a\u0430")) {
                    return 16;
                }
                if (raw.contains("\u0421\u0443\u043b\u0442\u0430\u043d")) {
                    return 17;
                }
                if (raw.contains("\u041c\u0410\u0416\u041e\u0420")) {
                    return 18;
                }
                if (raw.contains("\u0413\u041e\u0421\u041f\u041e\u0414\u042c")) {
                    return 19;
                }
                if (raw.contains("\u0421\u041f\u041e\u041d\u0421\u041e\u0420")) {
                    return 20;
                }
                continue;
            }
        }
        return 0;
    }
    
    public boolean helper(final String nick) {
        final NetHandlerPlayClient nethandlerplayclient = ClanInvite.mc.player.connection;
        final List<NetworkPlayerInfo> list = (List<NetworkPlayerInfo>)ClanInvite.ENTRY_ORDERING.sortedCopy((Iterable)nethandlerplayclient.getPlayerInfoMap());
        for (final NetworkPlayerInfo networkplayerinfo : list) {
            if (this.getPlayerName2(networkplayerinfo).contains(nick)) {
                final String raw = this.getPlayerName2(networkplayerinfo);
                if (this.check(raw.toLowerCase())) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    public boolean check(final String name) {
        return name.contains("helper") || name.contains("moder") || name.contains("\u0431\u043e\u0433") || name.contains("admin") || name.contains("owner") || name.contains("curator") || name.contains("\u0445\u0435\u043b\u043f\u0435\u0440") || name.contains("\u043c\u043e\u0434\u0435\u0440") || name.contains("\u0430\u0434\u043c\u0438\u043d") || name.contains("\u043a\u0443\u0440\u0430\u0442\u043e\u0440");
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (fullNullCheck()) {
            return;
        }
        if (!((DiscordWebhook)Thunderhack.moduleManager.getModuleByClass((Class)DiscordWebhook.class)).isEnabled()) {
            return;
        }
        if (e.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)e.getPacket();
            if (packet.getType() != ChatType.GAME_INFO && packet.getChatComponent().getFormattedText().contains("\u043f\u0440\u0438\u043d\u044f\u043b \u0432\u0430\u0448\u0435")) {
                ++this.aboba;
                final String finalmsg = "```\u0418\u0433\u0440\u043e\u043a " + ThunderUtils.solvename(packet.getChatComponent().getFormattedText()) + " \u043f\u0440\u0438\u043d\u044f\u043b \u043f\u0440\u0438\u0433\u043b\u0430\u0448\u0435\u043d\u0438\u0435 \u0432 \u043a\u043b\u0430\u043d!\n\u041f\u0440\u0438\u0433\u043b\u0430\u0448\u0435\u043d\u043e \u0437\u0430 \u0441\u0435\u0433\u043e\u0434\u043d\u044f " + this.aboba + "```";
                DiscordWebhook.sendMsg(finalmsg, DiscordWebhook.readurl());
            }
        }
    }
    
    static {
        ENTRY_ORDERING = Ordering.from((Comparator)new PlayerComparator());
    }
    
    private enum Mode
    {
        ALL, 
        Vip, 
        Premium, 
        Creativ, 
        Admin, 
        Lord, 
        glAdmin, 
        Sozdatel, 
        Osnovatel, 
        Vladelec, 
        Cesar, 
        President, 
        Bog, 
        Vlastelin, 
        Pravitel, 
        Baron, 
        Vladika, 
        Sultan, 
        Major, 
        Gospod, 
        Sponsor;
    }
    
    @SideOnly(Side.CLIENT)
    static class PlayerComparator implements Comparator<NetworkPlayerInfo>
    {
        private PlayerComparator() {
        }
        
        @Override
        public int compare(final NetworkPlayerInfo p_compare_1_, final NetworkPlayerInfo p_compare_2_) {
            final ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
            final ScorePlayerTeam scoreplayerteam2 = p_compare_2_.getPlayerTeam();
            return ComparisonChain.start().compareTrueFirst(p_compare_1_.getGameType() != GameType.SPECTATOR, p_compare_2_.getGameType() != GameType.SPECTATOR).compare((Comparable)((scoreplayerteam != null) ? scoreplayerteam.getName() : ""), (Comparable)((scoreplayerteam2 != null) ? scoreplayerteam2.getName() : "")).compare((Comparable)p_compare_1_.getGameProfile().getName(), (Comparable)p_compare_2_.getGameProfile().getName()).result();
        }
    }
}
