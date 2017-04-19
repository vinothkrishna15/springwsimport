/**
 * 
 */
package hello;

import static org.junit.Assert.assertEquals;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

import javax.xml.transform.Source;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.destination.DestinationProvider;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.ResourceSource;

import hello.wsdl.GetEmployeeResponse;

/**
 * @author TCS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfig.class, ServiceClient.class })
public class ApplicationIntegrationTests {

	@Autowired
	private ServiceClient client;

	private MockWebServiceServer mockServer;
	
	@Mock
	private WebServiceTemplate wsTemp;
	
	private DestinationProvider destinationProvider;

	@Before
	public void createServer() throws Exception {
		mockServer = MockWebServiceServer.createServer(client);
		//Mockito.when(destinationProvider.getDestination()).thenReturn("http://10.144.135.147:8090/ws/employees.wsdl");
	}


	
	@Test
	public void testEmployeeService() throws Exception {
		
		Source expectedPayload = new ResourceSource(new ClassPathResource("SampleRequest.xml"));
        Source expectedResponse = new ResourceSource(new ClassPathResource("SampleResponse.xml"));
        mockServer.expect(payload(expectedPayload)).andRespond(withPayload(expectedResponse));
        GetEmployeeResponse getEmpDet = client.getEmployeeDetails("arun");
        //assertThat(getEmpDet, getEmpDet);
        mockServer.verify();
	}

	 	
}
