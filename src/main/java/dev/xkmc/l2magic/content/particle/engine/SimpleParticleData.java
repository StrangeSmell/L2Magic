package dev.xkmc.l2magic.content.particle.engine;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.particle.render.ParticleRenderer;
import dev.xkmc.l2magic.content.particle.render.SimpleParticleSprite;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public record SimpleParticleData(
		RenderTypePreset renderType, ParticleType<?> particle
) implements ParticleRenderData<SimpleParticleData> {

	public static final MapCodec<SimpleParticleData> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			RenderTypePreset.CODEC.fieldOf("renderType").forGetter(e -> e.renderType),
			BuiltInRegistries.PARTICLE_TYPE.byNameCodec().fieldOf("particle").forGetter(e -> e.particle)
	).apply(i, SimpleParticleData::new));

	@Override
	public ParticleRenderType<SimpleParticleData> type() {
		return EngineRegistry.SIMPLE_RENDER.get();
	}

	@Override
	public ParticleRenderer resolve(EngineContext ctx) {
		var rl = BuiltInRegistries.PARTICLE_TYPE.getKey(particle);
		assert rl != null;
		return new SimpleParticleSprite(renderType, rl);
	}

}
