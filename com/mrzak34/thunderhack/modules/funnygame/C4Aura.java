//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.events.*;
import java.text.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.util.math.*;

public class C4Aura extends Module
{
    public Setting<Float> mindmg;
    public Setting<Float> maxSelfDmg;
    public Setting<Float> stophp;
    public Setting<Float> targetRange;
    public Setting<Boolean> placeinside;
    public Setting<Boolean> autoBurrow;
    private List<BlockPos> positions;
    private BlockPos renderblockpos;
    private BlockPos postSyncPlace;
    public static EntityPlayer target;
    
    public C4Aura() {
        super("C4Aura", "\u0421\u0442\u0430\u0432\u0438\u0442 \u04414", "mcfunny.su only", Category.FUNNYGAME);
        this.mindmg = (Setting<Float>)this.register(new Setting("MinDamage", (T)6.0f, (T)0.0f, (T)20.0f));
        this.maxSelfDmg = (Setting<Float>)this.register(new Setting("MaxSelfDamage", (T)6.0f, (T)0.0f, (T)20.0f));
        this.stophp = (Setting<Float>)this.register(new Setting("StopHp", (T)6.0f, (T)0.0f, (T)20.0f));
        this.targetRange = (Setting<Float>)this.register(new Setting("TargetRange", (T)8.0f, (T)3.0f, (T)15.0f));
        this.placeinside = (Setting<Boolean>)this.register(new Setting("placeInside", (T)true));
        this.autoBurrow = (Setting<Boolean>)this.register(new Setting("AutoBurrow", (T)true));
        this.positions = null;
    }
    
