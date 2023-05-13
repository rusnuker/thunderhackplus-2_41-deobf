//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.util.math.*;
import java.awt.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.util.render.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;

public class HitParticles2 extends Module
{
    public final Setting<ColorSetting> colorLight;
    public final Setting<ColorSetting> colorLight2;
    public final Setting<ColorSetting> colorLight3;
    public final Setting<ColorSetting> colorLight4;
    public Setting<Boolean> selfp;
    public Setting<Integer> speedor;
    public Setting<Integer> speedor2;
    ArrayList<Particle> particles;
    
    public HitParticles2() {
        super("HitParticles", "HitParticles", Module.Category.RENDER);
        this.colorLight = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.colorLight2 = (Setting<ColorSetting>)this.register(new Setting("Color2", (T)new ColorSetting(-2013200640)));
        this.colorLight3 = (Setting<ColorSetting>)this.register(new Setting("Color3", (T)new ColorSetting(-2013200640)));
        this.colorLight4 = (Setting<ColorSetting>)this.register(new Setting("Color4", (T)new ColorSetting(-2013200640)));
        this.selfp = (Setting<Boolean>)this.register(new Setting("Self", (T)false));
        this.speedor = (Setting<Integer>)this.register(new Setting("Time", (T)8000, (T)1, (T)10000));
        this.speedor2 = (Setting<Integer>)this.register(new Setting("speed", (T)20, (T)1, (T)1000));
        this.particles = new ArrayList<Particle>();
    }
    
