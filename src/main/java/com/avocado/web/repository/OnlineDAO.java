package com.avocado.web.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.OnlineDTO;

@Repository
@Mapper
public interface OnlineDAO {

	List<OnlineDTO> online();

	OnlineDTO detail(int bno);

	int write(Map<String, String> map);

	int count();

	List<OnlineDTO> findAll(Map<String, Integer> map);
}
