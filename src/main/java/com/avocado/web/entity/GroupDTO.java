package com.avocado.web.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDTO {
	private String prg_nm, prg_cd, cns_no, cns_nm, prg_content, prg_ymd, prg_hr, prg_place, prg_start, prg_end, prg_schdl, prg_dow, req_end, prg_aprv;
	private String applyCount, req_open;
	private int prg_no, prg_nope, prg_nmtm;
	
	private List<String> groupSCHDL;

}
