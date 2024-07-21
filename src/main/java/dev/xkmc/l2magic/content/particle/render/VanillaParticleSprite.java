package dev.xkmc.l2magic.content.particle.render;

import dev.xkmc.l2magic.mixin.ParticleEngineAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.resources.ResourceLocation;

public interface VanillaParticleSprite extends SpriteData {

	ResourceLocation particle();

	@Override
	default SpriteSet spriteSet() {
		return ((ParticleEngineAccessor) Minecraft.getInstance().particleEngine).getSpriteSets().get(particle());
	}

}
