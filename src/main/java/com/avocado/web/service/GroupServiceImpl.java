package com.avocado.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.GroupDTO;
import com.avocado.web.repository.GroupDAO;

@Service("groupService")
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupDAO groupDAO;

	@Override
	public List<GroupDTO> programList() {
		return groupDAO.programList();
	}
	
}
