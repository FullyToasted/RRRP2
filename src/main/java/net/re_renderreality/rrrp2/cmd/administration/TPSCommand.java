package net.re_renderreality.rrrp2.cmd.administration;

import javax.annotation.Nonnull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import com.google.common.collect.Iterables;

import net.re_renderreality.rrrp2.backend.AsyncCommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

public class TPSCommand extends AsyncCommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/tps";
		description = "Find the sever's tickrate";
		perm = "rrr.admin.tps";
		useage = "/tps";
		notes = "This is not the most acurate method in the world";
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
	
	/**
	 * Display the sever TPS
	 */
	public void executeAsync(CommandSource src, CommandContext ctx)
	{
		//gets server tps
		double tps = Sponge.getServer().getTicksPerSecond();
		src.sendMessage(Text.of(TextColors.GOLD, "Current TPS: ", TextColors.GRAY, tps));
		src.sendMessage(Text.of(TextColors.GOLD, "World Info:"));

		//gets world stats
		for (World world : Sponge.getServer().getWorlds()) {
			int numOfEntities = world.getEntities().size();
			int loadedChunks = Iterables.size(world.getLoadedChunks());
			src.sendMessage(Text.of());
			src.sendMessage(Text.of(TextColors.GREEN, "World: ", world.getName()));
			src.sendMessage(Text.of(TextColors.GOLD, "Loaded Chunks: ", TextColors.GRAY, loadedChunks));
			src.sendMessage(Text.of(TextColors.GOLD, "Entities: ", TextColors.GRAY, numOfEntities));
			
		}
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "tps", "TPS", "Tps" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Admin;
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of(description))
			.permission(perm)
			.executor(this)
			.build();
	}
}
