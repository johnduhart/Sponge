/**
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered.org <http://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.mod.text.format;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.spongepowered.api.text.format.TextStyle;

import com.google.common.collect.Maps;

public abstract class SpongeTextStyle implements TextStyle {

    public Map<String, Boolean> states;

    public SpongeTextStyle() {
        super();
        states = initNewStates();
    }

    public SpongeTextStyle(String name) {
        super();
        states = initNewStates();
        states.put(name, true);
    }

    public Map<String, Boolean> initNewStates() {
        Map<String, Boolean> states = Maps.newHashMap();
        states.put("BOLD", null);
        states.put("OBFUSCATED", null);
        states.put("ITALIC", null);
        states.put("STRIKETHROUGH", null);
        states.put("UNDERLINE", null);
        states.put("RESET", null);
        return states;
    }

    @Override
    public boolean isComposite() {
        int count = 0;
        for (Boolean state : states.values()) {
            if (state) {
                count++;
            }
        }
        return count > 1;
    }

    @Override
    public boolean is(TextStyle style) {
        SpongeTextStyle textStyle = (SpongeTextStyle) style;
        for (Map.Entry<String, Boolean> mapEntry : states.entrySet()) {
            if (textStyle.states.containsKey(mapEntry.getKey())) {
                if (mapEntry.getValue() != textStyle.states.get(mapEntry.getKey())) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    public static class BaseStyle extends SpongeTextStyle implements TextStyle.Base {

        private String name;
        private char legacyCode;

        public BaseStyle(String name, char code) {
            super(name);
            this.name = name;
        }

        public BaseStyle(Map<String, Boolean> newStates) {
            this.states = Maps.newHashMap(newStates);
        }

        @Override
        public TextStyle negate() {
            BaseStyle textStyle = (BaseStyle) copyStyle(this);
            for (Map.Entry<String, Boolean> mapEntry : textStyle.states.entrySet()) {
                String type = mapEntry.getKey();
                Boolean value = mapEntry.getValue();
                if (value != null) {
                    textStyle.states.put(type, !value);
                }
            }

            return textStyle;
        }

        @Override
        public TextStyle and(TextStyle... styles) {
            BaseStyle textStyle = (BaseStyle) copyStyle(this);

            for (TextStyle style : styles) {
                BaseStyle baseStyle = (BaseStyle) style;
                for (Map.Entry<String, Boolean> mapEntry : baseStyle.states.entrySet()) {
                    String type = mapEntry.getKey();
                    Boolean value = mapEntry.getValue();
                    if (value != null) {
                        textStyle.states.put(type, value);
                    }
                }
            }
            return textStyle; 
        }

        @Override
        public TextStyle andNot(TextStyle... styles) {
            List<TextStyle> newStyles = new ArrayList<TextStyle>();
            for (TextStyle style : styles) {
                BaseStyle baseStyle = (BaseStyle) style;
                newStyles.add(baseStyle.negate());
            }

            return and(newStyles.toArray(new TextStyle[newStyles.size()]));
        }

        public TextStyle copyStyle(TextStyle style) {
            BaseStyle textStyle = new BaseStyle(((BaseStyle)style).states);
            return textStyle;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        @Deprecated
        public char getCode() {
            return legacyCode;
        }
    }
}
