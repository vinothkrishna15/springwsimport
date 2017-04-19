/**
 * 
 */
package hello;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.xml.transform.Source;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.ResourceSource;

import hello.controller.EmployeeController;
import hello.wsdl.GetEmployeeRequest;
import hello.wsdl.GetEmployeeResponse;

/**
 * @author TCS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfig.class, ServiceClient.class })
public class ApplicationIntegrationTests {

	private MockWebServiceServer mockServer;
	
	@Mock
	private ServiceClient client;
	
	@Mock
	private WebserviceTemplateExtended wsExtend;
	
	@Mock
	private WebServiceGatewaySupport wsGateway;
	
	@InjectMocks
	private EmployeeController empCont;
	
	private MockMvc mockMvc;

	@Before
	public void setup() {
		 
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
 
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(empCont).build();
 
    }
	/*public void createServer() throws Exception {
		mockServer = MockWebServiceServer.createServer(client);
		//Mockito.when(destinationProvider.getDestination()).thenReturn("http://10.144.135.147:8090/ws/employees.wsdl");
	}*/

	
	@Test
	public void testEmployeeServicee() throws Exception {
		GetEmployeeRequest request = new GetEmployeeRequest();
		request.setName("arun");
		Mockito.when(client.getMarshalSupport(request)).thenReturn("");
		Source expectedPayload = new ResourceSource(new ClassPathResource("SampleRequest.xml"));
        Source expectedResponse = new ResourceSource(new ClassPathResource("SampleResponse.xml"));
        mockServer.expect(payload(expectedPayload)).andRespond(withPayload(expectedResponse));
        GetEmployeeResponse getEmpDet = client.getEmployeeDetails("arun");
        //assertThat(getEmpDet, getEmpDet);
        mockServer.verify();
	}
	@Test
	public void testEmployeeService() throws Exception {
	 
	    
	    this.mockMvc.perform(post("/getEmployee")
	            .param("name", "madan"))
	            .andExpect(status().isOk());
	 
	}
	@Test
	public void testEmployeeServiceWithError() throws Exception {
	 
	    
	    this.mockMvc.perform(post("/getEmployee")
	            .param("name", "dd"))
	            .andExpect(status().isInternalServerError());
	 
	}
	 	
}
