package net.re_renderreality.rrrp2.cmd.cheats;

import java.util.HashMap;
import java.util.Map;
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
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.gen.PopulatorObject;
import org.spongepowered.api.world.gen.type.BiomeTreeTypes;

import com.flowpowered.math.vector.Vector3i;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

public class TreeCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/tree";
		description = "Spawns a tree where the player is looking";
		perm = "rrr.cheat.tree";
		useage = "/tree <TreeType>";
		notes = null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getPerm() {
		return this.perm;
	}
	
	public String getUseage() {
		return this.useage;
	}
	
	public String getNotes() {
		return this.notes;
	}
	
	private PopulatorObject populate;
	private Random random;
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Integer> treeType = ctx.<Integer> getOne("Tree Type");
		//same as other tree command but smaller trees
		if(src instanceof Player) {
			if(treeType.isPresent()) {
				random = new Random();
				
				Player player = (Player) src;
				World world = player.getWorld();

				BlockRay<World> playerBlockRay = BlockRay.from(player).blockLimit(350).build();
				BlockRayHit<World> finalHitRay = null;

				while (playerBlockRay.hasNext()) {
					BlockRayHit<World> currentHitRay = playerBlockRay.next();

					if (!world.getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.AIR)) {
						finalHitRay = currentHitRay;
						break;
					} 
				}

				Location<World> treeLocation = null;

				if (finalHitRay == null) {
					treeLocation = player.getLocation();
				} else {
					treeLocation = finalHitRay.getLocation();
				}
				
				int x = treeLocation.getBlockX();
		        int y = treeLocation.getBlockY();
		        int z = treeLocation.getBlockZ();
		     
		        int tree = treeType.get();
		        
		        if(tree == 1) {
		        	populate = BiomeTreeTypes.BIRCH.getPopulatorObject();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Birch Tree"));
		        }else if (tree == 2) {
		        	populate = BiomeTreeTypes.POINTY_TAIGA.getPopulatorObject();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Taiga Tree"));
		        }else if (tree == 3) {
		        	populate = BiomeTreeTypes.CANOPY.getPopulatorObject();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Dark Oak Tree"));
		        }else if (tree == 4) {
		        	populate = BiomeTreeTypes.JUNGLE.getPopulatorObject();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Jungle Tree"));
		        }else if (tree == 5) {
		        	populate = BiomeTreeTypes.JUNGLE_BUSH.getPopulatorObject();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Jungle Bush Tree"));
		        }else if (tree == 6) {
		        	populate = BiomeTreeTypes.OAK.getPopulatorObject();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Oak Tree"));
		        }else if (tree == 7) {
		        	populate = BiomeTreeTypes.SAVANNA.getPopulatorObject();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Savanna Tree"));
		        }else if (tree == 8) {
		        	populate = BiomeTreeTypes.SWAMP.getPopulatorObject();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Swamp Tree"));
		        }else if (tree == 9) {
		        	populate = BiomeTreeTypes.TALL_TAIGA.getPopulatorObject();
		        	src.sendMessage(Text.of(TextColors.GOLD, "Attempting to spawn Taiga Tree"));
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
		return new String[] { "tree", "Tree" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Cheater;
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("Birch", 1);
		map.put("Spruce", 2);
		map.put("Canopy", 3);
		map.put("Jungle", 4);
		map.put("Jungle_Bush", 5);
		map.put("Oak", 6);
		map.put("Savanna", 7);
		map.put("Swamp", 8);
		map.put("Tall_Taiga", 9);
		
		return CommandSpec.builder()
				.description(Text.of(description))
				.permission(perm)
				.arguments(GenericArguments.onlyOne(GenericArguments.choices(Text.of("Tree Type"), map)))
				.executor(this).build();
	}
}
