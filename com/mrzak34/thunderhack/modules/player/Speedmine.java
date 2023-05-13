//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.math.*;
import java.awt.*;
import com.mrzak34.thunderhack.modules.render.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.block.*;

public class Speedmine extends Module
{
    private static float mineDamage;
    private final Setting<Float> range;
    public Setting<Boolean> rotate;
    public Setting<Boolean> strict;
    public Setting<Boolean> strictReMine;
    public Setting<Boolean> render;
    private final Setting<Mode> mode;
    private final Setting<Float> startDamage;
    private final Setting<Float> endDamage;
    private BlockPos minePosition;
    private EnumFacing mineFacing;
    private int mineBreaks;
    
    public Speedmine() {
        super("Speedmine", "\u043f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0431\u044b\u0441\u0442\u0440\u043e-\u043a\u043e\u043f\u0430\u0442\u044c", "Allows you to dig-quickly", Module.Category.PLAYER);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)4.2f, (T)3.0f, (T)10.0f));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)false));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.strictReMine = (Setting<Boolean>)this.register(new Setting("StrictBreak", (T)false));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)false));
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.Packet));
        this.startDamage = (Setting<Float>)this.register(new Setting("StartDamage", (T)0.1f, (T)0.0f, (T)1.0f, v -> this.mode.getValue() == Mode.Damage));
        this.endDamage = (Setting<Float>)this.register(new Setting("EndDamage", (T)0.9f, (T)0.0f, (T)1.0f, v -> this.mode.getValue() == Mode.Damage));
    }
    
    public void onUpdate() {
        if (!Speedmine.mc.player.capabilities.isCreativeMode) {
            if (this.minePosition != null) {
                final double mineDistance = Speedmine.mc.player.getDistanceSq(this.minePosition.add(0.5, 0.5, 0.5));
                if ((this.mineBreaks >= 2 && this.strictReMine.getValue()) || mineDistance > this.range.getPow2Value()) {
                    this.minePosition = null;
                    this.mineFacing = null;
                    Speedmine.mineDamage = 0.0f;
                    this.mineBreaks = 0;
                }
            }
            if (this.mode.getValue() == Mode.Damage) {
                if (((IPlayerControllerMP)Speedmine.mc.playerController).getCurBlockDamageMP() < this.startDamage.getValue()) {
                    ((IPlayerControllerMP)Speedmine.mc.playerController).setCurBlockDamageMP((float)this.startDamage.getValue());
                }
                if (((IPlayerControllerMP)Speedmine.mc.playerController).getCurBlockDamageMP() >= this.endDamage.getValue()) {
                    ((IPlayerControllerMP)Speedmine.mc.playerController).setCurBlockDamageMP(1.0f);
                }
            }
            else if (this.mode.getValue() == Mode.NexusGrief) {
                if (Speedmine.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
                    if (((IPlayerControllerMP)Speedmine.mc.playerController).getCurBlockDamageMP() < 0.17f) {
                        ((IPlayerControllerMP)Speedmine.mc.playerController).setCurBlockDamageMP(0.17f);
                    }
                    if (((IPlayerControllerMP)Speedmine.mc.playerController).getCurBlockDamageMP() >= 0.83) {
                        ((IPlayerControllerMP)Speedmine.mc.playerController).setCurBlockDamageMP(1.0f);
                    }
                }
                else if (Speedmine.mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe) {
                    if (((IPlayerControllerMP)Speedmine.mc.playerController).getCurBlockDamageMP() < 0.17f) {
                        ((IPlayerControllerMP)Speedmine.mc.playerController).setCurBlockDamageMP(0.17f);
                    }
                    if (((IPlayerControllerMP)Speedmine.mc.playerController).getCurBlockDamageMP() >= 1.0f) {
                        ((IPlayerControllerMP)Speedmine.mc.playerController).setCurBlockDamageMP(1.0f);
                    }
                }
                else if (Speedmine.mc.player.getHeldItemMainhand().getItem() == Items.STONE_SHOVEL || Speedmine.mc.player.getHeldItemMainhand().getItem() == Items.IRON_SHOVEL || Speedmine.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SHOVEL) {
                    if (((IPlayerControllerMP)Speedmine.mc.playerController).getCurBlockDamageMP() < 0.17f) {
                        ((IPlayerControllerMP)Speedmine.mc.playerController).setCurBlockDamageMP(0.17f);
                    }
                    if (((IPlayerControllerMP)Speedmine.mc.playerController).getCurBlockDamageMP() >= 1.0f) {
                        ((IPlayerControllerMP)Speedmine.mc.playerController).setCurBlockDamageMP(1.0f);
                    }
                }
            }
            else if (this.mode.getValue() == Mode.Packet) {
                if (this.minePosition != null && !Speedmine.mc.world.isAirBlock(this.minePosition)) {
                    if (Speedmine.mineDamage >= 1.0f) {
                        final int previousSlot = Speedmine.mc.player.inventory.currentItem;
                        final int swapSlot = this.getTool(this.minePosition);
                        if (swapSlot == -1) {
                            return;
                        }
                        if (this.strict.getValue()) {
                            final short nextTransactionID = Speedmine.mc.player.openContainer.getNextTransactionID(Speedmine.mc.player.inventory);
                            final ItemStack itemstack = Speedmine.mc.player.openContainer.slotClick(swapSlot, Speedmine.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Speedmine.mc.player);
                            Speedmine.mc.player.connection.sendPacket((Packet)new CPacketClickWindow(Speedmine.mc.player.inventoryContainer.windowId, swapSlot, Speedmine.mc.player.inventory.currentItem, ClickType.SWAP, itemstack, nextTransactionID));
                        }
                        else {
                            Speedmine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(swapSlot));
                        }
                        Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.minePosition, this.mineFacing));
                        Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.minePosition, EnumFacing.UP));
                        if (this.strict.getValue()) {
                            Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.minePosition, this.mineFacing));
                        }
                        Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.minePosition, this.mineFacing));
                        if (previousSlot != -1) {
                            if (this.strict.getValue()) {
                                final short nextTransactionID = Speedmine.mc.player.openContainer.getNextTransactionID(Speedmine.mc.player.inventory);
                                final ItemStack itemstack = Speedmine.mc.player.openContainer.slotClick(swapSlot, Speedmine.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Speedmine.mc.player);
                                Speedmine.mc.player.connection.sendPacket((Packet)new CPacketClickWindow(Speedmine.mc.player.inventoryContainer.windowId, swapSlot, Speedmine.mc.player.inventory.currentItem, ClickType.SWAP, itemstack, nextTransactionID));
                                Speedmine.mc.player.connection.sendPacket((Packet)new CPacketConfirmTransaction(Speedmine.mc.player.inventoryContainer.windowId, nextTransactionID, true));
                            }
                            else {
                                Speedmine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(previousSlot));
                            }
                        }
                        Speedmine.mineDamage = 0.0f;
                        ++this.mineBreaks;
                    }
                    Speedmine.mineDamage += this.getBlockStrength(Speedmine.mc.world.getBlockState(this.minePosition), this.minePosition);
                }
                else {
                    Speedmine.mineDamage = 0.0f;
                }
            }
        }
    }
    
    public float getBlockStrength(final IBlockState state, final BlockPos position) {
        final float hardness = state.getBlockHardness((World)Speedmine.mc.world, position);
        if (hardness < 0.0f) {
            return 0.0f;
        }
        if (!this.canBreak(position)) {
            return this.getDigSpeed(state) / hardness / 100.0f;
        }
        return this.getDigSpeed(state) / hardness / 30.0f;
    }
    
    public float getDigSpeed(final IBlockState state) {
        float digSpeed = this.getDestroySpeed(state);
        if (digSpeed > 1.0f) {
            final ItemStack itemstack = this.getTool2(state);
            final int efficiencyModifier = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemstack);
            if (efficiencyModifier > 0 && !itemstack.isEmpty()) {
                digSpeed += (float)(StrictMath.pow(efficiencyModifier, 2.0) + 1.0);
            }
        }
        if (Speedmine.mc.player.isPotionActive(MobEffects.HASTE)) {
            digSpeed *= 1.0f + (Speedmine.mc.player.getActivePotionEffect(MobEffects.HASTE).getAmplifier() + 1) * 0.2f;
        }
        if (Speedmine.mc.player.isPotionActive(MobEffects.MINING_FATIGUE)) {
            float fatigueScale = 0.0f;
            switch (Speedmine.mc.player.getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier()) {
                case 0: {
                    fatigueScale = 0.3f;
                    break;
                }
                case 1: {
                    fatigueScale = 0.09f;
                    break;
                }
                case 2: {
                    fatigueScale = 0.0027f;
                    break;
                }
                default: {
                    fatigueScale = 8.1E-4f;
                    break;
                }
            }
            digSpeed *= fatigueScale;
        }
        if (Speedmine.mc.player.isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier((EntityLivingBase)Speedmine.mc.player)) {
            digSpeed /= 5.0f;
        }
        if (!Speedmine.mc.player.onGround) {
            digSpeed /= 5.0f;
        }
        return (digSpeed < 0.0f) ? 0.0f : digSpeed;
    }
    
    public float getDestroySpeed(final IBlockState state) {
        float destroySpeed = 1.0f;
        if (this.getTool2(state) != null && !this.getTool2(state).isEmpty()) {
            destroySpeed *= this.getTool2(state).getDestroySpeed(state);
        }
        return destroySpeed;
    }
    
    public void onDisable() {
        this.minePosition = null;
        this.mineFacing = null;
        Speedmine.mineDamage = 0.0f;
        this.mineBreaks = 0;
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent e) {
        if (this.mode.getValue() == Mode.Packet && this.minePosition != null && !Speedmine.mc.world.isAirBlock(this.minePosition)) {
            GlStateManager.pushMatrix();
            GL11.glPushAttrib(1048575);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.shadeModel(7425);
            GlStateManager.disableDepth();
            final AxisAlignedBB mineBox = Speedmine.mc.world.getBlockState(this.minePosition).getSelectedBoundingBox((World)Speedmine.mc.world, this.minePosition);
            final Vec3d mineCenter = mineBox.getCenter();
            final AxisAlignedBB shrunkMineBox = new AxisAlignedBB(mineCenter.x, mineCenter.y, mineCenter.z, mineCenter.x, mineCenter.y, mineCenter.z);
            BreakHighLight.renderBreakingBB2(shrunkMineBox.shrink(MathUtil.clamp(Speedmine.mineDamage, 0.0f, 1.0f) * 0.5), (Speedmine.mineDamage >= 0.95) ? new Color(47, 255, 0, 120) : new Color(255, 0, 0, 120), (Speedmine.mineDamage >= 0.95) ? new Color(0, 255, 13, 120) : new Color(255, 0, 0, 120));
            GL11.glPopAttrib();
            GlStateManager.popMatrix();
        }
    }
    
    @SubscribeEvent
    public void onLeftClickBlock(final DamageBlockEvent event) {
        if (this.canBreak(event.getBlockPos()) && !Speedmine.mc.player.capabilities.isCreativeMode) {
            if (this.mode.getValue() == Mode.Creative) {
                Speedmine.mc.player.swingArm(EnumHand.MAIN_HAND);
                Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
                Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
                Speedmine.mc.playerController.onPlayerDestroyBlock(event.getBlockPos());
                Speedmine.mc.world.setBlockToAir(event.getBlockPos());
            }
            if (this.mode.getValue() == Mode.Packet && !event.getBlockPos().equals((Object)this.minePosition)) {
                this.minePosition = event.getBlockPos();
                this.mineFacing = event.getEnumFacing();
                Speedmine.mineDamage = 0.0f;
                this.mineBreaks = 0;
                if (this.minePosition != null && this.mineFacing != null) {
                    Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.minePosition, this.mineFacing));
                    Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.minePosition, EnumFacing.UP));
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onEntitySync(final EventSync event) {
        if (this.rotate.getValue() && Speedmine.mineDamage > 0.95 && this.minePosition != null) {
            final float[] angle = SilentRotationUtil.calcAngle(Speedmine.mc.player.getPositionEyes(Speedmine.mc.getRenderPartialTicks()), new Vec3d((Vec3i)this.minePosition.add(0.5, 0.5, 0.5)));
            Speedmine.mc.player.rotationYaw = angle[0];
            Speedmine.mc.player.rotationPitch = angle[1];
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketHeldItemChange && this.strict.getValue()) {
            Speedmine.mineDamage = 0.0f;
        }
    }
    
    private int getTool(final BlockPos pos) {
        int index = -1;
        float CurrentFastest = 1.0f;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Speedmine.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                final float digSpeed = (float)EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
                final float destroySpeed = stack.getDestroySpeed(Speedmine.mc.world.getBlockState(pos));
                if (digSpeed + destroySpeed > CurrentFastest) {
                    CurrentFastest = digSpeed + destroySpeed;
                    index = i;
                }
            }
        }
        return index;
    }
    
    private ItemStack getTool2(final IBlockState pos) {
        ItemStack itemStack = null;
        float CurrentFastest = 1.0f;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Speedmine.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                final float digSpeed = (float)EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
                final float destroySpeed = stack.getDestroySpeed(pos);
                if (digSpeed + destroySpeed > CurrentFastest) {
                    CurrentFastest = digSpeed + destroySpeed;
                    itemStack = stack;
                }
            }
        }
        return itemStack;
    }
    
    private boolean canBreak(final BlockPos pos) {
        final IBlockState blockState = Speedmine.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)Speedmine.mc.world, pos) != -1.0f;
    }
    
    public enum Mode
    {
        Packet, 
        Damage, 
        Creative, 
        NexusGrief;
    }
}
