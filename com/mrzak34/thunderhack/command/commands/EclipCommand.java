//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import com.mojang.realmsclient.gui.*;
import org.apache.commons.lang3.math.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

public class EclipCommand extends Command
{
    public EclipCommand() {
        super("eclip");
    }
    
    public static int getSlotIDFromItem(final Item item) {
        int slot = -1;
        for (int i = 0; i < 36; ++i) {
            final ItemStack s = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
            if (s.getItem() == item) {
                slot = i;
                break;
            }
        }
        if (slot < 9 && slot != -1) {
            slot += 36;
        }
        return slot;
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            Command.sendMessage(".eclip <value> , /up/down/bedrock");
            return;
        }
        float y = 0.0f;
        if (commands[0].equals("bedrock")) {
            y = -(float)this.mc.player.posY - 3.0f;
        }
        if (commands[0].equals("down")) {
            for (int i = 1; i < 255; ++i) {
                if (this.mc.world.getBlockState(new BlockPos((Entity)this.mc.player).add(0, -i, 0)) == Blocks.AIR.getDefaultState()) {
                    y = (float)(-i - 1);
                    break;
                }
                if (this.mc.world.getBlockState(new BlockPos((Entity)this.mc.player).add(0, -i, 0)) == Blocks.BEDROCK.getDefaultState()) {
                    Command.sendMessage(ChatFormatting.RED + " \u043c\u043e\u0436\u043d\u043e \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f \u0442\u043e\u043b\u044c\u043a\u043e \u043f\u043e\u0434 \u0431\u0435\u0434\u0440\u043e\u043a");
                    Command.sendMessage(ChatFormatting.RED + "eclip bedrock");
                    return;
                }
            }
        }
        if (commands[0].equals("up")) {
            for (int i = 4; i < 255; ++i) {
                if (this.mc.world.getBlockState(new BlockPos((Entity)this.mc.player).add(0, i, 0)) == Blocks.AIR.getDefaultState()) {
                    y = (float)(i + 1);
                    break;
                }
            }
        }
        if (y == 0.0f) {
            if (!NumberUtils.isNumber(commands[0])) {
                Command.sendMessage(ChatFormatting.RED + commands[0] + ChatFormatting.GRAY + "\u043d\u0435 \u044f\u0432\u043b\u044f\u0435\u0441\u0442\u044f \u0447\u0438\u0441\u043b\u043e\u043c");
                return;
            }
            y = Float.parseFloat(commands[0]);
        }
        final int elytra;
        if ((elytra = getSlotIDFromItem(Items.ELYTRA)) == -1) {
            Command.sendMessage(ChatFormatting.RED + "\u0432\u0430\u043c \u043d\u0443\u0436\u043d\u044b \u044d\u043b\u0438\u0442\u0440\u044b \u0432 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u0435");
            return;
        }
        if (elytra != -2) {
            this.mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        }
        this.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, false));
        this.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, false));
        this.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        this.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + y, this.mc.player.posZ, false));
        this.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        if (elytra != -2) {
            this.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        }
        this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY + y, this.mc.player.posZ);
    }
}
