package dev.xkmc.l2magic.content.engine.block;

import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.core.IPredicate;
import dev.xkmc.l2magic.content.engine.iterator.BlockInRangeIterator;
import dev.xkmc.l2magic.content.engine.logic.PredicateLogic;
import dev.xkmc.l2magic.content.engine.predicate.AndPredicate;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IBlockProcessor<T extends Record & ConfiguredEngine<T> & IBlockProcessor<T>> extends ConfiguredEngine<T> {

	default ConfiguredEngine<?> circular(
			DoubleVariable radius,
			DoubleVariable delayPerBlock,
			boolean plane,
			@Nullable String variable,
			IPredicate... predicates
	) {
		return new BlockInRangeIterator(radius, delayPerBlock, plane,
				new PredicateLogic(new AndPredicate(List.of(predicates)),
						this, null),
				variable);
	}

}
