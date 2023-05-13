//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.dism;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;

public class ParticleBlood extends Particle
{
    public ParticleBlood(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5, final boolean isPlayer) {
        super(world, d, d1, d2, d3, d4, d5);
        this.particleGravity = 0.06f;
        this.particleRed = 1.0f;
        this.particleGreen = 0.0f;
        this.particleBlue = 0.0f;
        this.particleScale *= 1.2f;
        this.multiplyVelocity(1.2f);
        this.motionY += this.rand.nextFloat() * 0.15f;
        this.motionZ *= 0.4f / (this.rand.nextFloat() * 0.9f + 0.1f);
        this.motionX *= 0.4f / (this.rand.nextFloat() * 0.9f + 0.1f);
        this.particleMaxAge = (int)(200.0f + 20.0f / (this.rand.nextFloat() * 0.9f + 0.1f));
        this.setSize(0.01f, 0.01f);
        this.setParticleTextureIndex(19 + this.rand.nextInt(4));
    }
    
    public void onUpdate() {
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setExpired();
            return;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.motionX != 0.0 && this.motionZ != 0.0 && !this.onGround) {
            this.motionY -= this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.9800000190734863;
            this.motionZ *= 0.9800000190734863;
            if (this.onGround) {
                this.motionX *= 0.699999988079071;
                this.motionZ *= 0.699999988079071;
                this.posY += 0.2;
            }
        }
    }
    
    public int getFXLayer() {
        return 0;
    }
}
