package dev.xkmc.l2magic.content.engine.core;

import com.mojang.serialization.MapCodec;

public interface SelectorType<T extends Record & EntitySelector<T>> {

	MapCodec<T> codec();

}
