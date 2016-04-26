package net.re_renderreality.rrrp2.api.util.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

/**
 * Utility class to get all the config classes from one place
 */
public final class Configs
{
	/**
	 * Blank Constructor.
	 */
	private Configs()
	{
		;
	}

	/**
	 * 
	 * @param config {@link Optional} {@link Configurable} the config file
	 * @return the value stored in a {@link CommentedConfigurationNode}
	 */
	public static CommentedConfigurationNode getConfig(Configurable config)
	{
		return config.get();
	}

	/**
	 * @param config {@link Optional} {@link Configurable} the config file
	 * @note saves the config node in the config file
	 */
	public static void saveConfig(Configurable config)
	{
		config.save();
	}

	/**
	 * 
	 * @param config {@link Optional} {@link Configurable} the config file
	 * @param nodePath The Path to the config node
	 * @param value the new setting of the configuation node
	 * 
	 * @note gets the node sets the new value then saves/writes the new value to the config file
	 */
	public static void setValue(Configurable config, Object[] nodePath, Object value)
	{
		config.get().getNode(nodePath).setValue(value);
		config.save();
	}

	/**
	 * @param config {@link Optional} {@link Configurable} the config file
	 * @param nodePath The Path to the config node
	 * @param child the child node to be removed
	 * 
	 * @note removes a child node from a config file
	 */
	public static void removeChild(Configurable config, Object[] nodePath, Object child)
	{
		config.get().getNode(nodePath).removeChild(child);
		config.save();
	}

	/**
	 * @param config {@link Optional} {@link Configurable} the config file
	 * @param nodePath The Path to the config node
	 * 
	 * @note removes all the children node from a config file
	 */
	public static void removeChildren(Configurable config, Object[] nodePath)
	{
		for (Object child : config.get().getNode(nodePath).getChildrenMap().keySet())
		{
			config.get().getNode(nodePath).removeChild(child);
		}

		config.save();
	}
}
