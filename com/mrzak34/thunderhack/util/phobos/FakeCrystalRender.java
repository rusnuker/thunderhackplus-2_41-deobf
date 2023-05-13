//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;

public class FakeCrystalRender
{
    private final List<EntityEnderCrystal> crystals;
    private final Setting<Integer> simulate;
    
    public FakeCrystalRender(final Setting<Integer> simulate) {
        this.crystals = new ArrayList<EntityEnderCrystal>();
        this.simulate = simulate;
    }
    
    public void addFakeCrystal(final EntityEnderCrystal crystal) {
        crystal.setShowBottom(false);
        final Iterator<EntityEnderCrystal> iterator;
        EntityEnderCrystal entity;
        Util.mc.addScheduledTask(() -> {
            if (Util.mc.world != null) {
                Util.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, crystal.getEntityBoundingBox()).iterator();
                if (iterator.hasNext()) {
                    entity = iterator.next();
                    crystal.innerRotation = entity.innerRotation;
                }
            }
            this.crystals.add(crystal);
        });
    }
    
    public void onSpawn(final EntityEnderCrystal crystal) {
        final Iterator<EntityEnderCrystal> itr = this.crystals.iterator();
        while (itr.hasNext()) {
            final EntityEnderCrystal fake = itr.next();
            if (fake.getEntityBoundingBox().intersects(crystal.getEntityBoundingBox())) {
                crystal.innerRotation = fake.innerRotation;
                itr.remove();
            }
        }
    }
    
    public void tick() {
        if ((int)this.simulate.getValue() == 0) {
            this.crystals.clear();
            return;
        }
        final Iterator<EntityEnderCrystal> itr = this.crystals.iterator();
        while (itr.hasNext()) {
            final EntityEnderCrystal crystal = itr.next();
            crystal.onUpdate();
            if (++crystal.ticksExisted >= (int)this.simulate.getValue()) {
                crystal.setDead();
                itr.remove();
            }
        }
    }
    
    public void render(final float partialTicks) {
        final RenderManager manager = Util.mc.getRenderManager();
        for (final EntityEnderCrystal crystal : this.crystals) {
            manager.renderEntityStatic((Entity)crystal, partialTicks, false);
        }
    }
    
    public void clear() {
        this.crystals.clear();
    }
}
