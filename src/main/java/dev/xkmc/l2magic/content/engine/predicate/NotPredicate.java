package dev.xkmc.l2magic.content.engine.predicate;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ContextPredicate;
import dev.xkmc.l2magic.content.engine.core.IPredicate;
import dev.xkmc.l2magic.content.engine.core.PredicateType;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;

public record NotPredicate(
		IPredicate child
) implements ContextPredicate<NotPredicate> {

	public static final MapCodec<NotPredicate> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			IPredicate.CODEC.fieldOf("child").forGetter(NotPredicate::child)
	).apply(i, NotPredicate::new));

	@Override
	public PredicateType<NotPredicate> type() {
		return EngineRegistry.NOT.get();
	}

	@Override
	public boolean test(EngineContext ctx) {
		return !ctx.test(child);
	}

}
