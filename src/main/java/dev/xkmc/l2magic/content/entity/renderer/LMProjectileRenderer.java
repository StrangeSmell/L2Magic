package dev.xkmc.l2magic.content.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.xkmc.fastprojectileapi.render.ProjectileRenderer;
import dev.xkmc.l2magic.content.entity.core.LMProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;

public class LMProjectileRenderer<T extends LMProjectile> extends EntityRenderer<T> implements ProjectileRenderer {

	public LMProjectileRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
	}

	@Override
	public void render(T e, float yaw, float pTick, PoseStack pose, MultiBufferSource buffer, int light) {
		var renderer = e.getRenderer();
		if (renderer != null) {
			renderer.render(e, this, pTick, pose, buffer, light);
		}
	}

	public Quaternionf cameraOrientation() {
		return this.entityRenderDispatcher.cameraOrientation();
	}

	@Override
	public ResourceLocation getTextureLocation(T e) {
		var renderer = e.getRenderer();
		if (renderer != null) {
			return renderer.getTexture();
		}
		return TextureAtlas.LOCATION_BLOCKS;
	}

}
