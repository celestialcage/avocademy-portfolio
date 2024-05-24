package com.avocado.web.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalDTO {
	// dscsn_cn(상담내용), dscsn_nmtm(상담회차)
	private int cns_no, user_no, sch_no, sch_state, aply_no, aply_no_old, dscsn_nmtm;
	private String cns_nm, ofc_no, cns_field, stud_no, sch_ymd, sch_hr, dscsn_ymd, dscsn_hr, dscsn_stat, dscsn_cn;
}
