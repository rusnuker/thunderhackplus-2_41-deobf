//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.math;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.block.material.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import java.util.*;

public class ExplosionBuilder
{
    private final World worldObj;
    private final double explosionX;
    private final double explosionY;
    private final double explosionZ;
    private final Entity exploder;
    private final float explosionSize;
    public HashMap<EntityPlayer, Float> damageMap;
    
    public ExplosionBuilder(final World worldIn, final Entity entityIn, final double x, final double y, final double z, final float size) {
        this.damageMap = new HashMap<EntityPlayer, Float>();
        this.worldObj = worldIn;
        this.exploder = entityIn;
        this.explosionSize = size;
        this.explosionX = x;
        this.explosionY = y;
        this.explosionZ = z;
        this.doExplosionA();
    }
    
    public void doExplosionA() {
        final Set<BlockPos> set = (Set<BlockPos>)Sets.newHashSet();
        for (int j = 0; j < 16; ++j) {
            for (int k = 0; k < 16; ++k) {
                for (int l = 0; l < 16; ++l) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        double d0 = j / 15.0f * 2.0f - 1.0f;
                        double d2 = k / 15.0f * 2.0f - 1.0f;
                        double d3 = l / 15.0f * 2.0f - 1.0f;
                        final double d4 = Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
                        d0 /= d4;
                        d2 /= d4;
                        d3 /= d4;
                        float f = this.explosionSize * (0.7f + this.worldObj.rand.nextFloat() * 0.6f);
                        double d5 = this.explosionX;
                        double d6 = this.explosionY;
                        double d7 = this.explosionZ;
                        final float f2 = 0.3f;
                        while (f > 0.0f) {
                            final BlockPos blockpos = new BlockPos(d5, d6, d7);
                            final IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
                            if (iblockstate.getMaterial() != Material.AIR) {
                                final float f3 = iblockstate.getBlock().getExplosionResistance((Entity)null);
                                f -= (f3 + 0.3f) * 0.3f;
                            }
                            if (f > 0.0f && this.exploder == null) {
                                set.add(blockpos);
                            }
                            d5 += d0 * 0.30000001192092896;
                            d6 += d2 * 0.30000001192092896;
                            d7 += d3 * 0.30000001192092896;
                            f -= 0.22500001f;
                        }
                    }
                }
            }
        }
        final float f4 = this.explosionSize * 2.0f;
        final int k2 = MathHelper.floor(this.explosionX - f4 - 1.0);
        final int l2 = MathHelper.floor(this.explosionX + f4 + 1.0);
        final int i2 = MathHelper.floor(this.explosionY - f4 - 1.0);
        final int i3 = MathHelper.floor(this.explosionY + f4 + 1.0);
        final int j2 = MathHelper.floor(this.explosionZ - f4 - 1.0);
        final int j3 = MathHelper.floor(this.explosionZ + f4 + 1.0);
        final List<Entity> list = (List<Entity>)this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB((double)k2, (double)i2, (double)j2, (double)l2, (double)i3, (double)j3));
        final Vec3d vec3d = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);
        for (final Entity entity : list) {
            if (!entity.isImmuneToExplosions()) {
                final double d8 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / f4;
                if (d8 > 1.0) {
                    continue;
                }
                final double d9 = entity.posX - this.explosionX;
                final double d10 = entity.posY + entity.getEyeHeight() - this.explosionY;
                final double d11 = entity.posZ - this.explosionZ;
                final double d12 = MathHelper.sqrt(d9 * d9 + d10 * d10 + d11 * d11);
                if (d12 == 0.0) {
                    continue;
                }
                final double d13 = this.worldObj.getBlockDensity(vec3d, entity.getEntityBoundingBox());
                final double d14 = (1.0 - d8) * d13;
                if (!(entity instanceof EntityPlayer)) {
                    continue;
                }
                this.damageMap.put((EntityPlayer)entity, Float.valueOf((int)((d14 * d14 + d14) / 2.0 * 7.0 * f4 + 1.0)));
            }
        }
    }
}
