package dev.xkmc.l2magic.content.engine.predicate;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ContextPredicate;
import dev.xkmc.l2magic.content.engine.core.IPredicate;
import dev.xkmc.l2magic.content.engine.core.PredicateType;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;

import java.util.List;

public record AndPredicate(
		List<IPredicate> list
) implements ContextPredicate<AndPredicate> {

	public static final MapCodec<AndPredicate> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			IPredicate.CODEC.listOf().fieldOf("list").forGetter(AndPredicate::list)
	).apply(i, AndPredicate::new));

	@Override
	public PredicateType<AndPredicate> type() {
		return EngineRegistry.AND.get();
	}

	@Override
	public boolean test(EngineContext ctx) {
		for (var e : list) {
			if (!e.test(ctx)) {
				return false;
			}
		}
		return true;
	}

}
