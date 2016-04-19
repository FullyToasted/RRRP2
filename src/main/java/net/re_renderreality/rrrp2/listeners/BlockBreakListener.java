package net.re_renderreality.rrrp2.listeners;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class BlockBreakListener {
	@Listener
	public void onBlockBreak(ChangeBlockEvent.Break event, @First Player player) {
		if(!player.hasPermission("rrr.cheat.breakbedrock")) {
			for (Transaction<BlockSnapshot> transaction : event.getTransactions()) {
				if(transaction.getOriginal().getState().getType().equals(BlockTypes.BEDROCK)) {
					player.sendMessage(Text.of(TextColors.RED, "Error! You do not have permission to break bedrock!!"));
					event.setCancelled(true);
				}
			}
		}
	}
}
