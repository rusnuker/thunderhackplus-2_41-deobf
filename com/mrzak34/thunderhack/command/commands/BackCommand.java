//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.manager.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.*;

public class BackCommand extends Command
{
    public BackCommand() {
        super("back");
    }
    
    public void execute(final String[] var1) {
        if (EventManager.backX == 0 && EventManager.backY == 0 && EventManager.backZ == 0) {
            return;
        }
        final BlockPos pos = new BlockPos(EventManager.backX, EventManager.backY, EventManager.backZ);
        for (int i = 0; i < 10; ++i) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position((double)pos.getX(), (double)(1 + pos.getY()), (double)pos.getZ(), false));
        }
        this.mc.player.setPosition((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ());
        if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
            Command.sendMessage("\u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u0443\u0435\u043c\u0441\u044f \u043d\u0430 \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u044b X: " + EventManager.backX + " Y: " + EventManager.backY + " Z: " + EventManager.backZ);
        }
        else {
            Command.sendMessage("Teleporting to X: " + EventManager.backX + " Y: " + EventManager.backY + " Z: " + EventManager.backZ);
        }
    }
}
