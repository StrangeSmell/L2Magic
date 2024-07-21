package dev.xkmc.l2magic.content.engine.core;

import com.mojang.serialization.MapCodec;

public interface ModifierType<T extends Record & Modifier<T>> {

	MapCodec<T> codec();

}
