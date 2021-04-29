package com.nktfh100.BowTrails.info.arrows;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;

import com.nktfh100.BowTrails.info.ArrowInfo;
import com.nktfh100.BowTrails.info.BowTrail;

public class EffectArrow extends ArrowInfo {

	private BowTrail bowTrail;

	public EffectArrow(Arrow arrow, BowTrail bowTrail) {
		super(arrow, bowTrail.getType(), bowTrail);
		this.bowTrail = bowTrail;
	}

	@Override
	public void update() {
		if (this.bowTrail.getEffect() == Particle.REDSTONE && this.bowTrail.getDustOptions() != null) {
			this.getArrow().getWorld().spawnParticle(Particle.REDSTONE, this.getArrow().getLocation(), this.bowTrail.getCount(), this.bowTrail.getOffset(), this.bowTrail.getOffset(), this.bowTrail.getOffset(), 0.1, this.bowTrail.getDustOptions());
		} else {
			this.getArrow().getWorld().spawnParticle(this.bowTrail.getEffect(), this.getArrow().getLocation(), this.bowTrail.getCount(), this.bowTrail.getOffset(), this.bowTrail.getOffset(),
					bowTrail.getOffset());
		}
	}

	@Override
	public void destroyed() {
	}

	public BowTrail getBowTrail() {
		return this.bowTrail;
	}

}
