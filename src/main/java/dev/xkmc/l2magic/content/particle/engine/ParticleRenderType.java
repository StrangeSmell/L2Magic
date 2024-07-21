package dev.xkmc.l2magic.content.particle.engine;

import com.mojang.serialization.MapCodec;

public interface ParticleRenderType<T extends Record & ParticleRenderData<T>> {

	MapCodec<T> codec();

}
