/**
  * Copyright (c) 2019 ylland.net CO.,LTD. All rights reserved.
  *
  * This software is the confidential and proprietary information of ylland.net CO.,LTD.
  * You shall not disclose such Confidential Information and shall use it
  * only in accordance with the terms of the license agreement you entered into
  * with ylland.net CO.,LTD.
  */

package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.exception;

import net.hkpark.cockstalgia.core.exception.EntityNotFoundException;

/**
  * 유효하지 않은 값일 경우 예외를 던지는 Excetion
  * @Author : hkpark
  * @Date   : 2020. 8. 28.
  * @see
*/

public class MemberNotFoundException extends EntityNotFoundException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public MemberNotFoundException() {
		super();
	}

	public MemberNotFoundException(String msg) {
		super(msg);
	}

	public MemberNotFoundException(String msg, Throwable e) {
		super(msg, e);
	}

	public MemberNotFoundException(Throwable e) {
		super(e);
	}
}
