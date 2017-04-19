/**
 * 
 */
package hello;

import hello.wsdl.GetEmployeeRequest;
import hello.wsdl.GetEmployeeResponse;

/**
 * @author TCS
 *
 */
public class ServiceClient extends WebserviceTemplateExtended {
	
	
	public GetEmployeeResponse getEmployeeDetails(String name) {
	GetEmployeeRequest request = new GetEmployeeRequest();
	request.setName(name);
	GetEmployeeResponse response = (GetEmployeeResponse) getMarshalSupport(request);
	return response;
}
}
