package org.spongepowered.mod.text.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.util.ChatComponentStyle;

import org.spongepowered.api.text.action.ClickAction;
import org.spongepowered.api.text.action.HoverAction;
import org.spongepowered.api.text.action.ShiftClickAction;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextStyle;
import org.spongepowered.api.text.message.Message;
import org.spongepowered.mod.text.message.SpongeMessageText.SpongeTextBuilder;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public abstract class SpongeMessage implements Message {

    protected final String content;
    protected final List<Message> children;
    protected final TextColor color;
    protected final TextStyle style;
    protected final Optional<ClickAction<?>> clickAction;
    protected final Optional<HoverAction<?>> hoverAction;
    protected final Optional<ShiftClickAction<?>> shiftClickAction;
    protected final ChatComponentStyle handle;

    protected SpongeMessage(SpongeTextBuilder builder) {
        this.children = ImmutableList.copyOf(builder.children);
        this.content = builder.content;
        this.color = builder.color;
        this.style = builder.style;
        this.clickAction = builder.clickAction;
        this.hoverAction = builder.hoverAction;
        this.shiftClickAction = builder.shiftClickAction;
        this.handle = builder.handle;
    }

    @Override
    public Iterator<Message> iterator() {
        List<Message> messageList = new ArrayList<Message>();
        messageList.add(this);
        messageList.addAll(children);
        return messageList.iterator();
    }

    @Override
    public TextColor getColor() {
        return color;
    }

    @Override
    public TextStyle getStyle() {
        return style;
    }

    @Override
    public List<Message> getChildren() {
        return children;
    }

    @Override
    public Optional<ClickAction<?>> getClickAction() {
        return clickAction;
    }

    @Override
    public Optional<HoverAction<?>> getHoverAction() {
        return hoverAction;
    }

    @Override
    public Optional<ShiftClickAction<?>> getShiftClickAction() {
        return shiftClickAction;
    }

    @Override
    public String getContent() {
        return content;
    }

    public ChatComponentStyle getHandle() {
        return handle;
    }
}
