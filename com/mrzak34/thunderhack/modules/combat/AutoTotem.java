//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.inventory.*;
import com.mrzak34.thunderhack.manager.*;
import java.util.stream.*;
import com.mrzak34.thunderhack.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import java.util.function.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.modules.movement.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.util.*;
import com.mojang.realmsclient.gui.*;

public class AutoTotem extends Module
{
    public Setting<ModeEn> mode;
    public Setting<Boolean> totem;
    public Setting<Boolean> gapple;
    public Setting<Boolean> rightClick;
    public Setting<Boolean> crystal;
    public Setting<Boolean> StopSprint;
    public Setting<Float> delay;
    public Setting<Boolean> hotbarTotem;
    public Setting<Float> totemHealthThreshold;
    public Setting<CrystalCheck> crystalCheck;
    public Setting<Float> crystalRange;
    public Setting<Boolean> extraSafe;
    public Setting<Boolean> fallCheck;
    public Setting<Boolean> totemOnElytra;
    public Setting<Boolean> clearAfter;
    public Setting<Boolean> hard;
    public Setting<Boolean> notFromHotbar;
    public Setting<Default> defaultItem;
    public Setting<Boolean> absorptionHP;
    public Setting<Boolean> checkTNT;
    public Setting<Boolean> checkObsidian;
    public Setting<Boolean> deathVerbose;
    public Setting<OffHand> offhand;
    public Setting<Float> healthF;
    public Setting<Boolean> lethal;
    public Setting<Integer> GappleSlot;
    public Setting<Boolean> offhandoverride;
    public Setting<Boolean> crapple;
    public Setting<Boolean> funnyGame;
    public static long packet_latency_timer;
    public static int last_packet_time;
    private final Queue<Integer> clickQueue;
    private final Timer stop_spam;
    private final Timer timer;
    private int swapBack;
    
