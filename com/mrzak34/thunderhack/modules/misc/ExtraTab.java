//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.network.*;
import net.minecraft.scoreboard.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;

public class ExtraTab extends Module
{
    private static ExtraTab INSTANCE;
    public Setting<Integer> size;
    
    public ExtraTab() {
        super("ExtraTab", "\u0440\u0430\u0441\u0448\u0438\u0440\u044f\u0435\u0442 \u0442\u0430\u0431", Category.MISC);
        this.size = (Setting<Integer>)this.register(new Setting("Size", (T)250, (T)1, (T)1000));
        this.setInstance();
    }
    
    public static String getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn) {
        final String string;
        final String name = string = ((networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName()));
        if (Thunderhack.friendManager.isFriend(name)) {
            return ChatFormatting.GREEN + name;
        }
        return name;
    }
    
    public static ExtraTab getINSTANCE() {
        if (ExtraTab.INSTANCE == null) {
            ExtraTab.INSTANCE = new ExtraTab();
        }
        return ExtraTab.INSTANCE;
    }
    
    private void setInstance() {
        ExtraTab.INSTANCE = this;
    }
    
    static {
        ExtraTab.INSTANCE = new ExtraTab();
    }
}
