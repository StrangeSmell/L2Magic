package dev.xkmc.l2magic.content.engine.core;

import com.mojang.serialization.MapCodec;

public interface PredicateType<T extends Record & ContextPredicate<T>> {

	MapCodec<T> codec();

}
