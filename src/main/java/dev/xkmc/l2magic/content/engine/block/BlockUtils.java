package dev.xkmc.l2magic.content.engine.block;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockUtils {

	public static final Codec<BlockState> BLOCK_OR_STATE = Codec.either(
			BuiltInRegistries.BLOCK.byNameCodec(), BlockState.CODEC
	).xmap(e -> e.map(Block::defaultBlockState, r -> r),
			e -> e == e.getBlock().defaultBlockState() ?
					Either.left(e.getBlock()) : Either.right(e));



}
