package com.avocado.web.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalDTO {

	private int cns_no, user_no, sch_no, sch_state;
	private String cns_nm, ofc_no, cns_field, sch_ymd, sch_hr;
}
