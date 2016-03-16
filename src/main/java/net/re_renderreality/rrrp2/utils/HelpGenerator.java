package net.re_renderreality.rrrp2.utils;

import java.util.ArrayList;

import org.spongepowered.api.text.Text;

public class HelpGenerator {
	private static ArrayList<Text> admin;
	private static ArrayList<Text> cheat;
	private static ArrayList<Text> general;
	private static ArrayList<Text> teleport;
	private static ArrayList<Text> various;
	private static ArrayList<Text> all;
	
	public HelpGenerator() {
		populate();
	}
	private static ArrayList<Text> admin() {
		admin.add(Text.of("        Admin Commands"));
		admin.add(Text.of("------------------------------------"));
		admin.add(Text.of("ClearEntities: Clears all the entities of the specified type"));
		admin.add(Text.of("ListEntities: List all entities currently loaded"));
		admin.add(Text.of("whois: Gives all known information about player"));
		admin.add(Text.of(" "));
		return admin;
	}
	
	private static ArrayList<Text> cheat() {
		cheat.add(Text.of("       Cheater Commands =)"));
		cheat.add(Text.of("------------------------------------"));
		cheat.add(Text.of("Heal: Heal Command"));
		
		cheat.add(Text.of(" "));
		return cheat;
	}
	
	private static ArrayList<Text> general() {
		general.add(Text.of("         General Commands"));
		general.add(Text.of("------------------------------------"));
		general.add(Text.of("Help: Help Command"));
		
		
		general.add(Text.of(" "));
		return general;
	}
	
	private static ArrayList<Text> teleport() {
		
		
		
		admin.add(Text.of(" "));
		return teleport;
	}
	
	private static ArrayList<Text> various() {
		
		
		
		admin.add(Text.of(" "));
		return various;
	}
	
	public static void populate() {
		admin = admin();
		cheat = cheat();
		general = general();
		teleport = teleport();
		various = various();
		
		all.addAll(admin);
		all.addAll(cheat);
		all.addAll(general);
		all.addAll(teleport);
		all.addAll(various);
		
		return ;
	}
	
	public static Iterable<Text> getAdmin() { 
		Iterable<Text> a =  admin;
		return a;}
	
	public static Iterable<Text> getCheat() { 
		Iterable<Text> c =  cheat;
		return c;}
	
	public static Iterable<Text> getGeneral() { 
		Iterable<Text> g =  general;
		return g;}
	
	public static Iterable<Text> getTeleport() { 
		Iterable<Text> t =  teleport;
		return t;}
	
	public static Iterable<Text> getVarious() { 
		Iterable<Text> v =  various;
		return v;}
	
	public static Iterable<Text> getAll() { 
		Iterable<Text> a =  all;
		return a;}
}
