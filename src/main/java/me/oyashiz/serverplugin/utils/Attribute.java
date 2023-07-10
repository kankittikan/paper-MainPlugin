package me.oyashiz.serverplugin.utils;

import org.bukkit.attribute.AttributeModifier;

public class Attribute {
    private org.bukkit.attribute.Attribute attribute;
    private AttributeModifier attributeModifier;

    public Attribute(org.bukkit.attribute.Attribute attribute, AttributeModifier attributeModifier) {
        this.attribute = attribute;
        this.attributeModifier = attributeModifier;
    }

    public org.bukkit.attribute.Attribute getAttribute() {
        return attribute;
    }

    public AttributeModifier getAttributeModifier() {
        return attributeModifier;
    }
}
