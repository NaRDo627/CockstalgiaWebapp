/**
  * Copyright (c) 2019 ylland.net CO.,LTD. All rights reserved.
  *
  * This software is the confidential and proprietary information of ylland.net CO.,LTD.
  * You shall not disclose such Confidential Information and shall use it
  * only in accordance with the terms of the license agreement you entered into
  * with ylland.net CO.,LTD.
  */

package net.hkpark.cockstalgiacore.core.exception;

/**
  * 비즈니스 로직 최상위 예외
  * @Author : hkpark
  * @Date   : 2020. 8. 28.
  * @see
*/

public class BusinessException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public BusinessException() {
		super();
	}

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(String msg, Throwable e) {
		super(msg, e);
	}

	public BusinessException(Throwable e) {
		super(e);
	}
}
