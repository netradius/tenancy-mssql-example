package com.netradius.demo.controller;

import com.netradius.demo.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * A Simple controller to handle requets for tenancy demo purposes.
 *
 * @author Kevin Hawkins
 */
@Controller
public class TenancyDemoController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/demo/{user}", method = RequestMethod.GET)
	@ResponseBody
	public String getUser(@PathVariable("user") String user) {
		return userService.getUsername(user);
	}
}
