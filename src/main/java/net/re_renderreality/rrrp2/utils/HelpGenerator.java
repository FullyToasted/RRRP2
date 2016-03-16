package net.re_renderreality.rrrp2.utils;

import java.util.ArrayList;

import org.spongepowered.api.text.Text;

public class HelpGenerator {
	public static Iterable<Text> admin;
	public static Iterable<Text> cheat;
	public static Iterable<Text> general;
	public static Iterable<Text> teleport;
	public static Iterable<Text> various;
	public static Iterable<Text> all;
	
	private static ArrayList<Text> admin() {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of("        Admin Commands"));
		array.add(Text.of("------------------------------------"));
		array.add(Text.of("ClearEntities: Clears all the entities of the specified type"));
		array.add(Text.of("ListEntities: List all entities currently loaded"));
		array.add(Text.of("whois: Gives all known information about player"));
		array.add(Text.of(" "));
		return array;
	}
	
	private static ArrayList<Text> cheat() {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of("       Cheater Commands =)"));
		array.add(Text.of("------------------------------------"));
		array.add(Text.of("Heal: Heal Command"));
		
		array.add(Text.of(" "));
		return array;
	}
	
	private static ArrayList<Text> general() {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of("         General Commands"));
		array.add(Text.of("------------------------------------"));
		array.add(Text.of("Help: Help Command"));
		
		
		array.add(Text.of(" "));
		return array;
	}
	
	private static ArrayList<Text> teleport() {
		ArrayList<Text> array = new ArrayList<Text>();
		
		
		array.add(Text.of(" "));
		return array;
	}
	
	private static ArrayList<Text> various() {
		ArrayList<Text> array = new ArrayList<Text>();
		
		
		array.add(Text.of(" "));
		return array;
	}
	
	public static void populate() {
		admin = admin();
		cheat = cheat();
		general = general();
		teleport = teleport();
		various = various();
		
		ArrayList<Text> allcommands = new ArrayList<Text>();
		allcommands.addAll(admin());
		allcommands.addAll(cheat());
		allcommands.addAll(general());
		allcommands.addAll(teleport());
		allcommands.addAll(various());
		all = allcommands;
		
		return ;
	}
}
