package dev.xkmc.l2magic.content.engine.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ContextPredicate;
import dev.xkmc.l2magic.content.engine.core.PredicateType;
import dev.xkmc.l2magic.content.engine.helper.EngineHelper;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public record BlockTestCondition(
		Type test
) implements ContextPredicate<BlockTestCondition> {

	public enum Type {
		REPLACEABLE(BlockBehaviour.BlockStateBase::canBeReplaced),
		REQUIRES_TOOL(BlockBehaviour.BlockStateBase::requiresCorrectToolForDrops),
		BLOCKS_MOTION(BlockBehaviour.BlockStateBase::blocksMotion);

		private final Predicate<BlockState> pred;

		Type(Predicate<BlockState> pred) {
			this.pred = pred;
		}

		public BlockTestCondition get() {
			return new BlockTestCondition(this);
		}

	}

	public static final Codec<Type> TYPE_CODEC = EngineHelper.enumCodec(Type.class, Type.values());

	public static final MapCodec<BlockTestCondition> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			TYPE_CODEC.fieldOf("test").forGetter(BlockTestCondition::test)
	).apply(i, BlockTestCondition::new));

	@Override
	public PredicateType<BlockTestCondition> type() {
		return EngineRegistry.BLOCK_TEST.get();
	}

	@Override
	public boolean test(EngineContext ctx) {
		return test.pred.test(ctx.user().level().getBlockState(BlockPos.containing(ctx.loc().pos())));
	}

}
