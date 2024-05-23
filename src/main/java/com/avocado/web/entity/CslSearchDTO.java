package com.avocado.web.entity;

import lombok.Data;

@Data
public class CslSearchDTO {

	private int page, cns_no;
	private String search, stud_no;
}
