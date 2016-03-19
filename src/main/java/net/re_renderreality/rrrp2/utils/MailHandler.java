package net.re_renderreality.rrrp2.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import net.re_renderreality.rrrp2.RRRP2;

public class MailHandler {
	private static Gson gson = new GsonBuilder().create();
	
	/**
	 * @return an arrayList of all mail
	 * @TODO update to database
	 */
	public static ArrayList<Mail> getMail()
	{
		String json;

		try
		{
			json = readFile("Mail.json", StandardCharsets.UTF_8);
		}
		catch (Exception e)
		{
			return new ArrayList<>();
		}

		if (json != null && json.length() > 0)
		{
			return new ArrayList<>(Arrays.asList(gson.fromJson(json, Mail[].class)));
		}
		else
		{
			return new ArrayList<>();
		}
	}

	/**
	 * @TODO: Remove
	 */
	static String readFile(String path, Charset encoding) throws IOException
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	/**
	 * @param senderID ID of the mail sender
	 * @param recipientID ID of the mail Recipient
	 * @param message Contents of the mail object
	 * 
	 * @TODO update to database
	 */
	public static void addMail(int senderID, int recipientID, String message)
	{
		if (getMail() != null)
		{
			ArrayList<Mail> currentMail = getMail();

			currentMail.add(new Mail(recipientID, senderID, message));
			String json;

			try
			{
				json = gson.toJson(currentMail);

				// Assume default encoding.
				FileWriter fileWriter = new FileWriter("Mail.json");

				// Always wrap FileWriter in BufferedWriter.
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				bufferedWriter.write(json);
				bufferedWriter.flush();

				// Always close files.
				bufferedWriter.close();
			}
			catch (Exception ex)
			{
				RRRP2.getRRRP2().getLogger().error("Could not save JSON file!");
			}
		}
	}
	
	/**
	 * @param mail Mail object to be removed
	 * 
	 * @TODO database implementation
	 */
	public static void removeMail(Mail mail) {
		if (getMail() != null)
		{
			ArrayList<Mail> currentMail = getMail();
				Mail mailToRemove = null;
				for (Mail m : currentMail)
			{
				if ((m.getRecipientID() == mail.getRecipientID()) && (m.getSenderID() == mail.getSenderID()) && (m.getMessage().equals(mail.getMessage())))
				{
					mailToRemove = m;
					break;
				}
			}
				if (mailToRemove != null)
			{
				currentMail.remove(mailToRemove);
			}
				String json;
				try
			{
				json = gson.toJson(currentMail);
					// Assume default encoding.
				FileWriter fileWriter = new FileWriter("Mail.json");
					// Always wrap FileWriter in BufferedWriter.
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(json);
				bufferedWriter.flush();
					// Always close files.
				bufferedWriter.close();
			}
			catch (Exception ex)
			{
				RRRP2.getRRRP2().getLogger().error("Could not save JSON file!");
			}
		}
	}
}

