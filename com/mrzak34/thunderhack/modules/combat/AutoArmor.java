//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import com.mrzak34.thunderhack.modules.player.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.inventory.*;
import java.util.concurrent.atomic.*;
import com.mrzak34.thunderhack.modules.movement.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.*;

public class AutoArmor extends Module
{
    private final Setting<Mode> mode;
    private final Setting<Boolean> armorSaver;
    public Setting<Float> depletion;
    private final Setting<Integer> delay;
    private final Setting<Boolean> elytraPrio;
    private final Setting<Boolean> smart;
    private final Setting<Boolean> strict;
    private final Setting<Boolean> pauseWhenSafe;
    private final Setting<Boolean> allowMend;
    private final Timer rightClickTimer;
    private boolean sleep;
    
    public AutoArmor() {
        super("AutoArmor", "\u0410\u0432\u0442\u043e\u0431\u0440\u043e\u043d\u044f", "puts on best armor", Category.PLAYER);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.FunnyGame));
        this.armorSaver = (Setting<Boolean>)this.register(new Setting("ArmorSaver", (T)false));
        this.depletion = (Setting<Float>)this.register(new Setting("Depletion", (T)0.75f, (T)0.5f, (T)0.95f, v -> this.armorSaver.getValue()));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)1, (T)1, (T)10));
        this.elytraPrio = (Setting<Boolean>)this.register(new Setting("ElytraPrio", (T)false));
        this.smart = (Setting<Boolean>)this.register(new Setting("Smart", (T)false, v -> this.elytraPrio.getValue()));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.pauseWhenSafe = (Setting<Boolean>)this.register(new Setting("PauseWhenSafe", (T)false));
        this.allowMend = (Setting<Boolean>)this.register(new Setting("AllowMend", (T)false));
        this.rightClickTimer = new Timer();
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketClickWindow) {
            AutoArmor.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoArmor.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.mode.getValue() == Mode.Default) {
            if (AutoArmor.mc.world == null || AutoArmor.mc.player == null) {
                return;
            }
            if (AutoArmor.mc.player.ticksExisted % this.delay.getValue() != 0) {
                return;
            }
            if (this.strict.getValue() && (AutoArmor.mc.player.motionX != 0.0 || AutoArmor.mc.player.motionZ != 0.0)) {
                return;
            }
            if (this.pauseWhenSafe.getValue()) {
                final List<Entity> proximity = (List<Entity>)AutoArmor.mc.world.loadedEntityList.stream().filter(e -> (e instanceof EntityPlayer && !e.equals((Object)AutoArmor.mc.player) && AutoArmor.mc.player.getDistance(e) <= 6.0f) || (e instanceof EntityEnderCrystal && AutoArmor.mc.player.getDistance(e) <= 12.0f)).collect(Collectors.toList());
                if (proximity.isEmpty()) {
                    return;
                }
            }
            if (AutoMend.isMending) {
                return;
            }
            if (this.allowMend.getValue() && !this.rightClickTimer.passedMs(500L)) {
                for (int i = 0; i < AutoArmor.mc.player.inventory.armorInventory.size(); ++i) {
                    final ItemStack armorPiece = (ItemStack)AutoArmor.mc.player.inventory.armorInventory.get(i);
                    if (armorPiece.getEnchantmentTagList() != null) {
                        boolean mending = false;
                        for (final Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.getEnchantments(armorPiece).entrySet()) {
                            if (entry.getKey().getName().contains("mending")) {
                                mending = true;
                                break;
                            }
                        }
                        if (!mending) {
                            continue;
                        }
                    }
                    if (!armorPiece.isEmpty()) {
                        final long freeSlots = AutoArmor.mc.player.inventory.mainInventory.stream().filter(is -> is.isEmpty() || is.getItem() == Items.AIR).map(is -> AutoArmor.mc.player.inventory.getSlotFor(is)).count();
                        if (freeSlots <= 0L) {
                            return;
                        }
                        if (armorPiece.getItemDamage() != 0) {
                            this.shiftClickSpot(8 - i);
                            return;
                        }
                    }
                }
                return;
            }
            if (AutoArmor.mc.currentScreen instanceof GuiContainer) {
                return;
            }
            final AtomicBoolean hasSwapped = new AtomicBoolean(false);
            if (this.sleep) {
                this.sleep = false;
                return;
            }
            boolean ep = this.elytraPrio.getValue();
            if (this.smart.getValue() && !((ElytraFlight)Thunderhack.moduleManager.getModuleByClass((Class)ElytraFlight.class)).isOn()) {
                ep = false;
            }
            final Set<InvStack> replacements = new HashSet<InvStack>();
            for (int slot = 0; slot < 36; ++slot) {
                final InvStack invStack2 = new InvStack(slot, AutoArmor.mc.player.inventory.getStackInSlot(slot));
                if (invStack2.stack.getItem() instanceof ItemArmor || invStack2.stack.getItem() instanceof ItemElytra) {
                    replacements.add(invStack2);
                }
            }
            List<InvStack> armors = replacements.stream().filter(invStack -> invStack.stack.getItem() instanceof ItemArmor).filter(invStack -> !this.armorSaver.getValue() || invStack.stack.getItem().getDurabilityForDisplay(invStack.stack) < this.depletion.getValue()).sorted(Comparator.comparingInt(invStack -> invStack.slot)).sorted(Comparator.comparingInt(invStack -> ((ItemArmor)invStack.stack.getItem()).damageReduceAmount)).collect((Collector<? super Object, ?, List<InvStack>>)Collectors.toList());
            final boolean wasEmpty = armors.isEmpty();
            if (wasEmpty) {
                armors = replacements.stream().filter(invStack -> invStack.stack.getItem() instanceof ItemArmor).sorted(Comparator.comparingInt(invStack -> invStack.slot)).sorted(Comparator.comparingInt(invStack -> ((ItemArmor)invStack.stack.getItem()).damageReduceAmount)).collect((Collector<? super Object, ?, List<InvStack>>)Collectors.toList());
            }
            final List<InvStack> elytras = replacements.stream().filter(invStack -> invStack.stack.getItem() instanceof ItemElytra).sorted(Comparator.comparingInt(invStack -> invStack.slot)).collect((Collector<? super Object, ?, List<InvStack>>)Collectors.toList());
            final Item currentHeadItem = AutoArmor.mc.player.inventory.getStackInSlot(39).getItem();
            final Item currentChestItem = AutoArmor.mc.player.inventory.getStackInSlot(38).getItem();
            final Item currentLegsItem = AutoArmor.mc.player.inventory.getStackInSlot(37).getItem();
            final Item currentFeetItem = AutoArmor.mc.player.inventory.getStackInSlot(36).getItem();
            final boolean replaceHead = currentHeadItem.equals(Items.AIR) || (!wasEmpty && this.armorSaver.getValue() && AutoArmor.mc.player.inventory.getStackInSlot(39).getItem().getDurabilityForDisplay(AutoArmor.mc.player.inventory.getStackInSlot(39)) >= this.depletion.getValue());
            final boolean replaceChest = currentChestItem.equals(Items.AIR) || (!wasEmpty && this.armorSaver.getValue() && AutoArmor.mc.player.inventory.getStackInSlot(38).getItem().getDurabilityForDisplay(AutoArmor.mc.player.inventory.getStackInSlot(38)) >= this.depletion.getValue());
            final boolean replaceLegs = currentLegsItem.equals(Items.AIR) || (!wasEmpty && this.armorSaver.getValue() && AutoArmor.mc.player.inventory.getStackInSlot(37).getItem().getDurabilityForDisplay(AutoArmor.mc.player.inventory.getStackInSlot(37)) >= this.depletion.getValue());
            final boolean replaceFeet = currentFeetItem.equals(Items.AIR) || (!wasEmpty && this.armorSaver.getValue() && AutoArmor.mc.player.inventory.getStackInSlot(36).getItem().getDurabilityForDisplay(AutoArmor.mc.player.inventory.getStackInSlot(36)) >= this.depletion.getValue());
            if (replaceHead && !hasSwapped.get()) {
                final AtomicBoolean atomicBoolean;
                armors.stream().filter(invStack -> invStack.stack.getItem() instanceof ItemArmor).filter(invStack -> ((ItemArmor)invStack.stack.getItem()).armorType.equals((Object)EntityEquipmentSlot.HEAD)).findFirst().ifPresent(invStack -> {
                    this.swapSlot(invStack.slot, 5);
                    atomicBoolean.set(true);
                    return;
                });
            }
            if (ep && !(currentChestItem instanceof ItemElytra) && elytras.size() > 0 && !hasSwapped.get()) {
                final AtomicBoolean atomicBoolean2;
                elytras.stream().findFirst().ifPresent(invStack -> {
                    this.swapSlot(invStack.slot, 6);
                    atomicBoolean2.set(true);
                    return;
                });
            }
            if (replaceChest || (!ep && currentChestItem.equals(Items.ELYTRA) && !hasSwapped.get())) {
                final AtomicBoolean atomicBoolean3;
                armors.stream().filter(invStack -> invStack.stack.getItem() instanceof ItemArmor).filter(invStack -> ((ItemArmor)invStack.stack.getItem()).armorType.equals((Object)EntityEquipmentSlot.CHEST)).findFirst().ifPresent(invStack -> {
                    this.swapSlot(invStack.slot, 6);
                    atomicBoolean3.set(true);
                    return;
                });
            }
            if (replaceLegs && !hasSwapped.get()) {
                final AtomicBoolean atomicBoolean4;
                armors.stream().filter(invStack -> invStack.stack.getItem() instanceof ItemArmor).filter(invStack -> ((ItemArmor)invStack.stack.getItem()).armorType.equals((Object)EntityEquipmentSlot.LEGS)).findFirst().ifPresent(invStack -> {
                    this.swapSlot(invStack.slot, 7);
                    atomicBoolean4.set(true);
                    return;
                });
            }
            if (replaceFeet && !hasSwapped.get()) {
                final AtomicBoolean atomicBoolean5;
                armors.stream().filter(invStack -> invStack.stack.getItem() instanceof ItemArmor).filter(invStack -> ((ItemArmor)invStack.stack.getItem()).armorType.equals((Object)EntityEquipmentSlot.FEET)).findFirst().ifPresent(invStack -> {
                    this.swapSlot(invStack.slot, 8);
                    atomicBoolean5.set(true);
                });
            }
        }
        else if (AutoArmor.mc.player != null && AutoArmor.mc.world != null) {
            if (AutoArmor.mc.player.ticksExisted % 2 == 0) {
                return;
            }
            if (AutoArmor.mc.currentScreen instanceof GuiContainer && !(AutoArmor.mc.currentScreen instanceof InventoryEffectRenderer)) {
                return;
            }
            final int[] bestArmorSlots = new int[4];
            final int[] bestArmorValues = new int[4];
            for (int armorType = 0; armorType < 4; ++armorType) {
                final ItemStack oldArmor = AutoArmor.mc.player.inventory.armorItemInSlot(armorType);
                if (oldArmor.getItem() instanceof ItemArmor) {
                    bestArmorValues[armorType] = ((ItemArmor)oldArmor.getItem()).damageReduceAmount;
                }
                bestArmorSlots[armorType] = -1;
            }
            for (int slot = 0; slot < 36; ++slot) {
                final ItemStack stack = AutoArmor.mc.player.inventory.getStackInSlot(slot);
                if (stack.getCount() <= 1) {
                    if (stack.getItem() instanceof ItemArmor) {
                        final ItemArmor armor = (ItemArmor)stack.getItem();
                        final int armorType2 = armor.armorType.ordinal() - 2;
                        if (armorType2 != 2 || !AutoArmor.mc.player.inventory.armorItemInSlot(armorType2).getItem().equals(Items.ELYTRA)) {
                            final int armorValue;
                            if ((armorValue = armor.damageReduceAmount) > bestArmorValues[armorType2]) {
                                bestArmorSlots[armorType2] = slot;
                                bestArmorValues[armorType2] = armorValue;
                            }
                        }
                    }
                }
            }
            for (int armorType = 0; armorType < 4; ++armorType) {
                int slot2 = bestArmorSlots[armorType];
                final ItemStack oldArmor;
                if (slot2 != -1 && ((oldArmor = AutoArmor.mc.player.inventory.armorItemInSlot(armorType)) != ItemStack.EMPTY || AutoArmor.mc.player.inventory.getFirstEmptyStack() != -1)) {
                    if (slot2 < 9) {
                        slot2 += 36;
                    }
                    AutoArmor.mc.playerController.windowClick(0, 8 - armorType, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmor.mc.player);
                    AutoArmor.mc.playerController.windowClick(0, slot2, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmor.mc.player);
                    break;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onItemRightClick(final PlayerInteractEvent.RightClickItem event) {
        if (event.getEntityPlayer() != AutoArmor.mc.player) {
            return;
        }
        if (event.getItemStack().getItem() != Items.EXPERIENCE_BOTTLE) {
            return;
        }
        this.rightClickTimer.reset();
    }
    
    private void swapSlot(final int source, final int target) {
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, (source < 9) ? (source + 36) : source, 0, ClickType.PICKUP, (EntityPlayer)AutoArmor.mc.player);
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, target, 0, ClickType.PICKUP, (EntityPlayer)AutoArmor.mc.player);
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, (source < 9) ? (source + 36) : source, 0, ClickType.PICKUP, (EntityPlayer)AutoArmor.mc.player);
        this.sleep = true;
    }
    
    private void shiftClickSpot(final int source) {
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, source, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmor.mc.player);
    }
    
    public enum Mode
    {
        FunnyGame, 
        Default;
    }
}
