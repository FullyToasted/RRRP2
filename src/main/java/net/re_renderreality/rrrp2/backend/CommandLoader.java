package net.re_renderreality.rrrp2.backend;

import com.google.common.collect.Sets;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.cmd.*;

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
            new HealCommand(),
            new ClearEntitiesCommand(),
            new HelpCommand(),
            new ListEntitiesCommand(),
            new WhoisCommand(),
            new TPSCommand(),
            new FlyCommand(),
            new PlayerCountCommand(),
            new GodCommand(),
            new NickCommand(),
            new BackCommand()
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
