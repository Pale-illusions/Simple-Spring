package com.iflove.simplespring.webmvc.context;

import com.iflove.simplespring.beans.factory.Aware;
import javax.servlet.ServletConfig;


/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public interface ServletConfigAware extends Aware {

    void setServletConfig(ServletConfig servletConfig);

}
