//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.util.*;

public enum CooldownBypass
{
    None {
        @Override
        public void switchTo(final int slot) {
            InventoryUtil.switchTo(slot);
        }
        
        @Override
        public void switchBack(final int lastSlot, final int from) {
            this.switchTo(lastSlot);
        }
    }, 
    Slot {
        @Override
        public void switchTo(final int slot) {
            InventoryUtil.switchToBypass(InventoryUtil.hotbarToInventory(slot));
        }
    }, 
    Swap {
        @Override
        public void switchTo(final int slot) {
            InventoryUtil.switchToBypassAlt(InventoryUtil.hotbarToInventory(slot));
        }
    }, 
    Pick {
        @Override
        public void switchTo(final int slot) {
            InventoryUtil.bypassSwitch(slot);
        }
    };
    
    public abstract void switchTo(final int p0);
    
    public void switchBack(final int lastSlot, final int from) {
        this.switchTo(from);
    }
}
