//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class ChestStealer extends Module
{
    public Setting<Integer> delayed;
    Timer timer;
    
    public ChestStealer() {
        super("ChestStealer", "\u0421\u0442\u0438\u043b\u0438\u0442 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b-\u0438\u0437 \u0441\u0443\u043d\u0434\u0443\u043a\u0430", Category.MISC);
        this.delayed = (Setting<Integer>)this.register(new Setting("Delay", (T)100, (T)0, (T)1000));
        this.timer = new Timer();
    }
    
    @Override
    public void onUpdate() {
        if (Util.mc.player.openContainer != null && Util.mc.player.openContainer instanceof ContainerChest) {
            final ContainerChest container = (ContainerChest)Util.mc.player.openContainer;
            for (int i = 0; i < container.inventorySlots.size(); ++i) {
                if (container.getLowerChestInventory().getStackInSlot(i).getItem() != Item.getItemById(0) && this.timer.passedMs(this.delayed.getValue())) {
                    ChestStealer.mc.playerController.windowClick(container.windowId, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)Util.mc.player);
                    this.timer.reset();
                }
                else if (this.empty((Container)container)) {
                    Util.mc.player.closeScreen();
                }
            }
        }
    }
    
    public boolean empty(final Container container) {
        boolean voll = true;
        for (int slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27, i = 0; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                voll = false;
            }
        }
        return voll;
    }
}
