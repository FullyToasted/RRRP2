package net.re_renderreality.rrrp2.backend;

import com.google.common.collect.Sets;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.cmd.administration.*;
import net.re_renderreality.rrrp2.cmd.cheats.*;
import net.re_renderreality.rrrp2.cmd.general.*;
import net.re_renderreality.rrrp2.cmd.ingameinfo.*;
import net.re_renderreality.rrrp2.cmd.spawn.*;

import org.spongepowered.api.Sponge;

import java.util.Set;

/**
 * Utility class that handles loading and registering commands.
 */
public final class CommandLoader {

    /* (non-Javadoc)
     *
     * No need to instantiate!
     */
    private CommandLoader() {}

    /**
     * @return returns A set of all the command Objects
     */
    private static Set<? extends CommandExecutorBase> getCommands() {
        return Sets.newHashSet(
                new AnnouncementsCommand(),
                new AnnouncementsAddCommand(),
                new AnnouncementsModifyCommand(),
                new AnnouncementsRemoveCommand(),
        		new BackCommand(),
        		new BanCommand(),
        		new BanManagerCommand(),
                new BigTreeCommand(),
                new ClearEntitiesCommand(),
                new DepthCommand(),
                new FreeCommand(),
                new FlyCommand(),
                new GetDimCommand(),
                new GetMailCommand(),
                new GetPosCommand(),
                new GetWorldCommand(),
                new GodCommand(),
                new HealCommand(),
                new HelpCommand(),
                new HelpOPCommand(),
                new ListEntitiesCommand(),
                new MailCommand(),
                new ManageHelpOP(),
                new ManageMailCommand(),
                new MOTDCommand(),
                new NickCommand(),
                new PardonCommand(),
                new PlayerCountCommand(),
                new RulesAddCommand(),
                new RulesCommand(),
                new RulesModifyCommand(),
                new RulesRemoveCommand(),
                new SeenCommand(),
                new SetSpawnCommand(),
                new SmiteCommand(),
                new SpawnCommand(),
                new SuicideCommand(),
                new SurroundCommand(),
                new TempBanCommand(),
                new TPSCommand(),
                new TreeCommand(),
                new WhoisCommand()
        );
    }

    /**
     * Registers the RRRP2 commands.
     */
    public static void registerCommands() {
        // TODO: Put module checks here in a stream.
        // getCommands().stream().filter(c -> c.getAssociatedModules().length == 0 && checkModules).forEach(CommandLoader::registerCommand);
        getCommands().forEach(cmd -> Sponge.getCommandManager().register(RRRP2.getRRRP2(), cmd.getSpec(), cmd.getAliases()));   		
    }
    
}
