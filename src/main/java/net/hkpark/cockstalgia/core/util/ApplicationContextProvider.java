/**
  * Copyright (c) 2019 ylland.net CO.,LTD. All rights reserved.
  *
  * This software is the confidential and proprietary information of ylland.net CO.,LTD.
  * You shall not disclose such Confidential Information and shall use it
  * only in accordance with the terms of the license agreement you entered into
  * with ylland.net CO.,LTD.
  */

package net.hkpark.cockstalgia.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
  * ApplicationContextProvider.java
  * @author  hkpark
  * @since 2020. 9. 14.
*/

@Component
public class ApplicationContextProvider implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        applicationContext = ctx;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}