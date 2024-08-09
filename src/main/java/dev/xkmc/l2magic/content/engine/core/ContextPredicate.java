package dev.xkmc.l2magic.content.engine.core;

import com.mojang.serialization.Codec;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;

public interface ContextPredicate<T extends Record & ContextPredicate<T>>
		extends IPredicate {

	Codec<ContextPredicate<?>> CODEC = EngineRegistry.PREDICATE.codec()
			.dispatch(ContextPredicate::type, PredicateType::codec);

	PredicateType<T> type();

	boolean test(EngineContext ctx);

}
