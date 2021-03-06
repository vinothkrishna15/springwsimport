/**
 * 
 */
package hello;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.test.client.MockWebServiceServer;

import hello.controller.EmployeeController;
import hello.wsdl.Employee;
import hello.wsdl.GetEmployeeRequest;
import hello.wsdl.GetEmployeeResponse;

/**
 * @author TCS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfig.class, ServiceClient.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTests {

	private MockWebServiceServer mockServer;

	@Autowired
	private ServiceClient client;

	@Mock
	private WebserviceTemplateExtended wsExtend;

	@Mock
	private WebServiceGatewaySupport wsGateway;

	@Mock
	private WebServiceTemplate wsT;

	@InjectMocks
	private EmployeeController empCont;

	private MockMvc mockMvc;

	@Before
	public void setup() {

		// Process mock annotations
		MockitoAnnotations.initMocks(this);

		// Setup Spring test in standalone mode
		this.mockMvc = MockMvcBuilders.standaloneSetup(empCont).build();
		mockServer = MockWebServiceServer.createServer(client);
	}

	@Test
	public void testEmployeeServicee() throws Exception {
		// Mocking the request
		GetEmployeeRequest request = new GetEmployeeRequest();
		request.setName("arun");
		// Mocking the response
		Employee emp = new Employee();
		emp.setDesignation("as");
		emp.setId(12);
		emp.setName("arun");
		// emp.setRole();
		GetEmployeeResponse res = new GetEmployeeResponse();
		res.setEmployee(emp);

		// Mocking the marshal support
		Mockito.when(wsGateway.getWebServiceTemplate()).thenReturn(wsT);
		Mockito.when(wsT.marshalSendAndReceive(request,
				new SoapActionCallback("http://10.144.135.147:8090/ws/getEmployeeResponse"))).thenReturn(res);
		Mockito.when(wsExtend.getMarshalSupport(request)).thenReturn(res);
		assertNotNull(this.mockMvc.perform(post("/getEmployee").param("name", "arun")).andExpect(status().isOk()));
		// mockServer.verify();
	}

}
