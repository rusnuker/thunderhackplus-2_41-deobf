//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import com.mrzak34.thunderhack.util.shaders.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.awt.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.modules.funnygame.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.gui.thundergui2.*;
import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.modules.misc.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.client.resources.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;

public class TargetHud extends HudElement
{
    private static final ResourceLocation thudPic;
    public static BetterDynamicAnimation healthanimation;
    public static BetterDynamicAnimation ebaloAnimation;
    static ResourceLocation customImg;
    private final ArrayList<Particles> particles;
    private final Timer timer;
    public BetterAnimation animation;
    float ticks;
    private final Setting<ColorSetting> color;
    private final Setting<ColorSetting> color2;
    private final Setting<Integer> slices;
    private final Setting<Integer> slices1;
    private final Setting<Integer> slices2;
    private final Setting<Integer> slices3;
    private final Setting<Integer> pcount;
    private final Setting<Float> psize;
    private final Setting<Integer> blurRadius;
    private final Setting<PositionSetting> pos;
    private final Setting<Integer> animX;
    private final Setting<Integer> animY;
    private final Setting<HPmodeEn> hpMode;
    private final Setting<ImageModeEn> imageMode;
    private boolean sentParticles;
    private boolean direction;
    private EntityPlayer target;
    
    public TargetHud() {
        super("TargetHud", "\u041f\u0418\u0417\u0414\u0410\u0422\u0415\u0419\u0428\u0418\u0419", 150, 50);
        this.particles = new ArrayList<Particles>();
        this.timer = new Timer();
        this.animation = new BetterAnimation();
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color1", (T)new ColorSetting(-16492289)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("Color2", (T)new ColorSetting(-2353224)));
        this.slices = (Setting<Integer>)this.register(new Setting("colorOffset1", (T)135, (T)10, (T)500));
        this.slices1 = (Setting<Integer>)this.register(new Setting("colorOffset2", (T)211, (T)10, (T)500));
        this.slices2 = (Setting<Integer>)this.register(new Setting("colorOffset3", (T)162, (T)10, (T)500));
        this.slices3 = (Setting<Integer>)this.register(new Setting("colorOffset4", (T)60, (T)10, (T)500));
        this.pcount = (Setting<Integer>)this.register(new Setting("ParticleCount", (T)20, (T)0, (T)50));
        this.psize = (Setting<Float>)this.register(new Setting("ParticleSize", (T)4.0f, (T)0.1f, (T)15.0f));
        this.blurRadius = (Setting<Integer>)this.register(new Setting("BallonBlur", (T)10, (T)1.0f, (T)10));
        this.pos = (Setting<PositionSetting>)this.register(new Setting("Position", (T)new PositionSetting(0.5f, 0.5f)));
        this.animX = (Setting<Integer>)this.register(new Setting("AnimationX", (T)0, (T)(-2000), (T)2000));
        this.animY = (Setting<Integer>)this.register(new Setting("AnimationY", (T)0, (T)(-2000), (T)2000));
        this.hpMode = (Setting<HPmodeEn>)this.register(new Setting("HP Mode", (T)HPmodeEn.HP));
        this.imageMode = (Setting<ImageModeEn>)this.register(new Setting("Image", (T)ImageModeEn.Anime));
        this.direction = false;
    }
    
