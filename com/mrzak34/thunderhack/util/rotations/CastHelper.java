//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.rotations;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;

public class CastHelper
{
    private final List<EntityType> casts;
    
    public CastHelper() {
        this.casts = new ArrayList<EntityType>();
    }
    
    public static EntityType isInstanceof(final Entity entity, final EntityType... types) {
        for (final EntityType type : types) {
            if (entity == Minecraft.getMinecraft().player && type == EntityType.SELF) {
                return type;
            }
            if (type == EntityType.VILLAGERS && entity instanceof EntityVillager) {
                return type;
            }
            if (type == EntityType.FRIENDS && entity instanceof EntityPlayer && Thunderhack.friendManager.isFriend(entity.getName())) {
                return type;
            }
            if (type == EntityType.PLAYERS && entity instanceof EntityPlayer && entity != Minecraft.getMinecraft().player && !Thunderhack.friendManager.isFriend(entity.getName())) {
                return type;
            }
            if (type == EntityType.MOBS && entity instanceof EntityMob) {
                return type;
            }
            if (type == EntityType.ANIMALS && (entity instanceof EntityAnimal || entity instanceof EntityGolem)) {
                return type;
            }
        }
        return null;
    }
    
    public CastHelper apply(final EntityType type) {
        this.casts.add(type);
        return this;
    }
    
    public EntityType[] build() {
        return this.casts.toArray(new EntityType[0]);
    }
    
    public enum EntityType
    {
        PLAYERS, 
        MOBS, 
        ANIMALS, 
        VILLAGERS, 
        FRIENDS, 
        SELF;
    }
}
