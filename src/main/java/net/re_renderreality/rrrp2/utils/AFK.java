package net.re_renderreality.rrrp2.utils;

public class AFK
{
	public long lastMovementTime;
	public boolean AFK = false;
	public boolean messaged = false;
	public int foodLevel;

	/**
	 * @param lastMovementTime last time the player moved
	 * @note when player goes afk this is passed to constructor
	 */
	public AFK(long lastMovementTime)
	{
		this.lastMovementTime = lastMovementTime;
	}

	/**
	 * @return gets their food level
	 */
	public int getFood()
	{
		return this.foodLevel;
	}

	/**
	 * @param foodLevel food level to set the Afk object to
	 */
	public void setFood(int foodLevel)
	{
		this.foodLevel = foodLevel;
	}

	/**
	 * @return returns if afk is T or F
	 */
	public boolean getAFK()
	{
		return AFK;
	}

	/**
	 * @param AFK set if AFK is T or F
	 */
	public void setAFK(boolean AFK)
	{
		this.AFK = AFK;
	}

	/**
	 * @return return if the player was messaged or not
	 */
	public boolean getMessaged()
	{
		return messaged;
	}

	/**
	 * @param messaged sets if the player was messeged
	 */
	public void setMessaged(boolean messaged)
	{
		this.messaged = messaged;
	}

	/**
	 * @return gets the last movement time
	 */
	public long getLastMovementTime()
	{
		return lastMovementTime;
	}
}
