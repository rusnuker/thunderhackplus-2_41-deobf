//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.entity.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mrzak34.thunderhack.modules.funnygame.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import com.mrzak34.thunderhack.manager.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.block.*;
import com.mrzak34.thunderhack.util.phobos.*;
import net.minecraft.entity.*;
import java.util.*;
import com.mrzak34.thunderhack.util.rotations.*;
import net.minecraft.entity.monster.*;
import net.minecraft.block.material.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.modules.player.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.util.math.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.enchantment.*;

public class Aura extends Module
{
    public static EntityLivingBase target;
    public static BackTrack.Box bestBtBox;
    public static int CPSLimit;
    public final Setting<Parent> antiCheat;
    private final Setting<rotmod> rotation;
    public final Setting<Float> rotateDistance;
    public final Setting<Float> attackDistance;
    public final Setting<RayTracingMode> rayTracing;
    public final Setting<PointsMode> pointsMode;
    public final Setting<TimingMode> timingMode;
    public final Setting<Integer> minCPS;
    public final Setting<Integer> maxCPS;
    public final Setting<Boolean> rtx;
    public final Setting<Float> walldistance;
    public final Setting<Integer> fov;
    public final Setting<Float> hitboxScale;
    public final Setting<Integer> yawStep;
    public final Setting<Boolean> moveSync;
    public final Setting<Parent> exploits;
    public final Setting<Boolean> resolver;
    public final Setting<Boolean> shieldDesync;
    public final Setting<Boolean> backTrack;
    public final Setting<Boolean> shiftTap;
    public final Setting<Boolean> egravity;
    public final Setting<Parent> misc;
    public final Setting<Boolean> shieldDesyncOnlyOnAura;
    public final Setting<Boolean> criticals;
    public final Setting<CritMode> critMode;
    public final Setting<Float> critdist;
    public final Setting<Boolean> criticals_autojump;
    public final Setting<Boolean> smartCrit;
    public final Setting<Boolean> watercrits;
    public final Setting<Boolean> weaponOnly;
    public final Setting<AutoSwitch> autoswitch;
    public final Setting<Boolean> firstAxe;
    public final Setting<Boolean> clientLook;
    public final Setting<Boolean> snap;
    public final Setting<Boolean> shieldBreaker;
    public final Setting<Boolean> offhand;
    public final Setting<Boolean> teleport;
    public final Setting<Float> tpY;
    public final Setting<Boolean> Debug;
    public final Setting<Parent> targets;
    public final Setting<Boolean> Playersss;
    public final Setting<Boolean> Mobsss;
    public final Setting<Boolean> Animalsss;
    public final Setting<Boolean> Villagersss;
    public final Setting<Boolean> Slimesss;
    public final Setting<Boolean> Crystalsss;
    public final Setting<Boolean> ignoreNaked;
    public final Setting<Boolean> ignoreInvisible;
    public final Setting<Boolean> ignoreCreativ;
    public final Setting<Parent> render;
    public final Setting<Boolean> RTXVisual;
    public final Setting<Boolean> targetesp;
    public final Setting<Float> circleStep1;
    public final Setting<Float> circleHeight;
    public final Setting<Integer> colorOffset1;
    public final Setting<ColorSetting> shitcollor;
    public final Setting<ColorSetting> shitcollor2;
    private final Timer oldTimer;
    private final Timer hitttimer;
    private float prevCircleStep;
    private float circleStep;
    private float prevAdditionYaw;
    private boolean swappedToAxe;
    private boolean swapBack;
    private boolean rotatedBefore;
    private int tickshift;
    private Vec3d last_best_vec;
    private float rotation_smoother;
    public static float rotationPitch;
    public static float rotationYaw;
    float save_rotationYaw;
    
