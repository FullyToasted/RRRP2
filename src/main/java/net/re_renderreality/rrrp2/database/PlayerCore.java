package net.re_renderreality.rrrp2.database;

import java.util.HashMap;
import java.util.List;

import org.spongepowered.api.text.Text;

public class PlayerCore {
	private String uuid;
	private String name;
	private String nick;
	private String channel;
	private double money;
	private double god;
	private double fly;
	private double tptoggle;
	private double invisible;
	private double onlinetime;
	private String mails;
	private String lastlocation;
	private String lastdeath;
	private String firstseen;
	private String lastseen;
	
	//private CoreSelection selection;
	private double lastaction;
	private boolean afk;
	private HashMap<String, HomeCore> homes;
	private String reply;
	private HashMap<String, Double> tpa;
	private HashMap<String, Double> tpahere;
	private HashMap<String, String> powertools;
	private HashMap<Integer, List<Text>> pages;
	private Text page_title;
	private Text page_header;
	
	public PlayerCore(String uuid, 
					  String name, 
					  String nick, 
					  String channel, 
					  double money, 
					  double god, 
					  double fly, 
					  double tptoggle, 
					  double invisible, 
					  double onlinetime, 
					  String mails, 
					  String lastlocation, 
					  String lastdeath, 
					  String firstseen, 
					  String lastseen) {
		
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
		
		//selection = new CoreSelection();
		lastaction = 0;
		afk = false;
		homes = new HashMap<String, HomeCore>();
		reply = "";
		tpa = new HashMap<String, Double>();
		tpahere = new HashMap<String, Double>();
		powertools = new HashMap<String, String>();
		pages = new HashMap<Integer, List<Text>>();
		
	}
	
	public void insert() {
		Database.queue("INSERT INTO players VALUES ('" + uuid + "', '" + name + "', '" + nick + "', '" + channel + "', " + money + ", " + god + ", " + fly + ", " + tptoggle + ", " + invisible + ", " + onlinetime + ", '" + mails + "', '" + lastlocation + "', '" + lastdeath + "', " + firstseen + ", " + lastseen + ")");
		Database.addPlayer(uuid, this);
		Database.addUUID(name, uuid);
	}
	
	public void update() {
		Database.queue("UPDATE players SET name = '" + name + "', nick = '" + nick + "', channel = '" + channel + "', money = " + money + ", god = " + god + ", fly = " + fly + ", tptoggle = " + tptoggle + ", invisible = " + invisible + ", onlinetime = " + onlinetime + ", mails = '" + mails + "', lastlocation = '" + lastlocation + "', lastdeath = '" + lastdeath + "', firstseen = " + firstseen + ", lastseen = " + lastseen + " WHERE uuid = '" + uuid + "'");
		Database.removePlayer(uuid);
		Database.removeUUID(name);
		Database.addPlayer(uuid, this);
		Database.addUUID(name, uuid);
	}
	
	public void delete() {
		Database.queue("DELETE FROM players WHERE uuid = '" + uuid + "'");
		Database.removePlayer(uuid);
		Database.removeUUID(name);
	}

	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setName(String name) { this.name = name; }
	public void setNick(String nick) { this.nick = nick; }
	public void setChannel(String channel) { this.channel = channel; }
	public void setMoney(double money) { this.money = money; }
	public void setGod(double god) { this.god = god; }
	public void setFly(double fly) { this.fly = fly; }
	public void setTPToggle(double tptoggle) { this.tptoggle = tptoggle; }
	public void setInvisible(double invisible) { this.invisible = invisible; }
	public void setOnlinetime(double onlinetime) { this.onlinetime = onlinetime; }
	public void setMails(String mails) { this.mails = mails; }
	public void setLastlocation(String lastlocation) { this.lastlocation = lastlocation; }
	public void setLastdeath(String lastdeath) { this.lastdeath = lastdeath; }
	public void setFirstseen(String firstseen) { this.firstseen = firstseen; }
	public void setLastseen(String lastseen) { this.lastseen = lastseen; }

	//public void setSelection(CoreSelection selection) { this.selection = selection; }
	public void setLastaction(double lastaction) { this.lastaction = lastaction; }
	public void setAFK(boolean afk) { this.afk = afk; }
	public void setHome(String name, HomeCore home) { if(homes == null) homes = new HashMap<String, HomeCore>(); homes.put(name, home); }
	public void setHomes(HashMap<String, HomeCore> homes) { if(homes == null) homes = new HashMap<String, HomeCore>(); this.homes = homes; }
	public void setReply(String reply) { this.reply = reply; }
	public void setTPA(HashMap<String, Double> tpa) { this.tpa = tpa; }
	public void setTPAHere(HashMap<String, Double> tpahere) { this.tpahere = tpahere; }
	public void setPowertools(HashMap<String, String> powertools) { this.powertools = powertools; }
	public void setPages(HashMap<Integer, List<Text>> pages) { this.pages = pages; }
	public void setPageTitle(Text page_title) { this.page_title = page_title; }
	public void setPageHeader(Text page_header) { this.page_header = page_header; }
	
	public String getUUID() { return uuid; }
	public String getName() { return name; }
	public String getNick() { return nick; }
	public String getChannel() { return channel; }
	public double getMoney() { return Math.round(money * 100) / 100; }
	public double getGod() { return god; }
	public double getFly() { return fly; }
	public double getTPToggle() { return tptoggle; }
	public double getInvisible() { return invisible; }
	public double getOnlinetime() { return onlinetime; }
	public String getMails() { return mails; }
	public String getLastlocation() { return lastlocation; }
	public String getLastdeath() { return lastdeath; }
	public String getFirstseen() { return firstseen; }
	public String getLastseen() { return lastseen; }
	
	//public CoreSelection getSelection() { return selection; }
	public double getLastaction() { return lastaction; }
	public boolean getAFK() { return afk; }
	public HomeCore getHome(String name) { if(homes == null) homes = new HashMap<String, HomeCore>(); return homes.containsKey(name) ? homes.get(name) : null; }
	public HashMap<String, HomeCore> getHomes() { if(homes == null) homes = new HashMap<String, HomeCore>(); return homes; }
	public String getReply() { return reply; }
	public HashMap<String, Double> getTPA() { return tpa; }
	public HashMap<String, Double> getTPAHere() { return tpahere; }
	public HashMap<Integer, List<Text>> getPages() { return pages; }
	public HashMap<String, String> getPowertools() { return powertools; }
	public Text getPageTitle() { return page_title; }
	public Text getPageHeader() { return page_header; }
	
	public void addMoney(double amount) {
		amount *= 100; amount = Math.round(amount); amount /= 100;
		this.money += amount;
	}
	public void removeMoney(double amount) {
		amount *= 100; amount = Math.round(amount); amount /= 100;
		this.money -= amount;
	}
}
