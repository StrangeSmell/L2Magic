package dev.xkmc.l2magic.content.engine.core;

import com.mojang.serialization.MapCodec;

public interface EngineType<T extends Record & ConfiguredEngine<T>> {

	MapCodec<T> codec();

}
