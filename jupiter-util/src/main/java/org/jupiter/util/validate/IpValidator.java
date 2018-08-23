package org.jupiter.util.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.jupiter.util.IpUtil;

public class IpValidator implements ConstraintValidator<Ip, String> {

	@Override
	public void initialize(Ip constraintAnnotation) {}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return null == value ? true : IpUtil.isIp(value);
	}
}
