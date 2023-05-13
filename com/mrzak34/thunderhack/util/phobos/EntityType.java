//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.awt.*;
import net.minecraft.entity.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.item.*;

public enum EntityType
{
    Animal(new Color(0, 200, 0, 255)), 
    Monster(new Color(200, 60, 60, 255)), 
    Player(new Color(255, 255, 255, 255)), 
    Boss(new Color(40, 0, 255, 255)), 
    Vehicle(new Color(200, 100, 0, 255)), 
    Other(new Color(200, 100, 200, 255)), 
    Entity(new Color(255, 255, 0, 255));
    
    private final Color color;
    
    private EntityType(final Color color) {
        this.color = color;
    }
    
    public static Supplier<EntityType> getEntityType(final Entity entity) {
        if (entity instanceof EntityWolf) {
            return () -> isAngryWolf((EntityWolf)entity) ? EntityType.Monster : EntityType.Animal;
        }
        if (entity instanceof EntityEnderman) {
            return () -> isAngryEnderMan((EntityEnderman)entity) ? EntityType.Monster : EntityType.Entity;
        }
        if (entity instanceof EntityPolarBear) {
            return () -> isAngryPolarBear((EntityPolarBear)entity) ? EntityType.Monster : EntityType.Animal;
        }
        if (entity instanceof EntityPigZombie) {
            return () -> (entity.rotationPitch == 0.0f && ((EntityPigZombie)entity).getRevengeTimer() <= 0) ? EntityType.Monster : EntityType.Entity;
        }
        if (entity instanceof EntityIronGolem) {
            return () -> isAngryGolem((EntityIronGolem)entity) ? EntityType.Monster : EntityType.Entity;
        }
        if (entity instanceof EntityVillager) {
            return () -> EntityType.Entity;
        }
        if (entity instanceof EntityRabbit) {
            return () -> isFriendlyRabbit((EntityRabbit)entity) ? EntityType.Animal : EntityType.Monster;
        }
        if (isAnimal(entity)) {
            return () -> EntityType.Animal;
        }
        if (isMonster(entity)) {
            return () -> EntityType.Monster;
        }
        if (isPlayer(entity)) {
            return () -> EntityType.Player;
        }
        if (isVehicle(entity)) {
            return () -> EntityType.Vehicle;
        }
        if (isBoss(entity)) {
            return () -> EntityType.Boss;
        }
        if (isOther(entity)) {
            return () -> EntityType.Other;
        }
        return () -> EntityType.Entity;
    }
    
    public static boolean isPlayer(final Entity entity) {
        return entity instanceof EntityPlayer;
    }
    
    public static boolean isAnimal(final Entity entity) {
        return entity instanceof EntityPig || entity instanceof EntityParrot || entity instanceof EntityCow || entity instanceof EntitySheep || entity instanceof EntityChicken || entity instanceof EntitySquid || entity instanceof EntityBat || entity instanceof EntityVillager || entity instanceof EntityOcelot || entity instanceof EntityHorse || entity instanceof EntityLlama || entity instanceof EntityMule || entity instanceof EntityDonkey || entity instanceof EntitySkeletonHorse || entity instanceof EntityZombieHorse || entity instanceof EntitySnowman || (entity instanceof EntityRabbit && isFriendlyRabbit((EntityRabbit)entity));
    }
    
    public static boolean isMonster(final Entity entity) {
        return entity instanceof EntityCreeper || entity instanceof EntityIllusionIllager || entity instanceof EntitySkeleton || (entity instanceof EntityZombie && !(entity instanceof EntityPigZombie)) || entity instanceof EntityBlaze || entity instanceof EntitySpider || entity instanceof EntityWitch || entity instanceof EntitySlime || entity instanceof EntitySilverfish || entity instanceof EntityGuardian || entity instanceof EntityEndermite || entity instanceof EntityGhast || entity instanceof EntityEvoker || entity instanceof EntityShulker || entity instanceof EntityWitherSkeleton || entity instanceof EntityStray || entity instanceof EntityVex || entity instanceof EntityVindicator || (entity instanceof EntityPolarBear && !isAngryPolarBear((EntityPolarBear)entity)) || (entity instanceof EntityWolf && !isAngryWolf((EntityWolf)entity)) || (entity instanceof EntityPigZombie && !isAngryPigMan(entity)) || (entity instanceof EntityEnderman && !isAngryEnderMan((EntityEnderman)entity)) || (entity instanceof EntityRabbit && !isFriendlyRabbit((EntityRabbit)entity)) || (entity instanceof EntityIronGolem && !isAngryGolem((EntityIronGolem)entity));
    }
    
    public static boolean isBoss(final Entity entity) {
        return entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityGiantZombie;
    }
    
    public static boolean isOther(final Entity entity) {
        return entity instanceof EntityEnderCrystal || entity instanceof EntityEvokerFangs || entity instanceof EntityShulkerBullet || entity instanceof EntityFallingBlock || entity instanceof EntityFireball || entity instanceof EntityEnderEye || entity instanceof EntityEnderPearl;
    }
    
    public static boolean isVehicle(final Entity entity) {
        return entity instanceof EntityBoat || entity instanceof EntityMinecart;
    }
    
    public static boolean isAngryEnderMan(final EntityEnderman enderman) {
        return enderman.isScreaming();
    }
    
    public static boolean isAngryPigMan(final Entity entity) {
        return entity instanceof EntityPigZombie && entity.rotationPitch == 0.0f && ((EntityPigZombie)entity).getRevengeTimer() <= 0;
    }
    
    public static boolean isAngryGolem(final EntityIronGolem ironGolem) {
        return ironGolem.rotationPitch == 0.0f;
    }
    
    public static boolean isAngryWolf(final EntityWolf wolf) {
        return wolf.isAngry();
    }
    
    public static boolean isAngryPolarBear(final EntityPolarBear polarBear) {
        return polarBear.rotationPitch == 0.0f && polarBear.getRevengeTimer() <= 0;
    }
    
    public static boolean isFriendlyRabbit(final EntityRabbit rabbit) {
        return rabbit.getRabbitType() != 99;
    }
    
    public Color getColor() {
        return this.color;
    }
}
