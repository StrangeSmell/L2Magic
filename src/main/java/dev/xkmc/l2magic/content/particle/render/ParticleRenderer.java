package dev.xkmc.l2magic.content.particle.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.xkmc.l2magic.content.particle.core.LMGenericParticle;
import dev.xkmc.l2magic.content.particle.engine.RenderTypePreset;
import net.minecraft.client.Camera;

public interface ParticleRenderer {

	void onParticleInit(LMGenericParticle e);

	void onPostTick(LMGenericParticle e);

	RenderTypePreset renderType();

	boolean specialRender(LMGenericParticle lmGenericParticle, VertexConsumer vc, Camera camera, float pTick);

	default boolean needOrientation() {
		return false;
	}

}
