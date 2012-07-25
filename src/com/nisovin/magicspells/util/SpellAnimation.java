package com.nisovin.magicspells.util;

import org.bukkit.Bukkit;

import com.nisovin.magicspells.MagicSpells;

/**
 * This class represents a spell animation. It facilitates creating a spell effect that happens over a period of time, 
 * without having to worry about stopping and starting scheduled tasks.
 * 
 * @author nisovin
 *
 */
public abstract class SpellAnimation implements Runnable {

	private int taskId;
	private long delay;
	private long interval;
	private int tick;
	
	/**
	 * Create a new spell animation with the specified interval and no delay. It will not auto start.
	 * @param interval the animation interval, in server ticks (animation speed)
	 */
	public SpellAnimation(long interval) {
		this(0, interval, false);
	}
	
	/**
	 * Create a new spell animation with the specified interval and no delay.
	 * @param interval the animation interval, in server ticks (animation speed)
	 * @param autoStart whether the animation should start immediately upon being created
	 */
	public SpellAnimation(long interval, boolean autoStart) {
		this(0, interval, autoStart);
	}
	
	/**
	 * Create a new spell animation with the specified interval and delay. It will not auto start.
	 * @param delay the delay before the animation begins, in server ticks
	 * @param interval the animation interval, in server ticks (animation speed)
	 */
	public SpellAnimation(long delay, long interval) {
		this(delay, interval, false);
	}
	
	/**
	 * Create a new spell animation with the specified interval and delay.
	 * @param delay the delay before the animation begins, in server ticks
	 * @param interval the animation interval, in server ticks (animation speed)
	 * @param autoStart whether the animation should start immediately upon being created
	 */
	public SpellAnimation(long delay, long interval, boolean autoStart) {
		this.delay = delay;
		this.interval = interval;
		this.tick = -1;
		if (autoStart) {
			play();
		}
	}
	
	/**
	 * Start the spell animation.
	 */
	public void play() {
		taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MagicSpells.plugin, this, delay, interval);
	}
	
	/**
	 * Stop the spell animation.
	 */
	protected void stop() {
		Bukkit.getServer().getScheduler().cancelTask(taskId);
	}
	
	/**
	 * This method is called every time the animation ticks (with the interval defined in the constructor).
	 * @param tick the current tick number, starting with 0
	 */
	protected abstract void onTick(int tick);
	
	@Override
	public final void run() {
		onTick(++tick);
	}
	
}