    public static void renderTexture(final double x, final double y, final float u, final float v, final int uWidth, final int vHeight, final int width, final int height, final float tileWidth, final float tileHeight) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TargetHud.thudPic);
        GL11.glEnable(3042);
        Gui.drawScaledCustomSizeModalRect((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
        GL11.glDisable(3042);
    }
    
    public static void sizeAnimation(final double width, final double height, final double animation) {
        GL11.glTranslated(width, height, 0.0);
        GL11.glScaled(animation, animation, 1.0);
        GL11.glTranslated(-width, -height, 0.0);
    }
    
    public static String getPotionName(final Potion potion) {
        if (potion == MobEffects.REGENERATION) {
            return "Reg";
        }
        if (potion == MobEffects.STRENGTH) {
            return "Str";
        }
        if (potion == MobEffects.SPEED) {
            return "Spd";
        }
        if (potion == MobEffects.HASTE) {
            return "H";
        }
        if (potion == MobEffects.WEAKNESS) {
            return "W";
        }
        if (potion == MobEffects.RESISTANCE) {
            return "Res";
        }
        return "pon";
    }
    
    public static String getDurationString(final PotionEffect pe) {
        if (pe.getIsPotionDurationMax()) {
            return "*:*";
        }
        final int var1 = pe.getDuration();
        return StringUtils.ticksToElapsedTime(var1);
    }
    
    public static void fastRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, final float radius) {
        float z = 0.0f;
        if (paramXStart > paramXEnd) {
            z = paramXStart;
            paramXStart = paramXEnd;
            paramXEnd = z;
        }
        if (paramYStart > paramYEnd) {
            z = paramYStart;
            paramYStart = paramYEnd;
            paramYEnd = z;
        }
        final double x1 = paramXStart + radius;
        final double y1 = paramYStart + radius;
        final double x2 = paramXEnd - radius;
        final double y2 = paramYEnd - radius;
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(9);
        final double degree = 0.017453292519943295;
        for (double i = 0.0; i <= 90.0; ++i) {
            GL11.glVertex2d(x2 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius);
        }
        for (double i = 90.0; i <= 180.0; ++i) {
            GL11.glVertex2d(x2 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius);
        }
        for (double i = 180.0; i <= 270.0; ++i) {
            GL11.glVertex2d(x1 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius);
        }
        for (double i = 270.0; i <= 360.0; ++i) {
            GL11.glVertex2d(x1 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
    }
    
    public static Color TwoColoreffect(final Color cl1, final Color cl2, final double speed) {
        final double thing = speed / 4.0 % 1.0;
        final float val = MathHelper.clamp((float)Math.sin(18.84955592153876 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
        return new Color(lerp(cl1.getRed() / 255.0f, cl2.getRed() / 255.0f, val), lerp(cl1.getGreen() / 255.0f, cl2.getGreen() / 255.0f, val), lerp(cl1.getBlue() / 255.0f, cl2.getBlue() / 255.0f, val));
    }
    
    public static float lerp(final float a, final float b, final float f) {
        return a + f * (b - a);
    }
    
    public static void renderPlayerModelTexture(final double x, final double y, final float u, final float v, final int uWidth, final int vHeight, final int width, final int height, final float tileWidth, final float tileHeight, final AbstractClientPlayer target) {
        final ResourceLocation skin = target.getLocationSkin();
        Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
        GL11.glEnable(3042);
        Gui.drawScaledCustomSizeModalRect((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
        GL11.glDisable(3042);
    }
    
    @Override
    public void onUpdate() {
        this.animation.update(this.direction);
        TargetHud.healthanimation.update();
        TargetHud.ebaloAnimation.update();
    }
    
    public void renderTHud(final Render2DEvent e) {
        if (Aura.target != null) {
            if (Aura.target instanceof EntityPlayer) {
                this.target = (EntityPlayer)Aura.target;
                this.direction = true;
            }
            else {
                this.target = null;
                this.direction = false;
            }
        }
        else if (C4Aura.target != null) {
            this.target = C4Aura.target;
            this.direction = true;
        }
        else if (AutoExplosion.trgt != null) {
            this.target = AutoExplosion.trgt;
            this.direction = true;
        }
        else if (Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class).getTarget() != null) {
            this.target = Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class).getTarget();
            this.direction = true;
        }
        else if (TargetHud.mc.currentScreen instanceof GuiChat || TargetHud.mc.currentScreen instanceof HudEditorGui || (TargetHud.mc.currentScreen instanceof ThunderGui2 && ThunderGui2.getInstance().current_category == Category.HUD && ThunderGui2.currentMode == ThunderGui2.CurrentMode.Modules)) {
            this.target = (EntityPlayer)TargetHud.mc.player;
            this.direction = true;
        }
        else {
            this.direction = false;
            if (this.animation.getAnimationd() < 0.02) {
                this.target = null;
            }
        }
        if (this.target == null) {
            return;
        }
        GlStateManager.pushMatrix();
        sizeAnimation(this.getPosX() + 75.0f + this.animX.getValue(), this.getPosY() + 25.0f + this.animY.getValue(), this.animation.getAnimationd());
        if (this.animation.getAnimationd() > 0.0) {
            final float hurtPercent = (this.target.hurtTime - TargetHud.mc.getRenderPartialTicks()) / 6.0f;
            Particles.roundedRect((double)this.getPosX(), (double)this.getPosY(), 70.0, 50.0, 12.0, new Color(0, 0, 0, 139));
            Particles.roundedRect((double)(this.getPosX() + 50.0f), (double)this.getPosY(), 100.0, 50.0, 12.0, new Color(0, 0, 0, 255));
            if (this.imageMode.getValue() != ImageModeEn.None) {
                GL11.glPushMatrix();
                Stencil.write(false);
                final boolean texture2 = GL11.glIsEnabled(3553);
                final boolean blend2 = GL11.glIsEnabled(3042);
                GL11.glDisable(3553);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                Particles.roundedRect((double)(this.getPosX() + 50.0f), (double)this.getPosY(), 100.0, 50.0, 12.0, new Color(0, 0, 0, 255));
                if (!blend2) {
                    GL11.glDisable(3042);
                }
                if (texture2) {
                    GL11.glEnable(3553);
                }
                Stencil.erase(true);
                GlStateManager.color(0.3f, 0.3f, 0.3f);
                if (this.imageMode.getValue() == ImageModeEn.Anime) {
                    renderTexture(this.getPosX() + 50.0f, this.getPosY(), 0.0f, 0.0f, 100, 50, 100, 50, 100.0f, 50.0f);
                }
                else {
                    this.renderCustomTexture(this.getPosX() + 50.0f, this.getPosY(), 0.0f, 0.0f, 100, 50, 100, 50, 100.0f, 50.0f);
                }
                Stencil.dispose();
                GL11.glPopMatrix();
                GlStateManager.resetColor();
            }
            for (final Particles p : this.particles) {
                if (p.opacity > 4.0) {
                    p.render2D();
                }
            }
            if (this.timer.passedMs(16L)) {
                this.ticks += 0.1f;
                for (final Particles p : this.particles) {
                    p.updatePosition();
                    if (p.opacity < 1.0) {
                        this.particles.remove(p);
                    }
                }
                this.timer.reset();
            }
            final ArrayList<Particles> removeList = new ArrayList<Particles>();
            for (final Particles p2 : this.particles) {
                if (p2.opacity <= 1.0) {
                    removeList.add(p2);
                }
            }
            for (final Particles p2 : removeList) {
                this.particles.remove(p2);
            }
            if (this.target.hurtTime == 9 && !this.sentParticles) {
                for (int i = 0; i <= this.pcount.getValue(); ++i) {
                    final Particles p2 = new Particles();
                    final Color c = Particles.mixColors(this.color.getValue().getColorObject(), this.color2.getValue().getColorObject(), (Math.sin(this.ticks + this.getPosX() * 0.4f + i) + 1.0) * 0.5);
                    p2.init((double)(this.getPosX() + 19.0f), (double)(this.getPosY() + 19.0f), (Math.random() - 0.5) * 2.0 * 1.4, (Math.random() - 0.5) * 2.0 * 1.4, Math.random() * this.psize.getValue(), c);
                    this.particles.add(p2);
                }
                this.sentParticles = true;
            }
            if (this.target.hurtTime == 8) {
                this.sentParticles = false;
            }
            GL11.glPushMatrix();
            Stencil.write(false);
            final boolean texture3 = GL11.glIsEnabled(3553);
            final boolean blend3 = GL11.glIsEnabled(3042);
            GL11.glDisable(3553);
            GL11.glEnable(3042);
            float hurtPercent2 = hurtPercent;
            TargetHud.ebaloAnimation.setValue(hurtPercent2);
            hurtPercent2 = (float)TargetHud.ebaloAnimation.getAnimationD();
            if (hurtPercent2 < 0.0f && hurtPercent2 > -0.17) {
                hurtPercent2 = 0.0f;
            }
            GL11.glBlendFunc(770, 771);
            fastRoundedRect(this.getPosX() + 5.5f + hurtPercent2, this.getPosY() + 5.5f + hurtPercent2, this.getPosX() + 44.0f - hurtPercent2 * 2.0f, this.getPosY() + 44.0f - hurtPercent2 * 2.0f, 6.0f);
            if (!blend3) {
                GL11.glDisable(3042);
            }
            if (texture3) {
                GL11.glEnable(3553);
            }
            Stencil.erase(true);
            GlStateManager.color(1.0f, 1.0f - hurtPercent, 1.0f - hurtPercent);
            renderPlayerModelTexture(this.getPosX() + 5.5f + hurtPercent2, this.getPosY() + 5.5f + hurtPercent2, 3.0f, 3.0f, 3, 3, (int)(39.0 - hurtPercent2 * 2.0), (int)(39.0 - hurtPercent2 * 2.0), 24.0f, 24.5f, (AbstractClientPlayer)this.target);
            renderPlayerModelTexture(this.getPosX() + 5.5f + hurtPercent2, this.getPosY() + 5.5f + hurtPercent2, 15.0f, 3.0f, 3, 3, (int)(39.0 - hurtPercent2 * 2.0), (int)(39.0 - hurtPercent2 * 2.0), 24.0f, 24.5f, (AbstractClientPlayer)this.target);
            Stencil.dispose();
            GL11.glPopMatrix();
            GlStateManager.resetColor();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            float health = Math.min(20.0f, this.target.getHealth());
            TargetHud.healthanimation.setValue(health);
            health = (float)TargetHud.healthanimation.getAnimationD();
            final Color a = TwoColoreffect(this.color.getValue().getColorObject(), this.color2.getValue().getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0 * (this.slices.getValue() * 2.55) / 60.0);
            final Color b = TwoColoreffect(this.color.getValue().getColorObject(), this.color2.getValue().getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0 * (this.slices1.getValue() * 2.55) / 60.0);
            final Color c2 = TwoColoreffect(this.color.getValue().getColorObject(), this.color2.getValue().getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0 * (this.slices2.getValue() * 2.55) / 60.0);
            final Color d = TwoColoreffect(this.color.getValue().getColorObject(), this.color2.getValue().getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0 * (this.slices3.getValue() * 2.55) / 60.0);
            RenderUtil.drawBlurredShadow(this.getPosX() + 54.0f, this.getPosY() + 34.0f - 12.0f, 90.0f, 8.0f, this.blurRadius.getValue(), a);
            RoundedShader.drawGradientRound(this.getPosX() + 55.0f, this.getPosY() + 35.0f - 12.0f, 90.0f, 8.0f, 2.0f, a.darker().darker(), b.darker().darker().darker().darker(), c2.darker().darker().darker().darker(), d.darker().darker().darker().darker());
            RoundedShader.drawGradientRound(this.getPosX() + 55.0f, this.getPosY() + 35.0f - 12.0f, 90.0f * (health / 20.0f), 8.0f, 2.0f, a, b, c2, d);
            if (this.hpMode.getValue() == HPmodeEn.HP) {
                FontRender.drawCentString6(String.valueOf(Math.round(10.0 * health) / 10.0), this.getPosX() + 100.0f, this.getPosY() + 25.5f, -1);
            }
            else {
                FontRender.drawCentString6(Math.round(10.0 * health) / 10.0 / 20.0 * 100.0 + "%", this.getPosX() + 100.0f, this.getPosY() + 25.5f, -1);
            }
            final NonNullList<ItemStack> armor = (NonNullList<ItemStack>)this.target.inventory.armorInventory;
            final ItemStack[] items = { this.target.getHeldItemMainhand(), (ItemStack)armor.get(3), (ItemStack)armor.get(2), (ItemStack)armor.get(1), (ItemStack)armor.get(0), this.target.getHeldItemOffhand() };
            float xItemOffset = this.getPosX() + 60.0f;
            for (final ItemStack itemStack : items) {
                if (!itemStack.isEmpty()) {
                    GL11.glPushMatrix();
                    GL11.glTranslated((double)xItemOffset, (double)(this.getPosY() + 35.0f), 0.0);
                    GL11.glScaled(0.75, 0.75, 0.75);
                    RenderHelper.enableGUIStandardItemLighting();
                    TargetHud.mc.getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase)TargetHud.mc.player, itemStack, 0, 0);
                    TargetHud.mc.getRenderItem().renderItemOverlays(TargetHud.mc.fontRenderer, itemStack, 0, 0);
                    RenderHelper.disableStandardItemLighting();
                    GL11.glPopMatrix();
                    xItemOffset += 14.0f;
                }
            }
            if (!Thunderhack.moduleManager.getModuleByClass(NameProtect.class).isEnabled()) {
                FontRender.drawString6(this.target.getName() + " | " + Math.round(10.0 * TargetHud.mc.player.getDistance((Entity)this.target)) / 10.0 + " m", this.getPosX() + 55.0f, this.getPosY() + 5.0f, -1, false);
            }
            else {
                FontRender.drawString6("Protected | " + Math.round(10.0 * TargetHud.mc.player.getDistance((Entity)this.target)) / 10.0 + " m", this.getPosX() + 55.0f, this.getPosY() + 5.0f, -1, false);
            }
            this.drawPotionEffect(this.target);
        }
        GlStateManager.popMatrix();
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        GL11.glPushAttrib(1048575);
        this.renderTHud(e);
        GL11.glPopAttrib();
    }
    
    public void renderCustomTexture(final double x, final double y, final float u, final float v, final int uWidth, final int vHeight, final int width, final int height, final float tileWidth, final float tileHeight) {
        if (TargetHud.customImg == null) {
            if (PNGtoResourceLocation.getCustomImg("targethud", "png") != null) {
                TargetHud.customImg = PNGtoResourceLocation.getCustomImg("targethud", "png");
            }
            else {
                Command.sendMessage("\u041f\u0435\u0440\u0435\u0439\u0434\u0438 \u0432 .minecraft/ThunderHack/images \u0438 \u0434\u043e\u0431\u0430\u0432\u044c \u0442\u0443\u0434\u0430 png \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0443 \u0441 \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435\u043c targethud");
                this.toggle();
            }
            return;
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(TargetHud.customImg);
        GL11.glEnable(3042);
        Gui.drawScaledCustomSizeModalRect((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
        GL11.glDisable(3042);
    }
    
    private void drawPotionEffect(final EntityPlayer entity) {
        final StringBuilder finalString = new StringBuilder();
        for (final PotionEffect potionEffect : entity.getActivePotionEffects()) {
            final Potion potion = potionEffect.getPotion();
            if (potion != MobEffects.REGENERATION && potion != MobEffects.SPEED && potion != MobEffects.STRENGTH && potion != MobEffects.WEAKNESS) {
                continue;
            }
            final boolean potRanOut = potionEffect.getDuration() != 0.0;
            if (!entity.isPotionActive(potion)) {
                continue;
            }
            if (!potRanOut) {
                continue;
            }
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            finalString.append(I18n.format(getPotionName(potion), new Object[0])).append((potionEffect.getAmplifier() < 1) ? "" : Integer.valueOf(potionEffect.getAmplifier() + 1)).append(" ").append(getDurationString(potionEffect)).append(" ");
            GlStateManager.popMatrix();
        }
        FontRender.drawString7(finalString.toString(), this.getPosX() + 55.0f, this.getPosY() + 14.0f, new Color(9276813).getRGB(), false);
    }
    
    @Override
    public int normaliseX() {
        return (int)(Mouse.getX() / 2.0f);
    }
    
    @Override
    public int normaliseY() {
        final ScaledResolution sr = new ScaledResolution(TargetHud.mc);
        return (-Mouse.getY() + sr.getScaledHeight() + sr.getScaledHeight()) / 2;
    }
    
    @Override
    public boolean isHovering() {
        return this.normaliseX() > this.getPosX() && this.normaliseX() < this.getPosX() + 150.0f && this.normaliseY() > this.getPosY() && this.normaliseY() < this.getPosY() + 50.0f;
    }
    
    static {
        thudPic = new ResourceLocation("textures/thud.png");
        TargetHud.healthanimation = new BetterDynamicAnimation();
        TargetHud.ebaloAnimation = new BetterDynamicAnimation();
    }
    
    public enum HPmodeEn
    {
        HP, 
        Percentage;
    }
    
    public enum ImageModeEn
    {
        None, 
        Anime, 
        Custom;
    }
}
