/**
  * Copyright (c) 2019 ylland.net CO.,LTD. All rights reserved.
  *
  * This software is the confidential and proprietary information of ylland.net CO.,LTD.
  * You shall not disclose such Confidential Information and shall use it
  * only in accordance with the terms of the license agreement you entered into
  * with ylland.net CO.,LTD.
  */

package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.exception;

import net.hkpark.cockstalgia.core.exception.EntityAlreadyExistsException;

/**
  * 유효하지 않은 값일 경우 예외를 던지는 Excetion
  * @Author : hkpark
  * @Date   : 2020. 8. 28.
  * @see
*/

public class MemberAlreadyExistsException extends EntityAlreadyExistsException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public MemberAlreadyExistsException() {
		super();
	}

	public MemberAlreadyExistsException(String msg) {
		super(msg);
	}

	public MemberAlreadyExistsException(String msg, Throwable e) {
		super(msg, e);
	}

	public MemberAlreadyExistsException(Throwable e) {
		super(e);
	}
}
