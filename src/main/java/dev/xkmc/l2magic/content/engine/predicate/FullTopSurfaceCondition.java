package dev.xkmc.l2magic.content.engine.predicate;

import com.mojang.serialization.MapCodec;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ContextPredicate;
import dev.xkmc.l2magic.content.engine.core.PredicateType;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;

public record FullTopSurfaceCondition(
) implements ContextPredicate<FullTopSurfaceCondition> {

	public static final FullTopSurfaceCondition INS = new FullTopSurfaceCondition();

	public static final MapCodec<FullTopSurfaceCondition> CODEC = MapCodec.unit(INS);

	@Override
	public PredicateType<FullTopSurfaceCondition> type() {
		return EngineRegistry.TOP_SURFACE.get();
	}

	@Override
	public boolean test(EngineContext ctx) {
		return Block.canSupportCenter(ctx.user().level(), BlockPos.containing(ctx.loc().pos()), Direction.UP);
	}

}
