package com.avocado.web.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnlineDTO {
	private int bno, user_no, bno2, fno, commentYN;
	private String btitle, bcontent,bdate, secret, bdel, comment;
}
