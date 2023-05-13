//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.client;

import com.mrzak34.thunderhack.modules.*;
import java.util.*;

public class MultiConnect extends Module
{
    private static MultiConnect INSTANCE;
    public List<Integer> serverData;
    
    public MultiConnect() {
        super("MultiConnect", "MultiConnect", Category.CLIENT);
        this.serverData = new ArrayList<Integer>();
        this.setInstance();
    }
    
    public static MultiConnect getInstance() {
        if (MultiConnect.INSTANCE == null) {
            MultiConnect.INSTANCE = new MultiConnect();
        }
        return MultiConnect.INSTANCE;
    }
    
    private void setInstance() {
        MultiConnect.INSTANCE = this;
    }
    
    static {
        MultiConnect.INSTANCE = new MultiConnect();
    }
}
