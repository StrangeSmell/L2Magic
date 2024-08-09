package dev.xkmc.l2magic.content.engine.block;

import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.core.IPredicate;
import dev.xkmc.l2magic.content.engine.iterator.LoopIterator;
import dev.xkmc.l2magic.content.engine.logic.DelayLogic;
import dev.xkmc.l2magic.content.engine.logic.MoveEngine;
import dev.xkmc.l2magic.content.engine.logic.PredicateLogic;
import dev.xkmc.l2magic.content.engine.logic.VariableLogic;
import dev.xkmc.l2magic.content.engine.modifier.OffsetModifier;
import dev.xkmc.l2magic.content.engine.predicate.AndPredicate;
import dev.xkmc.l2magic.content.engine.variable.BooleanVariable;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.content.engine.variable.IntVariable;

import java.util.List;

public interface IBlockProcessor<T extends Record & ConfiguredEngine<T> & IBlockProcessor<T>> extends ConfiguredEngine<T> {

	/**
	 * @param radius     radius for blocks to be processed
	 * @param delay      delay in terms of variable "dist" defined within context
	 * @param predicates several predicates ran after delay
	 */
	default ConfiguredEngine<?> circular(
			DoubleVariable radius,
			IntVariable delay,
			IPredicate... predicates
	) {
		var max = IntVariable.of("(radius)*2+1");
		var dist = DoubleVariable.of("sqrt((ix-radius)*(ix-radius)+(iy-radius)*(iy-radius)+(iz-radius)*(iz-radius))");
		return new VariableLogic("radius", radius,
				new LoopIterator(max, new LoopIterator(max, new LoopIterator(max,
						new VariableLogic("dist", dist,
								new PredicateLogic(BooleanVariable.of("dist<radius"),
										new DelayLogic(delay, new MoveEngine(List.of(
												OffsetModifier.of("ix-radius", "iy-radius", "iz-radius")
										), new PredicateLogic(
												new AndPredicate(List.of(predicates)), this, null
										))), null
								)
						), "iy"), "iz"), "ix"
				)
		);
	}

}
