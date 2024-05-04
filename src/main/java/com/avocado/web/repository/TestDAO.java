package com.avocado.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.TestDTO;

@Repository
@Mapper
public interface TestDAO {

	List<TestDTO> staff();

}
