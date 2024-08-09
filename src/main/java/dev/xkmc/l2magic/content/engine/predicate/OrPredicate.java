package dev.xkmc.l2magic.content.engine.predicate;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ContextPredicate;
import dev.xkmc.l2magic.content.engine.core.IPredicate;
import dev.xkmc.l2magic.content.engine.core.PredicateType;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;

import java.util.List;

public record OrPredicate(
		List<IPredicate> list
) implements ContextPredicate<OrPredicate> {

	public static final MapCodec<OrPredicate> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			IPredicate.CODEC.listOf().fieldOf("list").forGetter(OrPredicate::list)
	).apply(i, OrPredicate::new));

	@Override
	public PredicateType<OrPredicate> type() {
		return EngineRegistry.OR.get();
	}

	@Override
	public boolean test(EngineContext ctx) {
		for (var e : list) {
			if (e.test(ctx)) {
				return true;
			}
		}
		return false;
	}

}
