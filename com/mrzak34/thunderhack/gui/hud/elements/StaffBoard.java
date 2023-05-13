//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import java.util.regex.*;
import com.mrzak34.thunderhack.setting.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.network.*;
import java.util.function.*;
import com.mojang.authlib.*;
import java.util.stream.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.command.commands.*;
import net.minecraft.world.*;
import net.minecraft.scoreboard.*;
import com.mrzak34.thunderhack.events.*;
import java.util.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class StaffBoard extends HudElement
{
    private static final Pattern validUserPattern;
    public final Setting<ColorSetting> shadowColor;
    public final Setting<ColorSetting> color2;
    public final Setting<ColorSetting> color3;
    public final Setting<ColorSetting> textColor;
    private final Setting<Float> psize;
    List<String> players;
    List<String> notSpec;
    private final LinkedHashMap<UUID, String> nameMap;
    
    public StaffBoard() {
        super("StaffBoard", "StaffBoard", 50, 50);
        this.shadowColor = (Setting<ColorSetting>)this.register(new Setting("ShadowColor", (T)new ColorSetting(-15724528)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-15724528)));
        this.color3 = (Setting<ColorSetting>)this.register(new Setting("Color2", (T)new ColorSetting(-979657829)));
        this.textColor = (Setting<ColorSetting>)this.register(new Setting("TextColor", (T)new ColorSetting(12500670)));
        this.psize = (Setting<Float>)this.register(new Setting("Size", (T)1.0f, (T)0.1f, (T)2.0f));
        this.players = new ArrayList<String>();
        this.notSpec = new ArrayList<String>();
        this.nameMap = new LinkedHashMap<UUID, String>();
    }
    
    public static void size(final double width, final double height, final double animation) {
        GL11.glTranslated(width, height, 0.0);
        GL11.glScaled(animation, animation, 1.0);
        GL11.glTranslated(-width, -height, 0.0);
    }
    
    public static List<String> getOnlinePlayer() {
        return StaffBoard.mc.player.connection.getPlayerInfoMap().stream().map((Function<? super Object, ?>)NetworkPlayerInfo::getGameProfile).map((Function<? super Object, ?>)GameProfile::getName).filter(profileName -> StaffBoard.validUserPattern.matcher(profileName).matches()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public static List<String> getOnlinePlayerD() {
        final List<String> S = new ArrayList<String>();
        for (final NetworkPlayerInfo player : StaffBoard.mc.player.connection.getPlayerInfoMap()) {
            if (StaffBoard.mc.isSingleplayer()) {
                break;
            }
            if (player.getPlayerTeam() == null) {
                break;
            }
            final String prefix = player.getPlayerTeam().getPrefix();
            if (!check(ChatFormatting.stripFormatting(prefix).toLowerCase()) && !StaffCommand.staffNames.toString().toLowerCase().contains(player.getGameProfile().getName().toLowerCase()) && !player.getGameProfile().getName().toLowerCase().contains("1danil_mansoru1") && !player.getPlayerTeam().getPrefix().contains("YT") && (!player.getPlayerTeam().getPrefix().contains("Y") || !player.getPlayerTeam().getPrefix().contains("T"))) {
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
    
    public List<String> getVanish() {
        final List<String> list = new ArrayList<String>();
        for (final ScorePlayerTeam s : StaffBoard.mc.world.getScoreboard().getTeams()) {
            if (s.getPrefix().length() != 0) {
                if (StaffBoard.mc.isSingleplayer()) {
                    continue;
                }
                final String name = Arrays.asList(s.getMembershipCollection().stream().toArray()).toString().replace("[", "").replace("]", "");
                if (getOnlinePlayer().contains(name)) {
                    continue;
                }
                if (name.isEmpty()) {
                    continue;
                }
                if ((!StaffCommand.staffNames.toString().toLowerCase().contains(name.toLowerCase()) || !check(s.getPrefix().toLowerCase())) && !check(s.getPrefix().toLowerCase()) && !name.toLowerCase().contains("1danil_mansoru1") && !s.getPrefix().contains("YT") && (!s.getPrefix().contains("Y") || !s.getPrefix().contains("T"))) {
                    continue;
                }
                list.add(s.getPrefix() + name + ":vanish");
            }
        }
        return list;
    }
    
    public static boolean check(final String name) {
        return name.contains("helper") || name.contains("moder") || name.contains("admin") || name.contains("owner") || name.contains("curator") || name.contains("\u043a\u0443\u0440\u0430\u0442\u043e\u0440") || name.contains("\u043c\u043e\u0434\u0435\u0440") || name.contains("\u0430\u0434\u043c\u0438\u043d") || name.contains("\u0445\u0435\u043b\u043f\u0435\u0440");
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        int y_offset1 = 11;
        final List<String> all = new ArrayList<String>();
        all.addAll(this.players);
        all.addAll(this.notSpec);
        float scale_x = 50.0f;
        for (final String player : all) {
            if (player != null) {
                final String a = player.split(":")[0] + " " + (player.split(":")[1].equalsIgnoreCase("vanish") ? (ChatFormatting.RED + "VANISH") : (player.split(":")[1].equalsIgnoreCase("gm3") ? (ChatFormatting.RED + "VANISH " + ChatFormatting.YELLOW + "(NEAR!)") : (ChatFormatting.GREEN + "ACTIVE")));
                if (FontRender.getStringWidth6(a) > scale_x) {
                    scale_x = (float)FontRender.getStringWidth6(a);
                }
            }
            y_offset1 += 13;
        }
        GlStateManager.pushMatrix();
        size(this.getPosX() + 50.0f, this.getPosY() + (20 + y_offset1) / 2.0f, this.psize.getValue());
        RenderUtil.drawBlurredShadow(this.getPosX(), this.getPosY(), scale_x + 20.0f, (float)(20 + y_offset1), 20, this.shadowColor.getValue().getColorObject());
        RoundedShader.drawRound(this.getPosX(), this.getPosY(), scale_x + 20.0f, (float)(20 + y_offset1), 7.0f, this.color2.getValue().getColorObject());
        FontRender.drawCentString6("StaffBoard", this.getPosX() + (scale_x + 20.0f) / 2.0f, this.getPosY() + 5.0f, this.textColor.getValue().getColor());
        RoundedShader.drawRound(this.getPosX() + 2.0f, this.getPosY() + 13.0f, scale_x + 16.0f, 1.0f, 0.5f, this.color3.getValue().getColorObject());
        int y_offset2 = 11;
        for (final String player2 : all) {
            GlStateManager.pushMatrix();
            GlStateManager.resetColor();
            final String a2 = player2.split(":")[0] + " " + (player2.split(":")[1].equalsIgnoreCase("vanish") ? (ChatFormatting.RED + "VANISH") : (player2.split(":")[1].equalsIgnoreCase("gm3") ? (ChatFormatting.RED + "VANISH " + ChatFormatting.YELLOW + "(NEAR!)") : (ChatFormatting.GREEN + "ACTIVE")));
            FontRender.drawString6(a2, this.getPosX() + 5.0f, this.getPosY() + 18.0f + y_offset2, -1, false);
            GlStateManager.resetColor();
            GlStateManager.popMatrix();
            y_offset2 += 13;
        }
        GlStateManager.popMatrix();
    }
    
    @Override
    public void onDisable() {
        this.nameMap.clear();
    }
    
    @Override
    public void onUpdate() {
        if (StaffBoard.mc.player.ticksExisted % 10 == 0) {
            this.players = this.getVanish();
            this.notSpec = getOnlinePlayerD();
            this.players.sort(String::compareTo);
            this.notSpec.sort(String::compareTo);
        }
    }
    
    static {
        validUserPattern = Pattern.compile("^\\w{3,16}$");
    }
}
