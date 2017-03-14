package com.netradius.demo.utils;

import com.netradius.demo.tenancy.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Erik R. Jensen
 */
public class UniqueTenantValidator implements ConstraintValidator<UniqueTenant, String> {

	@Autowired
	TenantService tenantService;

	@Override
	public void initialize(UniqueTenant constraintAnnotation) {
		// do nothing
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.hasText(value) && !tenantService.exists(value);
	}
}
