package com.nktfh100.BowTrails.managers;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;

import com.nktfh100.BowTrails.enums.ArrowEffectType;
import com.nktfh100.BowTrails.info.ArrowInfo;
import com.nktfh100.BowTrails.main.BowTrails;

public class ArrowsManager {

	private BowTrails plugin;

	private ArrayList<ArrowInfo> arrows = new ArrayList<ArrowInfo>();

	public ArrowsManager(BowTrails instance) {
		this.plugin = instance;
		new BukkitRunnable() {
			@Override
			public void run() {
				if (plugin.isEnabled()) {
					updateEntityArrows();
				}
			}
		}.runTaskTimer(plugin, 20L, 1L);
		new BukkitRunnable() {
			@Override
			public void run() {
				if (plugin.isEnabled()) {
					updateArrows();
				}
			}
		}.runTaskTimer(plugin, 20L, 2L);
	}

	public void updateArrows() {
		Iterator<ArrowInfo> iterator = arrows.iterator();
		while (iterator.hasNext()) {
			ArrowInfo arrowInfo = iterator.next();
			if (arrowInfo.getEffectType() == ArrowEffectType.ENTITY) {
				continue;
			}
			if (arrowInfo.getArrow().isDead()) {
				arrowInfo.destroyed();
				iterator.remove();
				continue;
			}
			arrowInfo.update();
		}
	}

	public void updateEntityArrows() {
		Iterator<ArrowInfo> iterator = arrows.iterator();
		while (iterator.hasNext()) {
			ArrowInfo arrowInfo = iterator.next();
			if (arrowInfo.getEffectType() != ArrowEffectType.ENTITY) {
				continue;
			}
			if (arrowInfo.getArrow().isDead() || arrowInfo.getArrow().getVelocity().length() <= 0.1) {
				arrowInfo.destroyed();
				iterator.remove();
				continue;
			}
			arrowInfo.update();
		}
	}

	public ArrowInfo getArrowInfo(Arrow arrow) {
		for (ArrowInfo info : this.arrows) {
			if (info.getArrow().getUniqueId() == arrow.getUniqueId()) {
				return info;
			}
		}
		return null;
	}

	public void addArrow(ArrowInfo arrowInfo) {
		arrows.add(arrowInfo);
	}

	public void removeArrow(ArrowInfo arrowInfo) {
		arrowInfo.destroyed();
		this.arrows.remove(arrowInfo);
	}

	public void removeArrow(Arrow arrow) {
		Iterator<ArrowInfo> iterator = arrows.iterator();
		while (iterator.hasNext()) {
			ArrowInfo arrowInfo = iterator.next();
			if (arrowInfo.getArrow().getUniqueId() == arrow.getUniqueId()) {
				arrowInfo.destroyed();
				iterator.remove();
				return;
			}
		}
	}

	public ArrayList<ArrowInfo> getArrows() {
		return arrows;
	}
}
