package dev.xkmc.l2magic.content.engine.logic;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.core.EngineType;
import dev.xkmc.l2magic.content.engine.core.IPredicate;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;

import javax.annotation.Nullable;

public record PredicateLogic(
		IPredicate predicate,
		@Nullable ConfiguredEngine<?> action,
		@Nullable ConfiguredEngine<?> fallback
) implements ConfiguredEngine<PredicateLogic> {

	public static MapCodec<PredicateLogic> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			IPredicate.CODEC.fieldOf("predicate").forGetter(e -> e.predicate),
			ConfiguredEngine.optionalCodec("action", PredicateLogic::action),
			ConfiguredEngine.optionalCodec("fallback", PredicateLogic::fallback)
	).apply(i, (a, b, c) -> new PredicateLogic(a, b.orElse(null), c.orElse(null))));

	@Override
	public EngineType<PredicateLogic> type() {
		return EngineRegistry.IF.get();
	}

	@Override
	public void execute(EngineContext ctx) {
		if (ctx.test(predicate)) {
			if (action != null) ctx.execute(action);
		} else if (fallback != null) ctx.execute(fallback);
	}

}
