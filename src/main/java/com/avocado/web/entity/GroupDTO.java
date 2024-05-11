package com.avocado.web.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDTO {
	private String prg_nm, prg_cd, prg_content, prg_ymd, prg_hr, prg_place, prg_start, prg_end; 	
	private int prg_no, prg_nope, prg_nmtm, cns_no;
}
