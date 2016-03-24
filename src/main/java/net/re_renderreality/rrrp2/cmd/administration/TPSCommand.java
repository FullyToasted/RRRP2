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

public class TPSCommand extends AsyncCommandExecutorBase
{
	/**
	 * Explanation of what command does and if complicated how to do it
	 */
	public void executeAsync(CommandSource src, CommandContext ctx)
	{
		double tps = Sponge.getServer().getTicksPerSecond();
		src.sendMessage(Text.of(TextColors.GOLD, "Current TPS: ", TextColors.GRAY, tps));
		src.sendMessage(Text.of(TextColors.GOLD, "World Info:"));

		for (World world : Sponge.getServer().getWorlds())
		{
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
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Displays the server TPS and other useful information"))
			.permission("rrrp2.admin.tps")
			.executor(this)
			.build();
	}
}
