//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class NoInteract extends Module
{
    public NoInteract() {
        super("NoInteract", "\u043d\u0435 \u043f\u043e\u0441\u044b\u043b\u0430\u0442\u044c \u043f\u0430\u043a\u0435\u0442\u044b-\u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u044f \u0431\u043b\u043e\u043a\u043e\u0432", Module.Category.PLAYER);
    }
    
    @SubscribeEvent
    public void onInteract(final ClickBlockEvent.Right event) {
        final Block block = NoInteract.mc.world.getBlockState(event.getPos()).getBlock();
        if (block == Blocks.ANVIL || block == Blocks.CRAFTING_TABLE || block == Blocks.ACACIA_FENCE_GATE || block == Blocks.BIRCH_FENCE_GATE || block == Blocks.DARK_OAK_FENCE_GATE || block == Blocks.JUNGLE_FENCE_GATE || block == Blocks.SPRUCE_FENCE_GATE || block == Blocks.OAK_FENCE_GATE || block == Blocks.CHEST || block == Blocks.ENDER_CHEST || block == Blocks.ENCHANTING_TABLE || block == Block.getBlockById(63) || block == Blocks.FURNACE || block == Blocks.LIT_FURNACE) {
            event.setCanceled(true);
        }
    }
}
