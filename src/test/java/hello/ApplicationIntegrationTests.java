/**
 * 
 */
package hello;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import hello.wsdl.GetEmployeeRequest;
import hello.wsdl.GetEmployeeResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;



/**
 * @author TCS
 *
 */
public class ApplicationIntegrationTests {

	private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

    @LocalServerPort
    private int port = 8090;

    @Before
    public void init() throws Exception {
        marshaller.setPackagesToScan(ClassUtils.getPackageName(GetEmployeeResponse.class));
        marshaller.afterPropertiesSet();
    }

    @Test
    public void testSendAndReceive() {
        WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        GetEmployeeRequest request = new GetEmployeeRequest();
        request.setName("arun");

        assertThat(ws.marshalSendAndReceive("http://localhost:"
                + port + "/ws", request)).isNotNull();
    }

}
