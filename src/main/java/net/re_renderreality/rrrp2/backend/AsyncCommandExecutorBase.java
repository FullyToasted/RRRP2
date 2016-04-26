package net.re_renderreality.rrrp2.backend;

import net.re_renderreality.rrrp2.RRRP2;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

/**
 * Represents a command that can be run on an Async thread. 
 */
public abstract class AsyncCommandExecutorBase extends CommandExecutorBase {

    /**
     * The command to execute on an Async scheduler thread.
     *
     * <p>
     *     <strong>DO NOT USE NON-THREAD SAFE API CALLS WITHIN THIS METHOD</strong> unless you use a scheduler to put the
     *     calls back on the main thread.
     * </p>
     *
     * @param src The {@link CommandSource}
     * @param args The arguments. {@link CommandSource}
     * @throws CommandException 
     */
    public abstract void executeAsync(CommandSource src, CommandContext args);

    @Override
    public final CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Sponge.getScheduler().createAsyncExecutor(RRRP2.getRRRP2()).execute(() -> executeAsync(src, args));
        return CommandResult.success();
    }
}
