package org.spongepowered.mod.text.format;

import java.awt.Color;

import net.minecraft.util.EnumChatFormatting;

import org.spongepowered.api.text.format.TextColor;

public class SpongeTextColor implements TextColor.Base {

    private Color color;
    private String name;
    private EnumChatFormatting handle;

    public SpongeTextColor(Color color, EnumChatFormatting format) {
        this.color = color;
        this.handle = format;
        this.name = format.getFriendlyName();
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isReset() {
        return color == Color.WHITE;
    }

    @Override
    public String getName() {
        return name;
    }

    public EnumChatFormatting getHandle() {
        return handle;
    }

    @Override
    @Deprecated
    public char getCode() {
        // TODO
        return 0;
    }

}
