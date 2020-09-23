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
  * 각 엔티티들을 못찾았을 경우, findById, findByCode 메서드에서 조회가 안되었을 경우
  * @Author : hkpark
  * @Date   : 2020. 8. 28.
  * @see
*/

public class EntityNotFoundException extends BusinessException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public EntityNotFoundException() {
		super();
	}

	public EntityNotFoundException(String msg) {
		super(msg);
	}

	public EntityNotFoundException(String msg, Throwable e) {
		super(msg, e);
	}

	public EntityNotFoundException(Throwable e) {
		super(e);
	}
}
