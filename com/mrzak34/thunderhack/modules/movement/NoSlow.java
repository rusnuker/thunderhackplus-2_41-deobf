//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraftforge.client.event.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.client.entity.*;

public class NoSlow extends Module
{
    public Setting<Integer> speed;
    private final Setting<mode> Mode;
    
    public NoSlow() {
        super("NoSlow", "NoSlow", Module.Category.MOVEMENT);
        this.speed = (Setting<Integer>)this.register(new Setting("Speed", (T)100, (T)1, (T)100));
        this.Mode = (Setting<mode>)this.register(new Setting("Mode", (T)mode.NCP));
    }
    
    @SubscribeEvent
    public void onInput(final InputUpdateEvent e) {
        if ((this.Mode.getValue() != mode.StrictNCP || this.Mode.getValue() != mode.NCP) && NoSlow.mc.player.isHandActive() && !NoSlow.mc.player.isRiding()) {
            final MovementInput movementInput = NoSlow.mc.player.movementInput;
            movementInput.moveForward *= 5.0f * (this.speed.getValue() / 100.0f);
            final MovementInput movementInput2 = NoSlow.mc.player.movementInput;
            movementInput2.moveStrafe *= 5.0f * (this.speed.getValue() / 100.0f);
        }
        if ((this.Mode.getValue() == mode.StrictNCP || this.Mode.getValue() == mode.NCP) && NoSlow.mc.player.isHandActive() && !NoSlow.mc.player.isRiding() && !NoSlow.mc.player.isSneaking()) {
            if (this.Mode.getValue() == mode.StrictNCP && (NoSlow.mc.player.getHeldItemMainhand().getItem() instanceof ItemFood || NoSlow.mc.player.getHeldItemOffhand().getItem() instanceof ItemFood)) {
                NoSlow.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(NoSlow.mc.player.inventory.currentItem));
            }
            final MovementInput movementInput3 = NoSlow.mc.player.movementInput;
            movementInput3.moveForward /= (float)0.2;
            final MovementInput movementInput4 = NoSlow.mc.player.movementInput;
            movementInput4.moveStrafe /= (float)0.2;
        }
    }
    
    @SubscribeEvent
    public void onPreMotion(final EventSync event) {
        if (NoSlow.mc.player.isHandActive()) {
            if (NoSlow.mc.player.onGround) {
                if (NoSlow.mc.player.ticksExisted % 2 == 0) {
                    if (this.Mode.getValue() == mode.Matrix) {
                        final EntityPlayerSP player = NoSlow.mc.player;
                        player.motionX *= ((NoSlow.mc.player.moveStrafing == 0.0f) ? 0.55 : 0.5);
                        final EntityPlayerSP player2 = NoSlow.mc.player;
                        player2.motionZ *= ((NoSlow.mc.player.moveStrafing == 0.0f) ? 0.55 : 0.5);
                    }
                    else if (this.Mode.getValue() == mode.SunRise) {
                        final EntityPlayerSP player3 = NoSlow.mc.player;
                        player3.motionX *= 0.47;
                        final EntityPlayerSP player4 = NoSlow.mc.player;
                        player4.motionZ *= 0.47;
                    }
                    else if (this.Mode.getValue() == mode.Matrix2) {
                        final EntityPlayerSP player5 = NoSlow.mc.player;
                        player5.motionX *= 0.5;
                        final EntityPlayerSP player6 = NoSlow.mc.player;
                        player6.motionZ *= 0.5;
                    }
                }
            }
            else if (this.Mode.getValue() == mode.Matrix2) {
                final EntityPlayerSP player7 = NoSlow.mc.player;
                player7.motionX *= 0.95;
                final EntityPlayerSP player8 = NoSlow.mc.player;
                player8.motionZ *= 0.95;
            }
            else if (NoSlow.mc.player.fallDistance > ((this.Mode.getValue() == mode.Matrix) ? 0.7 : 0.2)) {
                if (this.Mode.getValue() == mode.Matrix) {
                    final EntityPlayerSP player9 = NoSlow.mc.player;
                    player9.motionX *= 0.93;
                    final EntityPlayerSP player10 = NoSlow.mc.player;
                    player10.motionZ *= 0.93;
                }
                else if (this.Mode.getValue() == mode.SunRise) {
                    final EntityPlayerSP player11 = NoSlow.mc.player;
                    player11.motionX *= 0.91;
                    final EntityPlayerSP player12 = NoSlow.mc.player;
                    player12.motionZ *= 0.91;
                }
            }
        }
    }
    
    public enum mode
    {
        NCP, 
        StrictNCP, 
        Matrix, 
        Matrix2, 
        SunRise;
    }
}
