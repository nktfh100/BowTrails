package com.nktfh100.BowTrails.info;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;

import com.nktfh100.BowTrails.enums.ArrowEffectType;

public class BowTrail {

	private String configKey;
	private ArrowEffectType type;
	private String permission;
	// For normal
	private Particle effect;
	private int count;
	private double offset;
	private DustOptions dustOptions;
	// For entity
	private EntityType entityType;

	public BowTrail(String configKey, ConfigurationSection trailSC) {
		this.configKey = configKey;
		this.type = ArrowEffectType.valueOf(trailSC.getString("type").toUpperCase());
		if (this.type == ArrowEffectType.NORMAL) {
			this.effect = Particle.valueOf(trailSC.getString("effect", "BARRIER"));
			this.count = trailSC.getInt("count", 1);
			this.offset = trailSC.getDouble("offset", 0.1);
			if (this.effect == Particle.REDSTONE) {
				String colorStr_ = trailSC.getString("color", "255,255,255");
				String[] colorSplit = colorStr_.split(",");

				this.dustOptions = new DustOptions(Color.fromRGB(Integer.parseInt(colorSplit[0]), Integer.parseInt(colorSplit[1]), Integer.parseInt(colorSplit[2])), 2);
			}
		} else if (this.type == ArrowEffectType.ENTITY) {
			this.entityType = EntityType.valueOf(trailSC.getString("entity", "SHEEP"));
		}
		this.permission = trailSC.getString("permission", "");
	}

	public ArrowEffectType getType() {
		return type;
	}

	public Particle getEffect() {
		return effect;
	}

	public int getCount() {
		return count;
	}

	public ArrowInfo createArrowInfo(Arrow arrow) {
		try {
			ArrowInfo newArrowInfo = this.type.getArrowInfoClass().getDeclaredConstructor(Arrow.class, BowTrail.class).newInstance(arrow, this);
			return newArrowInfo;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public String getConfigKey() {
		return configKey;
	}

	public double getOffset() {
		return offset;
	}

	public String getPermission() {
		return permission;
	}

	public DustOptions getDustOptions() {
		return dustOptions;
	}

}
