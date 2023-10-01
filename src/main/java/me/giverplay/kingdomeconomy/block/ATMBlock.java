package me.giverplay.kingdomeconomy.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;

public class ATMBlock extends HorizontalDirectionalBlock {
  public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

  public ATMBlock(Properties properties) {
    super(properties);
  }

  @Override
  public BlockState getStateForPlacement(@NotNull BlockPlaceContext placeContext) {
    return this.defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection().getOpposite());
  }

  @NotNull
  @Override
  public BlockState rotate(@NotNull BlockState blockState, @NotNull Rotation rotation) {
    return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
  }

  @NotNull
  @Override
  public BlockState mirror(BlockState blockState, Mirror mirror) {
    return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
    stateBuilder.add(FACING);
  }
}
