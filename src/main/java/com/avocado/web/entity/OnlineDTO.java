package com.avocado.web.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OnlineDTO {
	private int bno, user_no, bno2, fno, commentYN, bread;
	private String btitle, bcontent, bdate, secret, bdel, comment, uname, bupdate;

}
