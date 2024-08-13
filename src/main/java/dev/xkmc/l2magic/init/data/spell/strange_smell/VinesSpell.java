package dev.xkmc.l2magic.init.data.spell.strange_smell;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2magic.content.engine.context.DataGenContext;
import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;
import dev.xkmc.l2magic.content.engine.iterator.*;
import dev.xkmc.l2magic.content.engine.logic.*;
import dev.xkmc.l2magic.content.engine.modifier.*;
import dev.xkmc.l2magic.content.engine.particle.BlockParticleInstance;
import dev.xkmc.l2magic.content.engine.particle.DustParticleInstance;
import dev.xkmc.l2magic.content.engine.particle.SimpleParticleInstance;
import dev.xkmc.l2magic.content.engine.processor.*;
import dev.xkmc.l2magic.content.engine.selector.*;
import dev.xkmc.l2magic.content.engine.sound.SoundInstance;
import dev.xkmc.l2magic.content.engine.spell.SpellAction;
import dev.xkmc.l2magic.content.engine.spell.SpellCastType;
import dev.xkmc.l2magic.content.engine.spell.SpellTriggerType;
import dev.xkmc.l2magic.content.engine.variable.BooleanVariable;
import dev.xkmc.l2magic.content.engine.variable.ColorVariable;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.content.engine.variable.IntVariable;
import dev.xkmc.l2magic.content.entity.motion.MovePosMotion;
import dev.xkmc.l2magic.content.particle.engine.*;
import dev.xkmc.l2magic.init.data.SpellDataGenEntry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class VinesSpell extends SpellDataGenEntry {

    public static final ResourceKey<SpellAction> VINE = spell("vine");

    @Override
    public void genLang(RegistrateLangProvider pvd) {
        pvd.add(SpellAction.lang(VINE.location()), "Vine");
    }

    @Override
    public void register(BootstrapContext<SpellAction> ctx) {
        new SpellAction(
                vine(new DataGenContext(ctx), 4, 0.3),
                Items.VINE, 3100,
                SpellCastType.INSTANT,
                SpellTriggerType.TARGET_POS
        ).verifyOnBuild(ctx, VINE);
    }

    private static ConfiguredEngine<?> vine(DataGenContext ctx, double radius, double step) {
        return new ListLogic(List.of(
                new ProcessorEngine(
                        SelectionType.ENEMY,
                        new BoxSelector(
                                DoubleVariable.of("16"),
                                DoubleVariable.of("16"),
                                true
                        ),
                        List.of(
                                new DamageProcessor(
                                        ctx.damage(DamageTypes.INDIRECT_MAGIC),
                                        DoubleVariable.of("1"),
                                        true, false
                                ),
                                new PushProcessor(
                                        DoubleVariable.of("-.5"),
                                        DoubleVariable.ZERO,
                                        DoubleVariable.ZERO,
                                        PushProcessor.Type.TO_CENTER
                                )
                        )
                ),
                new LoopIterator(
                        IntVariable.of("1"),
                        new SphereRandomIterator(
                                DoubleVariable.of("8"),
                                IntVariable.of("200"),
                                new BlockParticleInstance(
                                        Blocks.VINE,
                                        DoubleVariable.of("-.5"),
                                        DoubleVariable.of("1"),
                                        IntVariable.of("40"),
                                        true
                                ),
                                null
                        )
                        ,
                        null

                )

        ));
    }

}
