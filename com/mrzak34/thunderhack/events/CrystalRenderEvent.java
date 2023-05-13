//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

@Cancelable
public class CrystalRenderEvent extends Event
{
    private final RenderEnderCrystal render;
    private final Entity entity;
    private final ModelBase model;
    
    private CrystalRenderEvent(final RenderEnderCrystal render, final Entity entity, final ModelBase model) {
        this.render = render;
        this.entity = entity;
        this.model = model;
    }
    
    public RenderEnderCrystal getRender() {
        return this.render;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public ModelBase getModel() {
        return this.model;
    }
    
    public static class Pre extends CrystalRenderEvent
    {
        private final float limbSwing;
        private final float limbSwingAmount;
        private final float ageInTicks;
        private final float netHeadYaw;
        private final float headPitch;
        private final float scale;
        
        public Pre(final RenderEnderCrystal render, final Entity entity, final ModelBase model, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
            super(render, entity, model, null);
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.ageInTicks = ageInTicks;
            this.netHeadYaw = netHeadYaw;
            this.headPitch = headPitch;
            this.scale = scale;
        }
        
        public float getLimbSwing() {
            return this.limbSwing;
        }
        
        public float getLimbSwingAmount() {
            return this.limbSwingAmount;
        }
        
        public float getAgeInTicks() {
            return this.ageInTicks;
        }
        
        public float getNetHeadYaw() {
            return this.netHeadYaw;
        }
        
        public float getHeadPitch() {
            return this.headPitch;
        }
        
        public float getScale() {
            return this.scale;
        }
    }
    
    public static class Post extends CrystalRenderEvent
    {
        private final float limbSwing;
        private final float limbSwingAmount;
        private final float ageInTicks;
        private final float netHeadYaw;
        private final float headPitch;
        private final float scale;
        
        public Post(final RenderEnderCrystal render, final Entity entity, final ModelBase model, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
            super(render, entity, model, null);
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.ageInTicks = ageInTicks;
            this.netHeadYaw = netHeadYaw;
            this.headPitch = headPitch;
            this.scale = scale;
        }
        
        public float getLimbSwing() {
            return this.limbSwing;
        }
        
        public float getLimbSwingAmount() {
            return this.limbSwingAmount;
        }
        
        public float getAgeInTicks() {
            return this.ageInTicks;
        }
        
        public float getNetHeadYaw() {
            return this.netHeadYaw;
        }
        
        public float getHeadPitch() {
            return this.headPitch;
        }
        
        public float getScale() {
            return this.scale;
        }
    }
}
