/**
 * This file is part of Prism, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 Helion3 http://helion3.com/
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
package com.helion3.prism.api.parameters;

import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.collect.ImmutableList;
import com.helion3.prism.api.query.Conditions;
import com.helion3.prism.api.query.Query;
import com.helion3.prism.api.query.QuerySession;

public class ParameterRadius extends SimpleParameterHandler {
    private final Pattern pattern = Pattern.compile("[\\w,:-]+");

    /**
     * Parameter handling a radius around a single location.
     */
    public ParameterRadius() {
        super(ImmutableList.of("r", "radius"));
    }

    @Override
    public boolean acceptsSource(@Nullable CommandSource source) {
        return (source instanceof Player);
    }

    @Override
    public boolean acceptsValue(String value) {
        return pattern.matcher(value).matches();
    }

    @Override
    public void process(QuerySession session, String parameter, String value, Query query) {
        if (session.getCommandSource().get() instanceof Player) {
            Location<World> location = ((Player) session.getCommandSource().get()).getLocation();
            query.addConditions(Conditions.from(location, Integer.parseInt(value)));
        }
    }
}
