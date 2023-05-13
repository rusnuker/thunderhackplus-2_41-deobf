//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;

public class SearchCommand extends Command
{
    public SearchCommand() {
        super("search");
    }
    
    private static Block getRegisteredBlock(final String blockName) {
        return (Block)Block.REGISTRY.getObject((Object)new ResourceLocation(blockName));
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            if (Search.defaultBlocks.isEmpty()) {
                sendMessage("Search list empty");
            }
            else {
                String f = "Search list: ";
                for (final Block name : Search.defaultBlocks) {
                    try {
                        f = f + name.getRegistryName() + ", ";
                    }
                    catch (Exception ex) {}
                }
                sendMessage(f);
            }
            return;
        }
        if (commands.length != 2) {
            if (commands.length >= 2) {
                final String s = commands[0];
                switch (s) {
                    case "add": {
                        Search.defaultBlocks.add(getRegisteredBlock(commands[1]));
                        sendMessage(ChatFormatting.GREEN + commands[1] + " added to search");
                        this.mc.renderGlobal.loadRenderers();
                    }
                    case "del": {
                        Search.defaultBlocks.remove(getRegisteredBlock(commands[1]));
                        sendMessage(ChatFormatting.RED + commands[1] + " removed from search");
                        this.mc.renderGlobal.loadRenderers();
                    }
                    default: {
                        sendMessage("Unknown Command, try search add/del <block name>");
                        break;
                    }
                }
            }
            return;
        }
        if ("reset".equals(commands[0])) {
            Search.defaultBlocks.clear();
            sendMessage("Search got reset.");
            this.mc.renderGlobal.loadRenderers();
        }
    }
}
