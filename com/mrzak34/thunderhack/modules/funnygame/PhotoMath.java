//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;
import org.apache.commons.lang3.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class PhotoMath extends Module
{
    public Setting<Boolean> spam;
    
    public PhotoMath() {
        super("PhotoMath", "\u0420\u0435\u0448\u0430\u0435\u0442 \u0447\u0430\u0442 \u0438\u0433\u0440\u0443 \u0430\u0432\u0442\u043e\u043c\u0430\u0442\u043e\u043c", Category.FUNNYGAME);
        this.spam = (Setting<Boolean>)this.register(new Setting("Spam", (T)false));
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)event.getPacket();
            if (packet.getType() != ChatType.GAME_INFO && packet.getChatComponent().getFormattedText().contains("\u0420\u0435\u0448\u0438\u0442\u0435: ") && Objects.equals(ThunderUtils.solvename(packet.getChatComponent().getFormattedText()), "err")) {
                final int solve = Integer.parseInt(StringUtils.substringBetween(packet.getChatComponent().getUnformattedText(), "\u0420\u0435\u0448\u0438\u0442\u0435: ", " + ")) + Integer.parseInt(StringUtils.substringBetween(packet.getChatComponent().getUnformattedText(), " + ", " \u043a\u0442\u043e \u043f\u0435\u0440\u0432\u044b\u0439"));
                for (int i = 0; i < (this.spam.getValue() ? 9 : 1); ++i) {
                    PhotoMath.mc.player.sendChatMessage(String.valueOf(solve));
                }
            }
        }
    }
}
