//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import io.netty.util.internal.*;
import java.util.concurrent.*;
import net.minecraftforge.common.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.util.math.*;

public class SetDeadManager
{
    private final Map<Integer, EntityTime> killed;
    private final Set<SoundObserver> observers;
    
    public SetDeadManager() {
        this.observers = (Set<SoundObserver>)new ConcurrentSet();
        this.killed = new ConcurrentHashMap<Integer, EntityTime>();
    }
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void unload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (e.getPacket() instanceof SPacketDestroyEntities) {
            final int[] array;
            int length;
            int i = 0;
            int id;
            Util.mc.addScheduledTask(() -> {
                ((SPacketDestroyEntities)e.getPacket()).getEntityIDs();
                for (length = array.length; i < length; ++i) {
                    id = array[i];
                    this.confirmKill(id);
                }
                return;
            });
        }
        if (e.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect p = (SPacketSoundEffect)e.getPacket();
            if (p.getCategory() == SoundCategory.BLOCKS && p.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE && this.shouldRemove()) {
                final Vec3d pos = new Vec3d(p.getX(), p.getY(), p.getZ());
                final Vec3d pos2;
                final Iterator<SoundObserver> iterator;
                SoundObserver observer;
                final Object o;
                Util.mc.addScheduledTask(() -> {
                    this.removeCrystals(pos2, 11.0f, Util.mc.world.loadedEntityList);
                    this.observers.iterator();
                    while (iterator.hasNext()) {
                        observer = iterator.next();
                        if (observer.shouldBeNotified()) {
                            observer.onChange(o);
                        }
                    }
                });
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdate(final EventSync e) {
        this.updateKilled();
    }
    
    @SubscribeEvent
    public void onConnect(final ConnectToServerEvent e) {
        this.clear();
    }
    
    public Entity getEntity(final int id) {
        final EntityTime time = this.killed.get(id);
        if (time != null) {
            return time.getEntity();
        }
        return null;
    }
    
    public void setDeadCustom(final Entity entity, final long t) {
        final EntityTime time = this.killed.get(entity.getEntityId());
        if (time instanceof CustomEntityTime) {
            time.getEntity().setDead();
            time.reset();
        }
        else {
            entity.setDead();
            this.killed.put(entity.getEntityId(), (EntityTime)new CustomEntityTime(entity, t));
        }
    }
    
    public void revive(final int id) {
        final EntityTime time = this.killed.remove(id);
        if (time != null && time.isValid()) {
            final Entity entity = time.getEntity();
            entity.isDead = false;
            Util.mc.world.addEntityToWorld(entity.getEntityId(), entity);
            entity.isDead = false;
        }
    }
    
    public void updateKilled() {
        for (final Map.Entry<Integer, EntityTime> entry : this.killed.entrySet()) {
            if (!entry.getValue().isValid()) {
                entry.getValue().getEntity().setDead();
                this.killed.remove(entry.getKey());
            }
            else {
                if (!entry.getValue().passed((long)(int)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).deathTime.getValue())) {
                    continue;
                }
                final Entity entity = entry.getValue().getEntity();
                entity.isDead = false;
                if (Util.mc.world.loadedEntityList.contains(entity)) {
                    continue;
                }
                Util.mc.world.addEntityToWorld((int)entry.getKey(), entity);
                entity.isDead = false;
                this.killed.remove(entry.getKey());
            }
        }
    }
    
    public void removeCrystals(final Vec3d pos, final float range, final List<Entity> entities) {
        for (final Entity entity : entities) {
            if (entity instanceof EntityEnderCrystal && entity.getDistanceSq(pos.x, pos.y, pos.z) <= MathUtil.square((double)range)) {
                this.setDead(entity);
            }
        }
    }
    
    public void setDead(final Entity entity) {
        final EntityTime time = this.killed.get(entity.getEntityId());
        if (time != null) {
            time.getEntity().setDead();
            time.reset();
        }
        else if (!entity.isDead) {
            entity.setDead();
            this.killed.put(entity.getEntityId(), new EntityTime(entity));
        }
    }
    
    public void confirmKill(final int id) {
        final EntityTime time = this.killed.get(id);
        if (time != null) {
            time.setValid(false);
            time.getEntity().setDead();
        }
    }
    
    public boolean passedDeathTime(final Entity entity, final long deathTime) {
        return this.passedDeathTime(entity.getEntityId(), deathTime);
    }
    
    public boolean passedDeathTime(final int id, final long deathTime) {
        if (deathTime <= 0L) {
            return true;
        }
        final EntityTime time = this.killed.get(id);
        return time == null || !time.isValid() || time.passed(deathTime);
    }
    
    public void clear() {
        this.killed.clear();
    }
    
    public void addObserver(final SoundObserver observer) {
        this.observers.add(observer);
    }
    
    public void removeObserver(final SoundObserver observer) {
        this.observers.remove(observer);
    }
    
    private boolean shouldRemove() {
        if (((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).soundRemove.getValue()) {
            return false;
        }
        for (final SoundObserver soundObserver : this.observers) {
            if (soundObserver.shouldRemove()) {
                return true;
            }
        }
        return false;
    }
}
