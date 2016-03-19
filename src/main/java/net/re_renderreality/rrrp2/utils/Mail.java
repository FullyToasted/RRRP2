package net.re_renderreality.rrrp2.utils;

public class Mail
{
	public int recipientID;
	public int senderID;
	public String message;

	/**
	 * @param recipientID ID of the recipient
	 * @param senderID ID of the sender
	 * @param message Contents of the message
	 */ 
	public Mail(int recipientID, int senderID, String message)
	{
		this.recipientID = recipientID;
		this.senderID = senderID;
		this.message = message;
	}

	/**
	 * @param recipientID the ID number of the recipient
	 */
	public void setRecipientName(int recipientID)
	{
		this.recipientID = recipientID;
	}

	/**
	 * @param senderID the ID number of the sender
	 */
	public void setSenderID(int senderID)
	{
		this.senderID = senderID;
	}

	/**
	 * @param message the message to set the mail to
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return the ID of the recipient
	 */
	public int getRecipientID()
	{
		return recipientID;
	}

	/**
	 * @return the ID of the sender
	 */
	public int getSenderID()
	{
		return senderID;
	}

	/**
	 * @return the Message of the mail
	 */
	public String getMessage()
	{
		return message;
	}
}
