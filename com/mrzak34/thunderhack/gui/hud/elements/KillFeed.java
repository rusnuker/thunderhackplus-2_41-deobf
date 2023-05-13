//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import java.util.concurrent.*;
import com.mrzak34.thunderhack.modules.funnygame.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.renderer.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.client.network.*;
import java.util.*;

public class KillFeed extends HudElement
{
    List<String> players;
    
    public KillFeed() {
        super("KillFeed", "\u0441\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430 \u0443\u0431\u0438\u0439\u0441\u0442\u0432", 100, 100);
        this.players = new CopyOnWriteArrayList<String>();
    }
    
    @SubscribeEvent
    public void onPlayerDeath(final DeathEvent e) {
        if (Aura.target != null && Aura.target == e.player) {
            this.players.add(getFullName(e.player.getName()));
            return;
        }
        if (C4Aura.target != null && C4Aura.target == e.player) {
            this.players.add(getFullName(e.player.getName()));
            return;
        }
        if (Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class).target != null && Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class).target == e.player) {
            this.players.add(getFullName(e.player.getName()));
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        int y_offset1 = 11;
        float scale_x = 50.0f;
        for (final String player : this.players) {
            if (player != null && FontRender.getStringWidth6("EZ - " + player) > scale_x) {
                scale_x = (float)FontRender.getStringWidth6("EZ - " + player);
            }
            y_offset1 += 13;
        }
        RenderUtil.drawBlurredShadow(this.getPosX(), this.getPosY(), scale_x + 20.0f, (float)(20 + y_offset1), 20, this.shadowColor.getValue().getColorObject());
        RoundedShader.drawRound(this.getPosX(), this.getPosY(), scale_x + 20.0f, (float)(20 + y_offset1), 7.0f, this.color2.getValue().getColorObject());
        FontRender.drawCentString6("KillFeed [" + this.players.size() + "]", this.getPosX() + (scale_x + 20.0f) / 2.0f, this.getPosY() + 5.0f, this.textColor.getValue().getColor());
        RoundedShader.drawRound(this.getPosX() + 2.0f, this.getPosY() + 13.0f, scale_x + 16.0f, 1.0f, 0.5f, this.color3.getValue().getColorObject());
        int y_offset2 = 11;
        for (final String player2 : this.players) {
            GlStateManager.pushMatrix();
            GlStateManager.resetColor();
            FontRender.drawString6(ChatFormatting.RED + "EZ - " + ChatFormatting.RESET + player2, this.getPosX() + 5.0f, this.getPosY() + 18.0f + y_offset2, -1, false);
            GlStateManager.resetColor();
            GlStateManager.popMatrix();
            y_offset2 += 13;
        }
    }
    
    public static String getFullName(final String raw) {
        for (final NetworkPlayerInfo player : KillFeed.mc.player.connection.getPlayerInfoMap()) {
            if (KillFeed.mc.isSingleplayer()) {
                break;
            }
            if (player.getPlayerTeam() == null) {
                break;
            }
            final String name = Arrays.asList(player.getPlayerTeam().getMembershipCollection().stream().toArray()).toString().replace("[", "").replace("]", "");
            if (name.contains(raw)) {
                return player.getPlayerTeam().getPrefix() + name;
            }
        }
        return "null";
    }
}
