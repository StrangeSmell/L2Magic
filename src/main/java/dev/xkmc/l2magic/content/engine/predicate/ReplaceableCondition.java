package dev.xkmc.l2magic.content.engine.predicate;

import com.mojang.serialization.MapCodec;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ContextPredicate;
import dev.xkmc.l2magic.content.engine.core.PredicateType;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.core.BlockPos;

public record ReplaceableCondition(
) implements ContextPredicate<ReplaceableCondition> {

	public static final ReplaceableCondition INS = new ReplaceableCondition();

	public static final MapCodec<ReplaceableCondition> CODEC = MapCodec.unit(INS);

	@Override
	public PredicateType<ReplaceableCondition> type() {
		return EngineRegistry.REPLACEABLE.get();
	}

	@Override
	public boolean test(EngineContext ctx) {
		return ctx.user().level().getBlockState(BlockPos.containing(ctx.loc().pos())).canBeReplaced();
	}

}
