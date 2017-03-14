package com.netradius.demo.data.dto.form;

import com.netradius.demo.utils.UniqueTenant;
import lombok.Data;

/**
 * @author Kevin Hawkins
 */
@Data
public class UserRequestForm {
	@UniqueTenant
	private String name;
}
