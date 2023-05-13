//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.math;

import net.minecraft.item.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;

public class DamageUtil implements Util
{
    public static int getItemDamage(final ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }
    
    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity, final boolean ignoreTerrain) {
        float finalDamage = 1.0f;
        try {
            final float doubleExplosionSize = 12.0f;
            final double distancedSize = entity.getDistance(posX, posY, posZ) / doubleExplosionSize;
            final double blockDensity = ignoreTerrain ? ignoreTerrainDecntiy(new Vec3d(posX, posY, posZ), entity.getEntityBoundingBox(), (World)DamageUtil.mc.world) : ((double)entity.world.getBlockDensity(new Vec3d(posX, posY, posZ), entity.getEntityBoundingBox()));
            final double v = (1.0 - distancedSize) * blockDensity;
            final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * doubleExplosionSize + 1.0);
            if (entity instanceof EntityLivingBase) {
                finalDamage = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)DamageUtil.mc.world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
            }
        }
        catch (NullPointerException ex) {}
        return finalDamage;
    }
    
    public static float ignoreTerrainDecntiy(final Vec3d vec, final AxisAlignedBB bb, final World world) {
        final double d0 = 1.0 / ((bb.maxX - bb.minX) * 2.0 + 1.0);
        final double d2 = 1.0 / ((bb.maxY - bb.minY) * 2.0 + 1.0);
        final double d3 = 1.0 / ((bb.maxZ - bb.minZ) * 2.0 + 1.0);
        final double d4 = (1.0 - Math.floor(1.0 / d0) * d0) / 2.0;
        final double d5 = (1.0 - Math.floor(1.0 / d3) * d3) / 2.0;
        if (d0 >= 0.0 && d2 >= 0.0 && d3 >= 0.0) {
            int j2 = 0;
            int k2 = 0;
            for (float f = 0.0f; f <= 1.0f; f += (float)d0) {
                for (float f2 = 0.0f; f2 <= 1.0f; f2 += (float)d2) {
                    for (float f3 = 0.0f; f3 <= 1.0f; f3 += (float)d3) {
                        final double d6 = bb.minX + (bb.maxX - bb.minX) * f;
                        final double d7 = bb.minY + (bb.maxY - bb.minY) * f2;
                        final double d8 = bb.minZ + (bb.maxZ - bb.minZ) * f3;
                        final RayTraceResult result;
                        if ((result = world.rayTraceBlocks(new Vec3d(d6 + d4, d7, d8 + d5), vec)) == null) {
                            ++j2;
                        }
                        else {
                            final Block blockHit = BlockUtils.getBlock(result.getBlockPos());
                            if (((IBlock)blockHit).getBlockResistance() < 600.0f) {
                                ++j2;
                            }
                        }
                        ++k2;
                    }
                }
            }
            return j2 / (float)k2;
        }
        return 0.0f;
    }
    
    public static float getBlastReduction(final EntityLivingBase entity, final float damageI, final Explosion explosion) {
        float damage = damageI;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int k = 0;
            try {
                k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            }
            catch (Exception ex) {}
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(MobEffects.RESISTANCE)) {
                damage -= damage / 4.0f;
            }
            damage = Math.max(damage, 0.0f);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }
    
    public static float getDamageMultiplied(final float damage) {
        final int diff = DamageUtil.mc.world.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
}