    @SubscribeEvent
    public void onEntitySync(final EventSync e) {
        if (fullNullCheck()) {
            return;
        }
        if (this.autoBurrow.getValue() && C4Aura.mc.player.isSneaking() && this.findC4() != -1) {
            if (!this.canPlaceC4(new BlockPos((Entity)C4Aura.mc.player))) {
                return;
            }
            C4Aura.mc.player.rotationPitch = 90.0f;
            PlayerUtils.centerPlayer(C4Aura.mc.player.getPositionVector());
        }
        else {
            if (this.stophp.getValue() >= C4Aura.mc.player.getHealth()) {
                return;
            }
            if (this.findC4() == -1) {
                return;
            }
            C4Aura.target = this.findTarget();
            if (C4Aura.mc.player.ticksExisted % 5 == 0) {
                this.positions = this.getPositions((Entity)C4Aura.mc.player);
            }
            if (C4Aura.target != null && this.positions != null) {
                final BlockPos bp = this.getBestPos(this.positions, C4Aura.target);
                if (bp != null) {
                    this.placePre(bp);
                }
                if (C4Aura.mc.player.getHeldItemMainhand().getDisplayName().contains("C4") && !C4Aura.mc.player.getHeldItemMainhand().getDisplayName().contains("2")) {
                    C4Aura.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(C4Aura.mc.player.posX, C4Aura.mc.player.posY, C4Aura.mc.player.posZ), EnumFacing.UP));
                }
            }
            else {
                this.renderblockpos = null;
            }
        }
    }
    
    @SubscribeEvent
    public void postSync(final EventPostSync event) {
        if (fullNullCheck()) {
            return;
        }
        if (!this.autoBurrow.getValue() || !C4Aura.mc.player.isSneaking() || this.findC4() == -1) {
            if (this.postSyncPlace != null) {
                this.placePost(this.postSyncPlace);
            }
            return;
        }
        if (!this.canPlaceC4(new BlockPos((Entity)C4Aura.mc.player))) {
            return;
        }
        C4Aura.mc.player.inventory.currentItem = 2;
        C4Aura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.findC4()));
        this.placePost(new BlockPos((Entity)C4Aura.mc.player));
        C4Aura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(2));
    }
    
    private List<BlockPos> getPositions(final Entity entity2) {
        final ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        final int playerX = (int)entity2.posX;
        final int playerY = (int)entity2.posY;
        final int playerZ = (int)entity2.posZ;
        final int n4 = 4;
        final double playerX2 = entity2.posX - 0.5;
        final double playerY2 = entity2.posY + entity2.getEyeHeight() - 1.0;
        final double playerZ2 = entity2.posZ - 0.5;
        for (int n5 = playerX - n4; n5 <= playerX + n4; ++n5) {
            for (int n6 = playerZ - n4; n6 <= playerZ + n4; ++n6) {
                for (int n7 = playerY - n4; n7 < playerY + n4; ++n7) {
                    if ((n5 - playerX2) * (n5 - playerX2) + (n7 - playerY2) * (n7 - playerY2) + (n6 - playerZ2) * (n6 - playerZ2) <= 16.0 && this.canPlaceC4(new BlockPos(n5, n7, n6))) {
                        arrayList.add(new BlockPos(n5, n7, n6));
                    }
                }
            }
        }
        return arrayList;
    }
    
    private boolean canPlaceC4(final BlockPos bp) {
        if (C4Aura.mc.player.getDistanceSq((double)bp.getX(), (double)bp.getY(), (double)bp.getZ()) > 16.0) {
            return false;
        }
        if (C4Aura.target != null) {
            final BlockPos jew = new BlockPos((Entity)C4Aura.target);
            if (Objects.equals(bp, jew) && !this.placeinside.getValue()) {
                return false;
            }
            if (Objects.equals(bp, jew.add(0, 1, 0))) {
                return false;
            }
        }
        return this.getDamage(bp.getX(), bp.getY(), bp.getZ(), (EntityLivingBase)C4Aura.mc.player) <= this.maxSelfDmg.getValue() && C4Aura.mc.world.getBlockState(bp).getMaterial().isReplaceable() && C4Aura.mc.world.getBlockState(bp.down()).getBlock() != Blocks.SKULL && C4Aura.mc.world.getBlockState(bp.down()).getBlock() != Blocks.LEVER && (!C4Aura.mc.player.getHeldItemMainhand().getDisplayName().contains("C4") || !C4Aura.mc.player.getHeldItemMainhand().getDisplayName().contains("0")) && C4Aura.mc.world.getBlockState(bp).getBlock() == Blocks.AIR && C4Aura.mc.world.getBlockState(bp.down()).getBlock() != Blocks.AIR;
    }
    
    public float getDamage(final double d7, final double d2, final double d3, final EntityLivingBase entityLivingBase) {
        double d8 = entityLivingBase.getDistance(d7, d2, d3) / 12.0;
        final double d9 = entityLivingBase.world.getBlockDensity(new Vec3d(d7, d2, d3), entityLivingBase.getEntityBoundingBox());
        d8 = (1.0 - d8) * d9;
        float f = (float)(int)((d8 * d8 + d8) / 2.0 * 7.0 * 12.0 + 1.0);
        f = Math.min(f / 2.0f + 1.0f, f);
        final DamageSource dmgg = DamageSource.causeExplosionDamage(new Explosion((World)C4Aura.mc.world, (Entity)C4Aura.mc.player, d7, d2, d3, 6.0f, false, true));
        f = CombatRules.getDamageAfterAbsorb(f, (float)entityLivingBase.getTotalArmorValue(), (float)entityLivingBase.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        final int n = EnchantmentHelper.getEnchantmentModifierDamage(entityLivingBase.getArmorInventoryList(), dmgg);
        if (n > 0) {
            f = CombatRules.getDamageAfterMagicAbsorb(f, (float)n);
        }
        if (entityLivingBase.getActivePotionEffect(MobEffects.RESISTANCE) != null) {
            f = f * (25 - (Objects.requireNonNull(entityLivingBase.getActivePotionEffect(MobEffects.RESISTANCE)).getAmplifier() + 1) * 5) / 25.0f;
        }
        f = Math.max(f, 0.0f);
        return f;
    }
    
    public BlockPos getBestPos(final List<BlockPos> vsepos, final EntityPlayer nigger) {
        BlockPos pos = null;
        double bestdmg = this.mindmg.getValue();
        for (final BlockPos pos2 : vsepos) {
            if (this.getDamage(pos2.getX(), pos2.getY(), pos2.getZ(), (EntityLivingBase)nigger) > bestdmg) {
                bestdmg = this.getDamage(pos2.getX(), pos2.getY(), pos2.getZ(), (EntityLivingBase)nigger);
                pos = pos2;
            }
        }
        return pos;
    }
    
    public EntityPlayer findTarget() {
        EntityPlayer target = null;
        double distance = this.targetRange.getPow2Value();
        for (final EntityPlayer entity : C4Aura.mc.world.playerEntities) {
            if (entity == C4Aura.mc.player) {
                continue;
            }
            if (Thunderhack.friendManager.isFriend(entity)) {
                continue;
            }
            if (C4Aura.mc.player.getDistanceSq((Entity)entity) > distance) {
                continue;
            }
            target = entity;
            distance = C4Aura.mc.player.getDistanceSq((Entity)entity);
        }
        return target;
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent e) {
        if (C4Aura.mc.player == null && C4Aura.mc.world == null) {
            return;
        }
        if (this.renderblockpos != null && C4Aura.target != null) {
            try {
                final DecimalFormat df = new DecimalFormat("0.0");
                RenderUtil.drawBlockOutline(this.renderblockpos, new Color(392654), 3.0f, true, 0);
                GlStateManager.pushMatrix();
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                RenderUtil.glBillboardDistanceScaled(this.renderblockpos.getX() + 0.5f, this.renderblockpos.getY() + 0.5f, this.renderblockpos.getZ() + 0.5f, (EntityPlayer)C4Aura.mc.player, 1.0f);
                FontRender.drawString3(df.format(this.getDamage(this.renderblockpos.getX(), this.renderblockpos.getY(), this.renderblockpos.getZ(), (EntityLivingBase)C4Aura.target)), (float)(int)(-(FontRender.getStringWidth(df.format(this.getDamage(this.renderblockpos.getX(), this.renderblockpos.getY() + 1, this.renderblockpos.getZ(), (EntityLivingBase)C4Aura.target))) / 2.0)), -4.0f, -1);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                GlStateManager.popMatrix();
            }
            catch (Exception ex) {}
        }
    }
    
    private int findC4() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = C4Aura.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() == Item.getItemFromBlock(Blocks.LEVER)) {
                if (itemStack.getDisplayName().contains("C4")) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public void placePre(final BlockPos position) {
        C4Aura.mc.player.inventory.currentItem = this.findC4();
        ((IPlayerControllerMP)C4Aura.mc.playerController).syncItem();
        this.renderblockpos = position;
        for (final EnumFacing direction : EnumFacing.values()) {
            final BlockPos directionOffset = position.offset(direction);
            final EnumFacing oppositeFacing = direction.getOpposite();
            if (!C4Aura.mc.world.getBlockState(directionOffset).getMaterial().isReplaceable()) {
                float[] rotation = InteractionUtil.getAnglesToBlock(directionOffset, oppositeFacing);
                Vec3d interactVector = null;
                final RayTraceResult result = InteractionUtil.getTraceResult(4.0, rotation[0], rotation[1]);
                if (result != null && result.typeOfHit.equals((Object)RayTraceResult.Type.BLOCK)) {
                    interactVector = result.hitVec;
                }
                if (interactVector == null) {
                    interactVector = new Vec3d((Vec3i)directionOffset).add(0.5, 0.5, 0.5);
                    rotation = calculateAngles(interactVector);
                }
                C4Aura.mc.player.rotationYaw = rotation[0];
                C4Aura.mc.player.rotationPitch = rotation[1];
                this.postSyncPlace = position;
                break;
            }
        }
    }
    
    public void placePost(final BlockPos position) {
        for (final EnumFacing direction : EnumFacing.values()) {
            final BlockPos directionOffset = position.offset(direction);
            final EnumFacing oppositeFacing = direction.getOpposite();
            if (!C4Aura.mc.world.getBlockState(directionOffset).getMaterial().isReplaceable()) {
                final boolean sprint = C4Aura.mc.player.isSprinting();
                if (sprint) {
                    C4Aura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)C4Aura.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                }
                final boolean sneak = C4Aura.mc.world.getBlockState(directionOffset).getBlock().onBlockActivated((World)C4Aura.mc.world, directionOffset, C4Aura.mc.world.getBlockState(directionOffset), (EntityPlayer)C4Aura.mc.player, EnumHand.MAIN_HAND, EnumFacing.DOWN, 0.0f, 0.0f, 0.0f);
                if (sneak) {
                    C4Aura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)C4Aura.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                }
                final float[] rotation = InteractionUtil.getAnglesToBlock(directionOffset, oppositeFacing);
                Vec3d interactVector = null;
                final RayTraceResult result = InteractionUtil.getTraceResult(4.0, rotation[0], rotation[1]);
                if (result != null && result.typeOfHit.equals((Object)RayTraceResult.Type.BLOCK)) {
                    interactVector = result.hitVec;
                }
                if (interactVector == null) {
                    interactVector = new Vec3d((Vec3i)directionOffset).add(0.5, 0.5, 0.5);
                }
                C4Aura.mc.playerController.processRightClickBlock(C4Aura.mc.player, C4Aura.mc.world, directionOffset, direction.getOpposite(), interactVector, EnumHand.MAIN_HAND);
                if (sneak) {
                    C4Aura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)C4Aura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                if (sprint) {
                    C4Aura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)C4Aura.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                }
                C4Aura.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                C4Aura.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(C4Aura.mc.player.posX, C4Aura.mc.player.posY, C4Aura.mc.player.posZ), EnumFacing.UP));
                ((IMinecraft)C4Aura.mc).setRightClickDelayTimer(4);
                this.postSyncPlace = null;
                break;
            }
        }
    }
    
    public static float[] calculateAngles(final Vec3d to) {
        final float yaw = (float)(Math.toDegrees(Math.atan2(to.subtract(C4Aura.mc.player.getPositionEyes(1.0f)).z, to.subtract(C4Aura.mc.player.getPositionEyes(1.0f)).x)) - 90.0);
        final float pitch = (float)Math.toDegrees(-Math.atan2(to.subtract(C4Aura.mc.player.getPositionEyes(1.0f)).y, Math.hypot(to.subtract(C4Aura.mc.player.getPositionEyes(1.0f)).x, to.subtract(C4Aura.mc.player.getPositionEyes(1.0f)).z)));
        return new float[] { MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch) };
    }
    
    static {
        C4Aura.target = null;
    }
}
