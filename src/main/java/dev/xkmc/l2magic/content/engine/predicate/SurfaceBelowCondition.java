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
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;

public record SurfaceBelowCondition(
		Type face
) implements ContextPredicate<SurfaceBelowCondition> {

	public enum Type {
		FULL, CENTER
	}

	public static final Codec<Type> TYPE_CODEC = EngineHelper.enumCodec(Type.class, Type.values());

	public static final MapCodec<SurfaceBelowCondition> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			TYPE_CODEC.fieldOf("face").forGetter(SurfaceBelowCondition::face)
	).apply(i, SurfaceBelowCondition::new));

	public static SurfaceBelowCondition full() {
		return new SurfaceBelowCondition(Type.FULL);
	}

	public static SurfaceBelowCondition center() {
		return new SurfaceBelowCondition(Type.CENTER);
	}

	@Override
	public PredicateType<SurfaceBelowCondition> type() {
		return EngineRegistry.SURFACE_BELOW.get();
	}

	@Override
	public boolean test(EngineContext ctx) {
		var level = ctx.user().level();
		var pos = BlockPos.containing(ctx.loc().pos()).below();
		var state = level.getBlockState(pos);
		return switch (face) {
			case FULL -> Block.isFaceFull(state.getCollisionShape(level, pos), Direction.UP);
			case CENTER -> Block.canSupportCenter(level, pos, Direction.UP);
		};
	}

}
