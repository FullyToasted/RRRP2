package net.re_renderreality.rrrp2.database;

public class PlayerCore {
	private int ID;
	private String uuid;
	private String name;
	private String nick;
	private String channel;
	private double money;
	private boolean god;
	private boolean fly;
	private boolean tptoggle;
	private boolean invisible;
	private double onlinetime;
	private String mails;
	private String lastlocation;
	private String lastdeath;
	private String firstseen;
	private String lastseen;
	
	public PlayerCore(int ID,
					  String uuid, 
					  String name, 
					  String nick, 
					  String channel, 
					  double money, 
					  boolean god, 
					  boolean fly, 
					  boolean tptoggle, 
					  boolean invisible, 
					  double onlinetime, 
					  String mails, 
					  String lastlocation, 
					  String lastdeath, 
					  String firstseen, 
					  String lastseen) {
		
		this.ID = ID;
		this.uuid = uuid;
		this.name = name;
		this.nick = nick;
		this.channel = channel;
		this.money = money;
		this.god = god;
		this.fly = fly;
		this.tptoggle = tptoggle;
		this.invisible = invisible;
		this.onlinetime = onlinetime;
		this.mails = mails;
		this.lastlocation = lastlocation;
		this.lastdeath = lastdeath;
		this.firstseen = firstseen;
		this.lastseen = lastseen;		
	}
	
	public void insert() {
		Database.queue("INSERT INTO players VALUES (" + ID + ", '" + uuid + "', '" + name + "', '" + nick + "', '" + channel + "', " + money + ", " + god + ", " + fly + ", " + tptoggle + ", " + invisible + ", " + onlinetime + ", '" + mails + "', '" + lastlocation + "', '" + lastdeath + "', " + firstseen + ", " + lastseen + ")");
		Database.addPlayer(ID, this);
		Database.addUUID(ID, uuid);
	}
	
	public void update() {
		Database.queue("UPDATE players SET name = '" + name + "', nick = '" + nick + "', channel = '" + channel + "', money = " + money + ", god = " + god + ", fly = " + fly + ", tptoggle = " + tptoggle + ", invisible = " + invisible + ", onlinetime = " + onlinetime + ", mails = '" + mails + "', lastlocation = '" + lastlocation + "', lastdeath = '" + lastdeath + "', firstseen = " + firstseen + ", lastseen = " + lastseen + " WHERE uuid = '" + uuid + "'");
		Database.removePlayer(uuid);
		Database.removeUUID(ID);
		Database.addPlayer(ID, this);
		Database.addUUID(ID, uuid);
	}
	
	public void delete() {
		Database.queue("DELETE FROM players WHERE uuid = '" + uuid + "'");
		Database.removePlayer(uuid);
		Database.removeUUID(ID);
	}

	public void setID(int ID) {this.ID = ID; }
	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setName(String name) { this.name = name; }
	public void setNick(String nick) { this.nick = nick; }
	public void setChannel(String channel) { this.channel = channel; }
	public void setMoney(double money) { this.money = money; }
	public void setGod(boolean god) { this.god = god; }
	public void setFly(boolean fly) { this.fly = fly; }
	public void setTPToggle(boolean tptoggle) { this.tptoggle = tptoggle; }
	public void setInvisible(boolean invisible) { this.invisible = invisible; }
	public void setOnlinetime(double onlinetime) { this.onlinetime = onlinetime; }
	public void setMails(String mails) { this.mails = mails; }
	public void setLastlocation(String lastlocation) { this.lastlocation = lastlocation; }
	public void setLastdeath(String lastdeath) { this.lastdeath = lastdeath; }
	public void setFirstseen(String firstseen) { this.firstseen = firstseen; }
	public void setLastseen(String lastseen) { this.lastseen = lastseen; }
	
	public int getID() { return ID; }
	public String getUUID() { return uuid; }
	public String getName() { return name; }
	public String getNick() { return nick; }
	public String getChannel() { return channel; }
	public double getMoney() { return Math.round(money * 100) / 100; }
	public boolean getGod() { return god; }
	public boolean getFly() { return fly; }
	public boolean getTPToggle() { return tptoggle; }
	public boolean getInvisible() { return invisible; }
	public double getOnlinetime() { return onlinetime; }
	public String getMails() { return mails; }
	public String getLastlocation() { return lastlocation; }
	public String getLastdeath() { return lastdeath; }
	public String getFirstseen() { return firstseen; }
	public String getLastseen() { return lastseen; }
	
	public void addMoney(double amount) {
		amount *= 100; amount = Math.round(amount); amount /= 100;
		this.money += amount;
	}
	public void removeMoney(double amount) {
		amount *= 100; amount = Math.round(amount); amount /= 100;
		this.money -= amount;
	}
}