    public void onUpdate() {
        if (HitParticles2.mc.world != null && HitParticles2.mc.player != null) {
            for (final EntityPlayer player : HitParticles2.mc.world.playerEntities) {
                if (!this.selfp.getValue() && player == HitParticles2.mc.player) {
                    continue;
                }
                if (player.hurtTime > 0) {
                    Color col = null;
                    final int i = (int)MathUtil.random(0.0f, 3.0f);
                    switch (i) {
                        case 0: {
                            col = this.colorLight.getValue().getColorObject();
                            break;
                        }
                        case 1: {
                            col = this.colorLight2.getValue().getColorObject();
                            break;
                        }
                        case 2: {
                            col = this.colorLight3.getValue().getColorObject();
                            break;
                        }
                        case 3: {
                            col = this.colorLight4.getValue().getColorObject();
                            break;
                        }
                    }
                    this.particles.add(new Particle(player.posX + MathUtil.random(-0.05f, 0.05f), MathUtil.random((float)(player.posY + player.height), (float)player.posY), player.posZ + MathUtil.random(-0.05f, 0.05f), col));
                    this.particles.add(new Particle(player.posX, MathUtil.random((float)(player.posY + player.height), (float)(player.posY + 0.10000000149011612)), player.posZ, col));
                    this.particles.add(new Particle(player.posX, MathUtil.random((float)(player.posY + player.height), (float)(player.posY + 0.10000000149011612)), player.posZ, col));
                }
                for (int j = 0; j < this.particles.size(); ++j) {
                    if (System.currentTimeMillis() - this.particles.get(j).getTime() >= this.speedor.getValue()) {
                        this.particles.remove(j);
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (HitParticles2.mc.player != null && HitParticles2.mc.world != null) {
            for (final Particle particle : this.particles) {
                particle.render();
            }
        }
    }
    
    public class Particle
    {
        public int alpha;
        double x;
        double y;
        double z;
        double motionX;
        double motionY;
        double motionZ;
        long time;
        Color color;
        
        public Particle(final double x, final double y, final double z, final Color color) {
            this.alpha = 180;
            this.x = x;
            this.y = y;
            this.z = z;
            this.motionX = MathUtil.random(-HitParticles2.this.speedor2.getValue() / 1000.0f, HitParticles2.this.speedor2.getValue() / 1000.0f);
            this.motionY = MathUtil.random(-HitParticles2.this.speedor2.getValue() / 1000.0f, HitParticles2.this.speedor2.getValue() / 1000.0f);
            this.motionZ = MathUtil.random(-HitParticles2.this.speedor2.getValue() / 1000.0f, HitParticles2.this.speedor2.getValue() / 1000.0f);
            this.time = System.currentTimeMillis();
            this.color = color;
        }
        
        public long getTime() {
            return this.time;
        }
        
        public void update() {
            final double yEx = 0.0;
            final double sp = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0;
            this.x += this.motionX;
            this.y += this.motionY;
            if (this.posBlock(this.x, this.y, this.z)) {
                this.motionY = -this.motionY / 1.1;
            }
            else if (this.posBlock(this.x, this.y, this.z) || this.posBlock(this.x, this.y - yEx, this.z) || this.posBlock(this.x, this.y + yEx, this.z) || this.posBlock(this.x - sp, this.y, this.z - sp) || this.posBlock(this.x + sp, this.y, this.z + sp) || this.posBlock(this.x + sp, this.y, this.z - sp) || this.posBlock(this.x - sp, this.y, this.z + sp) || this.posBlock(this.x + sp, this.y, this.z) || this.posBlock(this.x - sp, this.y, this.z) || this.posBlock(this.x, this.y, this.z + sp) || this.posBlock(this.x, this.y, this.z - sp) || this.posBlock(this.x - sp, this.y - yEx, this.z - sp) || this.posBlock(this.x + sp, this.y - yEx, this.z + sp) || this.posBlock(this.x + sp, this.y - yEx, this.z - sp) || this.posBlock(this.x - sp, this.y - yEx, this.z + sp) || this.posBlock(this.x + sp, this.y - yEx, this.z) || this.posBlock(this.x - sp, this.y - yEx, this.z) || this.posBlock(this.x, this.y - yEx, this.z + sp) || this.posBlock(this.x, this.y - yEx, this.z - sp) || this.posBlock(this.x - sp, this.y + yEx, this.z - sp) || this.posBlock(this.x + sp, this.y + yEx, this.z + sp) || this.posBlock(this.x + sp, this.y + yEx, this.z - sp) || this.posBlock(this.x - sp, this.y + yEx, this.z + sp) || this.posBlock(this.x + sp, this.y + yEx, this.z) || this.posBlock(this.x - sp, this.y + yEx, this.z) || this.posBlock(this.x, this.y + yEx, this.z + sp) || this.posBlock(this.x, this.y + yEx, this.z - sp)) {
                this.motionX = -this.motionX + this.motionZ;
                this.motionZ = -this.motionZ + this.motionX;
            }
            this.z += this.motionZ;
            this.motionX /= 1.005;
            this.motionZ /= 1.005;
            this.motionY /= 1.005;
        }
        
        public void render() {
            this.color = DrawHelper.injectAlpha(this.color, this.alpha);
            this.update();
            this.alpha -= (int)0.1;
            final float scale = 0.07f;
            GlStateManager.enableDepth();
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glBlendFunc(770, 771);
            try {
                final double posX = this.x - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosX();
                final double posY = this.y - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosY();
                final double posZ = this.z - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosZ();
                final double distanceFromPlayer = Module.mc.player.getDistance(this.x, this.y - 1.0, this.z);
                int quality = (int)(distanceFromPlayer * 4.0 + 10.0);
                if (quality > 350) {
                    quality = 350;
                }
                GL11.glPushMatrix();
                GL11.glTranslated(posX, posY, posZ);
                GL11.glScalef(-scale, -scale, -scale);
                GL11.glRotated((double)(-Module.mc.getRenderManager().playerViewY), 0.0, 1.0, 0.0);
                GL11.glRotated((double)Module.mc.getRenderManager().playerViewX, 1.0, 0.0, 0.0);
                RenderUtil.drawFilledCircleNoGL(0, 0, 0.7, this.color.hashCode(), quality);
                if (distanceFromPlayer < 4.0) {
                    RenderUtil.drawFilledCircleNoGL(0, 0, 1.4, new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 50).hashCode(), quality);
                }
                if (distanceFromPlayer < 20.0) {
                    RenderUtil.drawFilledCircleNoGL(0, 0, 2.3, new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 30).hashCode(), quality);
                }
                GL11.glScalef(0.8f, 0.8f, 0.8f);
                GL11.glPopMatrix();
            }
            catch (ConcurrentModificationException ex) {}
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glColor3d(255.0, 255.0, 255.0);
        }
        
        private boolean posBlock(final double x, final double y, final double z) {
            return Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.AIR && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WATER && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.LAVA && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.BED && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.CAKE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.TALLGRASS && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.FLOWER_POT && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.RED_FLOWER && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.YELLOW_FLOWER && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SAPLING && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.VINE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.ACACIA_FENCE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.ACACIA_FENCE_GATE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.BIRCH_FENCE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.BIRCH_FENCE_GATE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DARK_OAK_FENCE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DARK_OAK_FENCE_GATE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.JUNGLE_FENCE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.JUNGLE_FENCE_GATE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.NETHER_BRICK_FENCE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.OAK_FENCE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.OAK_FENCE_GATE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SPRUCE_FENCE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SPRUCE_FENCE_GATE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.ENCHANTING_TABLE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.END_PORTAL_FRAME && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DOUBLE_PLANT && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.STANDING_SIGN && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WALL_SIGN && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SKULL && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DAYLIGHT_DETECTOR && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DAYLIGHT_DETECTOR_INVERTED && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.STONE_SLAB && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WOODEN_SLAB && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.CARPET && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DEADBUSH && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.VINE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.REDSTONE_WIRE && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.REEDS && Module.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SNOW_LAYER;
        }
    }
}
