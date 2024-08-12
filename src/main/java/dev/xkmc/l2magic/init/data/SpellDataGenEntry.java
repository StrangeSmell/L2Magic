package dev.xkmc.l2magic.init.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2magic.content.engine.spell.SpellAction;
import dev.xkmc.l2magic.content.entity.core.ProjectileConfig;
import dev.xkmc.l2magic.init.L2Magic;
import dev.xkmc.l2magic.init.registrate.EngineRegistry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public abstract class SpellDataGenEntry {

	protected static ResourceKey<SpellAction> spell(String id) {
		return ResourceKey.create(EngineRegistry.SPELL, L2Magic.loc(id));
	}

	protected static DataGenCachedHolder<ProjectileConfig> projectile(String id) {
		return new DataGenCachedHolder<>(ResourceKey.create(EngineRegistry.PROJECTILE, L2Magic.loc(id)));
	}

	public void registerProjectile(BootstrapContext<ProjectileConfig> ctx) {

	}

	public abstract void register(BootstrapContext<SpellAction> ctx);

	public abstract void genLang(RegistrateLangProvider pvd);

}
