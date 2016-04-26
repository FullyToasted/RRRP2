package net.re_renderreality.rrrp2.cmd.administration;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.text.Text;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

public class InvseeCommand extends CommandExecutorBase {
	/**
	 * @NOTE does not work
	 */
	@Override
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		Optional<Player> player = ctx.<Player> getOne("Player");
		
		if(src instanceof Player) {
			Player source = (Player) src;
			if(player.isPresent()) {
				Player target = player.get();
				Inventory inv = target.getInventory();
				source.openInventory(inv, Cause.of(NamedCause.source(src)));
				return CommandResult.success();
			}
			
		}
		return null;
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "InvSee", "Invsee", "invSee", "invsee"};
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("View the target's inventory"))
			.permission("rrr.admin.invsee")
			.arguments(GenericArguments.optional(GenericArguments.player(Text.of("Player"))))
			.executor(this)
			.build();
	}

	
}
