package dev.xkmc.l2magic.content.particle.engine;

import com.mojang.serialization.Codec;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.Verifiable;
import dev.xkmc.l2magic.content.particle.render.ParticleRenderer;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;

public interface ParticleRenderData<T extends Record & ParticleRenderData<T>> extends Verifiable {

	Codec<ParticleRenderData<?>> CODEC = EngineRegistry.PARTICLE_RENDERER.codec()
			.dispatch(ParticleRenderData::type, ParticleRenderType::codec);

	ParticleRenderType<T> type();

	ParticleRenderer resolve(EngineContext ctx);

}
