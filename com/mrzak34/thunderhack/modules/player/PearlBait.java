//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.setting.*;
import java.util.concurrent.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class PearlBait extends Module
{
    private final Queue<CPacketPlayer> packets;
    public Setting<Boolean> guarantee;
    private int thrownPearlId;
    
    public PearlBait() {
        super("PearlBait", "\u043a\u0438\u0434\u0430\u0435\u0448\u044c \u043f\u0435\u0440\u043b \u0438-\u043d\u0435 \u0442\u0435\u043f\u0430\u0435\u0448\u044c\u0441\u044f", Module.Category.PLAYER);
        this.packets = new ConcurrentLinkedQueue<CPacketPlayer>();
        this.guarantee = (Setting<Boolean>)this.register(new Setting("Forced Strafe", (T)true));
        this.thrownPearlId = -1;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSpawnObject) {
            final SPacketSpawnObject packet = (SPacketSpawnObject)event.getPacket();
            if (packet.getType() == 65) {
                final SPacketSpawnObject sPacketSpawnObject;
                final SPacketSpawnObject sPacketSpawnObject2;
                PearlBait.mc.world.playerEntities.stream().min(Comparator.comparingDouble(p -> p.getDistance(sPacketSpawnObject.getX(), sPacketSpawnObject.getY(), sPacketSpawnObject.getZ()))).ifPresent(player -> {
                    if (player.equals((Object)PearlBait.mc.player) && PearlBait.mc.player.onGround) {
                        PearlBait.mc.player.motionX = 0.0;
                        PearlBait.mc.player.motionY = 0.0;
                        PearlBait.mc.player.motionZ = 0.0;
                        PearlBait.mc.player.movementInput.moveForward = 0.0f;
                        PearlBait.mc.player.movementInput.moveStrafe = 0.0f;
                        PearlBait.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(PearlBait.mc.player.posX, PearlBait.mc.player.posY + 1.0, PearlBait.mc.player.posZ, false));
                        this.thrownPearlId = sPacketSpawnObject2.getEntityID();
                    }
                });
            }
        }
        else if (event.getPacket() instanceof CPacketPlayer && this.guarantee.getValue() && this.thrownPearlId != -1) {
            this.packets.add((CPacketPlayer)event.getPacket());
            event.setCanceled(true);
        }
    }
    
    public void onUpdate() {
        if (this.thrownPearlId != -1) {
            for (final Entity entity : PearlBait.mc.world.loadedEntityList) {
                if (entity.getEntityId() == this.thrownPearlId && entity instanceof EntityEnderPearl) {
                    final EntityEnderPearl pearl = (EntityEnderPearl)entity;
                    if (!pearl.isDead) {
                        continue;
                    }
                    this.thrownPearlId = -1;
                }
            }
        }
        else if (!this.packets.isEmpty()) {
            do {
                PearlBait.mc.player.connection.sendPacket((Packet)this.packets.poll());
            } while (!this.packets.isEmpty());
        }
    }
}
