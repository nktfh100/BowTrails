package com.nktfh100.BowTrails.enums;

import com.nktfh100.BowTrails.info.ArrowInfo;
import com.nktfh100.BowTrails.info.arrows.*;

@SuppressWarnings("rawtypes")
public enum ArrowEffectType {
	NORMAL(EffectArrow.class), ENTITY(EntityArrow.class);

	private Class arrowInfoClass;

	private ArrowEffectType(Class arrowInfoClass) {
		this.arrowInfoClass = arrowInfoClass;
	}

	@SuppressWarnings("unchecked")
	public Class<ArrowInfo> getArrowInfoClass() {
		return (Class<ArrowInfo>) this.arrowInfoClass;
	}

}
