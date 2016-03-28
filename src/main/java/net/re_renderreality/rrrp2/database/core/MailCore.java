package net.re_renderreality.rrrp2.database.core;

import org.slf4j.Logger;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.utils.Utilities;

public class MailCore {
	private int recepientID;
	private String recepientName;
	private int mailID;
	private int senderID;
	private String senderName;
	private String sentTime;
	private String message;
	private boolean read;
	
	//Database Layout <RecepientID> <RecepientName> <MailID> <senderID> <SenderName> <Sent Date/Time> <Msg> <Read> 
	public MailCore(int recepientID, String recepientName, int mailID, int senderID, String senderName, String sentTime, String message, boolean read) {
		this.recepientID = recepientID;
		this.recepientName = recepientName;
		this.mailID = mailID;
		this.senderID = senderID;
		this.senderName = senderName;
		this.sentTime = sentTime;
		this.message = message;
		this.read = read;
	}
	
	public MailCore() {
		;
	}
	
	public void setReadUpdate(boolean read) { 
		this.read = read;
		String command = "UPDATE mail SET Read = " + Utilities.boolToInt(this.read) + " WHERE MailID = "+ this.mailID + ";";
		Database.execute(command);
	}
	
	public void insert() {
		String command = "INSERT INTO mail VALUES (" + recepientID + ", '" + recepientName + "', " + mailID + ", " + senderID + ", '" + senderName + "', '" + message + "', '" + sentTime + "', " + Utilities.boolToInt(read) + ");";
		Database.execute(command);

	}
	
	/**
	 * update mail in the database
	 * @note update on login
	 */
	public void update() {
		Logger l = RRRP2.getRRRP2().getLogger();
		String command = "UPDATE mail SET recepientID = " + recepientID + ", recepientName = '" + recepientName + "', mailID = " + mailID 
															 + ", senderID = " + senderID + ", senderName = '" + senderName + "', sentTime = '" 
															 + sentTime + "', message = '" + message + "', read = " + Utilities.boolToInt(read) + ";";
		l.info(command);
		Database.execute(command);

	}
	
	/**
	 * delete player from database
	 */
	public boolean delete(PlayerCore players) {
		int id = players.getID();
		if( id == this.recepientID) {
			Database.execute("UPDATE mail SET recepientID = 0 WHERE MailID = " + this.mailID + ";");
			return true;
		} else {
			return false;
		}
	}
	
	//Setter
	public void setRecepientID(int ID) {
		this.recepientID = ID;
	}
	
	public void setRecepientName(String name) {
		this.recepientName = name;
	}
	
	public void setMailID(int ID) {
		this.mailID = ID;
	}
	
	public void setSenderID(int ID) {
		this.senderID = ID;
	}
	
	public void setSenderName(String name) {
		this.senderName = name;
	}
	
	public void setSentTime(String time) {
		this.sentTime = time;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setRead(boolean read) {
		this.read = read;
	}
	
	//Getters
	public int getRecepientID() {
		return recepientID;
	}
	
	public String getRecepientName() {
		return recepientName;
	}
	
	public int getMailID() {
		return mailID;
	}
	
	public int getSenderID() {
		return senderID;
	}
	
	public String getSenderName() {
		return senderName;
	}
	
	public String getSentTime() {
		return sentTime;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean getRead() {
		return read;
	}
}
