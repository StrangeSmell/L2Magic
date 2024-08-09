package dev.xkmc.l2magic.content.engine.core;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.variable.BooleanVariable;

public interface IPredicate extends ParameterizedVerifiable {

	Codec<IPredicate> CODEC = Codec.either(BooleanVariable.CODEC, ContextPredicate.CODEC)
			.xmap(e -> e.map(l -> l, r -> r), i -> i instanceof BooleanVariable b ?
					Either.left(b) : Either.right((ContextPredicate<?>) i));

	boolean test(EngineContext ctx);

}
