package me.oyashiz.serverplugin.utils;

import java.util.ArrayList;
import java.util.List;

public class AttributeList {
    private List<Attribute> attributes;

    public AttributeList() {
        attributes = new ArrayList<>();
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }
}
