package net.re_renderreality.rrrp2.cmd;

import java.util.Optional;
import java.util.Random;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.property.block.PassableProperty;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.gen.PopulatorObject;
import org.spongepowered.api.world.gen.type.BiomeTreeTypes;

import com.flowpowered.math.vector.Vector3i;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

public class BigTreeCommand extends CommandExecutorBase {
	
	private PopulatorObject populate;
	private Random random;
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> treeType = ctx.<String> getOne("Tree Type");
		Optional<Integer> X = ctx.<Integer> getOne("X");
		Optional<Integer> Y = ctx.<Integer> getOne("Y");
		Optional<Integer> Z = ctx.<Integer> getOne("Z");
		if(src instanceof Player) {
			if(treeType.isPresent() && X.isPresent() && Y.isPresent() && Z.isPresent()) {
				random = new Random();
				
				Player player = (Player) src;
				World world = player.getWorld();
				int x = X.get();
		        int y = Y.get();
		        int z = Z.get();
		        String tree = treeType.get();
		        
		        if(tree.equals("Birch")) {
		        	populate = BiomeTreeTypes.BIRCH.getLargePopulatorObject().get();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Large Birch Tree"));
		        }else if (tree.equals("Spruce")) {
		        	populate = BiomeTreeTypes.POINTY_TAIGA.getLargePopulatorObject().get();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Large Taiga Tree"));
		        }else if (tree.equals("Jungle")) {
		        	populate = BiomeTreeTypes.JUNGLE.getLargePopulatorObject().get();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Large Jungle Tree"));
		        }else if (tree.equals("Oak")) {
		        	populate = BiomeTreeTypes.OAK.getLargePopulatorObject().get();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Large Oak Tree"));
		        }else if (tree.equals("Tall_Taiga")) {
		        	populate = BiomeTreeTypes.TALL_TAIGA.getLargePopulatorObject().get();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Large Taiga Tree"));
		        }
		        
		        Vector3i pos = new Vector3i(x, y, z);
		        Vector3i below = new Vector3i(x, y - 1, z);
		        
		        BlockState blockBelow = world.containsBlock(below) ? world.getBlock(below) : null;
		        if (blockBelow != null) {
		            world.setBlock(below, BlockState.builder().blockType(BlockTypes.DIRT).build());
		        }
		        
		        //Remove Grass, redstone, etc.
		        BlockState blockOnPosition = world.getBlock(pos);
		        
		        Optional<PassableProperty> passableProperty_Op = blockOnPosition.getProperty(PassableProperty.class);
		        boolean blockPassable = passableProperty_Op.isPresent() && passableProperty_Op.get().getValue();

		        boolean passableBlockRemoved = false;
		        if (blockPassable) {
		            world.setBlock(pos, BlockState.builder().blockType(BlockTypes.AIR).build(), true);
		            passableBlockRemoved = true;
		        }
		        
		        boolean treePlaced = false;
		        //is the tree placeable?
		        if (populate.canPlaceAt(world, x, y, z)) {
		            //Place the tree
		        	populate.placeObject(world, random, x, y, z);
		        	src.sendMessage(Text.of(TextColors.GREEN, "Success!"));
		            treePlaced = true;
		        } else if (passableBlockRemoved) {
		            //Reset passable Block
		            world.setBlock(pos, blockOnPosition);
		            
		        } else {
		        	src.sendMessage(Text.of(TextColors.RED, "Failed"));
		        }
		        
		        //Replace block below with original Block (unless the tree was placed in mid-air or water)
		        if (blockBelow != null && !(treePlaced && (blockBelow.getType() == BlockTypes.AIR ||
		                blockBelow.getType() == BlockTypes.WATER ||
		                blockBelow.getType() == BlockTypes.FLOWING_WATER))) {
		            world.setBlock(below, blockBelow);
		        }
		        return CommandResult.success();
			}
		}
		return CommandResult.empty();
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "bigtree", "BigTree", "Bigtree" };
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Gives detailed information about the requested player"))
				.permission("rrrp2.cheat.tree")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("Tree Type"))), 
						   GenericArguments.onlyOne(GenericArguments.integer(Text.of("X"))),
				           GenericArguments.onlyOne(GenericArguments.integer(Text.of("Y"))),
				           GenericArguments.onlyOne(GenericArguments.integer(Text.of("Z"))))
				.executor(this).build();
	}

}
