package test.webmvc;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
* @author 苍镜月
* @version 1.0
* @implNote 
*/

public class DemoControllerTest {

    @Test
    public void mockTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("GET");
        request.setRequestURI("/query");
        request.setParameter("name", "苍镜月 ");

        MockHttpServletResponse response = new MockHttpServletResponse();

    }
}
