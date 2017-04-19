/**
 * 
 */
package hello;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import hello.wsdl.GetEmployeeRequest;

/**
 * @author TCS
 *
 */
public class WebserviceTemplateExtended extends WebServiceGatewaySupport {

	public Object getMarshalSupport(GetEmployeeRequest request) {
		return getWebServiceTemplate().marshalSendAndReceive(
				request, new SoapActionCallback("http://10.144.135.147:8090/ws/getEmployeeResponse"));
		
	}
	
}
