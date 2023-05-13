//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.network.*;

public class HelperInstantAttack
{
    public static void attack(final AutoCrystal module, final SPacketSpawnObject packet, final PacketEvent.Receive event, final EntityEnderCrystal entityIn, final boolean slow) {
        attack(module, packet, event, entityIn, slow, true);
    }
    
    public static void attack(final AutoCrystal module, final SPacketSpawnObject packet, final PacketEvent.Receive event, final EntityEnderCrystal entityIn, final boolean slow, final boolean allowAntiWeakness) {
        ((ISPacketSpawnObject)event.getPacket()).setAttacked(true);
        final CPacketUseEntity p = new CPacketUseEntity((Entity)entityIn);
        WeaknessSwitch w;
        if (allowAntiWeakness) {
            w = HelperRotation.antiWeakness(module);
            if (w.needsSwitch() && (w.getSlot() == -1 || !(boolean)module.instantAntiWeak.getValue())) {
                return;
            }
        }
        else {
            w = WeaknessSwitch.NONE;
        }
        final int lastSlot = Util.mc.player.inventory.currentItem;
        final WeaknessSwitch weaknessSwitch;
        final Packet packet2;
        final int n;
        final Runnable runnable = () -> {
            if (weaknessSwitch.getSlot() != -1) {
                switch ((AutoCrystal.CooldownBypass2)module.antiWeaknessBypass.getValue()) {
                    case None: {
                        CooldownBypass.None.switchTo(weaknessSwitch.getSlot());
                        break;
                    }
                    case Pick: {
                        CooldownBypass.Pick.switchTo(weaknessSwitch.getSlot());
                        break;
                    }
                    case Slot: {
                        CooldownBypass.Slot.switchTo(weaknessSwitch.getSlot());
                        break;
                    }
                    case Swap: {
                        CooldownBypass.Swap.switchTo(weaknessSwitch.getSlot());
                        break;
                    }
                }
            }
            if (module.breakSwing.getValue() == AutoCrystal.SwingTime.Pre) {
                Swing.Packet.swing(EnumHand.MAIN_HAND);
            }
            Util.mc.player.connection.sendPacket(packet2);
            if (module.breakSwing.getValue() == AutoCrystal.SwingTime.Post) {
                Swing.Packet.swing(EnumHand.MAIN_HAND);
            }
            if (weaknessSwitch.getSlot() != -1) {
                switch ((AutoCrystal.CooldownBypass2)module.antiWeaknessBypass.getValue()) {
                    case None: {
                        CooldownBypass.None.switchBack(n, weaknessSwitch.getSlot());
                        break;
                    }
                    case Pick: {
                        CooldownBypass.Pick.switchBack(n, weaknessSwitch.getSlot());
                        break;
                    }
                    case Slot: {
                        CooldownBypass.Slot.switchBack(n, weaknessSwitch.getSlot());
                        break;
                    }
                    case Swap: {
                        CooldownBypass.Swap.switchBack(n, weaknessSwitch.getSlot());
                        break;
                    }
                }
            }
            return;
        };
        if (w.getSlot() != -1) {
            HelperRotation.acquire(runnable);
        }
        else {
            runnable.run();
        }
        module.breakTimer.reset(slow ? ((long)(int)module.slowBreakDelay.getValue()) : ((long)(int)module.breakDelay.getValue()));
        final Entity entity;
        event.addPostEvent(() -> {
            entity = Util.mc.world.getEntityByID(packet.getEntityID());
            if (entity instanceof EntityEnderCrystal) {
                module.setCrystal(entity);
            }
            return;
        });
        if (module.simulateExplosion.getValue()) {
            HelperUtil.simulateExplosion(module, packet.getX(), packet.getY(), packet.getZ());
        }
        if (module.pseudoSetDead.getValue()) {
            final Entity entity2;
            event.addPostEvent(() -> {
                entity2 = Util.mc.world.getEntityByID(packet.getEntityID());
                if (entity2 != null) {
                    ((IEntity)entity2).setPseudoDeadT(true);
                }
            });
            return;
        }
        if (module.instantSetDead.getValue()) {
            event.setCanceled(true);
            final Entity entity3;
            Util.mc.addScheduledTask(() -> {
                entity3 = Util.mc.world.getEntityByID(packet.getEntityID());
                if (entity3 instanceof EntityEnderCrystal) {
                    module.crystalRender.onSpawn((EntityEnderCrystal)entity3);
                }
                if (!(!event.isCanceled())) {
                    EntityTracker.updateServerPosition((Entity)entityIn, packet.getX(), packet.getY(), packet.getZ());
                    Thunderhack.setDeadManager.setDead((Entity)entityIn);
                }
            });
        }
    }
}
