//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoSoup extends Module
{
    public Setting<Float> thealth;
    
    public AutoSoup() {
        super("AutoSoup", "\u0410\u0432\u0442\u043e\u0441\u0443\u043f \u0434\u043b\u044f-\u041c\u0438\u0433\u043e\u0441\u043c\u0441\u0438", Category.MISC);
        this.thealth = (Setting<Float>)this.register(new Setting("TriggerHealth", (T)7.0f, (T)1.0f, (T)20.0f));
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final EventSync event) {
        if (AutoSoup.mc.player.getHealth() <= this.thealth.getValue()) {
            final int soupslot = InventoryUtil.findSoupAtHotbar();
            final int currentslot = AutoSoup.mc.player.inventory.currentItem;
            if (soupslot != -1) {
                AutoSoup.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(soupslot));
                AutoSoup.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                InventoryUtil.switchToHotbarSlot(currentslot, true);
            }
        }
    }
}
