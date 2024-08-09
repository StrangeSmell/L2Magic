package dev.xkmc.l2magic.content.engine.predicate;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ContextPredicate;
import dev.xkmc.l2magic.content.engine.core.PredicateType;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

public record BlockPredicate(
		Block block
) implements ContextPredicate<BlockPredicate> {

	public static final MapCodec<BlockPredicate> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(BlockPredicate::block)
	).apply(i, BlockPredicate::new));

	@Override
	public PredicateType<BlockPredicate> type() {
		return EngineRegistry.BLOCK_PRED.get();
	}

	@Override
	public boolean test(EngineContext ctx) {
		return ctx.user().level().getBlockState(BlockPos.containing(ctx.loc().pos())).is(block);
	}

}
