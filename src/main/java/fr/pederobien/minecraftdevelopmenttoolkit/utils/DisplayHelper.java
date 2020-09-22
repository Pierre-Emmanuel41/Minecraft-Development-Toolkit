package fr.pederobien.minecraftdevelopmenttoolkit.utils;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.StringJoiner;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class DisplayHelper {
	private static DecimalFormat decimalFormat = new DecimalFormat();

	/**
	 * Method used to display the given time to minecraft player.
	 * 
	 * @param time The time to display.
	 * @param full True if the method show hours, minutes or second even if it equals 0.
	 * 
	 * @return time.getHour() + "h " + time.getMinute() + "m " + time.getSecond() + "s";
	 */
	public static String toString(LocalTime time, boolean full) {
		StringJoiner joiner = new StringJoiner(" ");
		if (full || time.getHour() != 0)
			joiner.add(time.getHour() + "h");
		if (full || time.getMinute() != 0)
			joiner.add(time.getMinute() + "m");
		if (full || time.getSecond() != 0)
			joiner.add(time.getSecond() + "s");
		return joiner.toString();
	}

	/**
	 * Method used to display the given block to minecraft player.
	 * 
	 * @param block The block to display.
	 * 
	 * @return "X=" + block.getX() + " Y=" + block.getY() + " Z=" + block.getZ();
	 */
	public static String toString(Block block) {
		return join("X=" + block.getX(), "Y=" + block.getY(), "Z=" + block.getZ());
	}

	/**
	 * Method used to display the given location to minecraft player.
	 * 
	 * @param location  The location to display.
	 * @param full      If should display pitch an yaw.
	 * @param castToInt True if locaction.getX(), location.getY() and location.getZ() should be cast to int instead of double
	 * 
	 * @return "X=" + location.getX() + " Y=" + location.getY() + " Z=" + location.getZ() + " Pitch=" + location.getPitch() + " Yaw="
	 *         + location.getYaw();
	 */
	public static String toString(Location location, boolean full, boolean castToInt) {
		StringJoiner joiner = new StringJoiner(" ");
		String X = "X=" + (castToInt ? (int) location.getX() : location.getX());
		String Y = "Y=" + (castToInt ? (int) location.getY() : location.getY());
		String Z = "Z=" + (castToInt ? (int) location.getZ() : location.getZ());

		join(joiner, X, Y, Z);
		if (full)
			join(joiner, "Pitch=" + location.getPitch(), "Yaw=" + location.getYaw());
		return joiner.toString();
	}

	/**
	 * Method used to display the given location to minecraft player.
	 * 
	 * @param location The location to display.
	 * @param full     If should display pitch an yaw.
	 * @return "X=" + location.getX() + " Y=" + location.getY() + " Z=" + location.getZ() + " Pitch=" + location.getPitch() + " Yaw="
	 *         + location.getYaw();
	 */
	public static String toString(Location location, boolean full) {
		return toString(location, full, false);
	}

	/**
	 * Format the given number using the pattern "#.##".
	 * 
	 * @param number The number to format.
	 * @return The formatted number.
	 * 
	 * @see #format(String, double)
	 */
	public static String format(double number) {
		return format("#.##", number);
	}

	/**
	 * Format the given number using the specified pattern.
	 * 
	 * @param pattern The pattern used to format the number.
	 * @param number  The number to format.
	 * @return The formatted number.
	 * 
	 * @see DecimalFormat#format(double)
	 */
	public static String format(String pattern, double number) {
		decimalFormat.applyPattern(pattern);
		return decimalFormat.format(number);
	}

	private static String join(String... toJoin) {
		return join(new StringJoiner(" "), toJoin);
	}

	private static String join(StringJoiner joiner, String... toJoin) {
		for (String join : toJoin)
			joiner.add(join);
		return joiner.toString();
	}
}
