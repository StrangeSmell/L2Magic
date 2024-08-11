package dev.xkmc.l2magic.content.engine.core;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import dev.xkmc.l2core.util.DataGenOnly;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.predicate.MovePredicate;
import dev.xkmc.l2magic.content.engine.predicate.NotPredicate;
import dev.xkmc.l2magic.content.engine.variable.BooleanVariable;

import java.util.List;

public interface IPredicate extends ParameterizedVerifiable {

	Codec<IPredicate> CODEC = Codec.either(BooleanVariable.CODEC, ContextPredicate.CODEC)
			.xmap(e -> e.map(l -> l, r -> r), i -> i instanceof BooleanVariable b ?
					Either.left(b) : Either.right((ContextPredicate<?>) i));

	boolean test(EngineContext ctx);

	@DataGenOnly
	default IPredicate move(Modifier<?>... modifiers) {
		return new MovePredicate(List.of(modifiers), this);
	}

	@DataGenOnly
	default IPredicate invert() {
		return new NotPredicate(this);
	}

}
