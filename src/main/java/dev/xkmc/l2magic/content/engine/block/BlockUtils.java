package dev.xkmc.l2magic.content.engine.block;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockUtils {

	public static final Codec<BlockState> BLOCK_OR_STATE = Codec.either(
			BuiltInRegistries.BLOCK.byNameCodec(), BlockState.CODEC
	).xmap(e -> e.map(Block::defaultBlockState, r -> r),
			e -> e == e.getBlock().defaultBlockState() ?
					Either.left(e.getBlock()) : Either.right(e));


	public static boolean test(BlockState state, ExtraCodecs.TagOrElementLocation block) {
		if (block.tag()) {
			TagKey<Block> key = TagKey.create(Registries.BLOCK, block.id());
			return state.is(key);
		}
		return state.is(BuiltInRegistries.BLOCK.get(block.id()));
	}
}
