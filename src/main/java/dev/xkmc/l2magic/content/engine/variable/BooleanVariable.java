package dev.xkmc.l2magic.content.engine.variable;

import com.mojang.serialization.Codec;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.IPredicate;

public record BooleanVariable(String str, ExpressionHolder exp) implements NumericVariable, IPredicate {

	public static final Codec<BooleanVariable> CODEC = Codec.STRING.xmap(BooleanVariable::of, BooleanVariable::str);

	public static BooleanVariable of(String str) {
		return new BooleanVariable(str, ExpressionHolder.of(str));
	}

	@Override
	public boolean test(EngineContext ctx) {
		return exp.eval(ctx) > 0.5;
	}

}
