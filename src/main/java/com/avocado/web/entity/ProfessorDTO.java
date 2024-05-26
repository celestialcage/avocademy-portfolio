package com.avocado.web.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorDTO {
	private String stud_no, stud_nm, scsbjt_cd, scsbjt_nm, ps_no, stud_telno, psc_ymd, psc_hr, ps_nm, uemail;
	private String ps_class, ps_cn, ps_ymd, ps_hr, ps_result, hr_nm;
	private int user_no, psc_no;
	private int pseq_no, ps_status;

}
