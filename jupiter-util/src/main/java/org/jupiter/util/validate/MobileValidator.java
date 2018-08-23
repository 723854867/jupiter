package org.jupiter.util.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.jupiter.util.PhoneUtil;

public class MobileValidator implements ConstraintValidator<Mobile, String> {
	
	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		return null == arg0 ? true : PhoneUtil.isMobile(arg0);
	}
}