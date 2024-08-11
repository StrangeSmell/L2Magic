package dev.xkmc.l2magic.content.engine.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ContextPredicate;
import dev.xkmc.l2magic.content.engine.core.IPredicate;
import dev.xkmc.l2magic.content.engine.core.Modifier;
import dev.xkmc.l2magic.content.engine.core.PredicateType;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;

import java.util.List;

public record MovePredicate(List<Modifier<?>> modifiers, IPredicate child)
		implements ContextPredicate<MovePredicate> {

	public static final MapCodec<MovePredicate> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			Codec.list(Modifier.CODEC).fieldOf("modifiers").forGetter(e -> e.modifiers),
			IPredicate.CODEC.fieldOf("child").forGetter(e -> e.child)
	).apply(i, MovePredicate::new));

	@Override
	public PredicateType<MovePredicate> type() {
		return EngineRegistry.MOVE_PRED.get();
	}

	@Override
	public boolean test(EngineContext ctx) {
		for (var e : modifiers) {
			ctx = ctx.with(e.modify(ctx));
		}
		return ctx.test(child());
	}

}