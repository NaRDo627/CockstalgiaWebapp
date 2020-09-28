/**
  * Copyright (c) 2019 ylland.net CO.,LTD. All rights reserved.
  *
  * This software is the confidential and proprietary information of ylland.net CO.,LTD.
  * You shall not disclose such Confidential Information and shall use it
  * only in accordance with the terms of the license agreement you entered into
  * with ylland.net CO.,LTD.
  */

package net.hkpark.cockstalgia.core.exception;

/**
  * 유효하지 않은 값일 경우 예외를 던지는 Excetion
  * @Author : hkpark
  * @Date   : 2020. 8. 28.
  * @see
*/

public class EntityAlreadyExistsException extends BusinessException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public EntityAlreadyExistsException() {
		super();
	}

	public EntityAlreadyExistsException(String msg) {
		super(msg);
	}

	public EntityAlreadyExistsException(String msg, Throwable e) {
		super(msg, e);
	}

	public EntityAlreadyExistsException(Throwable e) {
		super(e);
	}
}
