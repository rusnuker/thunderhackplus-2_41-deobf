//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.util.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.block.*;

public class AutoTool extends Module
{
    public Setting<Boolean> swapBack;
    public Setting<Boolean> saveItem;
    public Setting<Boolean> silent;
    public Setting<Boolean> echestSilk;
    public int itemIndex;
    private boolean swap;
    private long swapDelay;
    private final List<Integer> lastItem;
    
    public AutoTool() {
        super("AutoTool", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u043e\u043c \u0441\u0432\u0430\u043f\u0430\u0435\u0442\u0441\u044f \u043d\u0430-\u043b\u0443\u0447\u0448\u0438\u0439 \u0438\u043d\u0441\u0442\u0440\u0443\u043c\u0435\u043d\u0442", Module.Category.PLAYER);
        this.swapBack = (Setting<Boolean>)this.register(new Setting("SwapBack", (T)true));
        this.saveItem = (Setting<Boolean>)this.register(new Setting("SaveItem", (T)true));
        this.silent = (Setting<Boolean>)this.register(new Setting("Silent", (T)false));
        this.echestSilk = (Setting<Boolean>)this.register(new Setting("EchestSilk", (T)true));
        this.lastItem = new ArrayList<Integer>();
    }
    
    public void onUpdate() {
        if (AutoTool.mc.objectMouseOver == null) {
            return;
        }
        if (AutoTool.mc.objectMouseOver.getBlockPos() == null) {
            return;
        }
        if (this.getTool(AutoTool.mc.objectMouseOver.getBlockPos()) != -1 && ((IKeyBinding)AutoTool.mc.gameSettings.keyBindAttack).isPressed()) {
            this.lastItem.add(AutoTool.mc.player.inventory.currentItem);
            if (this.silent.getValue()) {
                AutoTool.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.getTool(AutoTool.mc.objectMouseOver.getBlockPos())));
            }
            else {
                AutoTool.mc.player.inventory.currentItem = this.getTool(AutoTool.mc.objectMouseOver.getBlockPos());
            }
            this.itemIndex = this.getTool(AutoTool.mc.objectMouseOver.getBlockPos());
            this.swap = true;
            this.swapDelay = System.currentTimeMillis();
        }
        else if (this.swap && !this.lastItem.isEmpty() && System.currentTimeMillis() >= this.swapDelay + 300L && this.swapBack.getValue()) {
            if (this.silent.getValue()) {
                AutoTool.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange((int)this.lastItem.get(0)));
            }
            else {
                AutoTool.mc.player.inventory.currentItem = this.lastItem.get(0);
            }
            this.itemIndex = this.lastItem.get(0);
            this.lastItem.clear();
            this.swap = false;
        }
    }
    
    private int getTool(final BlockPos pos) {
        int index = -1;
        float CurrentFastest = 1.0f;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoTool.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (AutoTool.mc.player.inventory.getStackInSlot(i).getMaxDamage() - AutoTool.mc.player.inventory.getStackInSlot(i).getItemDamage() > 10 || !this.saveItem.getValue()) {
                    final float digSpeed = (float)EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
                    final float destroySpeed = stack.getDestroySpeed(AutoTool.mc.world.getBlockState(pos));
                    if (AutoTool.mc.world.getBlockState(pos).getBlock() instanceof BlockAir) {
                        return -1;
                    }
                    if (AutoTool.mc.world.getBlockState(pos).getBlock() instanceof BlockEnderChest && this.echestSilk.getValue()) {
                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0 && digSpeed + destroySpeed > CurrentFastest) {
                            CurrentFastest = digSpeed + destroySpeed;
                            index = i;
                        }
                    }
                    else if (digSpeed + destroySpeed > CurrentFastest) {
                        CurrentFastest = digSpeed + destroySpeed;
                        index = i;
                    }
                }
            }
        }
        return index;
    }
}
