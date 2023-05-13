//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.client.entity.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class GuiMove extends Module
{
    public Setting<Boolean> clickBypass;
    public static boolean pause;
    
    public GuiMove() {
        super("GuiMove", "GuiMove", Module.Category.MOVEMENT);
        this.clickBypass = (Setting<Boolean>)this.register(new Setting("strict", (T)false));
    }
    
    public void onUpdate() {
        if (GuiMove.mc.currentScreen != null && !(GuiMove.mc.currentScreen instanceof GuiChat)) {
            GuiMove.mc.player.setSprinting(true);
            if (Keyboard.isKeyDown(200)) {
                final EntityPlayerSP player = GuiMove.mc.player;
                player.rotationPitch -= 5.0f;
            }
            if (Keyboard.isKeyDown(208)) {
                final EntityPlayerSP player2 = GuiMove.mc.player;
                player2.rotationPitch += 5.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                final EntityPlayerSP player3 = GuiMove.mc.player;
                player3.rotationYaw += 5.0f;
            }
            if (Keyboard.isKeyDown(203)) {
                final EntityPlayerSP player4 = GuiMove.mc.player;
                player4.rotationYaw -= 5.0f;
            }
            if (GuiMove.mc.player.rotationPitch > 90.0f) {
                GuiMove.mc.player.rotationPitch = 90.0f;
            }
            if (GuiMove.mc.player.rotationPitch < -90.0f) {
                GuiMove.mc.player.rotationPitch = -90.0f;
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send e) {
        if (GuiMove.pause) {
            GuiMove.pause = false;
            return;
        }
        if (e.getPacket() instanceof CPacketClickWindow && this.clickBypass.getValue() && GuiMove.mc.player.onGround && MovementUtil.isMoving() && GuiMove.mc.world.getCollisionBoxes((Entity)GuiMove.mc.player, GuiMove.mc.player.getEntityBoundingBox().offset(0.0, 0.0656, 0.0)).isEmpty()) {
            if (GuiMove.mc.player.isSprinting()) {
                GuiMove.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)GuiMove.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            GuiMove.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(GuiMove.mc.player.posX, GuiMove.mc.player.posY + 0.0656, GuiMove.mc.player.posZ, false));
        }
    }
    
    @SubscribeEvent
    public void onPacketSendPost(final PacketEvent.SendPost e) {
        if (e.getPacket() instanceof CPacketClickWindow && GuiMove.mc.player.isSprinting()) {
            GuiMove.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)GuiMove.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
    }
    
    static {
        GuiMove.pause = false;
    }
}
