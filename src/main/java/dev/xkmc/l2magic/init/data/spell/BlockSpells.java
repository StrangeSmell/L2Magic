package dev.xkmc.l2magic.init.data.spell;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2magic.content.engine.block.SetBlock;
import dev.xkmc.l2magic.content.engine.context.DataGenContext;
import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.modifier.OffsetModifier;
import dev.xkmc.l2magic.content.engine.predicate.FullTopSurfaceCondition;
import dev.xkmc.l2magic.content.engine.predicate.ReplaceableCondition;
import dev.xkmc.l2magic.content.engine.spell.SpellAction;
import dev.xkmc.l2magic.content.engine.spell.SpellCastType;
import dev.xkmc.l2magic.content.engine.spell.SpellTriggerType;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.init.data.SpellDataGenEntry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class BlockSpells extends SpellDataGenEntry {

	public static final ResourceKey<SpellAction> WEBS = spell("cobwebs");
	public static final ResourceKey<SpellAction> FLOOR = spell("floor");

	@Override
	public void register(BootstrapContext<SpellAction> ctx) {
		new SpellAction(cobwebs(new DataGenContext(ctx)),
				Items.COBWEB, 612, SpellCastType.INSTANT, SpellTriggerType.TARGET_POS
		).verifyOnBuild(ctx, WEBS);
		new SpellAction(floor(new DataGenContext(ctx)),
				Items.STONE, 622, SpellCastType.INSTANT, SpellTriggerType.SELF_POS
		).verifyOnBuild(ctx, FLOOR);
	}

	@Override
	public void genLang(RegistrateLangProvider pvd) {
		pvd.add(SpellAction.lang(WEBS.location()), "Spider Nest");
		pvd.add(SpellAction.lang(FLOOR.location()), "Floor");
	}


	private static ConfiguredEngine<?> floor(DataGenContext ctx) {
		return new SetBlock(Blocks.STONE.defaultBlockState()).circular(
				DoubleVariable.of("3"), DoubleVariable.ZERO, true, null,
				ReplaceableCondition.INS).move(OffsetModifier.of("0", "-0.5", "0"));
	}

	private static ConfiguredEngine<?> cobwebs(DataGenContext ctx) {
		return new SetBlock(Blocks.COBWEB.defaultBlockState()).circular(
				DoubleVariable.of("6"), DoubleVariable.of("2"), false, null,
				ReplaceableCondition.INS, FullTopSurfaceCondition.INS);
	}
}
