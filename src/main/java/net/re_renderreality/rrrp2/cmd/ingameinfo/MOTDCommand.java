package net.re_renderreality.rrrp2.cmd.ingameinfo;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigMOTD;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
public class MOTDCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/motd";
		description = "Displays the MOTD";
		perm = "rrr.general.mod";
		useage = "/motd";
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
	
	/**
	 * Display the MOTD
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		String uniquePlayerCount = ReadConfigMOTD.getMOTD();
		uniquePlayerCount = uniquePlayerCount.replaceAll("%player", src.getName());
		Text newMessage = TextSerializers.formattingCode('&').deserialize(uniquePlayerCount);
		src.sendMessage(newMessage);
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "MOTD", "motd", "Motd" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.General;
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
