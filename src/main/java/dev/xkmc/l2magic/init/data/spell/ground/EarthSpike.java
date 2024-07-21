package dev.xkmc.l2magic.init.data.spell.ground;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2magic.content.engine.context.DataGenContext;
import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.iterator.DelayedIterator;
import dev.xkmc.l2magic.content.engine.iterator.RingIterator;
import dev.xkmc.l2magic.content.engine.logic.*;
import dev.xkmc.l2magic.content.engine.modifier.ForwardOffsetModifier;
import dev.xkmc.l2magic.content.engine.modifier.SetDirectionModifier;
import dev.xkmc.l2magic.content.engine.modifier.SetNormalModifier;
import dev.xkmc.l2magic.content.engine.particle.DustParticleInstance;
import dev.xkmc.l2magic.content.engine.processor.DamageProcessor;
import dev.xkmc.l2magic.content.engine.processor.KnockBackProcessor;
import dev.xkmc.l2magic.content.engine.selector.ApproxCylinderSelector;
import dev.xkmc.l2magic.content.engine.selector.SelectionType;
import dev.xkmc.l2magic.content.engine.spell.SpellAction;
import dev.xkmc.l2magic.content.engine.spell.SpellCastType;
import dev.xkmc.l2magic.content.engine.spell.SpellTriggerType;
import dev.xkmc.l2magic.content.engine.variable.BooleanVariable;
import dev.xkmc.l2magic.content.engine.variable.ColorVariable;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.content.engine.variable.IntVariable;
import dev.xkmc.l2magic.init.data.SpellDataGenEntry;
import dev.xkmc.l2magic.init.data.spell.UnrealHelper;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class EarthSpike extends SpellDataGenEntry {

	public static final ResourceKey<SpellAction> EARTH_SPIKE = spell("earth_spike");
	public static final ResourceKey<SpellAction> EARTH_SPIKE_FIELD = spell("earth_spike_field");

	@Override
	public void genLang(RegistrateLangProvider pvd) {
		pvd.add(SpellAction.lang(EARTH_SPIKE.location()), "Earth Spike");
		pvd.add(SpellAction.lang(EARTH_SPIKE_FIELD.location()), "Earth Spike Field");
	}

	@Override
	public void register(BootstrapContext<SpellAction> ctx) {
		new SpellAction(
				earthSpike(new DataGenContext(ctx)),
				Blocks.POINTED_DRIPSTONE.asItem(), 3200,
				SpellCastType.INSTANT,
				SpellTriggerType.TARGET_POS
		).verifyOnBuild(ctx, EARTH_SPIKE);
		new SpellAction(
				earthSpikeField(new DataGenContext(ctx)),
				Blocks.POINTED_DRIPSTONE.asItem(), 3300,
				SpellCastType.INSTANT,
				SpellTriggerType.TARGET_POS
		).verifyOnBuild(ctx, EARTH_SPIKE_FIELD);
	}

	private static ConfiguredEngine<?> earthSpike(DataGenContext ctx) {
		return new ListLogic(List.of(
				new ProcessorEngine(  // Damage
						SelectionType.ENEMY,
						new ApproxCylinderSelector(
								DoubleVariable.of("1.5"),
								DoubleVariable.of("3")
						),
						List.of(
								new DamageProcessor(ctx.damage(DamageTypes.INDIRECT_MAGIC),
										DoubleVariable.of("8"), true, true),
								new KnockBackProcessor(
										DoubleVariable.of("0.2"),
										DoubleVariable.ZERO,
										DoubleVariable.ZERO
								)
						)
				),
				new MoveEngine(  // Render
						List.of(
								new SetDirectionModifier(
										DoubleVariable.ZERO,
										DoubleVariable.of("1"),
										DoubleVariable.ZERO
								),
								new ForwardOffsetModifier(DoubleVariable.of("-2"))
						),
						new ListLogic(List.of(
								new DelayedIterator(
										IntVariable.of("10"),
										IntVariable.of("1"),
										new MoveEngine(
												List.of(new ForwardOffsetModifier(DoubleVariable.of("0.2*t"))),
												UnrealHelper.cone(
														.5,
														2,
														40,
														20,
														new DustParticleInstance(
																ColorVariable.Static.of(0xE88B00),
																DoubleVariable.of(".5"),
																DoubleVariable.of(".01"),
																IntVariable.of("1")
														)
												)
										),
										"t"
								),
								new DelayLogic(
										IntVariable.of("11"),
										new MoveEngine(
												List.of(new ForwardOffsetModifier(DoubleVariable.of("2"))),
												UnrealHelper.cone(
														.5,
														2,
														40,
														20,
														new DustParticleInstance(
																ColorVariable.Static.of(0xE88B00),
																DoubleVariable.of(".5"),
																DoubleVariable.ZERO,
																IntVariable.of("10")
														)
												)
										)
								)
						))
				)
		));
	}

	private static ConfiguredEngine<?> earthSpikeField(DataGenContext ctx) {
		return new DelayedIterator(
				IntVariable.of("5"),
				IntVariable.of("10"),
				new PredicateLogic(
						BooleanVariable.of("c>0"),
						new MoveEngine(
								List.of(new SetNormalModifier(DoubleVariable.ZERO, DoubleVariable.of("1"), DoubleVariable.ZERO)),
								new RingIterator(
										DoubleVariable.of("2*c"),
										DoubleVariable.of("0"),
										DoubleVariable.of("360"),
										IntVariable.of("6*c"),
										false,
										earthSpike(ctx),
										null
								)
						),
						earthSpike(ctx)
				),
				"c"
		);
	}
}

