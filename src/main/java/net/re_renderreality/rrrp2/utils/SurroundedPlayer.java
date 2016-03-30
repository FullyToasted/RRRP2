package net.re_renderreality.rrrp2.utils;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

public class SurroundedPlayer {
	private World world;
	private BlockType block;
	private boolean surrounded;
	
	private BlockType bbelow;
	private BlockType babove;
	private BlockType bleft1;
	private BlockType bleft2;
	private BlockType bright1;
	private BlockType bright2;
	private BlockType bfront1;
	private BlockType bfront2;
	private BlockType bback1;
	private BlockType bback2;	

	private Vector3i below;
	private Vector3i above;
	private Vector3i left1;
	private Vector3i left2;
	private Vector3i right1;
	private Vector3i right2;
	private Vector3i front1;
	private Vector3i front2;
	private Vector3i back1;
	private Vector3i back2;	
	
	public SurroundedPlayer(Player target, int x, int y, int z, BlockType b) {
		this.world = target.getWorld();
		this.block = b;
		
		below = new Vector3i(x, y - 1, z);
		above = new Vector3i(x, y + 2, z);
		left1 = new Vector3i(x-1, y, z);
		left2 = new Vector3i(x-1, y+1, z);
		right1 = new Vector3i(x+1, y, z);
		right2 = new Vector3i(x+1, y+1, z);
		front1 = new Vector3i(x, y, z+1);
		front2 = new Vector3i(x, y+1, z+1);
		back1 = new Vector3i(x, y, z-1);
		back2 = new Vector3i(x, y+1, z-1);
		
	}
	
	public boolean surround() {
		BlockState blockBelow = world.containsBlock(below) ? world.getBlock(below) : null;
	    if (blockBelow != null) {
	    	bbelow = world.getBlock(below).getType(); 
	        world.setBlock(below, BlockState.builder().blockType(block).build());
	    }
	    BlockState blockAbove = world.containsBlock(above) ? world.getBlock(above) : null;
	    if (blockAbove != null) {
	    	babove = world.getBlock(above).getType();
	        world.setBlock(above, BlockState.builder().blockType(block).build());
	    }
	    BlockState blockLeft1 = world.containsBlock(left1) ? world.getBlock(left1) : null;
	    if (blockLeft1 != null) {
	    	bleft1 = world.getBlock(left1).getType();
	        world.setBlock(left1, BlockState.builder().blockType(block).build());
	    }
	    BlockState blockLeft2 = world.containsBlock(left2) ? world.getBlock(left2) : null;
	    if (blockLeft2 != null) {
	    	bleft2 = world.getBlock(left2).getType();
	        world.setBlock(left2, BlockState.builder().blockType(block).build());
	    }
	    BlockState blockRight1 = world.containsBlock(right1) ? world.getBlock(right1) : null;
	    if (blockRight1 != null) {
	    	bright1 = world.getBlock(right1).getType();
	        world.setBlock(right1, BlockState.builder().blockType(block).build());
	    }
	    BlockState blockRight2 = world.containsBlock(right2) ? world.getBlock(right2) : null;
	    if (blockRight2 != null) {
	    	bright2 = world.getBlock(right2).getType();
	        world.setBlock(right2, BlockState.builder().blockType(block).build());
	    }
	    BlockState blockFront1 = world.containsBlock(front1) ? world.getBlock(front1) : null;
	    if (blockFront1 != null) {
	    	bfront1 = world.getBlock(front1).getType();
	        world.setBlock(front1, BlockState.builder().blockType(block).build());
	    }
	    BlockState blockFront2 = world.containsBlock(front2) ? world.getBlock(front2) : null;
	    if (blockFront2 != null) {
	    	bfront2 = world.getBlock(front2).getType();
	        world.setBlock(front2, BlockState.builder().blockType(block).build());
	    }
	    BlockState blockBack1 = world.containsBlock(back1) ? world.getBlock(back1) : null;
	    if (blockBack1 != null) {
	    	bback1 = world.getBlock(back1).getType();
	        world.setBlock(back1, BlockState.builder().blockType(block).build());
	    }
	    BlockState blockBack2 = world.containsBlock(back2) ? world.getBlock(back2) : null;
	    if (blockBack2 != null) {
	    	bback2 = world.getBlock(back2).getType();
	        world.setBlock(back2, BlockState.builder().blockType(block).build());
	    }
	    this.surrounded = true;
	    return true;
	}
	
	public boolean free() {
		BlockState blockBelow = world.containsBlock(below) ? world.getBlock(below) : null;
	    if (blockBelow != null) {
	        world.setBlock(below, BlockState.builder().blockType(bbelow).build());
	    }
	    BlockState blockAbove = world.containsBlock(above) ? world.getBlock(above) : null;
	    if (blockAbove != null) {
	        world.setBlock(above, BlockState.builder().blockType(babove).build());
	    }
	    BlockState blockLeft1 = world.containsBlock(left1) ? world.getBlock(left1) : null;
	    if (blockLeft1 != null) {
	        world.setBlock(left1, BlockState.builder().blockType(bleft1).build());
	    }
	    BlockState blockLeft2 = world.containsBlock(left2) ? world.getBlock(left2) : null;
	    if (blockLeft2 != null) {
	        world.setBlock(left2, BlockState.builder().blockType(bleft2).build());
	    }
	    BlockState blockRight1 = world.containsBlock(right1) ? world.getBlock(right1) : null;
	    if (blockRight1 != null) {
	        world.setBlock(right1, BlockState.builder().blockType(bright1).build());
	    }
	    BlockState blockRight2 = world.containsBlock(right2) ? world.getBlock(right2) : null;
	    if (blockRight2 != null) {
	        world.setBlock(right2, BlockState.builder().blockType(bright2).build());
	    }
	    BlockState blockFront1 = world.containsBlock(front1) ? world.getBlock(front1) : null;
	    if (blockFront1 != null) {
	        world.setBlock(front1, BlockState.builder().blockType(bfront1).build());
	    }
	    BlockState blockFront2 = world.containsBlock(front2) ? world.getBlock(front2) : null;
	    if (blockFront2 != null) {
	        world.setBlock(front2, BlockState.builder().blockType(bfront2).build());
	    }
	    BlockState blockBack1 = world.containsBlock(back1) ? world.getBlock(back1) : null;
	    if (blockBack1 != null) {
	        world.setBlock(back1, BlockState.builder().blockType(bback1).build());
	    }
	    BlockState blockBack2 = world.containsBlock(back2) ? world.getBlock(back2) : null;
	    if (blockBack2 != null) {
	        world.setBlock(back2, BlockState.builder().blockType(bback2).build());
	    }
	    return true;
	}
	
	public boolean getSurrounded() {
		return this.surrounded;
	}
}
