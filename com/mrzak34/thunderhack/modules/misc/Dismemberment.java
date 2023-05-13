//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.*;
import net.minecraft.client.particle.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.multiplayer.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.dism.*;

public class Dismemberment extends Module
{
    public static int ticks;
    public Setting<Boolean> blood;
    public Setting<Integer> gibGroundTime;
    public Setting<Integer> gibTime;
    public Setting<Integer> bcount;
    public HashMap<EntityLivingBase, Integer> dismemberTimeout;
    public HashMap<Entity, Integer> exploTime;
    public ArrayList<Entity> explosionSources;
    public HashMap<EntityLivingBase, EntityGib[]> amputationMap;
    
    public Dismemberment() {
        super("Dismemberment", "Dismemberment", Category.MISC);
        this.blood = (Setting<Boolean>)this.register(new Setting("Blood", (T)false));
        this.gibGroundTime = (Setting<Integer>)this.register(new Setting("OnGroundTime", (T)425, (T)0, (T)2000));
        this.gibTime = (Setting<Integer>)this.register(new Setting("LiveTime", (T)425, (T)0, (T)2000));
        this.bcount = (Setting<Integer>)this.register(new Setting("BloodCount", (T)425, (T)0, (T)2000));
        this.dismemberTimeout = new HashMap<EntityLivingBase, Integer>();
        this.exploTime = new HashMap<Entity, Integer>();
        this.explosionSources = new ArrayList<Entity>();
        this.amputationMap = new HashMap<EntityLivingBase, EntityGib[]>();
    }
    
    public static void spawnParticle(final Particle particle) {
    }
    
    @SubscribeEvent
    public void onLivingDeath(final LivingDeathEvent event) {
        if (event.getEntity().world.isRemote && (event.getEntityLiving() instanceof EntityZombie || event.getEntityLiving() instanceof EntityPlayer || event.getEntityLiving() instanceof EntitySkeleton || event.getEntityLiving() instanceof EntityCreeper) && !event.getEntityLiving().isChild()) {
            this.dismemberTimeout.put(event.getEntityLiving(), 2);
        }
    }
    
    @SubscribeEvent
    public void onClientConnection(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.exploTime.clear();
        this.dismemberTimeout.clear();
        this.explosionSources.clear();
    }
    
    @SubscribeEvent
    public void worldTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().world != null) {
            final Minecraft mc = Minecraft.getMinecraft();
            final WorldClient world = mc.world;
            if (!mc.isGamePaused()) {
                for (int i = 0; i < world.loadedEntityList.size(); ++i) {
                    final Entity ent = world.loadedEntityList.get(i);
                    if ((ent instanceof EntityCreeper || ent instanceof EntityTNTPrimed || ent instanceof EntityMinecartTNT || ent instanceof EntityEnderCrystal) && !this.explosionSources.contains(ent)) {
                        this.explosionSources.add(ent);
                    }
                    if ((ent instanceof EntityZombie || ent instanceof EntityPlayer || ent instanceof EntitySkeleton || ent instanceof EntityCreeper) && !ent.isEntityAlive() && !this.dismemberTimeout.containsKey(ent)) {
                        this.dismemberTimeout.put((EntityLivingBase)ent, 2);
                    }
                }
                for (int i = this.explosionSources.size() - 1; i >= 0; --i) {
                    final Entity ent = this.explosionSources.get(i);
                    if (ent.isDead) {
                        if ((ent instanceof EntityTNTPrimed || ent instanceof EntityMinecartTNT) && !this.exploTime.containsKey(ent)) {
                            int time = Dismemberment.ticks % 24000;
                            if (time > 23959L) {
                                time -= (int)23999L;
                            }
                            this.exploTime.put(ent, time);
                        }
                        this.explosionSources.remove(i);
                    }
                }
                final Iterator<Map.Entry<EntityLivingBase, Integer>> ite = this.dismemberTimeout.entrySet().iterator();
                if (ite.hasNext()) {
                    final Map.Entry<EntityLivingBase, Integer> e = ite.next();
                    e.setValue(e.getValue() - 1);
                    e.getKey().hurtTime = 0;
                    e.getKey().deathTime = 0;
                    Entity explo = null;
                    double dist = 1000.0;
                    for (final Map.Entry<Entity, Integer> e2 : this.exploTime.entrySet()) {
                        final double mobDist = e2.getKey().getDistance((Entity)e.getKey());
                        if (mobDist < 10.0 && mobDist < dist) {
                            dist = mobDist;
                            explo = e2.getKey();
                            e.setValue(0);
                        }
                    }
                    if (e.getValue() <= 0) {
                        if (this.dismember(e.getKey().world, e.getKey(), explo)) {
                            e.getKey().setDead();
                        }
                        ite.remove();
                    }
                }
                final Iterator<Map.Entry<Entity, Integer>> ite2 = this.exploTime.entrySet().iterator();
                final int worldTime = Dismemberment.ticks % 24000;
                while (ite2.hasNext()) {
                    final Map.Entry<Entity, Integer> e3 = ite2.next();
                    if (e3.getValue() + 40L < worldTime) {
                        ite2.remove();
                    }
                }
            }
        }
    }
    
    public boolean dismember(final World world, final EntityLivingBase living, final Entity explo) {
        if (living.isChild()) {
            return false;
        }
        if (living instanceof EntityCreeper) {
            world.spawnEntity((Entity)new EntityGib(world, living, 0, explo));
            world.spawnEntity((Entity)new EntityGib(world, living, 3, explo));
            world.spawnEntity((Entity)new EntityGib(world, living, 6, explo));
            world.spawnEntity((Entity)new EntityGib(world, living, 7, explo));
            world.spawnEntity((Entity)new EntityGib(world, living, 8, explo));
            world.spawnEntity((Entity)new EntityGib(world, living, 9, explo));
        }
        else {
            if (living instanceof EntityPlayer) {
                for (int i = -1; i < 6; ++i) {
                    world.spawnEntity((Entity)new EntityGib(world, living, i, explo));
                }
            }
            else {
                for (int i = 0; i < 6; ++i) {
                    world.spawnEntity((Entity)new EntityGib(world, living, i, explo));
                }
            }
            if (living instanceof EntityZombie || (living instanceof EntityPlayer && this.blood.getValue())) {
                for (int k = 0; k < ((explo != null) ? (this.bcount.getValue() * 10) : this.bcount.getValue()); ++k) {
                    float var4 = 0.3f;
                    double mX = -MathHelper.sin(living.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(living.rotationPitch / 180.0f * 3.1415927f) * var4;
                    double mZ = MathHelper.cos(living.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(living.rotationPitch / 180.0f * 3.1415927f) * var4;
                    double mY = -MathHelper.sin(living.rotationPitch / 180.0f * 3.1415927f) * var4 + 0.1f;
                    var4 = 0.02f;
                    final float var5 = living.getRNG().nextFloat() * 3.1415927f * 2.0f;
                    var4 *= living.getRNG().nextFloat();
                    if (explo != null) {
                        var4 *= 100.0;
                    }
                    mX += Math.cos(var5) * var4;
                    mY += (living.getRNG().nextFloat() - living.getRNG().nextFloat()) * 0.1f;
                    mZ += Math.sin(var5) * var4;
                    spawnParticle(new ParticleBlood(living.world, living.posX, living.posY + 0.5 + living.getRNG().nextDouble() * 0.7, living.posZ, living.motionX + mX, living.motionY + mY, living.motionZ + mZ, false));
                }
            }
        }
        return true;
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ++Dismemberment.ticks;
        }
    }
}
