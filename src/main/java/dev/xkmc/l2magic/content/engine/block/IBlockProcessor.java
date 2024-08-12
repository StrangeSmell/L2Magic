package dev.xkmc.l2magic.content.engine.block;

import dev.xkmc.l2magic.content.engine.core.ConfiguredEngine;

public interface IBlockProcessor<T extends Record & ConfiguredEngine<T> & IBlockProcessor<T>> extends ConfiguredEngine<T> {

}
