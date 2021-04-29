package com.nktfh100.BowTrails.info;

import org.bukkit.entity.Arrow;

import com.nktfh100.BowTrails.enums.ArrowEffectType;

public abstract class ArrowInfo {

	private Arrow arrow;
	private ArrowEffectType effectType;
	private BowTrail bowTrail;

	public ArrowInfo(Arrow arrow, ArrowEffectType effectType, BowTrail bowTrail) {
		this.arrow = arrow;
		this.effectType = effectType;
		this.bowTrail = bowTrail;
	}

	public abstract void update();

	public abstract void destroyed();

	public Arrow getArrow() {
		return arrow;
	}

	public ArrowEffectType getEffectType() {
		return effectType;
	}

	public BowTrail getBowTrail() {
		return bowTrail;
	}

}
