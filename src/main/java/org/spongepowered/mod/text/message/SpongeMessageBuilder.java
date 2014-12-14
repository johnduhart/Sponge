package org.spongepowered.mod.text.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.util.IChatComponent;

import org.spongepowered.api.text.action.ClickAction;
import org.spongepowered.api.text.action.HoverAction;
import org.spongepowered.api.text.action.ShiftClickAction;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextStyle;
import org.spongepowered.api.text.message.Message;
import org.spongepowered.api.text.message.MessageBuilder;

import com.google.common.base.Optional;

public abstract class SpongeMessageBuilder<T extends Message> implements MessageBuilder<T> {

    protected List<Message> children;
    protected T content;
    protected TextColor color;
    protected TextStyle style;
    protected Optional<ClickAction<?>> clickAction;
    protected Optional<HoverAction<?>> hoverAction;
    protected Optional<ShiftClickAction<?>> shiftClickAction;
    protected IChatComponent handle;

    public SpongeMessageBuilder() {
        children = new ArrayList<Message>();
    }

    public SpongeMessageBuilder(T content) {
        this.content = content;
        children = new ArrayList<Message>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public MessageBuilder<T> content(Object content) {
        this.content = (T) content;
        return this;
    }

    @Override
    public MessageBuilder<T> append(Message... children) {
        for (Message message : children) {
            this.children.add(message);
        }
        return this;
    }

    @Override
    public MessageBuilder<T> append(Iterable<Message> children) {
        Iterator<Message> iter = children.iterator();
        while (iter.hasNext()) {
            Message message = iter.next();
            this.children.add(message);
            this.handle.appendSibling(((SpongeMessage)message).getHandle());
        }

        return this;
    }

    @Override
    public MessageBuilder<T> color(TextColor color) {
        this.color = color;
        return this;
    }

    @Override
    public MessageBuilder<T> style(TextStyle... styles) {
        for (TextStyle textStyle : styles) {
            this.style.and(textStyle);
        }
        return this;
    }

    @Override
    public MessageBuilder<T> onClick(ClickAction<?> action) {
        this.clickAction = Optional.<ClickAction<?>>fromNullable(action);
        return this;
    }

    @Override
    public MessageBuilder<T> onHover(HoverAction<?> action) {
        this.hoverAction = Optional.<HoverAction<?>>fromNullable(action);
        return this;
    }

    @Override
    public MessageBuilder<T> onShiftClick(ShiftClickAction<?> action) {
        this.shiftClickAction = Optional.<ShiftClickAction<?>>fromNullable(action);
        return this;
    }

}
