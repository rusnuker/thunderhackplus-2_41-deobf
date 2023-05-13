//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.setting;

import java.util.function.*;
import com.mrzak34.thunderhack.modules.*;

public class Setting<T>
{
    private final String name;
    private final T defaultValue;
    private T value;
    private T plannedValue;
    private T min;
    private T max;
    private Setting<Parent> parent;
    private boolean hasRestriction;
    private Predicate<T> visibility;
    private String description;
    private Module module;
    
    public Setting(final String name, final T defaultValue) {
        this.parent = null;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.plannedValue = defaultValue;
        this.description = "";
    }
    
    public Setting(final String name, final T defaultValue, final T min, final T max) {
        this.parent = null;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.plannedValue = defaultValue;
        this.description = "";
        this.hasRestriction = true;
    }
    
    public Setting(final String name, final T defaultValue, final T min, final T max, final Predicate<T> visibility) {
        this.parent = null;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.plannedValue = defaultValue;
        this.visibility = visibility;
        this.description = "";
        this.hasRestriction = true;
    }
    
    public Setting(final String name, final T defaultValue, final Predicate<T> visibility) {
        this.parent = null;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.visibility = visibility;
        this.plannedValue = defaultValue;
    }
    
    public static Enum get(final Enum clazz) {
        final int index = EnumConverter.currentEnum(clazz);
        for (int i = 0; i < ((Enum[])clazz.getClass().getEnumConstants()).length; ++i) {
            final Enum e = ((Enum[])clazz.getClass().getEnumConstants())[i];
            if (i == index + 1) {
                return e;
            }
        }
        return ((Enum[])clazz.getClass().getEnumConstants())[0];
    }
    
    public String getName() {
        return this.name;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public void setValue(final T value) {
        this.setPlannedValue(value);
        if (this.hasRestriction) {
            if (((Number)this.min).floatValue() > ((Number)value).floatValue()) {
                this.setPlannedValue(this.min);
            }
            if (((Number)this.max).floatValue() < ((Number)value).floatValue()) {
                this.setPlannedValue(this.max);
            }
        }
        this.value = this.plannedValue;
    }
    
    public float getPow2Value() {
        if (this.value instanceof Float) {
            return (float)this.value * (float)this.value;
        }
        return 0.0f;
    }
    
    public void setPlannedValue(final T value) {
        this.plannedValue = value;
    }
    
    public T getMin() {
        return this.min;
    }
    
    public void setMin(final T min) {
        this.min = min;
    }
    
    public T getMax() {
        return this.max;
    }
    
    public void setMax(final T max) {
        this.max = max;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public void setModule(final Module module) {
        this.module = module;
    }
    
    public String currentEnumName() {
        return EnumConverter.getProperName((Enum)this.value);
    }
    
    public String[] getModes() {
        return EnumConverter.getNames((Enum)this.value);
    }
    
    public void setEnum(final Enum mod) {
        this.plannedValue = (T)mod;
    }
    
    public void increaseEnum() {
        this.plannedValue = (T)EnumConverter.increaseEnum((Enum)this.value);
        this.value = this.plannedValue;
    }
    
    public void setEnumByNumber(final int id) {
        this.plannedValue = (T)EnumConverter.setEnumInt((Enum)this.value, id);
        this.value = this.plannedValue;
    }
    
    public String getType() {
        if (this.isEnumSetting()) {
            return "Enum";
        }
        if (this.isColorSetting()) {
            return "ColorSetting";
        }
        if (this.isPositionSetting()) {
            return "PositionSetting";
        }
        return this.getClassName(this.defaultValue);
    }
    
    public <T> String getClassName(final T value) {
        return value.getClass().getSimpleName();
    }
    
    public String getDescription() {
        if (this.description == null) {
            return "";
        }
        return this.description;
    }
    
    public boolean isNumberSetting() {
        return this.value instanceof Double || this.value instanceof Integer || this.value instanceof Short || this.value instanceof Long || this.value instanceof Float;
    }
    
    public boolean isColorHeader() {
        return this.value instanceof ColorSettingHeader;
    }
    
    public boolean isInteger() {
        return this.value instanceof Integer;
    }
    
    public boolean isFloat() {
        return this.value instanceof Float;
    }
    
    public boolean isEnumSetting() {
        return !this.isPositionSetting() && !this.isColorHeader() && !this.isNumberSetting() && !(this.value instanceof PositionSetting) && !(this.value instanceof String) && !(this.value instanceof ColorSetting) && !(this.value instanceof Parent) && !(this.value instanceof Bind) && !(this.value instanceof SubBind) && !(this.value instanceof Character) && !(this.value instanceof Boolean);
    }
    
    public boolean isBindSetting() {
        return this.value instanceof Bind;
    }
    
    public boolean isStringSetting() {
        return this.value instanceof String;
    }
    
    public boolean isColorSetting() {
        return this.value instanceof ColorSetting;
    }
    
    public boolean isPositionSetting() {
        return this.value instanceof PositionSetting;
    }
    
    public T getDefaultValue() {
        return this.defaultValue;
    }
    
    public String getValueAsString() {
        return this.value.toString();
    }
    
    public boolean hasRestriction() {
        return this.hasRestriction;
    }
    
    public Setting<T> withParent(final Setting<Parent> parent) {
        this.parent = parent;
        return this;
    }
    
    public Setting<Parent> getParent() {
        return this.parent;
    }
    
    public boolean isVisible() {
        return (this.parent == null || this.parent.getValue().isExtended()) && (this.visibility == null || this.visibility.test(this.getValue()));
    }
}
