package dev.xkmc.l2magic.content.engine.core;

import com.mojang.serialization.MapCodec;

public interface ProcessorType<T extends Record & EntityProcessor<T>> {

	MapCodec<T> codec();

}
