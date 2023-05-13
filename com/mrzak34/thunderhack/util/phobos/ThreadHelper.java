//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.events.*;

public class ThreadHelper
{
    private final Timer threadTimer;
    private final Setting<Boolean> multiThread;
    private final Setting<Boolean> mainThreadThreads;
    private final Setting<Integer> threadDelay;
    private final Setting<AutoCrystal.RotationThread> rotationThread;
    private final Setting<AutoCrystal.ACRotate> rotate;
    private final AutoCrystal module;
    private volatile AbstractCalculation<?> currentCalc;
    
    public ThreadHelper(final AutoCrystal module, final Setting<Boolean> multiThread, final Setting<Boolean> mainThreadThreads, final Setting<Integer> threadDelay, final Setting<AutoCrystal.RotationThread> rotationThread, final Setting<AutoCrystal.ACRotate> rotate) {
        this.threadTimer = new Timer();
        this.module = module;
        this.multiThread = multiThread;
        this.mainThreadThreads = mainThreadThreads;
        this.threadDelay = threadDelay;
        this.rotationThread = rotationThread;
        this.rotate = rotate;
    }
    
    public synchronized void start(final AbstractCalculation<?> calculation, final boolean multiThread) {
        if (this.threadTimer.passedMs((int)this.threadDelay.getValue()) && (this.currentCalc == null || this.currentCalc.isFinished())) {
            this.execute(this.currentCalc = calculation, multiThread);
        }
    }
    
    public synchronized void startThread(final BlockPos... blackList) {
        if (Util.mc.world == null || Util.mc.player == null || !this.threadTimer.passedMs((int)this.threadDelay.getValue()) || (this.currentCalc != null && !this.currentCalc.isFinished())) {
            return;
        }
        if (Util.mc.isCallingFromMinecraftThread()) {
            this.startThread(new ArrayList<Entity>(Util.mc.world.loadedEntityList), new ArrayList<EntityPlayer>(Util.mc.world.playerEntities), blackList);
        }
        else {
            this.startThread(Thunderhack.entityProvider.getEntities(), Thunderhack.entityProvider.getPlayers(), blackList);
        }
    }
    
    public synchronized void startThread(final boolean breakOnly, final boolean noBreak, final BlockPos... blackList) {
        if (Util.mc.world == null || Util.mc.player == null || !this.threadTimer.passedMs((int)this.threadDelay.getValue()) || (this.currentCalc != null && !this.currentCalc.isFinished())) {
            return;
        }
        if (Util.mc.isCallingFromMinecraftThread()) {
            this.startThread(new ArrayList<Entity>(Util.mc.world.loadedEntityList), new ArrayList<EntityPlayer>(Util.mc.world.playerEntities), breakOnly, noBreak, blackList);
        }
        else {
            this.startThread(Thunderhack.entityProvider.getEntities(), Thunderhack.entityProvider.getPlayers(), breakOnly, noBreak, blackList);
        }
    }
    
    private void startThread(final List<Entity> entities, final List<EntityPlayer> players, final boolean breakOnly, final boolean noBreak, final BlockPos... blackList) {
        this.execute(this.currentCalc = (AbstractCalculation<?>)new Calculation(this.module, (List)entities, (List)players, breakOnly, noBreak, blackList), (boolean)this.multiThread.getValue());
    }
    
    private void startThread(final List<Entity> entities, final List<EntityPlayer> players, final BlockPos... blackList) {
        this.execute(this.currentCalc = (AbstractCalculation<?>)new Calculation(this.module, (List)entities, (List)players, blackList), (boolean)this.multiThread.getValue());
    }
    
    private void execute(final AbstractCalculation<?> calculation, final boolean multiThread) {
        if (multiThread) {
            Thunderhack.threadManager.submitRunnable((Runnable)calculation);
            this.threadTimer.reset();
        }
        else {
            this.threadTimer.reset();
            calculation.run();
        }
    }
    
    public void schedulePacket(final PacketEvent.Receive event) {
        if (((boolean)this.multiThread.getValue() || (boolean)this.mainThreadThreads.getValue()) && (this.rotate.getValue() == AutoCrystal.ACRotate.None || this.rotationThread.getValue() != AutoCrystal.RotationThread.Predict)) {
            event.addPostEvent(() -> rec$.startThread(new BlockPos[0]));
        }
    }
    
    public AbstractCalculation<?> getCurrentCalc() {
        return this.currentCalc;
    }
    
    public void resetThreadHelper() {
        this.currentCalc = null;
    }
}
