package net.re_renderreality.rrrp2.utils;

import java.util.ArrayList;

import org.spongepowered.api.text.Text;

public class HelpGenerator {
	public Iterable<Text> admin;
	public Iterable<Text> cheat;
	public Iterable<Text> general;
	public Iterable<Text> teleport;
	public Iterable<Text> various;
	public Iterable<Text> all;
	//creates a HelpGenerator instance
	private static HelpGenerator help = new HelpGenerator();

	private HelpGenerator()
	{
		;
	}
	//Dont Change Spacing on first two Text lines of each function
	//Keep space-Text at end of command list
	private ArrayList<Text> admin() {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of("                              Admin Commands"));
		array.add(Text.of("-----------------------------------------------------"));
		array.add(Text.of("/ClearEntities: Clears all the entities of the specified type"));
		array.add(Text.of("/ListEntities: List all entities currently loaded"));
		array.add(Text.of("/TPS: Show useful information about the server performance"));
		array.add(Text.of("/Whois: Gives all known information about player"));
		array.add(Text.of(" "));
		return array;
	}
	
	private ArrayList<Text> cheat() {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of("                            Cheater Commands =)"));
		array.add(Text.of("-----------------------------------------------------"));
		array.add(Text.of("/Heal: Heals self or can heal another player"));
		array.add(Text.of("/Fly: Enable fly mode for self or another player"));
		
		array.add(Text.of(" "));
		return array;
	}
	
	private ArrayList<Text> general() {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of("                            General Commands"));
		array.add(Text.of("-----------------------------------------------------"));
		array.add(Text.of("/Help: Help Command"));
		
		
		array.add(Text.of(" "));
		return array;
	}
	
	private ArrayList<Text> teleport() {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of("                             Teleport Commands"));
		array.add(Text.of("-----------------------------------------------------"));
		
		array.add(Text.of(" "));
		return array;
	}
	
	private ArrayList<Text> various() {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of("                              Other Commands"));
		array.add(Text.of("-----------------------------------------------------"));
		
		array.add(Text.of(" "));
		return array;
	}
	
	/**
	 * populates the help generator object
	 */
	public void populate() {
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
	
	/**
	 * @return returns the populated helpGenerator object
	 */
	public static HelpGenerator getHelp() {
		return help;
	}
}
