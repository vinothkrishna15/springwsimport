/**
 * 
 */
package hello;

import hello.WebServiceClient;
import hello.wsdl.Employee;
import hello.wsdl.GetEmployeeResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author TCS
 *
 */
@Controller
public class EmployeeController {
	
	@Autowired
	WebServiceClient webServicesClient;

	@RequestMapping("/getEmployee")
    public Employee empDetails(@RequestParam(value="name", defaultValue="") String name) {
		GetEmployeeResponse response = webServicesClient.getDetails(name);
		Employee emp = new Employee();
		
		emp.setName(response.getEmployee().getName());
		emp.setId(response.getEmployee().getId());
		emp.setDesignation(response.getEmployee().getDesignation());
		emp.setRole(response.getEmployee().getRole());
        return emp;
        
        
		
    }
}
