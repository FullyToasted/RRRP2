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
import net.re_renderreality.rrrp2.database.Registry;

public class InvseeCommand extends CommandExecutorBase {
	/**
	 * @NOTE does not work
	 */
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/invsee";
		description = "Allows you to see another players inventory";
		perm = "rrr.admin.invsee";
		useage = "/invsee <target>";
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
			.arguments(GenericArguments.optional(GenericArguments.player(Text.of("Player"))))
			.executor(this)
			.build();
	}

	
}
