package net.re_renderreality.rrrp2.cmd.cheats;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.utils.SurroundedPlayer;

public class SurroundCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/surround";
		description = "Surrounds a player with the chosen block";
		perm = "rrr.cheat.surround";
		useage = "/surround <Player> (Block Type)";
		notes = "Use /free to free";
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
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		setLocalVariables();
		Optional<Player> player = ctx.<Player> getOne("Player");
		Optional<Integer> block = ctx.<Integer> getOne("Block");
		
		//surrounds the player in one of five block types
		if(src instanceof Player || src instanceof ConsoleSource) {
			if(player.isPresent()) {
				Player target = player.get();
				int id = Database.getID(target.getUniqueId().toString());
				int blockChoice = block.get();
				BlockType tBlock = null;
				String blockName = "";
				
				if(block.isPresent()) {
					if(blockChoice == 1) {
						tBlock = BlockTypes.DIRT;
						blockName = "Dirt";
					} else if (blockChoice == 2) {
						tBlock = BlockTypes.STONE;
						blockName = "Stone";
					} else if (blockChoice == 3) {
						tBlock = BlockTypes.STONEBRICK;
						blockName = "Stone Brick";
					} else if (blockChoice == 4) {
						tBlock = BlockTypes.OBSIDIAN;
						blockName = "Obsidian";
					} else if (blockChoice == 5) {
						tBlock = BlockTypes.BEDROCK;
						blockName = "Bedrock";
					}
				} else {
					tBlock = BlockTypes.DIRT;
				}
				
				Location<World> loc = target.getLocation();
				int x = loc.getBlockX();
		        int y = loc.getBlockY();
		        int z = loc.getBlockZ();
		        
		        SurroundedPlayer p = new SurroundedPlayer(target, x , y, z, tBlock);
		        
		        if(p.surround()) {
		        	RRRP2.surrounded.put(id, p);
		        	target.sendMessage(Text.of(TextColors.GOLD, "You have been encased in: ", TextColors.GRAY, blockName));
		        	src.sendMessage(Text.of(TextColors.GREEN, "Success!", TextColors.GOLD, "You have encased ", TextColors.GRAY, target.getName(), TextColors.GOLD, " in: ", TextColors.GRAY, blockName));
		            return CommandResult.success();
		        } 
		        return CommandResult.empty();
			}
			src.sendMessage(Text.of(TextColors.RED, "Proper Usage: /Surround <Player> (Block Type)"));
			return CommandResult.empty();
		}
		src.sendMessage(Text.of(TextColors.RED, "Only Players and the Console can execute this command!"));
		return CommandResult.empty();
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "Surround", "surround", "trap", "Trap" };
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
		map.put("Dirt", 1);
		map.put("Stone", 2);
		map.put("StoneBrick", 3);
		map.put("Obsidian", 4);
		map.put("Bedrock", 5);
		return CommandSpec.builder()
				.description(Text.of(description))
				.permission(perm)
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("Player"))),
						GenericArguments.optional(GenericArguments.choices(Text.of("Block"), map)))
				.executor(this).build();
	}
}