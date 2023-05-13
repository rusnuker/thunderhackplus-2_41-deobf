//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraftforge.common.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.modules.combat.*;
import java.util.concurrent.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

public class IDHelper
{
    private static final ScheduledExecutorService THREAD;
    private volatile int highestID;
    private boolean updated;
    
    public IDHelper() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public static CPacketUseEntity attackPacket(final int id) {
        final CPacketUseEntity packet = new CPacketUseEntity();
        ((ICPacketUseEntity)packet).setEntityId(id);
        ((ICPacketUseEntity)packet).setAction(CPacketUseEntity.Action.ATTACK);
        return packet;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSpawnObject) {
            this.checkID(((SPacketSpawnObject)event.getPacket()).getEntityID());
        }
        if (event.getPacket() instanceof SPacketSpawnExperienceOrb) {
            this.checkID(((SPacketSpawnExperienceOrb)event.getPacket()).getEntityID());
        }
        if (event.getPacket() instanceof SPacketSpawnPlayer) {
            this.checkID(((SPacketSpawnPlayer)event.getPacket()).getEntityID());
        }
        if (event.getPacket() instanceof SPacketSpawnGlobalEntity) {
            this.checkID(((SPacketSpawnGlobalEntity)event.getPacket()).getEntityId());
        }
        if (event.getPacket() instanceof SPacketSpawnPainting) {
            this.checkID(((SPacketSpawnPainting)event.getPacket()).getEntityID());
        }
        if (event.getPacket() instanceof SPacketSpawnMob) {
            this.checkID(((SPacketSpawnMob)event.getPacket()).getEntityID());
        }
    }
    
    public int getHighestID() {
        return this.highestID;
    }
    
    public void setHighestID(final int id) {
        this.highestID = id;
    }
    
    public boolean isUpdated() {
        return this.updated;
    }
    
    public void setUpdated(final boolean updated) {
        this.updated = updated;
    }
    
    public void update() {
        int highest = this.getHighestID();
        for (final Entity entity : Util.mc.world.loadedEntityList) {
            if (entity.getEntityId() > highest) {
                highest = entity.getEntityId();
            }
        }
        if (highest > this.highestID) {
            this.highestID = highest;
        }
    }
    
    public boolean isSafe(final List<EntityPlayer> players, final boolean holdingCheck, final boolean toolCheck) {
        if (!holdingCheck) {
            return true;
        }
        for (final EntityPlayer player : players) {
            if (this.isDangerous(player, true, toolCheck)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isDangerous(final EntityPlayer player, final boolean holdingCheck, final boolean toolCheck) {
        return holdingCheck && (InventoryUtil.isHolding(player, (Item)Items.BOW) || InventoryUtil.isHolding(player, Items.EXPERIENCE_BOTTLE) || (toolCheck && (player.getHeldItemMainhand().getItem() instanceof ItemPickaxe || player.getHeldItemMainhand().getItem() instanceof ItemSpade)));
    }
    
    public void attack(final AutoCrystal.SwingTime breakSwing, final AutoCrystal.PlaceSwing godSwing, final int idOffset, final int packets, final int sleep) {
        if (sleep <= 0) {
            this.attackPackets(breakSwing, godSwing, idOffset, packets);
        }
        else {
            IDHelper.THREAD.schedule(() -> {
                this.update();
                this.attackPackets(breakSwing, godSwing, idOffset, packets);
            }, sleep, TimeUnit.MILLISECONDS);
        }
    }
    
    private void attackPackets(final AutoCrystal.SwingTime breakSwing, final AutoCrystal.PlaceSwing godSwing, final int idOffset, final int packets) {
        for (int i = 0; i < packets; ++i) {
            final int id = this.highestID + idOffset + i;
            final Entity entity = Util.mc.world.getEntityByID(id);
            if (entity == null || entity instanceof EntityEnderCrystal) {
                if (godSwing == AutoCrystal.PlaceSwing.Always && breakSwing == AutoCrystal.SwingTime.Pre) {
                    Swing.Packet.swing(EnumHand.MAIN_HAND);
                }
                final CPacketUseEntity packet = attackPacket(id);
                Util.mc.player.connection.sendPacket((Packet)packet);
                if (godSwing == AutoCrystal.PlaceSwing.Always && breakSwing == AutoCrystal.SwingTime.Post) {
                    Swing.Packet.swing(EnumHand.MAIN_HAND);
                }
            }
        }
        if (godSwing == AutoCrystal.PlaceSwing.Once) {
            Swing.Packet.swing(EnumHand.MAIN_HAND);
        }
    }
    
    private void checkID(final int id) {
        if (id > this.highestID) {
            this.highestID = id;
        }
    }
    
    static {
        THREAD = ThreadUtil.newDaemonScheduledExecutor("ID-Helper");
    }
}