    public Aura() {
        super("Aura", "\u0417\u0430\u043f\u043e\u043c\u043d\u0438\u0442\u0435 \u0431\u043b\u044f\u0434\u044c-\u043a\u0438\u043b\u043b\u043a\u0430 \u0442\u0445 \u043d\u0435 \u043c\u0438\u0441\u0430\u0435\u0442-\u0430 \u0434\u0430\u0435\u0442 \u0448\u0430\u043d\u0441 \u0443\u0431\u0435\u0436\u0430\u0442\u044c", "attacks entities", Category.COMBAT);
        this.antiCheat = (Setting<Parent>)this.register(new Setting("AntiCheat", (T)new Parent(false)));
        this.rotation = this.register(new Setting("Rotation", (T)rotmod.Matrix)).withParent(this.antiCheat);
        this.rotateDistance = this.register(new Setting("RotateDst", (T)1.0f, (T)0.0f, (T)5.0f)).withParent(this.antiCheat);
        this.attackDistance = this.register(new Setting("AttackDst", (T)3.1f, (T)0.0f, (T)7.0f)).withParent(this.antiCheat);
        this.rayTracing = this.register(new Setting("RayTracing", (T)RayTracingMode.NewJitter)).withParent(this.antiCheat);
        this.pointsMode = this.register(new Setting("PointsSort", (T)PointsMode.Distance)).withParent(this.antiCheat);
        this.timingMode = this.register(new Setting("Timing", (T)TimingMode.Default)).withParent(this.antiCheat);
        this.minCPS = this.register(new Setting("MinCPS", (T)10, (T)1, (T)20, v -> this.timingMode.getValue() == TimingMode.Old)).withParent(this.antiCheat);
        this.maxCPS = this.register(new Setting("MaxCPS", (T)12, (T)1, (T)20, v -> this.timingMode.getValue() == TimingMode.Old)).withParent(this.antiCheat);
        this.rtx = this.register(new Setting("RTX", (T)true)).withParent(this.antiCheat);
        this.walldistance = this.register(new Setting("WallDst", (T)3.6f, (T)0.0f, (T)7.0f)).withParent(this.antiCheat);
        this.fov = this.register(new Setting("FOV", (T)180, (T)5, (T)180)).withParent(this.antiCheat);
        this.hitboxScale = this.register(new Setting("RTXScale", (T)2.8f, (T)0.0f, (T)3.0f)).withParent(this.antiCheat);
        this.yawStep = this.register(new Setting("YawStep", (T)80, (T)5, (T)180, v -> this.rotation.getValue() == rotmod.Matrix)).withParent(this.antiCheat);
        this.moveSync = this.register(new Setting("MoveSync", (T)false)).withParent(this.antiCheat);
        this.exploits = (Setting<Parent>)this.register(new Setting("Exploits", (T)new Parent(false)));
        this.resolver = this.register(new Setting("Resolver", (T)false)).withParent(this.exploits);
        this.shieldDesync = this.register(new Setting("Shield Desync", (T)false)).withParent(this.exploits);
        this.backTrack = this.register(new Setting("RotateToBackTrack", (T)true)).withParent(this.exploits);
        this.shiftTap = this.register(new Setting("ShiftTap", (T)false)).withParent(this.exploits);
        this.egravity = this.register(new Setting("ExtraGravity", (T)false)).withParent(this.exploits);
        this.misc = (Setting<Parent>)this.register(new Setting("Misc", (T)new Parent(false)));
        this.shieldDesyncOnlyOnAura = this.register(new Setting("Wait Target", (T)true, v -> this.shieldDesync.getValue())).withParent(this.misc);
        this.criticals = this.register(new Setting("OnlyCrits", (T)true)).withParent(this.misc);
        this.critMode = this.register(new Setting("CritMode", (T)CritMode.WexSide, v -> this.criticals.getValue())).withParent(this.misc);
        this.critdist = this.register(new Setting("FallDistance", (T)0.15f, (T)0.0f, (T)1.0f, v -> this.criticals.getValue() && this.critMode.getValue() == CritMode.Simple)).withParent(this.misc);
        this.criticals_autojump = this.register(new Setting("AutoJump", (T)false, v -> this.criticals.getValue())).withParent(this.misc);
        this.smartCrit = this.register(new Setting("SpaceOnly", (T)true, v -> this.criticals.getValue())).withParent(this.misc);
        this.watercrits = this.register(new Setting("WaterCrits", (T)false, v -> this.criticals.getValue())).withParent(this.misc);
        this.weaponOnly = this.register(new Setting("WeaponOnly", (T)true)).withParent(this.misc);
        this.autoswitch = this.register(new Setting("AutoSwitch", (T)AutoSwitch.None)).withParent(this.misc);
        this.firstAxe = this.register(new Setting("FirstAxe", (T)false, v -> this.autoswitch.getValue() != AutoSwitch.None)).withParent(this.misc);
        this.clientLook = this.register(new Setting("ClientLook", (T)false)).withParent(this.misc);
        this.snap = this.register(new Setting("Snap", (T)false)).withParent(this.misc);
        this.shieldBreaker = this.register(new Setting("ShieldBreaker", (T)true)).withParent(this.misc);
        this.offhand = this.register(new Setting("OffHandAttack", (T)false)).withParent(this.misc);
        this.teleport = this.register(new Setting("TP", (T)false)).withParent(this.misc);
        this.tpY = this.register(new Setting("TPY", (T)3.0f, (T)(-5.0f), (T)5.0f, v -> this.teleport.getValue())).withParent(this.misc);
        this.Debug = this.register(new Setting("HitsDebug", (T)false)).withParent(this.misc);
        this.targets = (Setting<Parent>)this.register(new Setting("Targets", (T)new Parent(false)));
        this.Playersss = this.register(new Setting("Players", (T)true)).withParent(this.targets);
        this.Mobsss = this.register(new Setting("Mobs", (T)true)).withParent(this.targets);
        this.Animalsss = this.register(new Setting("Animals", (T)true)).withParent(this.targets);
        this.Villagersss = this.register(new Setting("Villagers", (T)true)).withParent(this.targets);
        this.Slimesss = this.register(new Setting("Slimes", (T)true)).withParent(this.targets);
        this.Crystalsss = this.register(new Setting("Crystals", (T)true)).withParent(this.targets);
        this.ignoreNaked = this.register(new Setting("IgnoreNaked", (T)false)).withParent(this.targets);
        this.ignoreInvisible = this.register(new Setting("IgnoreInvis", (T)false)).withParent(this.targets);
        this.ignoreCreativ = this.register(new Setting("IgnoreCreativ", (T)true)).withParent(this.targets);
        this.render = (Setting<Parent>)this.register(new Setting("Render", (T)new Parent(false)));
        this.RTXVisual = this.register(new Setting("RTXVisual", (T)false)).withParent(this.render);
        this.targetesp = this.register(new Setting("Target Esp", (T)true)).withParent(this.render);
        this.circleStep1 = this.register(new Setting("CircleSpeed", (T)0.15f, (T)0.1f, (T)1.0f)).withParent(this.render);
        this.circleHeight = this.register(new Setting("CircleHeight", (T)0.15f, (T)0.1f, (T)1.0f)).withParent(this.render);
        this.colorOffset1 = this.register(new Setting("ColorOffset", (T)10, (T)1, (T)20)).withParent(this.render);
        this.shitcollor = this.register(new Setting("TargetColor", (T)new ColorSetting(-2009289807))).withParent(this.render);
        this.shitcollor2 = this.register(new Setting("TargetColor2", (T)new ColorSetting(-2009289807))).withParent(this.render);
        this.oldTimer = new Timer();
        this.hitttimer = new Timer();
    }
    