    public AutoTotem() {
        super("AutoTotem", "AutoTotem", Category.COMBAT);
        this.mode = (Setting<ModeEn>)this.register(new Setting("Mode", (T)ModeEn.SemiStrict));
        this.totem = (Setting<Boolean>)this.register(new Setting("Totem", (T)true, v -> this.mode.getValue() == ModeEn.SemiStrict));
        this.gapple = (Setting<Boolean>)this.register(new Setting("SwordGap", (T)false, v -> this.mode.getValue() == ModeEn.SemiStrict));
        this.rightClick = (Setting<Boolean>)this.register(new Setting("RightClickGap", (T)false, v -> this.gapple.getValue() && this.mode.getValue() == ModeEn.SemiStrict));
        this.crystal = (Setting<Boolean>)this.register(new Setting("Crystal", (T)true, v -> this.mode.getValue() == ModeEn.SemiStrict));
        this.StopSprint = (Setting<Boolean>)this.register(new Setting("StopSprint", (T)false, v -> this.mode.getValue() != ModeEn.Strict));
        this.delay = (Setting<Float>)this.register(new Setting("Delay", (T)0.0f, (T)0.0f, (T)5.0f, v -> this.mode.getValue() == ModeEn.SemiStrict));
        this.hotbarTotem = (Setting<Boolean>)this.register(new Setting("HotbarTotem", (T)false, v -> this.mode.getValue() == ModeEn.SemiStrict));
        this.totemHealthThreshold = (Setting<Float>)this.register(new Setting("TotemHealth", (T)5.0f, (T)0.0f, (T)36.0f, v -> this.mode.getValue() != ModeEn.Strict));
        this.crystalCheck = (Setting<CrystalCheck>)this.register(new Setting("CrystalCheck", (T)CrystalCheck.DAMAGE, v -> this.mode.getValue() == ModeEn.SemiStrict));
        this.crystalRange = (Setting<Float>)this.register(new Setting("CrystalRange", (T)10.0f, (T)1.0f, (T)15.0f, v -> this.crystalCheck.getValue() != CrystalCheck.NONE && this.mode.getValue() == ModeEn.SemiStrict));
        this.extraSafe = (Setting<Boolean>)this.register(new Setting("ExtraCheck", (T)false, v -> this.crystalCheck.getValue() != CrystalCheck.NONE && this.mode.getValue() == ModeEn.SemiStrict));
        this.fallCheck = (Setting<Boolean>)this.register(new Setting("FallCheck", (T)true, v -> this.mode.getValue() != ModeEn.Strict));
        this.totemOnElytra = (Setting<Boolean>)this.register(new Setting("TotemOnElytra", (T)true, v -> this.mode.getValue() == ModeEn.SemiStrict));
        this.clearAfter = (Setting<Boolean>)this.register(new Setting("SwapBack", (T)true, v -> this.mode.getValue() == ModeEn.SemiStrict));
        this.hard = (Setting<Boolean>)this.register(new Setting("AlwaysDefault", (T)false, v -> this.mode.getValue() == ModeEn.SemiStrict));
        this.notFromHotbar = (Setting<Boolean>)this.register(new Setting("NotFromHotbar", (T)false, v -> this.mode.getValue() == ModeEn.SemiStrict));
        this.defaultItem = (Setting<Default>)this.register(new Setting("DefaultItem", (T)Default.TOTEM, v -> this.mode.getValue() == ModeEn.SemiStrict));
        this.absorptionHP = (Setting<Boolean>)this.register(new Setting("absorptionHP", (T)true, v -> this.mode.getValue() == ModeEn.Matrix));
        this.checkTNT = (Setting<Boolean>)this.register(new Setting("CheckTNT", (T)true, v -> this.mode.getValue() == ModeEn.Matrix));
        this.checkObsidian = (Setting<Boolean>)this.register(new Setting("CheckObsidian", (T)true, v -> this.mode.getValue() == ModeEn.Matrix));
        this.deathVerbose = (Setting<Boolean>)this.register(new Setting("Death Verbose", (T)true, v -> this.mode.getValue() == ModeEn.Strict));
        this.offhand = (Setting<OffHand>)this.register(new Setting("OffHand", (T)OffHand.Totem, v -> this.mode.getValue() == ModeEn.Strict));
        this.healthF = (Setting<Float>)this.register(new Setting("Health", (T)16.0f, (T)0.0f, (T)20.0f, v -> this.mode.getValue() == ModeEn.Strict));
        this.lethal = (Setting<Boolean>)this.register(new Setting("Lethal", (T)true, v -> this.mode.getValue() == ModeEn.Strict));
        this.GappleSlot = (Setting<Integer>)this.register(new Setting("GAppleSlot", (T)0, (T)0, (T)8, v -> this.mode.getValue() == ModeEn.Strict));
        this.offhandoverride = (Setting<Boolean>)this.register(new Setting("OffHandOverride", (T)true, v -> this.mode.getValue() == ModeEn.Strict));
        this.crapple = (Setting<Boolean>)this.register(new Setting("Crapple", (T)true, v -> this.mode.getValue() == ModeEn.Strict));
        this.funnyGame = (Setting<Boolean>)this.register(new Setting("FunnyGameBypass", (T)true, v -> this.mode.getValue() == ModeEn.Strict));
        this.clickQueue = new LinkedList<Integer>();
        this.stop_spam = new Timer();
        this.timer = new Timer();
        this.swapBack = -1;
    }
    
