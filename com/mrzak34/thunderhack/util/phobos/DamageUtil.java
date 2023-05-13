//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.util.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

public class DamageUtil
{
    public static boolean canBreakWeakness(final boolean checkStack) {
        if (!Util.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
            return true;
        }
        int strengthAmp = 0;
        final PotionEffect effect = Util.mc.player.getActivePotionEffect(MobEffects.STRENGTH);
        if (effect != null) {
            strengthAmp = effect.getAmplifier();
        }
        return strengthAmp >= 1 || (checkStack && canBreakWeakness(Util.mc.player.getHeldItemMainhand()));
    }
    
    public static boolean canBreakWeakness(final ItemStack stack) {
        if (stack.getItem() instanceof ItemSword) {
            return true;
        }
        if (stack.getItem() instanceof ItemTool) {
            final IItemTool tool = (IItemTool)stack.getItem();
            return tool.getAttackDamage() > 4.0f;
        }
        return false;
    }
    
    public static int findAntiWeakness() {
        int slot = -1;
        for (int i = 8; i > -1 && (!canBreakWeakness(Util.mc.player.inventory.getStackInSlot(i)) || Util.mc.player.inventory.currentItem != (slot = i)); --i) {}
        return slot;
    }
    
    public static int getDamage(final ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }
    
    public static float calculate(final double x, final double y, final double z, final AxisAlignedBB bb, final EntityLivingBase base) {
        return calculate(x, y, z, bb, base, false);
    }
    
    public static float calculate(final double x, final double y, final double z, final AxisAlignedBB bb, final EntityLivingBase base, final boolean terrainCalc) {
        return calculate(x, y, z, bb, base, (IBlockAccess)Util.mc.world, terrainCalc);
    }
    
    public static float calculate(final double x, final double y, final double z, final AxisAlignedBB bb, final EntityLivingBase base, final IBlockAccess world, final boolean terrainCalc) {
        return calculate(x, y, z, bb, base, world, terrainCalc, false);
    }
    
    public static float calculate(final double x, final double y, final double z, final AxisAlignedBB bb, final EntityLivingBase base, final IBlockAccess world, final boolean terrainCalc, final boolean anvils) {
        return calculate(x, y, z, bb, base, world, terrainCalc, anvils, 6.0f);
    }
    
    public static float calculate(final double x, final double y, final double z, final AxisAlignedBB bb, final EntityLivingBase base, final IBlockAccess world, final boolean terrainCalc, final boolean anvils, final float power) {
        float f = 12.0f;
        double d5 = base.getDistance(x, y, z) / 12.0;
        if (d5 > 1.0) {
            return 0.0f;
        }
        final double d6 = base.world.getBlockDensity(new Vec3d(x, y, z), base.getEntityBoundingBox());
        d5 = (1.0 - d5) * d6;
        f = (float)(int)((d5 * d5 + d5) / 2.0 * 7.0 * 12.0 + 1.0);
        f = getDifDamage(f);
        final DamageSource dmsrc = DamageSource.causeExplosionDamage(new Explosion((World)Util.mc.world, (Entity)Util.mc.player, x, y, z, 6.0f, false, true));
        f = CombatRules.getDamageAfterAbsorb(f, (float)base.getTotalArmorValue(), (float)base.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        final int n = EnchantmentHelper.getEnchantmentModifierDamage(base.getArmorInventoryList(), dmsrc);
        if (n > 0) {
            f = CombatRules.getDamageAfterMagicAbsorb(f, (float)n);
        }
        if (base.getActivePotionEffect(MobEffects.RESISTANCE) != null) {
            f = f * (25 - (base.getActivePotionEffect(MobEffects.RESISTANCE).getAmplifier() + 1) * 5) / 25.0f;
        }
        f = Math.max(f, 0.0f);
        return f;
    }
    
    private static float getDifDamage(float f) {
        final EnumDifficulty enumDifficulty = Util.mc.world.getDifficulty();
        if (enumDifficulty == EnumDifficulty.PEACEFUL) {
            f = 0.0f;
            return 0.0f;
        }
        if (enumDifficulty == EnumDifficulty.EASY) {
            f = Math.min(f / 2.0f + 1.0f, f);
            return f;
        }
        if (enumDifficulty == EnumDifficulty.HARD) {
            f = f * 3.0f / 2.0f;
        }
        return f;
    }
    
    public static float getBlockDensity(final Vec3d vec, final AxisAlignedBB bb, final IBlockAccess world, final boolean ignoreWebs, final boolean ignoreBeds, final boolean terrainCalc, final boolean anvils) {
        final double x = 1.0 / ((bb.maxX - bb.minX) * 2.0 + 1.0);
        final double y = 1.0 / ((bb.maxY - bb.minY) * 2.0 + 1.0);
        final double z = 1.0 / ((bb.maxZ - bb.minZ) * 2.0 + 1.0);
        final double xFloor = (1.0 - Math.floor(1.0 / x) * x) / 2.0;
        final double zFloor = (1.0 - Math.floor(1.0 / z) * z) / 2.0;
        if (x >= 0.0 && y >= 0.0 && z >= 0.0) {
            int air = 0;
            int traced = 0;
            for (float a = 0.0f; a <= 1.0f; a += (float)x) {
                for (float b = 0.0f; b <= 1.0f; b += (float)y) {
                    for (float c = 0.0f; c <= 1.0f; c += (float)z) {
                        final double xOff = bb.minX + (bb.maxX - bb.minX) * a;
                        final double yOff = bb.minY + (bb.maxY - bb.minY) * b;
                        final double zOff = bb.minZ + (bb.maxZ - bb.minZ) * c;
                        final RayTraceResult result = rayTraceBlocks(new Vec3d(xOff + xFloor, yOff, zOff + zFloor), vec, world, false, false, false, ignoreWebs, ignoreBeds, terrainCalc, anvils);
                        if (result == null) {
                            ++air;
                        }
                        ++traced;
                    }
                }
            }
            return air / (float)traced;
        }
        return 0.0f;
    }
    
    public static RayTraceResult rayTraceBlocks(final Vec3d start, final Vec3d end, final IBlockAccess world, final boolean stopOnLiquid, final boolean ignoreNoBox, final boolean lastUncollidableBlock, final boolean ignoreWebs, final boolean ignoreBeds, final boolean terrainCalc, final boolean anvils) {
        return RayTracer.trace((World)Util.mc.world, world, start, end, stopOnLiquid, ignoreNoBox, lastUncollidableBlock, (b, p) -> ((!terrainCalc || b.getExplosionResistance((Entity)Util.mc.player) >= 100.0f || p.distanceSq(end.x, end.y, end.z) > 36.0) && (!ignoreBeds || !(b instanceof BlockBed)) && (!ignoreWebs || !(b instanceof BlockWeb))) || (anvils && b instanceof BlockAnvil));
    }
}
