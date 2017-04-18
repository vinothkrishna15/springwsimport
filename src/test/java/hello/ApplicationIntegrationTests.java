/**
 * 
 */
package hello;

import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;
import hello.controller.EmployeeController;
import hello.wsdl.Employee;
import hello.wsdl.GetEmployeeResponse;

import javax.xml.transform.Source;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.StringSource;

/**
 * @author TCS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfig.class, ServiceClient.class })
public class ApplicationIntegrationTests {

	@Configuration
	static class ApplicationIntegrationTestsConfiguration {

		@Bean
		public ServiceClient client() {
			return Mockito.mock(ServiceClient.class);
		}

		@Bean
		public EmployeeController employeeController() {
			return new EmployeeController();
		}
	}

	@Autowired
	private ServiceClient client;

	@Autowired
	private EmployeeController empCont;

	private MockWebServiceServer mockServer;

	@Before
	public void createServer() throws Exception {
		mockServer = MockWebServiceServer.createServer(client);

	}

	public void setup() throws Exception {
		Employee emp = new Employee();
		emp.setId(21);
		emp.setDesignation("ITA");
		emp.setName("arun");
		//emp.setRole();

		  //Mockito.when(this.empCont.empDetails(("arun")).thenReturn(emp);
		  }

	@SuppressWarnings("unused")
	@Test
	public void customerClient() throws Exception {

		empCont.empDetails("arun");
		Source requestPayload = new StringSource(
				" xmlns:gs='http://com.example/webservice/generatedwsdl'>"
						+ "<soapenv:Header/> <soapenv:Body> "
						+ "<gs:getEmployeeRequest>"
						+ " <gs:name>arun</gs:name>"
						+ "      </gs:getEmployeeRequest>");
		Source responsePayload = new StringSource(
				"<ns2:getEmployeeResponse xmlns:ns2='http://com.example/webservice/generatedwsdl'>"
						+ "  <ns2:employee>"
						+ " <ns2:name>arun</ns2:name>"
						+ " <ns2:id>535141</ns2:id>"
						+ " <ns2:designation>ITA</ns2:designation>"
						+ " <ns2:role>developer</ns2:role>    </ns2:employee> </ns2:getEmployeeResponse>");

		mockServer.expect(payload(requestPayload)).andRespond(
				withPayload(responsePayload));
		GetEmployeeResponse result = client.getEmployeeDetails("arun");
		// assertEquals(10, result);
		mockServer.verify();
	}

	@Test
	public void testGetEmployeeDetails() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.empCont).build();
		//mockMvc.perform(
			//	post("/getEmployee").param("arun"));
				
	}
}
