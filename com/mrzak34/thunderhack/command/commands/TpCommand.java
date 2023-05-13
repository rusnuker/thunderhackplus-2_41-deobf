//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class TpCommand extends Command
{
    public TpCommand() {
        super("tp");
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            Command.sendMessage("\u041f\u043e\u043f\u0440\u043e\u0431\u0443\u0439 .tp <\u0447\u0438\u0441\u043b\u043e> <\u0447\u0438\u0441\u043b\u043e> <\u0447\u0438\u0441\u043b\u043e>");
            return;
        }
        if (commands.length > 2) {
            final BlockPos pos = new BlockPos(Integer.parseInt(commands[0]), Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
            for (int i = 0; i < 10; ++i) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position((double)pos.getX(), (double)(1 + pos.getY()), (double)pos.getZ(), false));
            }
            this.mc.player.setPosition((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ());
            Command.sendMessage("\u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u0443\u0435\u043c\u0441\u044f \u043d\u0430 \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u044b X: " + pos.getX() + " Y: " + pos.getY() + " Z: " + pos.getZ());
        }
    }
}
