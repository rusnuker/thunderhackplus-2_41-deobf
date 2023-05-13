//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class NoVoid extends Module
{
    boolean aboveVoid;
    private final Timer voidTimer;
    
    public NoVoid() {
        super("NoVoid", "\u0440\u0443\u0431\u0435\u0440\u0431\u0435\u043d\u0434\u0438\u0442 \u0435\u0441\u043b\u0438 \u0442\u044b-\u0443\u043f\u0430\u043b \u0432 \u043f\u0443\u0441\u0442\u043e\u0442\u0443", Module.Category.MOVEMENT);
        this.aboveVoid = true;
        this.voidTimer = new Timer();
    }
    
    public void onUpdate() {
        if (NoVoid.mc.player == null || NoVoid.mc.world == null) {
            return;
        }
        if (PlayerUtils.isPlayerAboveVoid() && NoVoid.mc.player.posY <= 1.0) {
            if (this.aboveVoid && this.voidTimer.passedMs(1000L)) {
                this.aboveVoid = false;
                NoVoid.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(NoVoid.mc.player.posX, NoVoid.mc.player.posY + 0.1, NoVoid.mc.player.posZ, false));
            }
        }
        else {
            this.aboveVoid = true;
        }
    }
    
    @SubscribeEvent
    public void onReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook && !(NoVoid.mc.currentScreen instanceof GuiDownloadTerrain)) {
            final SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
            ((ISPacketPlayerPosLook)packet).setYaw(NoVoid.mc.player.rotationYaw);
            ((ISPacketPlayerPosLook)packet).setPitch(NoVoid.mc.player.rotationPitch);
            packet.getFlags().remove(SPacketPlayerPosLook.EnumFlags.X_ROT);
            packet.getFlags().remove(SPacketPlayerPosLook.EnumFlags.Y_ROT);
        }
    }
}
