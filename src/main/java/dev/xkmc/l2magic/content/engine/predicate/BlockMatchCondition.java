package dev.xkmc.l2magic.content.engine.predicate;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.block.BlockUtils;
import dev.xkmc.l2magic.content.engine.context.EngineContext;
import dev.xkmc.l2magic.content.engine.core.ContextPredicate;
import dev.xkmc.l2magic.content.engine.core.PredicateType;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;

public record BlockMatchCondition(
		ExtraCodecs.TagOrElementLocation block
) implements ContextPredicate<BlockMatchCondition> {

	public static final MapCodec<BlockMatchCondition> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			ExtraCodecs.TAG_OR_ELEMENT_ID.fieldOf("block").forGetter(BlockMatchCondition::block)
	).apply(i, BlockMatchCondition::new));

	public static BlockMatchCondition of(Block block) {
		return new BlockMatchCondition(new ExtraCodecs.TagOrElementLocation(BuiltInRegistries.BLOCK.getKey(block), false));
	}

	public static BlockMatchCondition of(TagKey<Block> tag) {
		return new BlockMatchCondition(new ExtraCodecs.TagOrElementLocation(tag.location(), true));
	}

	@Override
	public PredicateType<BlockMatchCondition> type() {
		return EngineRegistry.BLOCK_MATCH.get();
	}

	@Override
	public boolean test(EngineContext ctx) {
		return BlockUtils.test(ctx.user().level().getBlockState(BlockPos.containing(ctx.loc().pos())), block);
	}

}
