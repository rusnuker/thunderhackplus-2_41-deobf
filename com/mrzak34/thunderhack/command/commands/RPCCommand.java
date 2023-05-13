//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.modules.client.*;

public class RPCCommand extends Command
{
    public RPCCommand() {
        super("rpc");
    }
    
    public void execute(final String[] args) {
        if (args.length == 1) {
            ModuleCommand.sendMessage(".rpc url or .rpc url url");
            return;
        }
        if (args.length == 2) {
            RPC.WriteFile(args[0], "none");
            Command.sendMessage("\u0411\u043e\u043b\u044c\u0448\u0430\u044f \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0430 RPC \u0438\u0437\u043c\u0435\u043d\u0435\u043d\u0430 \u043d\u0430 " + args[0]);
            return;
        }
        if (args.length >= 2) {
            RPC.WriteFile(args[0], args[1]);
            Command.sendMessage("\u0411\u043e\u043b\u044c\u0448\u0430\u044f \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0430 RPC \u0438\u0437\u043c\u0435\u043d\u0435\u043d\u0430 \u043d\u0430 " + args[0]);
            Command.sendMessage("\u041c\u0430\u043b\u0435\u043d\u044c\u043a\u0430\u044f \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0430 RPC \u0438\u0437\u043c\u0435\u043d\u0435\u043d\u0430 \u043d\u0430 " + args[1]);
        }
    }
}
