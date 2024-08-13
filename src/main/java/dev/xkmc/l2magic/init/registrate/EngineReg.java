package dev.xkmc.l2magic.init.registrate;

import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2core.init.reg.simple.Val;
import dev.xkmc.l2magic.content.engine.core.*;
import dev.xkmc.l2magic.content.entity.core.Motion;
import dev.xkmc.l2magic.content.entity.core.MotionType;
import dev.xkmc.l2magic.content.entity.renderer.ProjectileRenderData;
import dev.xkmc.l2magic.content.entity.renderer.ProjectileRenderType;
import dev.xkmc.l2magic.content.particle.engine.ParticleRenderData;
import dev.xkmc.l2magic.content.particle.engine.ParticleRenderType;

public record EngineReg(L2Registrate reg) {

	public <T extends Record & ConfiguredEngine<T>> Val<EngineType<T>>
	reg(String id, EngineType<T> codec) {
		return new Val.Registrate<>(reg.simple(id, EngineRegistry.ENGINE.key(), () -> codec));
	}

	public <T extends Record & EntitySelector<T>> Val<SelectorType<T>>
	reg(String id, SelectorType<T> codec) {
		return new Val.Registrate<>(reg.simple(id, EngineRegistry.SELECTOR.key(), () -> codec));
	}

	public <T extends Record & Modifier<T>> Val<ModifierType<T>>
	reg(String id, ModifierType<T> codec) {
		return new Val.Registrate<>(reg.simple(id, EngineRegistry.MODIFIER.key(), () -> codec));
	}

	public <T extends Record & ContextPredicate<T>> Val<PredicateType<T>>
	reg(String id, PredicateType<T> codec) {
		return new Val.Registrate<>(reg.simple(id, EngineRegistry.PREDICATE.key(), () -> codec));
	}

	public <T extends Record & EntityProcessor<T>> Val<ProcessorType<T>>
	reg(String id, ProcessorType<T> codec) {
		return new Val.Registrate<>(reg.simple(id, EngineRegistry.PROCESSOR.key(), () -> codec));
	}

	public <T extends Record & Motion<T>> Val<MotionType<T>>
	reg(String id, MotionType<T> codec) {
		return new Val.Registrate<>(reg.simple(id, EngineRegistry.MOTION.key(), () -> codec));
	}

	public <T extends Record & ParticleRenderData<T>> Val<ParticleRenderType<T>>
	reg(String id, ParticleRenderType<T> codec) {
		return new Val.Registrate<>(reg.simple(id, EngineRegistry.PARTICLE_RENDERER.key(), () -> codec));
	}

	public <T extends Record & ProjectileRenderData<T>> Val<ProjectileRenderType<T>>
	reg(String id, ProjectileRenderType<T> codec) {
		return new Val.Registrate<>(reg.simple(id, EngineRegistry.PROJECTILE_RENDERER.key(), () -> codec));
	}

}
