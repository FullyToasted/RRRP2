package net.re_renderreality.rrrp2.cmd.cheats;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

public class SuicideCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/suicide";
		description = "It tickles";
		perm = "rrr.cheat.suicide";
		useage = "/suicide";
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
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		setLocalVariables();
		if(src instanceof Player) {
			Player player = (Player) src;
			
			//offers player 0 health and kills them
			MutableBoundedValue<Double> health = player.getValue(Keys.HEALTH).get();
			health.set(health.getMinValue()); 
			player.offer(health);
			Registry.getServer().getBroadcastChannel().send(Text.of(src.getName() + " committed suicide!"));
			
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "suicide", "Suicide" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Cheater;
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
