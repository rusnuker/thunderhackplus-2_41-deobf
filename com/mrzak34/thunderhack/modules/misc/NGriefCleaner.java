//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class NGriefCleaner extends Module
{
    public final Setting<Float> delay1;
    private final Timer timer;
    public Setting<Boolean> openinv;
    
    public NGriefCleaner() {
        super("NGriefCleaner", "\u0443\u0431\u0438\u0440\u0430\u0435\u0442 \u0442\u043e\u043f\u043e\u0440\u0438\u043a\u0438-\u0438 \u0433\u043e\u043b\u043e\u0432\u044b", Category.MISC);
        this.delay1 = (Setting<Float>)this.register(new Setting("Delay", (T)1.0f, (T)0.0f, (T)10.0f));
        this.timer = new Timer();
        this.openinv = (Setting<Boolean>)this.register(new Setting("OpenInv", (T)true));
    }
    
    @Override
    public void onUpdate() {
        final long delay = (long)(this.delay1.getValue() * 50.0f);
        if (!(NGriefCleaner.mc.currentScreen instanceof GuiInventory) && this.openinv.getValue()) {
            return;
        }
        if (this.timer.passedMs(delay)) {
            for (int i = 9; i < 45; ++i) {
                if (NGriefCleaner.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                    final ItemStack is = NGriefCleaner.mc.player.inventoryContainer.getSlot(i).getStack();
                    if (this.shouldDrop(is, i)) {
                        this.drop(i);
                        if (delay == 0L) {
                            NGriefCleaner.mc.player.closeScreen();
                        }
                        this.timer.reset();
                        if (delay > 0L) {
                            break;
                        }
                    }
                }
            }
        }
    }
    
    public void drop(final int slot) {
        NGriefCleaner.mc.playerController.windowClick(NGriefCleaner.mc.player.inventoryContainer.windowId, slot, 1, ClickType.THROW, (EntityPlayer)NGriefCleaner.mc.player);
    }
    
    public boolean shouldDrop(final ItemStack stack, final int slot) {
        return stack.getItem() == Items.SKULL || stack.getItem() == Items.WOODEN_SHOVEL || stack.getItem() == Items.STICK || stack.getItem() == Items.PAPER || stack.getItem() == Items.FLINT_AND_STEEL || stack.getItem() == Items.ROTTEN_FLESH || stack.getItem() == Items.WHEAT_SEEDS || stack.getItem() == Items.BUCKET || stack.getItem() == Items.KNOWLEDGE_BOOK || stack.getItem() == Item.getItemById(6) || stack.getItem() == Item.getItemById(50) || (stack.getItem() == Items.WOODEN_AXE && slot < 36);
    }
}
