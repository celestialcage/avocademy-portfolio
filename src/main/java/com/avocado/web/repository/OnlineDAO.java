package com.avocado.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.OnlineDTO;

@Repository
@Mapper
public interface OnlineDAO {

	List<OnlineDTO> online();

	List<OnlineDTO> online(int bno);

}
