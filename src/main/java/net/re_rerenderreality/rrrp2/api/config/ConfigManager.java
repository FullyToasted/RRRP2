package net.re_rerenderreality.rrrp2.api.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

import java.util.Optional;

/**
 * Handles the detection of {@link NullPointerException} in CommentedConfigurationNode using {@link java.util.Optional}
 */
public class ConfigManager
{
	public ConfigManager()
	{
		;
	}

	/**
	 * Get boolean {@link Optional} value for CommentedConfigurationNode if it is not null; Otherwise return Optional.empty.
	 *
	 * @param node The node for which the boolean value is needed
	 * @return Optional.empty if null or Optional#of(Boolean) if not
	 */
	public Optional<Boolean> getBoolean(CommentedConfigurationNode node)
	{
		if (checkNull(node))
			return Optional.empty();

		return Optional.of(node.getBoolean());
	}

	/**
	 * Get double {@link Optional} value for CommentedConfigurationNode if it is not null; Otherwise return Optional.empty.
	 *
	 * @param node The node for which the double value is needed
	 * @return Optional.empty if null or Optional#of(Double) if not
	 */
	public Optional<Double> getDouble(CommentedConfigurationNode node)
	{
		if (checkNull(node))
			return Optional.empty();

		return Optional.of(node.getDouble());
	}

	/**
	 * Get float {@link Optional} value for CommentedConfigurationNode if it is not null; Otherwise return Optional#empty.
	 *
	 * @param node The node for which the float value is needed
	 * @return Optional#empty if null or double value Optional#of(Float) if not
	 */
	public Optional<Float> getFloat(CommentedConfigurationNode node)
	{
		if (checkNull(node))
			return Optional.empty();

		return Optional.of(node.getFloat());
	}

	/**
	 * Get int {@link Optional} value for CommentedConfigurationNode if it is not null; Otherwise return Optional#empty.
	 *
	 * @param node The node for which the int value is needed
	 * @return Optional#empty if null or Optional#of(Integer) if not
	 */
	public Optional<Integer> getInt(CommentedConfigurationNode node)
	{
		if (checkNull(node))
			return Optional.empty();

		return Optional.of(node.getInt());
	}

	/**
	 * Get long {@link Optional} value for CommentedConfigurationNode if it is not null; Otherwise return Optional#empty.
	 *
	 * @param node The node for which the long value is needed
	 * @return Optional#empty if null or Optional#of(Long) if not
	 */
	public Optional<Long> getLong(CommentedConfigurationNode node)
	{
		if (checkNull(node))
			return Optional.empty();

		return Optional.of(node.getLong());
	}

	/**
	 * Get String {@link Optional} value for CommentedConfigurationNode if it is not null; Otherwise return Optional#empty.
	 *
	 * @param node The node for which the String value is needed
	 * @return Optional#empty if null or Optional#of(String) if not
	 */
	public Optional<String> getString(CommentedConfigurationNode node)
	{
		if (checkNull(node))
			return Optional.empty();

		return Optional.of(node.getString());
	}

	/**
	 * Get Object {@link Optional} value for CommentedConfigurationNode if it is not null; Otherwise return Optional#empty.
	 *
	 * @param node The node for which the Object value is needed
	 * @return Optional#empty if null or Optional#of(Object) if not
	 */
	public Optional<Object> getValue(CommentedConfigurationNode node)
	{
		if (checkNull(node))
			return Optional.empty();

		return Optional.of(node.getValue());
	}

	private boolean checkNull(CommentedConfigurationNode node)
	{
		if (node.getValue() == null)
			return true;
		else
			return false;
	}
}
