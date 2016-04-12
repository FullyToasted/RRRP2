package net.re_renderreality.rrrp2.backend;

import com.google.common.collect.Sets;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.cmd.administration.*;
import net.re_renderreality.rrrp2.cmd.cheats.*;
import net.re_renderreality.rrrp2.cmd.general.*;
import net.re_renderreality.rrrp2.cmd.home.*;
import net.re_renderreality.rrrp2.cmd.ingameinfo.*;
import net.re_renderreality.rrrp2.cmd.spawn.*;
import net.re_renderreality.rrrp2.cmd.teleport.*;
import net.re_renderreality.rrrp2.cmd.teleport.admintp.*;

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
                new DeleteHomeCommand(),
                new DepthCommand(),
                new FreeCommand(),
                new FreezeCommand(),
                new FlyCommand(),
                new GetDimCommand(),
                new GetMailCommand(),
                new GetPosCommand(),
                new GetWorldCommand(),
                new GodCommand(),
                new HealCommand(),
                new HelpCommand(),
                new HelpOPCommand(),
                new HomeCommand(),
                new KickCommand(),
                new ListEntitiesCommand(),
                new ListHomesCommand(),
                new MailCommand(),
                new ManageHelpOP(),
                new ManageHomesCommand(),
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
                new SetHomeCommand(),
                new SetSpawnCommand(),
                new SmiteCommand(),
                new SpawnCommand(),
                new SuicideCommand(),
                new SurroundCommand(),
                new TeleportCommand(),
                new TeleportAllCommand(),
                new TeleportHereCommand(),
                new TeleportPositionCommand(),
                new TeleportWorldCommand(),
                new TempBanCommand(),
                new TPAcceptCommand(),
                new TPCommand(),
                new TPDenyCommand(),
                new TPHereCommand(),
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
