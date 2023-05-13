//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.*;
import java.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.text.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.render.*;

public class DMGParticles extends Module
{
    public final Setting<ColorSetting> color1;
    public final Setting<ColorSetting> color2;
    private final Setting<Float> size;
    private final Setting<Integer> ticks;
    private final HashMap<Integer, Float> healthMap;
    private final ArrayList<Marker> particles;
    
    public DMGParticles() {
        super("DMGParticles", "\u043f\u0430\u0440\u0442\u0438\u043a\u043b\u044b \u0443\u0440\u043e\u043d\u0430", Module.Category.RENDER);
        this.color1 = (Setting<ColorSetting>)this.register(new Setting("HealColor", (T)new ColorSetting(3142544)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("DamageColor", (T)new ColorSetting(15811379)));
        this.size = (Setting<Float>)this.register(new Setting("size", (T)0.5f, (T)0.1f, (T)3.0f));
        this.ticks = (Setting<Integer>)this.register(new Setting("ticks", (T)35, (T)5.0f, (T)60));
        this.healthMap = new HashMap<Integer, Float>();
        this.particles = new ArrayList<Marker>();
    }
    
    public void onDisable() {
        this.particles.clear();
        this.healthMap.clear();
    }
    
    public void onUpdate() {
        synchronized (this.particles) {
            for (final Entity entity : DMGParticles.mc.world.loadedEntityList) {
                if (entity != null && DMGParticles.mc.player.getDistance(entity) <= 10.0f && !entity.isDead) {
                    if (!(entity instanceof EntityLivingBase)) {
                        continue;
                    }
                    final float lastHealth = this.healthMap.getOrDefault(entity.getEntityId(), ((EntityLivingBase)entity).getMaxHealth());
                    this.healthMap.put(entity.getEntityId(), EntityUtil.getHealth(entity));
                    if (lastHealth == EntityUtil.getHealth(entity)) {
                        continue;
                    }
                    this.particles.add(new Marker(entity, lastHealth - EntityUtil.getHealth(entity), entity.posX - 0.5 + new Random(System.currentTimeMillis()).nextInt(5) * 0.1, entity.getEntityBoundingBox().minY + (entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) / 2.0, entity.posZ - 0.5 + new Random(System.currentTimeMillis() + 1L).nextInt(5) * 0.1));
                }
            }
            final ArrayList<Marker> needRemove = new ArrayList<Marker>();
            for (final Marker marker : this.particles) {
                marker.ticks++;
                if (marker.ticks < (float)this.ticks.getValue() && !marker.getEntity().isDead) {
                    continue;
                }
                needRemove.add(marker);
            }
            for (final Marker marker : needRemove) {
                this.particles.remove(marker);
            }
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        synchronized (this.particles) {
            for (final Marker marker : this.particles) {
                final RenderManager renderManager = DMGParticles.mc.getRenderManager();
                double size = this.size.getValue() / marker.getScale() * 2.0f * 0.1;
                size = MathHelper.clamp(size, 0.03, (double)this.size.getValue());
                final double x = marker.posX - ((IRenderManager)renderManager).getRenderPosX();
                final double y = marker.posY - ((IRenderManager)renderManager).getRenderPosY();
                final double z = marker.posZ - ((IRenderManager)renderManager).getRenderPosZ();
                GlStateManager.pushMatrix();
                GlStateManager.enablePolygonOffset();
                GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
                GlStateManager.translate(x, y, z);
                GL11.glEnable(2884);
                GL11.glEnable(3553);
                GL11.glDisable(3042);
                GL11.glEnable(2929);
                GlStateManager.rotate(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                final double textY = (DMGParticles.mc.gameSettings.thirdPersonView == 2) ? -1.0 : 1.0;
                GlStateManager.rotate(renderManager.playerViewX, (float)textY, 0.0f, 0.0f);
                GlStateManager.scale(-size, -size, size);
                GL11.glDepthMask(false);
                final int color = (marker.getHp() > 0.0f) ? this.color1.getValue().getColor() : this.color2.getValue().getColor();
                final DecimalFormat decimalFormat = new DecimalFormat("#.#");
                Util.fr.drawStringWithShadow(decimalFormat.format(marker.getHp()), -(DMGParticles.mc.fontRenderer.getStringWidth(marker.getHp() + "") / 2.0f), (float)(-(DMGParticles.mc.fontRenderer.FONT_HEIGHT - 1)), color);
                GlStateManager.disableBlend();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDepthMask(true);
                GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
                GlStateManager.disablePolygonOffset();
                GlStateManager.popMatrix();
            }
        }
    }
    
    private class Marker
    {
        private final Entity entity;
        private final float hp;
        private final double posX;
        private final double posY;
        private final double posZ;
        private int ticks;
        
        public Marker(final Entity entity, final float hp, final double posX, final double posY, final double posZ) {
            this.ticks = 0;
            this.entity = entity;
            this.hp = hp;
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
        }
        
        public float getScale() {
            return (float)RenderUtil.interpolate(this.ticks, this.ticks - 1, Module.mc.getRenderPartialTicks());
        }
        
        public Entity getEntity() {
            return this.entity;
        }
        
        public float getHp() {
            return -this.hp;
        }
        
        public double getPosX() {
            return this.posX;
        }
        
        public double getPosY() {
            return this.posY;
        }
        
        public double getPosZ() {
            return this.posZ;
        }
        
        public int getTicks() {
            return this.ticks;
        }
    }
}
