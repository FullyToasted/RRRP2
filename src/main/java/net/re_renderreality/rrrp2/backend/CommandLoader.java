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
import net.re_renderreality.rrrp2.cmd.teleport.special.*;
import net.re_renderreality.rrrp2.cmd.teleport.warp.*;

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
        		new AFKCommand(),
                new AnnouncementsCommand(),
                new AnnouncementsAddCommand(),
                new AnnouncementsModifyCommand(),
                new AnnouncementsRemoveCommand(),
        		new BackCommand(),
        		new BanCommand(),
        		new BanManagerCommand(),
                new BigTreeCommand(),
                new BlockDBCommand(),
                new BroadcastCommand(),
                new CompassCommand(),
                new ConsoleCommand(),
                new ClearEntitiesCommand(),
                new DeleteHomeCommand(),
                new DeleteWarpCommand(),
                new DepthCommand(),
                new EnchantCommand(),
                new EntityDBCommand(),
                new FeedCommand(),
                new FreeCommand(),
                new FreezeCommand(),
                new FlyCommand(),
                new GetDimCommand(),
                new GetMailCommand(),
                new GetPosCommand(),
                new GetWorldCommand(),
                new GodCommand(),
                new HatCommand(),
                new HealCommand(),
                new HelpCommand(),
                new HelpOPCommand(),
                new HomeCommand(),
                new ItemDBCommand(),
                new JockeyCommand(),
                new KickCommand(),
                new ListCommand(),
                new ListEntitiesCommand(),
                new ListHomesCommand(),
                new ListWarpsCommand(),
                new MailCommand(),
                new ManageHelpOP(),
                new ManageHomesCommand(),
                new ManageMailCommand(),
                new ManageWarpsCommand(),
                new MoreCommand(),
                new MOTDCommand(),
                new NickCommand(),
                new OnePunchCommand(),
                new PardonCommand(),
                new PlayerCountCommand(),
                new RepairCommand(),
                new RulesAddCommand(),
                new RulesCommand(),
                new RulesModifyCommand(),
                new RulesRemoveCommand(),
                new SeenCommand(),
                new ServerStopCommand(),
                new SetHomeCommand(),
                new SetSpawnCommand(),
                new SetWarpCommand(),
                new SkullCommand(),
                new SmiteCommand(),
                new SpawnCommand(),
                new SpeedCommand(),
                new SudoCommand(),
                new SuicideCommand(),
                new SurroundCommand(),
                new TeleportCommand(),
                new TeleportAllCommand(),
                new TeleportHereCommand(),
                new TeleportPositionCommand(),
                new TeleportWorldCommand(),
                new TempBanCommand(),
                new TopCommand(),
                new TPAcceptCommand(),
                new TPCommand(),
                new TPDenyCommand(),
                new TPHereCommand(),
                new TPSCommand(),
                new TreeCommand(),
                new WarpCommand(),
                new WhoisCommand(),
                new WeatherCommand(),
                new WeatherLockCommand()
        );
    }

    /**
     * Registers the RRRP2 commands.
     */
    public static void registerCommands() {
        getCommands().forEach(cmd -> Sponge.getCommandManager().register(RRRP2.getRRRP2(), cmd.getSpec(), cmd.getAliases()));   		
    }    
}
