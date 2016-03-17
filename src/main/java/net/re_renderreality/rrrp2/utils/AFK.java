package net.re_renderreality.rrrp2.utils;

public class AFK
{
	public long lastMovementTime;
	public boolean AFK = false;
	public boolean messaged = false;
	public int foodLevel;

	public AFK(long lastMovementTime)
	{
		this.lastMovementTime = lastMovementTime;
	}

	public int getFood()
	{
		return this.foodLevel;
	}

	public void setFood(int foodLevel)
	{
		this.foodLevel = foodLevel;
	}

	public boolean getAFK()
	{
		return AFK;
	}

	public void setAFK(boolean AFK)
	{
		this.AFK = AFK;
	}

	public boolean getMessaged()
	{
		return messaged;
	}

	public void setMessaged(boolean messaged)
	{
		this.messaged = messaged;
	}

	public long getLastMovementTime()
	{
		return lastMovementTime;
	}
}
