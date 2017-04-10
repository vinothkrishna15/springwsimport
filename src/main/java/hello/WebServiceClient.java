
package hello;

import hello.wsdl.Employee;
import hello.wsdl.GetEmployeeResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class WebServiceClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(WebServiceClient.class);

	public GetEmployeeResponse getDetails(String name) {

		Employee request = new Employee();
		request.setName(name);

		log.info("Requesting details for " + name);

		GetEmployeeResponse response = (GetEmployeeResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://http://localhost:8090/index.html",
						request,
						new SoapActionCallback("http://localhost:8090/ws/GetEmployee"));

		return response;
	}

}
