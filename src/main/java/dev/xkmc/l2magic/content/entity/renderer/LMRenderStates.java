package dev.xkmc.l2magic.content.entity.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public abstract class LMRenderStates extends RenderType {

	public LMRenderStates(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
		super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
	}

	protected static final ShaderStateShard POS_TEX = new ShaderStateShard(GameRenderer::getPositionTexShader);

	private static final Function<ResourceLocation, RenderType> SOLID = Util.memoize((rl) ->
			create("projectile_solid",
					DefaultVertexFormat.POSITION_TEX,
					VertexFormat.Mode.QUADS,
					256, true, false,
					CompositeState.builder()
							.setShaderState(POS_TEX)
							.setTextureState(new TextureStateShard(rl, false, false))
							.setTransparencyState(NO_TRANSPARENCY)
							.setCullState(NO_CULL)
							.createCompositeState(false)));

	public static RenderType solid(ResourceLocation rl) {
		return SOLID.apply(rl);
	}

}
