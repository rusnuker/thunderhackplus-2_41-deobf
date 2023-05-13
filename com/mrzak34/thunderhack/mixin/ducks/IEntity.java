//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.ducks;

import com.mrzak34.thunderhack.util.phobos.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.modules.render.*;

public interface IEntity extends Dummy
{
    void setInPortal(final boolean p0);
    
    boolean isPseudoDeadT();
    
    void setPseudoDeadT(final boolean p0);
    
    Timer getPseudoTimeT();
    
    long getTimeStampT();
    
    boolean isInWeb();
    
    List<BackTrack.Box> getPosition_history();
    
    List<PlayerTrails.Trail> getTrails();
}
