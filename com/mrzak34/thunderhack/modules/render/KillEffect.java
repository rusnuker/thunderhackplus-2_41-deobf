//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.effect.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class KillEffect extends Module
{
    private final Setting<Boolean> sound;
    private final Timer timer;
    
    public KillEffect() {
        super("KillEffect", "KillEffect", Module.Category.RENDER);
        this.sound = (Setting<Boolean>)this.register(new Setting("Sound", (T)false));
        this.timer = new Timer();
    }
    
    @SubscribeEvent
    public void onDeath(final DeathEvent event) {
        if (!fullNullCheck() && event.player != null) {
            final Entity entity = (Entity)event.player;
            if (entity != null && (entity.isDead || (((EntityPlayer)entity).getHealth() <= 0.0f && this.timer.passedMs(1500L)))) {
                KillEffect.mc.world.spawnEntity((Entity)new EntityLightningBolt((World)KillEffect.mc.world, entity.posX, entity.posY, entity.posZ, true));
                if (this.sound.getValue()) {
                    KillEffect.mc.player.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 0.5f, 1.0f);
                }
                this.timer.reset();
            }
        }
    }
}