    @SubscribeEvent
    public void onCalc(final PlayerUpdateEvent e) {
        if (this.targetesp.getValue()) {
            this.prevCircleStep = this.circleStep;
            this.circleStep += this.circleStep1.getValue();
        }
        if (this.firstAxe.getValue() && this.hitttimer.passedMs(3000L) && InventoryUtil.getBestAxe() != -1) {
            if (this.autoswitch.getValue() == AutoSwitch.Default) {
                Aura.mc.player.inventory.currentItem = InventoryUtil.getBestAxe();
                this.swappedToAxe = true;
            }
        }
        else if (this.autoswitch.getValue() == AutoSwitch.Default) {
            if (InventoryUtil.getBestSword() != -1) {
                Aura.mc.player.inventory.currentItem = InventoryUtil.getBestSword();
            }
            else if (InventoryUtil.getBestAxe() != -1) {
                Aura.mc.player.inventory.currentItem = InventoryUtil.getBestAxe();
            }
        }
        if (Aura.CPSLimit > 0) {
            --Aura.CPSLimit;
        }
        boolean shieldDesyncActive = this.shieldDesync.getValue();
        if (this.shieldDesyncOnlyOnAura.getValue() && Aura.target == null) {
            shieldDesyncActive = false;
        }
        if (isActiveItemStackBlocking((EntityPlayer)Aura.mc.player, 4 + new Random().nextInt(4)) && shieldDesyncActive && Aura.mc.player.isHandActive()) {
            Aura.mc.playerController.onStoppedUsingItem((EntityPlayer)Aura.mc.player);
        }
        if (Aura.target != null) {
            if (Aura.target instanceof EntityOtherPlayerMP && this.resolver.getValue()) {
                ResolverUtil.resolve((EntityOtherPlayerMP)Aura.target);
            }
            if (!this.isEntityValid(Aura.target, false)) {
                Aura.target = null;
                ResolverUtil.reset();
            }
        }
        if (this.Crystalsss.getValue()) {
            for (final Entity entity : Aura.mc.world.loadedEntityList) {
                if (entity instanceof EntityEnderCrystal && this.getVector(entity) != null && this.needExplosion(entity.getPositionVector())) {
                    if (this.oldTimer.passedMs(100L)) {
                        this.attack(entity);
                        this.oldTimer.reset();
                    }
                    return;
                }
            }
        }
        if (Aura.target == null) {
            ResolverUtil.reset();
            Aura.target = this.findTarget();
        }
        if (Aura.target == null || Aura.mc.player.getDistanceSq((Entity)Aura.target) > this.attackDistance.getPow2Value()) {
            final BackTrack bt = (BackTrack)Thunderhack.moduleManager.getModuleByClass((Class)BackTrack.class);
            if (bt.isOn() && this.backTrack.getValue()) {
                float best_distance = 100.0f;
                for (final EntityPlayer BTtarget : Aura.mc.world.playerEntities) {
                    if (Aura.mc.player.getDistanceSq((Entity)BTtarget) > 100.0) {
                        continue;
                    }
                    if (!this.isEntityValid((EntityLivingBase)BTtarget, true)) {
                        continue;
                    }
                    if (((IEntity)BTtarget).getPosition_history().size() == 0) {
                        continue;
                    }
                    for (final BackTrack.Box box : ((IEntity)BTtarget).getPosition_history()) {
                        if (this.getDistanceBT(box) < best_distance) {
                            best_distance = this.getDistanceBT(box);
                            if (Aura.target != null && best_distance < Aura.mc.player.getDistanceSq((Entity)Aura.target)) {
                                Aura.target = (EntityLivingBase)BTtarget;
                            }
                            else {
                                if (Aura.target != null || best_distance >= this.attackDistance.getPow2Value()) {
                                    continue;
                                }
                                Aura.target = (EntityLivingBase)BTtarget;
                            }
                        }
                    }
                }
            }
        }
        if (Aura.target == null) {
            return;
        }
        if (this.weaponOnly.getValue() && !(Aura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && !(Aura.mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe)) {
            return;
        }
        if (this.shiftTap.getValue()) {
            if (!Aura.mc.player.onGround && Aura.mc.player.fallDistance > 0.0f && Aura.mc.player.fallDistance < 0.7) {
                ((IKeyBinding)Aura.mc.gameSettings.keyBindSneak).setPressed(true);
            }
            else {
                ((IKeyBinding)Aura.mc.gameSettings.keyBindSneak).setPressed(false);
            }
        }
        this.rotatedBefore = false;
        this.attack((Entity)Aura.target);
        if (!this.rotatedBefore) {
            this.rotate((Entity)Aura.target, false);
        }
        if (Aura.target != null && this.resolver.getValue() && Aura.target instanceof EntityOtherPlayerMP) {
            ResolverUtil.releaseResolver((EntityOtherPlayerMP)Aura.target);
        }
    }
    
    @SubscribeEvent
    public void onRotate(final EventSync e) {
        if (Aura.target != null) {
            if (this.egravity.getValue() && !Aura.mc.player.onGround && Aura.mc.player.fallDistance > 0.0f && this.criticals.getValue()) {
                final EntityPlayerSP player = Aura.mc.player;
                player.motionY -= 0.03;
            }
            if (this.rotation.getValue() != rotmod.None) {
                Aura.mc.player.rotationYaw = Aura.rotationYaw;
                Aura.mc.player.rotationPitch = Aura.rotationPitch;
                Aura.mc.player.rotationYawHead = Aura.rotationYaw;
                Aura.mc.player.renderYawOffset = Aura.rotationYaw;
            }
        }
        else {
            Aura.rotationYaw = Aura.mc.player.rotationYaw;
            Aura.rotationPitch = Aura.mc.player.rotationPitch;
        }
    }
    
    @Override
    public void onEnable() {
        Aura.rotationYaw = Aura.mc.player.rotationYaw;
        Aura.rotationPitch = Aura.mc.player.rotationPitch;
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent e) {
        if (this.targetesp.getValue()) {
            final EntityLivingBase entity = Aura.target;
            if (entity != null) {
                final double cs = this.prevCircleStep + (this.circleStep - this.prevCircleStep) * Aura.mc.getRenderPartialTicks();
                final double prevSinAnim = absSinAnimation(cs - this.circleHeight.getValue());
                final double sinAnim = absSinAnimation(cs);
                final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Aura.mc.getRenderPartialTicks() - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosX();
                final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Aura.mc.getRenderPartialTicks() - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosY() + prevSinAnim * 1.399999976158142;
                final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Aura.mc.getRenderPartialTicks() - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosZ();
                final double nextY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Aura.mc.getRenderPartialTicks() - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosY() + sinAnim * 1.399999976158142;
                GL11.glPushMatrix();
                final boolean cullface = GL11.glIsEnabled(2884);
                final boolean texture = GL11.glIsEnabled(3553);
                final boolean blend = GL11.glIsEnabled(3042);
                final boolean depth = GL11.glIsEnabled(2929);
                final boolean alpha = GL11.glIsEnabled(3008);
                GL11.glDisable(2884);
                GL11.glDisable(3553);
                GL11.glEnable(3042);
                GL11.glDisable(2929);
                GL11.glDisable(3008);
                GL11.glShadeModel(7425);
                GL11.glBegin(8);
                for (int i = 0; i <= 360; ++i) {
                    final Color clr = this.getTargetColor(this.shitcollor.getValue().getColorObject(), this.shitcollor2.getValue().getColorObject(), i);
                    GL11.glColor4f(clr.getRed() / 255.0f, clr.getGreen() / 255.0f, clr.getBlue() / 255.0f, 0.6f);
                    GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * entity.width * 0.8, nextY, z + Math.sin(Math.toRadians(i)) * entity.width * 0.8);
                    GL11.glColor4f(clr.getRed() / 255.0f, clr.getGreen() / 255.0f, clr.getBlue() / 255.0f, 0.01f);
                    GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * entity.width * 0.8, y, z + Math.sin(Math.toRadians(i)) * entity.width * 0.8);
                }
                GL11.glEnd();
                GL11.glEnable(2848);
                GL11.glBegin(2);
                for (int i = 0; i <= 360; ++i) {
                    final Color clr = this.getTargetColor(this.shitcollor.getValue().getColorObject(), this.shitcollor2.getValue().getColorObject(), i);
                    GL11.glColor4f(clr.getRed() / 255.0f, clr.getGreen() / 255.0f, clr.getBlue() / 255.0f, 1.0f);
                    GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * entity.width * 0.8, nextY, z + Math.sin(Math.toRadians(i)) * entity.width * 0.8);
                }
                GL11.glEnd();
                if (!cullface) {
                    GL11.glDisable(2848);
                }
                if (texture) {
                    GL11.glEnable(3553);
                }
                if (depth) {
                    GL11.glEnable(2929);
                }
                GL11.glShadeModel(7424);
                if (!blend) {
                    GL11.glDisable(3042);
                }
                if (cullface) {
                    GL11.glEnable(2884);
                }
                if (alpha) {
                    GL11.glEnable(3008);
                }
                GL11.glPopMatrix();
                GlStateManager.resetColor();
                if (this.RTXVisual.getValue()) {
                    GlStateManager.pushMatrix();
                    final Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Aura.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Aura.mc.player.rotationYaw));
                    if (this.rayTracing.getValue() == RayTracingMode.Beta) {
                        final Vec3d point = RayTracingUtils.getVecTarget((Entity)Aura.target, this.attackDistance.getValue() + this.rotateDistance.getValue());
                        if (point == null) {
                            return;
                        }
                        Search.renderTracer(eyes.x, eyes.y + Aura.mc.player.getEyeHeight(), eyes.z, point.x - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosX(), point.y - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosY(), point.z - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosZ(), new Color(-369062144, true).getRGB());
                    }
                    else {
                        for (final Vec3d point2 : RayTracingUtils.getHitBoxPoints(Aura.target.getPositionVector(), this.hitboxScale.getValue() / 10.0f)) {
                            if (!this.isPointVisible((Entity)Aura.target, point2, this.attackDistance.getValue() + this.rotateDistance.getValue())) {
                                Search.renderTracer(eyes.x, eyes.y + Aura.mc.player.getEyeHeight(), eyes.z, point2.x - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosX(), point2.y - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosY(), point2.z - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosZ(), new Color(-361861522, true).getRGB());
                            }
                            else {
                                Search.renderTracer(eyes.x, eyes.y + Aura.mc.player.getEyeHeight(), eyes.z, point2.x - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosX(), point2.y - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosY(), point2.z - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosZ(), new Color(-939491283, true).getRGB());
                            }
                        }
                    }
                    if (this.last_best_vec != null) {
                        Search.renderTracer(eyes.x, eyes.y + Aura.mc.player.getEyeHeight(), eyes.z, this.last_best_vec.x - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosX(), this.last_best_vec.y - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosY(), this.last_best_vec.z - ((IRenderManager)Aura.mc.getRenderManager()).getRenderPosZ(), new Color(-369033384, true).getRGB());
                    }
                    GlStateManager.popMatrix();
                    GlStateManager.resetColor();
                }
            }
        }
    }
    
    @Override
    public void onDisable() {
        Aura.target = null;
        if (EffectsRemover.jboost && Aura.mc.player.isPotionActive(MobEffects.STRENGTH)) {
            Aura.mc.player.addPotionEffect(new PotionEffect((Potion)Objects.requireNonNull(Potion.getPotionById(8)), EffectsRemover.nig, 1));
        }
    }
    
    public boolean isPointVisible(final Entity target, final Vec3d vector, final double dst) {
        return RayTracingUtils.getPointedEntity(getRotationForCoord(vector), dst, !this.ignoreWalls(target), target) == target;
    }
    
    public void attack(final Entity base) {
        if ((base instanceof EntityEnderCrystal || this.canAttack()) && this.getVector(base) != null) {
            this.rotate(base, true);
            if (RayTracingUtils.getMouseOver(base, Aura.rotationYaw, Aura.rotationPitch, this.attackDistance.getValue(), this.ignoreWalls(base)) == base || (base instanceof EntityEnderCrystal && Aura.mc.player.getDistanceSq(base) <= 20.0) || (this.backTrack.getValue() && Aura.bestBtBox != null) || !this.rtx.getValue() || this.rotation.getValue() == rotmod.None) {
                if (this.teleport.getValue()) {
                    Aura.mc.player.setPosition(base.posX, base.posY + this.tpY.getValue(), base.posZ);
                }
                final boolean blocking = Aura.mc.player.isHandActive() && Aura.mc.player.getActiveItemStack().getItem().getItemUseAction(Aura.mc.player.getActiveItemStack()) == EnumAction.BLOCK;
                if (blocking) {
                    Aura.mc.playerController.onStoppedUsingItem((EntityPlayer)Aura.mc.player);
                }
                boolean needSwap = false;
                if (EventManager.serversprint) {
                    Aura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Aura.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                    needSwap = true;
                }
                if (this.snap.getValue()) {
                    Aura.mc.player.rotationPitch = Aura.rotationPitch;
                    Aura.mc.player.rotationYaw = Aura.rotationYaw;
                }
                if (this.Debug.getValue() && Aura.target != null && this.last_best_vec != null) {
                    Command.sendMessage("Attacked with delay: " + this.hitttimer.getPassedTimeMs() + " | Distance to target: " + Aura.mc.player.getDistance((Entity)Aura.target) + " | Distance to best point: " + Aura.mc.player.getDistance(this.last_best_vec.x, this.last_best_vec.y, this.last_best_vec.z) + " | CAS: " + this.getCooledAttackStrength() + " | Possible damage " + this.getDamage((Entity)Aura.target));
                }
                Aura.mc.playerController.attackEntity((EntityPlayer)Aura.mc.player, base);
                this.hitttimer.reset();
                Aura.mc.player.swingArm(((boolean)this.offhand.getValue()) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                if (InventoryUtil.getBestAxe() >= 0 && this.shieldBreaker.getValue() && base instanceof EntityPlayer && isActiveItemStackBlocking((EntityPlayer)base, 1)) {
                    Aura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(InventoryUtil.getBestAxe()));
                    Aura.mc.playerController.attackEntity((EntityPlayer)Aura.mc.player, base);
                    Aura.mc.player.swingArm(EnumHand.MAIN_HAND);
                    Aura.mc.player.resetCooldown();
                    Aura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Aura.mc.player.inventory.currentItem));
                }
                if (blocking) {
                    Aura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(Aura.mc.player.getActiveHand()));
                }
                if (needSwap) {
                    Aura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Aura.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                }
                if (this.swappedToAxe) {
                    this.swapBack = true;
                    this.swappedToAxe = false;
                }
                Aura.CPSLimit = 10;
            }
        }
    }
    
    @SubscribeEvent
    public void onPostAttack(final EventPostSync e) {
        if (this.firstAxe.getValue() && InventoryUtil.getBestSword() != -1 && this.swapBack && this.autoswitch.getValue() == AutoSwitch.Default) {
            Aura.mc.player.inventory.currentItem = InventoryUtil.getBestSword();
            this.swapBack = false;
        }
        if (this.clientLook.getValue()) {
            Aura.mc.player.rotationYaw = Aura.rotationYaw;
            Aura.mc.player.rotationPitch = Aura.rotationPitch;
        }
    }
    
    public float getDistanceBT(final BackTrack.Box box) {
        final float f = (float)(Aura.mc.player.posX - box.getPosition().x);
        final float f2 = (float)(Aura.mc.player.getPositionEyes(1.0f).y - box.getPosition().y);
        final float f3 = (float)(Aura.mc.player.posZ - box.getPosition().z);
        return f * f + f2 * f2 + f3 * f3;
    }
    
    public float getDistanceBTPoint(final Vec3d point) {
        final float f = (float)(Aura.mc.player.posX - point.x);
        final float f2 = (float)(Aura.mc.player.getPositionEyes(1.0f).y - point.y);
        final float f3 = (float)(Aura.mc.player.posZ - point.z);
        return f * f + f2 * f2 + f3 * f3;
    }
    
    public boolean isNakedPlayer(final EntityLivingBase base) {
        return base instanceof EntityOtherPlayerMP && base.getTotalArmorValue() == 0;
    }
    
    public boolean isInvisible(final EntityLivingBase base) {
        return base instanceof EntityOtherPlayerMP && base.isInvisible();
    }
    
    public boolean needExplosion(final Vec3d position) {
        final ExplosionBuilder builder = new ExplosionBuilder((World)Aura.mc.world, null, position.x, position.y, position.z, 6.0f);
        boolean needExplosion = false;
        for (final Map.Entry<EntityPlayer, Float> entry : builder.damageMap.entrySet()) {
            if (Thunderhack.friendManager.isFriend(entry.getKey().getName()) && entry.getValue() > entry.getKey().getHealth()) {
                return false;
            }
            if (entry.getKey() == Aura.mc.player && entry.getValue() > 25.0f) {
                return false;
            }
            if (entry.getValue() <= 35.0f) {
                continue;
            }
            needExplosion = true;
        }
        return needExplosion;
    }
    
    public boolean canAttack() {
        final boolean reasonForCancelCritical = Aura.mc.player.isOnLadder() || isInLiquid() || ((IEntity)Aura.mc.player).isInWeb() || (this.smartCrit.getValue() && !this.criticals_autojump.getValue() && !Aura.mc.gameSettings.keyBindJump.isKeyDown());
        if (this.timingMode.getValue() == TimingMode.Default) {
            if (Aura.CPSLimit > 0) {
                return false;
            }
            if (Aura.mc.player.getCooledAttackStrength(1.5f) <= 0.93) {
                return false;
            }
        }
        else if (!this.oldTimer.passedMs((long)((1000.0f + (MathUtil.random(1.0f, 50.0f) - MathUtil.random(1.0f, 60.0f) + MathUtil.random(1.0f, 70.0f))) / (int)MathUtil.random(this.minCPS.getValue(), this.maxCPS.getValue())))) {
            return false;
        }
        if (this.last_best_vec != null && RayTracingUtils.getDistanceFromHead(new Vec3d(this.last_best_vec.x, this.last_best_vec.y, this.last_best_vec.z)) > this.attackDistance.getPow2Value()) {
            return false;
        }
        if (this.criticals.getValue() && this.watercrits.getValue() && Aura.mc.world.getBlockState(new BlockPos(Aura.mc.player.posX, Aura.mc.player.posY, Aura.mc.player.posZ)).getBlock() instanceof BlockLiquid && Aura.mc.world.getBlockState(new BlockPos(Aura.mc.player.posX, Aura.mc.player.posY + 1.0, Aura.mc.player.posZ)).getBlock() instanceof BlockAir) {
            return Aura.mc.player.fallDistance > 0.0f;
        }
        if (this.criticals.getValue() && !reasonForCancelCritical) {
            if (this.critMode.getValue() == CritMode.WexSide) {
                final EntityPlayerSP client = Aura.mc.player;
                final int r = (int)Aura.mc.player.posY;
                final int c = (int)Math.ceil(Aura.mc.player.posY);
                return (r != c && Aura.mc.player.onGround && isBlockAboveHead()) || (!client.onGround && client.fallDistance > 0.0f);
            }
            if (this.critMode.getValue() == CritMode.Simple) {
                if (isBlockAboveHead()) {
                    if (Aura.mc.player.fallDistance <= 0.0f) {
                        return false;
                    }
                }
                else if (Aura.mc.player.fallDistance < this.critdist.getValue()) {
                    return false;
                }
                if (!Aura.mc.player.onGround) {
                    return true;
                }
                return false;
            }
        }
        this.oldTimer.reset();
        return true;
    }
    
    private float getCooledAttackStrength() {
        return MathHelper.clamp((((IEntityLivingBase)Aura.mc.player).getTicksSinceLastSwing() + 1.5f) / this.getCooldownPeriod(), 0.0f, 1.0f);
    }
    
    public float getCooldownPeriod() {
        return (float)(1.0 / Aura.mc.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue() * (20.0f * Thunderhack.TICK_TIMER));
    }
    
    public EntityLivingBase findTarget() {
        final List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        for (final Entity entity : Aura.mc.world.loadedEntityList) {
            if (entity instanceof EntityLivingBase && this.isEntityValid((EntityLivingBase)entity, false)) {
                targets.add((EntityLivingBase)entity);
            }
        }
        final int dst1;
        final int dst2;
        targets.sort((e1, e2) -> {
            dst1 = (int)(Aura.mc.player.getDistance(e1) * 1000.0f);
            dst2 = (int)(Aura.mc.player.getDistance(e2) * 1000.0f);
            return dst1 - dst2;
        });
        return targets.isEmpty() ? null : targets.get(0);
    }
    
    public boolean isEntityValid(final EntityLivingBase entity, final boolean backtrack) {
        if (this.ignoreNaked.getValue() && this.isNakedPlayer(entity)) {
            return false;
        }
        if (this.ignoreInvisible.getValue() && this.isInvisible(entity)) {
            return false;
        }
        if (this.ignoreCreativ.getValue() && entity instanceof EntityPlayer && ((EntityPlayer)entity).isCreative()) {
            return false;
        }
        if (entity.getHealth() <= 0.0f) {
            return false;
        }
        if (AntiBot.bots.contains(entity)) {
            return false;
        }
        if (!this.targetsCheck(entity)) {
            return false;
        }
        if (backtrack) {
            return true;
        }
        if (!this.ignoreWalls((Entity)entity)) {
            return this.getVector((Entity)entity) != null;
        }
        return Aura.mc.player.getDistanceSq((Entity)entity) <= Math.pow(this.attackDistance.getValue() + this.rotateDistance.getValue(), 2.0);
    }
    
    public Vec3d getVector(final Entity target) {
        final BackTrack bt = (BackTrack)Thunderhack.moduleManager.getModuleByClass((Class)BackTrack.class);
        if (!this.backTrack.getValue() || Aura.mc.player.getDistanceSq(target) <= this.attackDistance.getPow2Value() || bt.isOff() || !(target instanceof EntityPlayer) || (this.backTrack.getValue() && ((IEntity)target).getPosition_history().size() == 0)) {
            if (this.rayTracing.getValue() == RayTracingMode.Beta) {
                return RayTracingUtils.getVecTarget(target, this.attackDistance.getValue() + this.rotateDistance.getValue());
            }
            final ArrayList<Vec3d> points = RayTracingUtils.getHitBoxPoints(target.getPositionVector(), this.hitboxScale.getValue() / 10.0f);
            points.removeIf(point -> !this.isPointVisible(target, point, this.attackDistance.getValue() + this.rotateDistance.getValue()));
            if (points.isEmpty()) {
                return null;
            }
            float best_distance = 100.0f;
            Vec3d best_point = null;
            float best_angle = 180.0f;
            if (this.pointsMode.getValue() == PointsMode.Angle) {
                for (final Vec3d point2 : points) {
                    final Vec2f r = getDeltaForCoord(new Vec2f(Aura.rotationYaw, Aura.rotationPitch), point2);
                    final float y = Math.abs(r.y);
                    if (y < best_angle) {
                        best_angle = y;
                        best_point = point2;
                    }
                }
            }
            else {
                for (final Vec3d point2 : points) {
                    if (RayTracingUtils.getDistanceFromHead(point2) < best_distance) {
                        best_point = point2;
                        best_distance = RayTracingUtils.getDistanceFromHead(point2);
                    }
                }
            }
            return this.last_best_vec = best_point;
        }
        else {
            Aura.bestBtBox = null;
            float best_distance2 = 36.0f;
            BackTrack.Box best_box = null;
            for (final BackTrack.Box boxes : ((IEntity)target).getPosition_history()) {
                if (this.getDistanceBT(boxes) < best_distance2) {
                    best_box = boxes;
                    best_distance2 = this.getDistanceBT(boxes);
                }
            }
            if (best_box == null) {
                return null;
            }
            Aura.bestBtBox = best_box;
            final ArrayList<Vec3d> points2 = RayTracingUtils.getHitBoxPoints(best_box.getPosition(), this.hitboxScale.getValue() / 10.0f);
            points2.removeIf(point -> this.getDistanceBTPoint(point) > Math.pow(this.attackDistance.getValue() + this.rotateDistance.getValue(), 2.0));
            if (points2.isEmpty()) {
                return null;
            }
            float best_distance3 = 100.0f;
            Vec3d best_point2 = null;
            float best_angle2 = 180.0f;
            if (this.pointsMode.getValue() == PointsMode.Angle) {
                for (final Vec3d point3 : points2) {
                    final Vec2f r2 = getDeltaForCoord(new Vec2f(Aura.rotationYaw, Aura.rotationPitch), point3);
                    final float y2 = Math.abs(r2.y);
                    if (y2 < best_angle2) {
                        best_angle2 = y2;
                        best_point2 = point3;
                    }
                }
            }
            else {
                for (final Vec3d point3 : points2) {
                    if (RayTracingUtils.getDistanceFromHead(point3) < best_distance3) {
                        best_point2 = point3;
                        best_distance3 = RayTracingUtils.getDistanceFromHead(point3);
                    }
                }
            }
            return this.last_best_vec = best_point2;
        }
    }
    
    public boolean targetsCheck(final EntityLivingBase entity) {
        final CastHelper castHelper = new CastHelper();
        if (this.Playersss.getValue()) {
            castHelper.apply(CastHelper.EntityType.PLAYERS);
        }
        if (this.Mobsss.getValue()) {
            castHelper.apply(CastHelper.EntityType.MOBS);
        }
        if (this.Animalsss.getValue()) {
            castHelper.apply(CastHelper.EntityType.ANIMALS);
        }
        if (this.Villagersss.getValue()) {
            castHelper.apply(CastHelper.EntityType.VILLAGERS);
        }
        if (entity instanceof EntitySlime) {
            return this.Slimesss.getValue();
        }
        return CastHelper.isInstanceof((Entity)entity, castHelper.build()) != null && !entity.isDead;
    }
    
    public boolean ignoreWalls(final Entity input) {
        return input instanceof EntityEnderCrystal || Aura.mc.world.getBlockState(new BlockPos(Aura.mc.player.posX, Aura.mc.player.posY, Aura.mc.player.posZ)).getMaterial() != Material.AIR || Aura.mc.player.getDistanceSq(input) <= this.walldistance.getPow2Value();
    }
    
    public void rotate(final Entity base, final boolean attackContext) {
        this.rotatedBefore = true;
        Vec3d bestVector = this.getVector(base);
        if (bestVector == null) {
            bestVector = base.getPositionEyes(1.0f);
        }
        final boolean inside_target = Aura.mc.player.getEntityBoundingBox().intersects(base.getEntityBoundingBox());
        if (this.rotation.getValue() == rotmod.Matrix3 && inside_target) {
            bestVector = base.getPositionVector().add(new Vec3d(0.0, (double)interpolateRandom(0.7f, 0.9f), 0.0));
        }
        final double x = bestVector.x - Aura.mc.player.posX;
        final double y = bestVector.y - Aura.mc.player.getPositionEyes(1.0f).y;
        final double z = bestVector.z - Aura.mc.player.posZ;
        final double dst = Math.sqrt(Math.pow(x, 2.0) + Math.pow(z, 2.0));
        final float yawToTarget = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(z, x)) - 90.0);
        final float pitchToTarget = (float)(-Math.toDegrees(Math.atan2(y, dst)));
        final float sensitivity = 1.0001f;
        float yawDelta = MathHelper.wrapDegrees(yawToTarget - Aura.rotationYaw) / sensitivity;
        final float pitchDelta = (pitchToTarget - Aura.rotationPitch) / sensitivity;
        if (yawDelta > 180.0f) {
            yawDelta -= 180.0f;
        }
        final int yawDeltaAbs = (int)Math.abs(yawDelta);
        if (yawDeltaAbs < this.fov.getValue()) {
            switch (this.rotation.getValue()) {
                case Matrix: {
                    final float pitchDeltaAbs = Math.abs(pitchDelta);
                    float additionYaw = (float)Math.min(Math.max(yawDeltaAbs, 1), this.yawStep.getValue());
                    final float additionPitch = Math.max(attackContext ? pitchDeltaAbs : 1.0f, 2.0f);
                    if (Math.abs(additionYaw - this.prevAdditionYaw) <= 3.0f) {
                        additionYaw = this.prevAdditionYaw + 3.1f;
                    }
                    final float newYaw = Aura.rotationYaw + ((yawDelta > 0.0f) ? additionYaw : (-additionYaw)) * sensitivity;
                    final float newPitch = MathHelper.clamp(Aura.rotationPitch + ((pitchDelta > 0.0f) ? additionPitch : (-additionPitch)) * sensitivity, -90.0f, 90.0f);
                    Aura.rotationYaw = newYaw;
                    Aura.rotationPitch = newPitch;
                    this.prevAdditionYaw = additionYaw;
                    break;
                }
                case SunRise:
                case Matrix2: {
                    final boolean sanik = this.rotation.getValue() == rotmod.SunRise;
                    final float absoluteYaw = MathHelper.abs(yawDelta);
                    final float randomize = interpolateRandom(-3.0f, 3.0f);
                    final float randomizeClamp = interpolateRandom(-5.0f, 5.0f);
                    final float deltaYaw = MathHelper.clamp(absoluteYaw + randomize, -60.0f + randomizeClamp, 60.0f + randomizeClamp);
                    final float deltaPitch = MathHelper.clamp(pitchDelta + randomize, (float)(-(sanik ? 13 : 45)), sanik ? 13.0f : 45.0f);
                    final float newYaw2 = Aura.rotationYaw + ((yawDelta > 0.0f) ? deltaYaw : (-deltaYaw));
                    final float newPitch2 = MathHelper.clamp(Aura.rotationPitch + deltaPitch / (sanik ? 4.0f : 2.0f), -90.0f, 90.0f);
                    final float gcdFix1 = Aura.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
                    final double gcdFix2 = Math.pow(gcdFix1, 3.0) * 8.0;
                    final double gcdFix3 = gcdFix2 * 0.15000000596046448;
                    Aura.rotationYaw = (float)(newYaw2 - (newYaw2 - Aura.rotationYaw) % gcdFix3);
                    Aura.rotationPitch = (float)(newPitch2 - (newPitch2 - Aura.rotationPitch) % gcdFix3);
                    break;
                }
                case Matrix3: {
                    final float absoluteYaw2 = MathHelper.abs(yawDelta);
                    final float randomize2 = interpolateRandom(-2.0f, 2.0f);
                    final float randomizeClamp2 = interpolateRandom(-5.0f, 5.0f);
                    final boolean looking_at_box = RayTracingUtils.getMouseOver(base, Aura.rotationYaw, Aura.rotationPitch, this.attackDistance.getValue() + this.rotateDistance.getValue(), this.ignoreWalls(base)) == base;
                    if (looking_at_box) {
                        this.rotation_smoother = 15.0f;
                    }
                    else if (this.rotation_smoother < 60.0f) {
                        this.rotation_smoother += 9.0f;
                    }
                    final float yaw_speed = (inside_target && attackContext) ? 60.0f : this.rotation_smoother;
                    final float pitch_speed = looking_at_box ? 0.5f : (this.rotation_smoother / 2.0f);
                    final float deltaYaw2 = MathHelper.clamp(absoluteYaw2 + randomize2, -yaw_speed + randomizeClamp2, yaw_speed + randomizeClamp2);
                    final float deltaPitch2 = MathHelper.clamp(pitchDelta, -pitch_speed, pitch_speed);
                    final float newYaw3 = Aura.rotationYaw + ((yawDelta > 0.0f) ? deltaYaw2 : (-deltaYaw2));
                    final float newPitch3 = MathHelper.clamp(Aura.rotationPitch + deltaPitch2, -90.0f, 90.0f);
                    final float gcdFix4 = Aura.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
                    final double gcdFix5 = Math.pow(gcdFix4, 3.0) * 8.0;
                    final double gcdFix6 = gcdFix5 * 0.15000000596046448;
                    Aura.rotationYaw = (float)(newYaw3 - (newYaw3 - Aura.rotationYaw) % gcdFix6);
                    Aura.rotationPitch = (float)(newPitch3 - (newPitch3 - Aura.rotationPitch) % gcdFix6);
                    break;
                }
                case FunnyGame: {
                    final float[] ncp = SilentRotationUtil.calcAngle(this.getVector(base));
                    if (ncp != null && !AutoGApple.stopAura) {
                        Aura.rotationYaw = ncp[0];
                        Aura.rotationPitch = ncp[1];
                        break;
                    }
                    break;
                }
                case AAC: {
                    if (attackContext) {
                        final int pitchDeltaAbs2 = (int)Math.abs(pitchDelta);
                        final float newYaw4 = Aura.rotationYaw + ((yawDelta > 0.0f) ? yawDeltaAbs : (-yawDeltaAbs)) * sensitivity;
                        final float newPitch4 = MathHelper.clamp(Aura.rotationPitch + ((pitchDelta > 0.0f) ? pitchDeltaAbs2 : (-pitchDeltaAbs2)) * sensitivity, -90.0f, 90.0f);
                        Aura.rotationYaw = newYaw4;
                        Aura.rotationPitch = newPitch4;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPostPlayerUpdate(final PostPlayerUpdateEvent event) {
        if (this.criticals_autojump.getValue() && Aura.mc.player.onGround && !isInLiquid() && !Aura.mc.player.isOnLadder() && !((IEntity)Aura.mc.player).isInWeb() && !Aura.mc.player.isPotionActive(MobEffects.SLOWNESS) && Aura.target != null && this.criticals_autojump.getValue()) {
            Aura.mc.player.jump();
        }
    }
    
    @SubscribeEvent
    public void onMoveDirection(final EventMoveDirection e) {
        if (this.moveSync.getValue()) {
            if (!e.isPost()) {
                this.save_rotationYaw = Aura.mc.player.rotationYaw;
                Aura.mc.player.rotationYaw = Aura.rotationYaw;
            }
            else {
                Aura.mc.player.rotationYaw = this.save_rotationYaw;
            }
        }
    }
    
    public static boolean isInLiquid() {
        return Aura.mc.player.isInWater() || Aura.mc.player.isInLava();
    }
    
    public static double absSinAnimation(final double input) {
        return Math.abs(1.0 + Math.sin(input)) / 2.0;
    }
    
    public static boolean isBlockAboveHead() {
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(Aura.mc.player.posX - 0.3, Aura.mc.player.posY + Aura.mc.player.getEyeHeight(), Aura.mc.player.posZ + 0.3, Aura.mc.player.posX + 0.3, Aura.mc.player.posY + (Aura.mc.player.onGround ? 2.5 : 1.5), Aura.mc.player.posZ - 0.3);
        return !Aura.mc.world.getCollisionBoxes((Entity)Aura.mc.player, axisAlignedBB).isEmpty();
    }
    
    public static Vec2f getDeltaForCoord(final Vec2f rot, final Vec3d point) {
        final EntityPlayerSP client = Minecraft.getMinecraft().player;
        final double x = point.x - client.posX;
        final double y = point.y - client.getPositionEyes(1.0f).y;
        final double z = point.z - client.posZ;
        final double dst = Math.sqrt(Math.pow(x, 2.0) + Math.pow(z, 2.0));
        final float yawToTarget = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(z, x)) - 90.0);
        final float pitchToTarget = (float)(-Math.toDegrees(Math.atan2(y, dst)));
        final float yawDelta = MathHelper.wrapDegrees(yawToTarget - rot.x);
        final float pitchDelta = pitchToTarget - rot.y;
        return new Vec2f(yawDelta, pitchDelta);
    }
    
    public static Vec2f getRotationForCoord(final Vec3d point) {
        final double x = point.x - Aura.mc.player.posX;
        final double y = point.y - Aura.mc.player.getPositionEyes(1.0f).y;
        final double z = point.z - Aura.mc.player.posZ;
        final double dst = Math.sqrt(Math.pow(x, 2.0) + Math.pow(z, 2.0));
        final float yawToTarget = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(z, x)) - 90.0);
        final float pitchToTarget = (float)(-Math.toDegrees(Math.atan2(y, dst)));
        return new Vec2f(yawToTarget, pitchToTarget);
    }
    
    public static boolean isActiveItemStackBlocking(final EntityPlayer other, final int time) {
        if (other.isHandActive() && !other.getActiveItemStack().isEmpty()) {
            final Item item = other.getActiveItemStack().getItem();
            return item.getItemUseAction(other.getActiveItemStack()) == EnumAction.BLOCK && item.getMaxItemUseDuration(other.getActiveItemStack()) - ((IEntityLivingBase)other).getActiveItemStackUseCount() >= time;
        }
        return false;
    }
    
    public static float interpolateRandom(final float var0, final float var1) {
        return (float)(var0 + (var1 - var0) * Math.random());
    }
    
    private Color getTargetColor(final Color color1, final Color color2, final int offset) {
        return RenderUtil.TwoColoreffect(color1, color2, Math.abs(System.currentTimeMillis() / 10L) / 100.0 + offset * ((20.0f - this.colorOffset1.getValue()) / 200.0f));
    }
    
    public float getDamage(final Entity targetEntity) {
        float f = (float)Aura.mc.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        float f2 = EnchantmentHelper.getModifierForCreature(Aura.mc.player.getHeldItemMainhand(), ((EntityLivingBase)targetEntity).getCreatureAttribute());
        final float f3 = Aura.mc.player.getCooledAttackStrength(0.5f);
        f *= 0.2f + f3 * f3 * 0.8f;
        f2 *= f3;
        if (f > 0.0f || f2 > 0.0f) {
            boolean flag2 = f3 > 0.9f && Aura.mc.player.fallDistance > 0.0f && !Aura.mc.player.onGround && !Aura.mc.player.isOnLadder() && !Aura.mc.player.isInWater() && !Aura.mc.player.isPotionActive(MobEffects.BLINDNESS);
            flag2 = (flag2 && !Aura.mc.player.isSprinting());
            if (flag2) {
                f *= 1.5f;
            }
            f += f2;
        }
        return f;
    }
    
    public enum rotmod
    {
        Matrix, 
        AAC, 
        FunnyGame, 
        Matrix2, 
        SunRise, 
        Matrix3, 
        None;
    }
    
    public enum CritMode
    {
        WexSide, 
        Simple;
    }
    
    public enum AutoSwitch
    {
        None, 
        Default;
    }
    
    public enum PointsMode
    {
        Distance, 
        Angle;
    }
    
    public enum TimingMode
    {
        Default, 
        Old;
    }
    
    public enum RayTracingMode
    {
        NewJitter, 
        New, 
        Old, 
        OldJitter, 
        Beta;
    }
    
    public enum Hitbox
    {
        HEAD, 
        CHEST, 
        LEGS;
    }
}
