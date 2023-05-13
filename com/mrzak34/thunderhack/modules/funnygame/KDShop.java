//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.gui.inventory.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.command.*;

public class KDShop extends Module
{
    public static GuiScreen lastGui;
    public static boolean cancelRender;
    public Setting<SubBind> breakBind;
    boolean closeInv;
    
    public KDShop() {
        super("KDShop", "\u041d\u0435 \u0432\u0441\u0435\u0433\u0434\u0430 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442-\u043d\u043e \u0434\u0430 \u043b\u0430\u0434\u043d\u043e", Category.FUNNYGAME);
        this.breakBind = (Setting<SubBind>)this.register(new Setting("Open", (T)new SubBind(23)));
    }
    
    @Override
    public void onUpdate() {
        if (KDShop.mc.currentScreen instanceof GuiContainer) {
            KDShop.lastGui = KDShop.mc.currentScreen;
        }
        if (PlayerUtils.isKeyDown(this.breakBind.getValue().getKey())) {
            KDShop.mc.displayGuiScreen(KDShop.lastGui);
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketCloseWindow) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketCloseWindow) {
            Command.sendMessage("fuck");
        }
    }
    
    static {
        KDShop.cancelRender = false;
    }
}