    public static int getItemSlot(final Item item, final boolean gappleCheck) {
        for (int i = 0; i < 36; ++i) {
            final ItemStack itemStackInSlot = AutoTotem.mc.player.inventory.getStackInSlot(i);
            if (!gappleCheck) {
                if (item == itemStackInSlot.getItem()) {
                    if (i < 9) {
                        i += 36;
                    }
                    return i;
                }
            }
            else if (item == itemStackInSlot.getItem() && (!item.getRarity(itemStackInSlot).equals((Object)EnumRarity.RARE) || noGapples())) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
    
    private static boolean noGapples() {
        for (int i = 0; i < 36; ++i) {
            final ItemStack itemStackInSlot = AutoTotem.mc.player.inventory.getStackInSlot(i);
            if (Items.GOLDEN_APPLE == itemStackInSlot.getItem() && !Items.GOLDEN_APPLE.getRarity(itemStackInSlot).equals((Object)EnumRarity.RARE)) {
                return false;
            }
        }
        return true;
    }
    
    public static List<BlockPos> getSphere(final BlockPos blockPos, final float n, final int n2, final boolean b, final boolean b2, final int n3) {
        final ArrayList<BlockPos> list = new ArrayList<BlockPos>();
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        for (int n4 = x - (int)n; n4 <= x + n; ++n4) {
            for (int n5 = z - (int)n; n5 <= z + n; ++n5) {
                for (int n6 = b2 ? (y - (int)n) : y; n6 < (b2 ? (y + n) : ((float)(y + n2))); ++n6) {
                    final double n7 = (x - n4) * (x - n4) + (z - n5) * (z - n5) + (b2 ? ((y - n6) * (y - n6)) : 0);
                    if (n7 < n * n && (!b || n7 >= (n - 1.0f) * (n - 1.0f))) {
                        list.add(new BlockPos(n4, n6 + n3, n5));
                    }
                }
            }
        }
        return list;
    }
    
    public static double getDistanceOfEntityToBlock(final Entity entity, final BlockPos blockPos) {
        return getDistance(entity.posX, entity.posY, entity.posZ, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
    
    public static double getDistance(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final double n7 = n - n4;
        final double n8 = n2 - n5;
        final double n9 = n3 - n6;
        return MathHelper.sqrt(n7 * n7 + n8 * n8 + n9 * n9);
    }
    
    public static int findNearestCurrentItem() {
        final int currentItem = AutoTotem.mc.player.inventory.currentItem;
        if (currentItem == 8) {
            return 7;
        }
        if (currentItem == 0) {
            return 1;
        }
        return currentItem - 1;
    }
    
    @SubscribeEvent
    public void onPlayerUpdate(final PlayerUpdateEvent e) {
        if (this.mode.getValue() != ModeEn.Matrix) {
            return;
        }
        int totemSlot = getItemSlot(Items.TOTEM_OF_UNDYING, false);
        if (totemSlot < 9 && totemSlot != -1) {
            totemSlot += 36;
        }
        float hp = AutoTotem.mc.player.getHealth();
        if (this.absorptionHP.getValue()) {
            hp = EntityUtil.getHealth((Entity)AutoTotem.mc.player);
        }
        final int prevCurrentItem = AutoTotem.mc.player.inventory.currentItem;
        final int currentItem = findNearestCurrentItem();
        final boolean totemCheck = this.totemHealthThreshold.getValue() >= hp || this.crystalCheck() || (this.fallCheck.getValue() && EntityUtil.getHealth((Entity)AutoTotem.mc.player) - ((AutoTotem.mc.player.fallDistance - 3.0f) / 2.0f + 3.5f) < 0.5 && !AutoTotem.mc.player.isElytraFlying()) || this.checkTNT() || this.checkObsidian();
        final boolean totemInHand = AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING;
        if (totemCheck) {
            if (totemSlot >= 0 && !totemInHand) {
                AutoTotem.mc.playerController.windowClick(0, totemSlot, currentItem, ClickType.SWAP, (EntityPlayer)AutoTotem.mc.player);
                AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem));
                AutoTotem.mc.player.inventory.currentItem = currentItem;
                final ItemStack itemstack = AutoTotem.mc.player.getHeldItem(EnumHand.OFF_HAND);
                AutoTotem.mc.player.setHeldItem(EnumHand.OFF_HAND, AutoTotem.mc.player.getHeldItem(EnumHand.MAIN_HAND));
                AutoTotem.mc.player.setHeldItem(EnumHand.MAIN_HAND, itemstack);
                AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
                AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(prevCurrentItem));
                AutoTotem.mc.player.inventory.currentItem = prevCurrentItem;
                AutoTotem.mc.playerController.windowClick(0, totemSlot, currentItem, ClickType.SWAP, (EntityPlayer)AutoTotem.mc.player);
                if (this.swapBack == -1) {
                    this.swapBack = totemSlot;
                }
                return;
            }
            if (totemInHand) {
                return;
            }
        }
        if (this.swapBack >= 0) {
            AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem));
            AutoTotem.mc.player.inventory.currentItem = currentItem;
            ItemStack itemstack = AutoTotem.mc.player.getHeldItem(EnumHand.OFF_HAND);
            AutoTotem.mc.player.setHeldItem(EnumHand.OFF_HAND, AutoTotem.mc.player.getHeldItem(EnumHand.MAIN_HAND));
            AutoTotem.mc.player.setHeldItem(EnumHand.MAIN_HAND, itemstack);
            AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            AutoTotem.mc.playerController.windowClick(0, this.swapBack, currentItem, ClickType.SWAP, (EntityPlayer)AutoTotem.mc.player);
            AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            itemstack = AutoTotem.mc.player.getHeldItem(EnumHand.OFF_HAND);
            AutoTotem.mc.player.setHeldItem(EnumHand.OFF_HAND, AutoTotem.mc.player.getHeldItem(EnumHand.MAIN_HAND));
            AutoTotem.mc.player.setHeldItem(EnumHand.MAIN_HAND, itemstack);
            AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(prevCurrentItem));
            AutoTotem.mc.player.inventory.currentItem = prevCurrentItem;
            this.swapBack = -1;
        }
    }
    
    @SubscribeEvent
    public void onLoop(final GameZaloopEvent event) {
        if (AutoTotem.mc.player == null || AutoTotem.mc.world == null) {
            return;
        }
        if (this.mode.getValue() != ModeEn.SemiStrict) {
            return;
        }
        if (!(AutoTotem.mc.currentScreen instanceof GuiContainer)) {
            if (!this.clickQueue.isEmpty()) {
                if (!this.timer.passedMs((long)(this.delay.getValue() * 100.0f))) {
                    return;
                }
                final int slot = this.clickQueue.poll();
                try {
                    this.timer.reset();
                    if (EventManager.serversprint && this.StopSprint.getValue()) {
                        AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTotem.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                    }
                    AutoTotem.mc.playerController.windowClick(AutoTotem.mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            else {
                if (!AutoTotem.mc.player.inventory.getItemStack().isEmpty()) {
                    for (int index = 44; index >= 9; --index) {
                        if (AutoTotem.mc.player.inventoryContainer.getSlot(index).getStack().isEmpty()) {
                            if (EventManager.serversprint && this.StopSprint.getValue()) {
                                AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTotem.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                            }
                            AutoTotem.mc.playerController.windowClick(0, index, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                            return;
                        }
                    }
                }
                if (this.totem.getValue()) {
                    if (AutoTotem.mc.player.getHealth() + AutoTotem.mc.player.getAbsorptionAmount() <= this.totemHealthThreshold.getValue() || (this.totemOnElytra.getValue() && AutoTotem.mc.player.isElytraFlying()) || (this.fallCheck.getValue() && EntityUtil.getHealth((Entity)AutoTotem.mc.player) - ((AutoTotem.mc.player.fallDistance - 3.0f) / 2.0f + 3.5f) < 0.5 && !AutoTotem.mc.player.isElytraFlying())) {
                        this.putItemIntoOffhand(Items.TOTEM_OF_UNDYING);
                        return;
                    }
                    if (this.crystalCheck.getValue() == CrystalCheck.RANGE) {
                        final EntityEnderCrystal crystal = (EntityEnderCrystal)AutoTotem.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal && AutoTotem.mc.player.getDistance(e) <= this.crystalRange.getValue()).min(Comparator.comparing(c -> AutoTotem.mc.player.getDistance(c))).orElse(null);
                        if (crystal != null) {
                            this.putItemIntoOffhand(Items.TOTEM_OF_UNDYING);
                            return;
                        }
                    }
                    else if (this.crystalCheck.getValue() == CrystalCheck.DAMAGE) {
                        float damage = 0.0f;
                        final List<Entity> crystalsInRange = (List<Entity>)AutoTotem.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> AutoTotem.mc.player.getDistance(e) <= this.crystalRange.getValue()).collect(Collectors.toList());
                        for (final Entity entity : crystalsInRange) {
                            damage += CrystalUtils.calculateDamage((EntityEnderCrystal)entity, (Entity)AutoTotem.mc.player);
                        }
                        if (AutoTotem.mc.player.getHealth() + AutoTotem.mc.player.getAbsorptionAmount() - damage <= this.totemHealthThreshold.getValue()) {
                            this.putItemIntoOffhand(Items.TOTEM_OF_UNDYING);
                            return;
                        }
                    }
                    if (this.extraSafe.getValue() && this.crystalCheck()) {
                        this.putItemIntoOffhand(Items.TOTEM_OF_UNDYING);
                        return;
                    }
                }
                if (this.gapple.getValue() && this.isSword(AutoTotem.mc.player.getHeldItemMainhand().getItem())) {
                    if (this.rightClick.getValue() && !AutoTotem.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                        if (this.clearAfter.getValue()) {
                            this.putItemIntoOffhand(this.defaultItem.getValue().item);
                        }
                        return;
                    }
                    this.putItemIntoOffhand(Items.GOLDEN_APPLE);
                }
                else {
                    if (this.crystal.getValue()) {
                        if (((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).isEnabled()) {
                            this.putItemIntoOffhand(Items.END_CRYSTAL);
                            return;
                        }
                        if (this.clearAfter.getValue()) {
                            this.putItemIntoOffhand(this.defaultItem.getValue().item);
                            return;
                        }
                    }
                    if (this.hard.getValue()) {
                        if ((this.defaultItem.getValue().item == Items.SHIELD && AutoTotem.mc.player.getCooldownTracker().hasCooldown(Items.SHIELD)) || (this.defaultItem.getValue().item == Items.SHIELD && this.findItemSlot(Items.SHIELD) == -1 && AutoTotem.mc.player.getHeldItemOffhand().getItem() != Items.SHIELD)) {
                            this.putItemIntoOffhand(Items.GOLDEN_APPLE);
                        }
                        else {
                            this.putItemIntoOffhand(this.defaultItem.getValue().item);
                        }
                    }
                }
            }
        }
    }
    
    private boolean isSword(final Item item) {
        return item == Items.DIAMOND_SWORD || item == Items.IRON_SWORD || item == Items.GOLDEN_SWORD || item == Items.STONE_SWORD || item == Items.WOODEN_SWORD;
    }
    
    private int findItemSlot(final Item item) {
        int itemSlot = -1;
        for (int i = this.notFromHotbar.getValue() ? 9 : 0; i < 36; ++i) {
            final ItemStack stack = AutoTotem.mc.player.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() == item) {
                itemSlot = i;
                break;
            }
        }
        return itemSlot;
    }
    
    private void putItemIntoOffhand(final Item item) {
        if (AutoTotem.mc.player.getHeldItemOffhand().getItem() == item) {
            return;
        }
        final int slot = this.findItemSlot(item);
        if (this.hotbarTotem.getValue() && item == Items.TOTEM_OF_UNDYING) {
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = (ItemStack)AutoTotem.mc.player.inventory.mainInventory.get(i);
                if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    if (AutoTotem.mc.player.inventory.currentItem != i) {
                        AutoTotem.mc.player.inventory.currentItem = i;
                    }
                    return;
                }
            }
        }
        if (slot != -1) {
            if (this.delay.getValue() > 0.0f) {
                if (this.timer.passedMs((long)(this.delay.getValue() * 100.0f))) {
                    if (EventManager.serversprint && this.StopSprint.getValue()) {
                        AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTotem.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                    }
                    AutoTotem.mc.playerController.windowClick(AutoTotem.mc.player.inventoryContainer.windowId, (slot < 9) ? (slot + 36) : slot, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                    this.timer.reset();
                }
                else {
                    this.clickQueue.add((slot < 9) ? (slot + 36) : slot);
                }
                this.clickQueue.add(45);
                this.clickQueue.add((slot < 9) ? (slot + 36) : slot);
            }
            else {
                this.timer.reset();
                if (EventManager.serversprint && this.StopSprint.getValue()) {
                    AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTotem.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                }
                AutoTotem.mc.playerController.windowClick(AutoTotem.mc.player.inventoryContainer.windowId, (slot < 9) ? (slot + 36) : slot, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                AutoTotem.mc.playerController.windowClick(AutoTotem.mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                AutoTotem.mc.playerController.windowClick(AutoTotem.mc.player.inventoryContainer.windowId, (slot < 9) ? (slot + 36) : slot, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
            }
        }
    }
    
    private boolean crystalCheck() {
        float cumDmg = 0.0f;
        final ArrayList<Float> damageValues = new ArrayList<Float>();
        damageValues.add(this.calculateDamageAABB(AutoTotem.mc.player.getPosition().add(1, 0, 0)));
        damageValues.add(this.calculateDamageAABB(AutoTotem.mc.player.getPosition().add(-1, 0, 0)));
        damageValues.add(this.calculateDamageAABB(AutoTotem.mc.player.getPosition().add(0, 0, 1)));
        damageValues.add(this.calculateDamageAABB(AutoTotem.mc.player.getPosition().add(0, 0, -1)));
        damageValues.add(this.calculateDamageAABB(AutoTotem.mc.player.getPosition()));
        for (final float damage : damageValues) {
            cumDmg += damage;
            if (AutoTotem.mc.player.getHealth() + AutoTotem.mc.player.getAbsorptionAmount() - damage <= this.totemHealthThreshold.getValue()) {
                return true;
            }
        }
        return AutoTotem.mc.player.getHealth() + AutoTotem.mc.player.getAbsorptionAmount() - cumDmg <= this.totemHealthThreshold.getValue();
    }
    
    private float calculateDamageAABB(final BlockPos pos) {
        final List<Entity> crystalsInAABB = (List<Entity>)AutoTotem.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).stream().filter(e -> e instanceof EntityEnderCrystal).collect(Collectors.toList());
        float totalDamage = 0.0f;
        for (final Entity crystal : crystalsInAABB) {
            totalDamage += CrystalUtils.calculateDamage(crystal.posX, crystal.posY, crystal.posZ, (Entity)AutoTotem.mc.player);
        }
        return totalDamage;
    }
    
    private boolean checkTNT() {
        if (!this.checkTNT.getValue()) {
            return false;
        }
        for (final Entity entity : AutoTotem.mc.world.loadedEntityList) {
            if (entity instanceof EntityTNTPrimed && AutoTotem.mc.player.getDistanceSq(entity) <= 25.0) {
                return true;
            }
            if (entity instanceof EntityMinecartTNT && AutoTotem.mc.player.getDistanceSq(entity) <= 25.0) {
                return true;
            }
        }
        return false;
    }
    
    private boolean IsValidBlockPos(final BlockPos pos) {
        final IBlockState state = AutoTotem.mc.world.getBlockState(pos);
        return state.getBlock() instanceof BlockObsidian;
    }
    
    private boolean checkObsidian() {
        if (!this.checkObsidian.getValue()) {
            return false;
        }
        final BlockPos pos = getSphere(new BlockPos(Math.floor(AutoTotem.mc.player.posX), Math.floor(AutoTotem.mc.player.posY), Math.floor(AutoTotem.mc.player.posZ)), 5.0f, 6, false, true, 0).stream().filter(this::IsValidBlockPos).min(Comparator.comparing(blockPos -> getDistanceOfEntityToBlock((Entity)AutoTotem.mc.player, blockPos))).orElse(null);
        return pos != null;
    }
    
    @SubscribeEvent
    public void onPostMotion(final EventPostSync e) {
        if (this.mode.getValue() == ModeEn.Strict) {
            if (AutoTotem.mc.currentScreen == null) {
                final int itemSlot = this.getItemSlot();
                if (itemSlot != -1 && !this.isOffhand(AutoTotem.mc.player.inventoryContainer.getSlot(itemSlot).getStack()) && this.timer.passedMs(200L)) {
                    this.timer.reset();
                    AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTotem.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                    AutoTotem.mc.playerController.windowClick(0, itemSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                    AutoTotem.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                    if (AutoTotem.mc.player.inventory.getItemStack().isEmpty()) {
                        AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketCloseWindow(AutoTotem.mc.player.inventoryContainer.windowId));
                        AutoTotem.mc.playerController.updateController();
                        return;
                    }
                    if (AutoTotem.mc.player.inventory.getItemStack().getItem() == Items.GOLDEN_APPLE) {
                        AutoTotem.mc.playerController.windowClick(0, this.GappleSlot.getValue() + 36, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                    }
                    if (AutoTotem.mc.player.inventory.getItemStack().isEmpty()) {
                        AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketCloseWindow(AutoTotem.mc.player.inventoryContainer.windowId));
                        AutoTotem.mc.playerController.updateController();
                        return;
                    }
                    int returnSlot = -1;
                    for (int i = 9; i < 45; ++i) {
                        if (AutoTotem.mc.player.inventory.getStackInSlot(i).isEmpty()) {
                            returnSlot = i;
                            break;
                        }
                    }
                    if (returnSlot != -1) {
                        AutoTotem.mc.playerController.windowClick(0, returnSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                    }
                    AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketCloseWindow(AutoTotem.mc.player.inventoryContainer.windowId));
                    AutoTotem.mc.playerController.updateController();
                }
            }
            if (this.deathVerbose.getValue() && AutoTotem.mc.currentScreen instanceof GuiGameOver && this.stop_spam.passedMs(3000L)) {
                Command.sendMessage(this.getVerbose());
                this.stop_spam.reset();
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.ReceivePost event) {
        if (fullNullCheck()) {
            return;
        }
        if (this.mode.getValue() == ModeEn.Strict) {
            if (event.getPacket() instanceof SPacketEntityStatus && ((SPacketEntityStatus)event.getPacket()).getOpCode() == 35) {
                final Entity entity = ((SPacketEntityStatus)event.getPacket()).getEntity((World)AutoTotem.mc.world);
                if (entity != null && entity.equals((Object)AutoTotem.mc.player) && this.timer.passedMs(200L)) {
                    this.timer.reset();
                    final int itemSlot = this.getItemSlot();
                    if (itemSlot != -1) {
                        AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTotem.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                        AutoTotem.mc.playerController.windowClick(0, itemSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                        AutoTotem.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                        AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketCloseWindow(AutoTotem.mc.player.inventoryContainer.windowId));
                        AutoTotem.mc.playerController.updateController();
                    }
                }
            }
            AutoTotem.last_packet_time = (int)(System.currentTimeMillis() - AutoTotem.packet_latency_timer);
        }
    }
    
    public int getItemSlot() {
        Item item;
        if (this.offhand.getValue() == OffHand.Totem) {
            item = Items.TOTEM_OF_UNDYING;
        }
        else if (this.offhand.getValue() == OffHand.Crystal) {
            item = Items.END_CRYSTAL;
        }
        else {
            item = Items.GOLDEN_APPLE;
        }
        if (this.offhandoverride.getValue() && this.isSword(AutoTotem.mc.player.getHeldItemMainhand().getItem()) && AutoTotem.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            item = Items.GOLDEN_APPLE;
        }
        if (this.lethal.getValue()) {
            if (EntityUtil.getHealth((Entity)AutoTotem.mc.player) - ((AutoTotem.mc.player.fallDistance - 3.0f) / 2.0f + 3.5f) < 0.5 && !AutoTotem.mc.player.isOverWater()) {
                item = Items.TOTEM_OF_UNDYING;
            }
            if (AutoTotem.mc.player.isElytraFlying()) {
                item = Items.TOTEM_OF_UNDYING;
            }
            for (final Entity entity : AutoTotem.mc.world.loadedEntityList) {
                if (entity != null) {
                    if (entity.isDead) {
                        continue;
                    }
                    final double crystalRange = AutoTotem.mc.player.getDistance(entity);
                    if (crystalRange > 6.0) {
                        continue;
                    }
                    if (entity instanceof EntityEnderCrystal && EntityUtil.getHealth((Entity)AutoTotem.mc.player) - CrystalUtils.calculateDamage((EntityEnderCrystal)entity, (Entity)AutoTotem.mc.player) < 0.5) {
                        item = Items.TOTEM_OF_UNDYING;
                        break;
                    }
                    continue;
                }
            }
        }
        if (EntityUtil.getHealth((Entity)AutoTotem.mc.player) <= this.healthF.getValue()) {
            item = Items.TOTEM_OF_UNDYING;
        }
        int itemSlot = -1;
        int gappleSlot = -1;
        int crappleSlot = -1;
        for (int i = 9; i < 45; ++i) {
            if (AutoTotem.mc.player.inventoryContainer.getSlot(i).getStack().getItem().equals(item)) {
                if (!item.equals(Items.GOLDEN_APPLE)) {
                    itemSlot = i;
                    break;
                }
                final ItemStack stack = AutoTotem.mc.player.inventoryContainer.getSlot(i).getStack();
                if (stack.hasEffect()) {
                    gappleSlot = i;
                }
                else {
                    crappleSlot = i;
                }
            }
        }
        if (item.equals(Items.GOLDEN_APPLE)) {
            if (this.crapple.getValue()) {
                if (AutoTotem.mc.player.isPotionActive(MobEffects.ABSORPTION)) {
                    if (crappleSlot != -1) {
                        itemSlot = crappleSlot;
                    }
                    else if (gappleSlot != -1) {
                        itemSlot = gappleSlot;
                    }
                }
                else if (gappleSlot != -1) {
                    itemSlot = gappleSlot;
                }
            }
            else if (gappleSlot != -1) {
                itemSlot = gappleSlot;
            }
            else if (crappleSlot != -1) {
                itemSlot = crappleSlot;
            }
        }
        return itemSlot;
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPacketSend(final PacketEvent.Send e) {
        GuiMove.pause = true;
        if (e.getPacket() instanceof CPacketClickWindow && this.mode.getValue() == ModeEn.Strict && this.funnyGame.getValue() && AutoTotem.mc.player.onGround && MovementUtil.isMoving() && AutoTotem.mc.world.getCollisionBoxes((Entity)AutoTotem.mc.player, AutoTotem.mc.player.getEntityBoundingBox().offset(0.0, 0.0656, 0.0)).isEmpty()) {
            if (AutoTotem.mc.player.isSprinting()) {
                AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTotem.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoTotem.mc.player.posX, AutoTotem.mc.player.posY + 0.0656, AutoTotem.mc.player.posZ, false));
        }
    }
    
    public boolean isOffhand(final ItemStack in) {
        final ItemStack offhandItem = AutoTotem.mc.player.getHeldItemOffhand();
        if (!in.getItem().equals(Items.GOLDEN_APPLE)) {
            return offhandItem.getItem().equals(in.getItem());
        }
        if (offhandItem.getItem().equals(in.getItem())) {
            final boolean gapple = in.hasEffect();
            return gapple == offhandItem.hasEffect();
        }
        return false;
    }
    
    public String getVerbose() {
        final String in = "Death due to possible reasons: ";
        final String server_latency = "server processing latency of " + Thunderhack.serverManager.getPing() + " ms, ";
        final String packet_latency = "packet process latency of " + MathUtil.round2(AutoTotem.last_packet_time / 1000.0f) + " ms, ";
        if (InventoryUtil.getCount(Items.TOTEM_OF_UNDYING) == 0) {
            return ChatFormatting.RED + in + server_latency + packet_latency + "(do not worry about rare spikes of 5-10 ms), no totems";
        }
        if (Thunderhack.serverManager.getPing() > 300) {
            return ChatFormatting.RED + in + server_latency + packet_latency + "(high ping) , totem fail";
        }
        if (MathUtil.round2(AutoTotem.last_packet_time / 1000.0f) > 20.0f) {
            return ChatFormatting.RED + in + server_latency + packet_latency + "(high load on the packet listener! turn off unnecessary modules) , totem fail";
        }
        return ChatFormatting.RED + in + server_latency + packet_latency + "(do not worry about rare spikes of 5-10 ms), totem fail";
    }
    
    static {
        AutoTotem.packet_latency_timer = 0L;
        AutoTotem.last_packet_time = 0;
    }
    
    private enum ModeEn
    {
        Strict, 
        SemiStrict, 
        Matrix;
    }
    
    private enum OffHand
    {
        Totem, 
        Crystal, 
        GApple;
    }
    
    private enum CrystalCheck
    {
        NONE, 
        DAMAGE, 
        RANGE;
    }
    
    private enum Default
    {
        TOTEM(Items.TOTEM_OF_UNDYING), 
        CRYSTAL(Items.END_CRYSTAL), 
        GAPPLE(Items.GOLDEN_APPLE), 
        AIR(Items.AIR), 
        SHIELD(Items.SHIELD);
        
        public Item item;
        
        private Default(final Item item) {
            this.item = item;
        }
    }
}
