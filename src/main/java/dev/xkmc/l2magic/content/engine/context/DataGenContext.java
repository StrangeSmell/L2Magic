package dev.xkmc.l2magic.content.engine.context;

import dev.xkmc.l2magic.content.entity.core.ProjectileConfig;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public record DataGenContext(BootstrapContext<?> ctx) {

	public Holder<DamageType> damage(ResourceKey<DamageType> key) {
		return ctx.lookup(Registries.DAMAGE_TYPE).getOrThrow(key);
	}

	public Holder<ProjectileConfig> getProjectile(ResourceKey<ProjectileConfig> key) {
		return ctx.lookup(EngineRegistry.PROJECTILE).getOrThrow(key);
	}

}
