/**
 * 
 */
package hello;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import hello.wsdl.GetEmployeeRequest;
import hello.wsdl.GetEmployeeResponse;

/**
 * @author TCS
 *
 */
public class ServiceClient extends WebServiceGatewaySupport {
	public GetEmployeeResponse getEmployeeDetails(String name) {
	GetEmployeeRequest request = new GetEmployeeRequest();
	request.setName(name);
	GetEmployeeResponse response = (GetEmployeeResponse) getWebServiceTemplate().marshalSendAndReceive(
			request, new SoapActionCallback("http://10.144.135.147:8090/ws/getEmployeeResponse"));
	return response;
}
}
