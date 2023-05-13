//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.enchantment.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.util.phobos.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.item.*;

public class InventoryUtil implements Util
{
    public static int getBestSword() {
        int b = -1;
        float f = 1.0f;
        for (int b2 = 0; b2 < 9; ++b2) {
            final ItemStack itemStack = Util.mc.player.inventory.getStackInSlot(b2);
            if (itemStack != null && itemStack.getItem() instanceof ItemSword) {
                final ItemSword itemSword = (ItemSword)itemStack.getItem();
                float f2 = (float)itemSword.getMaxDamage();
                f2 += EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(20), itemStack);
                if (f2 > f) {
                    f = f2;
                    b = b2;
                }
            }
        }
        return b;
    }
    
    public static int getItemCount(final Item item) {
        if (InventoryUtil.mc.player == null) {
            return 0;
        }
        int n = 0;
        for (int n2 = 44, i = 0; i <= n2; ++i) {
            final ItemStack itemStack = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() == item) {
                n += itemStack.getCount();
            }
        }
        return n;
    }
    
    public static int getBestAxe() {
        int b = -1;
        float f = 1.0f;
        for (int b2 = 0; b2 < 9; ++b2) {
            final ItemStack itemStack = Util.mc.player.inventory.getStackInSlot(b2);
            if (itemStack != null && itemStack.getItem() instanceof ItemAxe) {
                final ItemAxe axe = (ItemAxe)itemStack.getItem();
                float f2 = (float)axe.getMaxDamage();
                f2 += EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(20), itemStack);
                if (f2 > f) {
                    f = f2;
                    b = b2;
                }
            }
        }
        return b;
    }
    
    public static int hotbarToInventory(final int slot) {
        if (slot == -2) {
            return 45;
        }
        if (slot > -1 && slot < 9) {
            return 36 + slot;
        }
        return slot;
    }
    
    public static void bypassSwitch(final int slot) {
        if (slot >= 0) {
            InventoryUtil.mc.playerController.pickItem(slot);
        }
    }
    
    public static void switchTo(final int slot) {
        if (InventoryUtil.mc.player.inventory.currentItem != slot && slot > -1 && slot < 9) {
            InventoryUtil.mc.player.inventory.currentItem = slot;
            syncItem();
        }
    }
    
    public static void switchToBypass(final int slot) {
        int lastSlot;
        int targetSlot;
        int currentSlot;
        HelperRotation.acquire(() -> {
            if (InventoryUtil.mc.player.inventory.currentItem != slot && slot > -1 && slot < 9) {
                lastSlot = InventoryUtil.mc.player.inventory.currentItem;
                targetSlot = hotbarToInventory(slot);
                currentSlot = hotbarToInventory(lastSlot);
                InventoryUtil.mc.playerController.windowClick(0, targetSlot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
                InventoryUtil.mc.playerController.windowClick(0, currentSlot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
                InventoryUtil.mc.playerController.windowClick(0, targetSlot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
            }
        });
    }
    
    public static void switchToBypassAlt(final int slot) {
        HelperRotation.acquire(() -> {
            if (InventoryUtil.mc.player.inventory.currentItem != slot && slot > -1 && slot < 9) {
                HelperRotation.acquire(() -> InventoryUtil.mc.playerController.windowClick(0, slot, InventoryUtil.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)InventoryUtil.mc.player));
            }
        });
    }
    
    public static EnumHand getHand(final int slot) {
        return (slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
    }
    
    public static void syncItem() {
        ((IPlayerControllerMP)InventoryUtil.mc.playerController).syncItem();
    }
    
    public static EnumHand getHand(final Item item) {
        return (InventoryUtil.mc.player.getHeldItemMainhand().getItem() == item) ? EnumHand.MAIN_HAND : ((InventoryUtil.mc.player.getHeldItemOffhand().getItem() == item) ? EnumHand.OFF_HAND : null);
    }
    
    public static void switchToHotbarSlot(final int slot, final boolean silent) {
        if (InventoryUtil.mc.player.inventory.currentItem == slot || slot < 0) {
            return;
        }
        if (silent) {
            InventoryUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            InventoryUtil.mc.playerController.updateController();
        }
        else {
            InventoryUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            InventoryUtil.mc.player.inventory.currentItem = slot;
            InventoryUtil.mc.playerController.updateController();
        }
    }
    
    public static int containerToSlots(final int containerSlot) {
        if (containerSlot < 5 || containerSlot > 45) {
            return -1;
        }
        if (containerSlot <= 9) {
            return 44 - containerSlot;
        }
        if (containerSlot < 36) {
            return containerSlot;
        }
        if (containerSlot < 45) {
            return containerSlot - 36;
        }
        return 40;
    }
    
    public static void put(final int slot, final ItemStack stack) {
        if (slot == -2) {
            InventoryUtil.mc.player.inventory.setItemStack(stack);
        }
        InventoryUtil.mc.player.inventoryContainer.putStackInSlot(slot, stack);
        final int invSlot = containerToSlots(slot);
        if (invSlot != -1) {
            InventoryUtil.mc.player.inventory.setInventorySlotContents(invSlot, stack);
        }
    }
    
    public static int getCount(final Item item) {
        int result = 0;
        for (int i = 0; i < 46; ++i) {
            final ItemStack stack = (ItemStack)InventoryUtil.mc.player.inventoryContainer.getInventory().get(i);
            if (stack.getItem() == item) {
                result += stack.getCount();
            }
        }
        if (InventoryUtil.mc.player.inventory.getItemStack().getItem() == item) {
            result += InventoryUtil.mc.player.inventory.getItemStack().getCount();
        }
        return result;
    }
    
    public static ItemStack get(final int slot) {
        if (slot == -2) {
            return InventoryUtil.mc.player.inventory.getItemStack();
        }
        return (ItemStack)InventoryUtil.mc.player.inventoryContainer.getInventory().get(slot);
    }
    
    public static int findSoupAtHotbar() {
        int b = -1;
        for (int a = 0; a < 9; ++a) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(a).getItem() == Items.MUSHROOM_STEW) {
                b = a;
            }
        }
        return b;
    }
    
    public static int findFirstBlockSlot(final Class<? extends Block> blockToFind, final int lower, final int upper) {
        int slot = -1;
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = lower; i <= upper; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    if (blockToFind.isInstance(((ItemBlock)stack.getItem()).getBlock())) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    public static int getBowAtHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Util.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() instanceof ItemBow) {
                return i;
            }
        }
        return -1;
    }
    
    public static int getCrysathotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Util.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() instanceof ItemEndCrystal) {
                return i;
            }
        }
        return -1;
    }
    
    public static int getPicatHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Util.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() instanceof ItemPickaxe) {
                return i;
            }
        }
        return -1;
    }
    
    public static int getPowderAtHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Util.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem().getItemStackDisplayName(itemStack).equals("\u041f\u043e\u0440\u043e\u0445")) {
                return i;
            }
        }
        return 1;
    }
    
    public static int findHotbarBlock(final Class clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (clazz.isInstance(stack.getItem())) {
                    return i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block;
                    if (clazz.isInstance(block = ((ItemBlock)stack.getItem()).getBlock())) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static int findItemAtHotbar(final Item stacks) {
        for (int i = 0; i < 9; ++i) {
            final Item stack = InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem();
            if (stack != Items.AIR) {
                if (stack == stacks) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static int findHotbarBlock(final Block blockIn) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            final Block block;
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock && (block = ((ItemBlock)stack.getItem()).getBlock()) == blockIn) {
                return i;
            }
        }
        return -1;
    }
    
    public static int getCappuchinoAtHotbar(final boolean old) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Util.mc.player.inventory.getStackInSlot(i);
            if (old) {
                if (itemStack.getItem() != Items.POTIONITEM) {
                    continue;
                }
            }
            else if (itemStack.getItem() != Items.DRAGON_BREATH) {
                continue;
            }
            if (itemStack.getDisplayName().contains("\u041a\u0430\u043f\u043f\u0443\u0447\u0438\u043d\u043e")) {
                return i;
            }
        }
        return -1;
    }
    
    public static int getAmericanoAtHotbar(final boolean old) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Util.mc.player.inventory.getStackInSlot(i);
            if (old) {
                if (itemStack.getItem() != Items.POTIONITEM) {
                    continue;
                }
            }
            else if (itemStack.getItem() != Items.DRAGON_BREATH) {
                continue;
            }
            if (itemStack.getDisplayName().contains("\u0410\u043c\u0435\u0440\u0438\u043a\u0430\u043d\u043e")) {
                return i;
            }
        }
        return -1;
    }
    
    public static int getOzeraAtHotbar(final boolean old) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Util.mc.player.inventory.getStackInSlot(i);
            if (old) {
                if (itemStack.getItem() != Items.POTIONITEM) {
                    continue;
                }
            }
            else if (itemStack.getItem() != Items.DRAGON_BREATH) {
                continue;
            }
            if (itemStack.getDisplayName().contains("\u0420\u043e\u0434\u043d\u044b\u0435 \u043e\u0437\u0451\u0440\u0430")) {
                return i;
            }
        }
        return -1;
    }
    
    public static ItemStack getPotionItemStack(final boolean old) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Util.mc.player.inventory.getStackInSlot(i);
            if (old) {
                if (itemStack.getItem() != Items.POTIONITEM) {
                    continue;
                }
            }
            else if (itemStack.getItem() != Items.DRAGON_BREATH) {
                continue;
            }
            if (itemStack.getDisplayName().contains("\u041a\u0430\u043f\u043f\u0443\u0447\u0438\u043d\u043e")) {
                return itemStack;
            }
        }
        return null;
    }
    
    public static boolean isInstanceOf(final ItemStack stack, final Class clazz) {
        if (stack == null) {
            return false;
        }
        final Item item = stack.getItem();
        if (clazz.isInstance(item)) {
            return true;
        }
        if (item instanceof ItemBlock) {
            final Block block = Block.getBlockFromItem(item);
            return clazz.isInstance(block);
        }
        return false;
    }
    
    public static boolean isHolding(final EntityPlayer player, final Item experienceBottle) {
        return player.getHeldItemMainhand().getItem() == experienceBottle || player.getHeldItemOffhand().getItem() == experienceBottle;
    }
    
    public static boolean isHolding(final Item experienceBottle) {
        return InventoryUtil.mc.player.getHeldItemMainhand().getItem() == experienceBottle || InventoryUtil.mc.player.getHeldItemOffhand().getItem() == experienceBottle;
    }
    
    public static boolean isHolding(final Block block) {
        final ItemStack mainHand = InventoryUtil.mc.player.getHeldItemMainhand();
        final ItemStack offHand = InventoryUtil.mc.player.getHeldItemOffhand();
        return mainHand.getItem() instanceof ItemBlock && offHand.getItem() instanceof ItemBlock && (((ItemBlock)mainHand.getItem()).getBlock() == block || ((ItemBlock)offHand.getItem()).getBlock() == block);
    }
    
    public static int getRodSlot() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack item = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (item.getItem() == Items.FISHING_ROD && item.getItemDamage() < 52) {
                return i;
            }
        }
        return -1;
    }
    
    public static int swapToHotbarSlot(final int slot, final boolean silent) {
        if (InventoryUtil.mc.player.inventory.currentItem == slot || slot < 0 || slot > 8) {
            return slot;
        }
        InventoryUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        if (!silent) {
            InventoryUtil.mc.player.inventory.currentItem = slot;
        }
        InventoryUtil.mc.playerController.updateController();
        return slot;
    }
    
    public static int findItem(final Class clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (clazz.isInstance(stack.getItem())) {
                    return i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    if (clazz.isInstance(((ItemBlock)stack.getItem()).getBlock())) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static int getElytra() {
        for (final ItemStack stack : InventoryUtil.mc.player.getArmorInventoryList()) {
            if (stack.getItem() == Items.ELYTRA) {
                return -2;
            }
        }
        int slot = -1;
        for (int i = 0; i < 36; ++i) {
            final ItemStack s = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (s.getItem() == Items.ELYTRA) {
                slot = i;
                break;
            }
        }
        if (slot < 9 && slot != -1) {
            slot += 36;
        }
        return slot;
    }
    
    public static int getFireWorks() {
        for (int i = 0; i < 9; ++i) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemFirework) {
                return i;
            }
        }
        return -1;
    }
}
