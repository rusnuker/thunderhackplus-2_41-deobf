//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import java.util.regex.*;
import net.minecraft.client.network.*;
import java.util.function.*;
import com.mojang.authlib.*;
import java.util.stream.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.events.*;
import java.util.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.scoreboard.*;

public class StaffAlert extends Module
{
    private static final Pattern validUserPattern;
    List<String> players;
    List<String> notSpec;
    private final LinkedHashMap<UUID, String> nameMap;
    
    public StaffAlert() {
        super("StaffAlert", "StaffAlert", Category.MISC);
        this.players = new ArrayList<String>();
        this.notSpec = new ArrayList<String>();
        this.nameMap = new LinkedHashMap<UUID, String>();
    }
    
    public static List<String> getOnlinePlayer() {
        return StaffAlert.mc.player.connection.getPlayerInfoMap().stream().map((Function<? super Object, ?>)NetworkPlayerInfo::getGameProfile).map((Function<? super Object, ?>)GameProfile::getName).filter(profileName -> StaffAlert.validUserPattern.matcher(profileName).matches()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public static List<String> getOnlinePlayerD() {
        final List<String> S = new ArrayList<String>();
        for (final NetworkPlayerInfo player : StaffAlert.mc.player.connection.getPlayerInfoMap()) {
            if (StaffAlert.mc.isSingleplayer()) {
                break;
            }
            if (player.getPlayerTeam() == null) {
                break;
            }
            final String prefix = player.getPlayerTeam().getPrefix();
            if (!check(ChatFormatting.stripFormatting(prefix).toLowerCase()) && !player.getGameProfile().getName().toLowerCase().contains("1danil_mansoru1") && !player.getPlayerTeam().getPrefix().contains("YT")) {
                continue;
            }
            final String name = Arrays.asList(player.getPlayerTeam().getMembershipCollection().stream().toArray()).toString().replace("[", "").replace("]", "");
            if (player.getGameType() == GameType.SPECTATOR) {
                S.add(player.getPlayerTeam().getPrefix() + name + ":gm3");
            }
            else {
                S.add(player.getPlayerTeam().getPrefix() + name + ":active");
            }
        }
        return S;
    }
    
    public static boolean check(final String name) {
        return name.contains("helper") || name.contains("moder") || name.contains("admin") || name.contains("owner") || name.contains("curator") || name.contains("\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd") || name.contains("\ufffd\ufffd\ufffd\ufffd\ufffd") || name.contains("\ufffd\ufffd\ufffd\ufffd\ufffd") || name.contains("\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd");
    }
    
    @Override
    public void onDisable() {
        this.nameMap.clear();
    }
    
    @Override
    public void onUpdate() {
        if (StaffAlert.mc.player.ticksExisted % 10 == 0) {
            this.players = this.getVanish();
            this.notSpec = getOnlinePlayerD();
            this.players.sort(String::compareTo);
            this.notSpec.sort(String::compareTo);
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent er) {
        if (this.players.isEmpty() && this.notSpec.isEmpty()) {
            return;
        }
        final List<String> all = new ArrayList<String>();
        all.addAll(this.players);
        all.addAll(this.notSpec);
        int staffY = 11;
        for (final String player : all) {
            final String a = player.split(":")[1].equalsIgnoreCase("vanish") ? (ChatFormatting.RED + "VANISH") : (player.split(":")[1].equalsIgnoreCase("gm3") ? (ChatFormatting.RED + "VANISH " + ChatFormatting.YELLOW + "(GM 3)") : (ChatFormatting.GREEN + "ACTIVE"));
            FontRender.drawString6(player.split(":")[0] + " " + a, 13.0f, (float)(204 + staffY), -1, false);
            staffY += 13;
        }
    }
    
    public List<String> getVanish() {
        final List<String> list = new ArrayList<String>();
        for (final ScorePlayerTeam s : StaffAlert.mc.world.getScoreboard().getTeams()) {
            if (s.getPrefix().length() != 0) {
                if (StaffAlert.mc.isSingleplayer()) {
                    continue;
                }
                final String name = Arrays.asList(s.getMembershipCollection().stream().toArray()).toString().replace("[", "").replace("]", "");
                if (getOnlinePlayer().contains(name)) {
                    continue;
                }
                if (name.isEmpty()) {
                    continue;
                }
                if (!check(s.getPrefix().toLowerCase()) && !name.toLowerCase().contains("1danil_mansoru1") && !s.getPrefix().contains("YT")) {
                    continue;
                }
                list.add(s.getPrefix() + name + ":vanish");
            }
        }
        return list;
    }
    
    static {
        validUserPattern = Pattern.compile("^\\w{3,16}$");
    }
}
