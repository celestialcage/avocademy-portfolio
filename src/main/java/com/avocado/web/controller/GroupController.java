package com.avocado.web.controller;

import org.springframework.stereotype.Controller;

import com.avocado.web.service.GroupService;

import jakarta.annotation.Resource;

@Controller
public class GroupController {
	
	@Resource(name="groupService")
	private GroupService groupService;

}
