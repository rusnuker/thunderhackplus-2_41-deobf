//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.util.math.*;
import net.minecraftforge.common.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.command.*;

public class HelperSequential
{
    private final Timer timer;
    private final AutoCrystal module;
    private volatile BlockPos expecting;
    private volatile Vec3d crystalPos;
    
    public HelperSequential(final AutoCrystal module) {
        this.timer = new Timer();
        this.module = module;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.SendPost e) {
        if (e.getPacket() instanceof CPacketUseEntity) {
            final Entity entity = Util.mc.world.getEntityByID(((ICPacketUseEntity)e.getPacket()).getEntityId());
            if (entity instanceof EntityEnderCrystal) {
                if (this.module.endSequenceOnBreak.getValue()) {
                    this.setExpecting(null);
                }
                else {
                    this.crystalPos = entity.getPositionVector();
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (Util.mc.player == null || Util.mc.world == null) {
            return;
        }
        if (e.getPacket() instanceof SPacketSoundEffect) {
            final Vec3d cPos = this.crystalPos;
            if ((boolean)this.module.endSequenceOnExplosion.getValue() && ((SPacketSoundEffect)e.getPacket()).getCategory() == SoundCategory.BLOCKS && ((SPacketSoundEffect)e.getPacket()).getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE && cPos != null && cPos.squareDistanceTo(((SPacketSoundEffect)e.getPacket()).getX(), ((SPacketSoundEffect)e.getPacket()).getY(), ((SPacketSoundEffect)e.getPacket()).getZ()) < 144.0) {
                this.setExpecting(null);
            }
        }
        if (e.getPacket() instanceof SPacketSpawnObject && ((SPacketSpawnObject)e.getPacket()).getType() == 51) {
            final BlockPos pos = new BlockPos(((SPacketSpawnObject)e.getPacket()).getX(), ((SPacketSpawnObject)e.getPacket()).getY(), ((SPacketSpawnObject)e.getPacket()).getZ());
            if (pos.down().equals((Object)this.expecting)) {
                if (this.module.endSequenceOnSpawn.getValue()) {
                    this.setExpecting(null);
                }
                else if (this.crystalPos == null) {
                    this.crystalPos = new Vec3d(((SPacketSpawnObject)e.getPacket()).getX(), ((SPacketSpawnObject)e.getPacket()).getY(), ((SPacketSpawnObject)e.getPacket()).getZ());
                }
            }
        }
        if (e.getPacket() instanceof SPacketBlockChange) {
            final BlockPos expected = this.expecting;
            if (expected != null && expected.equals((Object)((SPacketBlockChange)e.getPacket()).getBlockPosition()) && (boolean)this.module.antiPlaceFail.getValue() && this.crystalPos == null) {
                this.module.placeTimer.setTime(0L);
                this.setExpecting(null);
                if (this.module.debugAntiPlaceFail.getValue()) {
                    Util.mc.addScheduledTask(() -> Command.sendMessage("Crystal failed to place!"));
                }
            }
        }
    }
    
    public boolean isBlockingPlacement() {
        return (boolean)this.module.sequential.getValue() && this.expecting != null && !this.timer.passedMs((int)this.module.seqTime.getValue());
    }
    
    public void setExpecting(final BlockPos expecting) {
        this.timer.reset();
        this.expecting = expecting;
        this.crystalPos = null;
    }
}
