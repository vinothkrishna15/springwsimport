/**
 * 
 */
package hello.controller;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.ServiceClient;
import hello.ServiceConfig;
import hello.wsdl.Employee;
import hello.wsdl.GetEmployeeResponse;

/**
 * @author TCS
 *
 */
@RestController
public class EmployeeController {
	
	
	@SuppressWarnings("resource")
	@RequestMapping("/getEmployee")
    public Employee empDetails(@RequestParam(value="name", defaultValue="") String name) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ServiceConfig.class);
        ctx.refresh();
	    ServiceClient serviceClient = ctx.getBean(ServiceClient.class);
		GetEmployeeResponse response = serviceClient.getEmployeeDetails(name);
		Employee emp = new Employee();
		if (response.getEmployee() != null) {
		emp.setName(response.getEmployee().getName());
		emp.setId(response.getEmployee().getId());
		emp.setDesignation(response.getEmployee().getDesignation());
		emp.setRole(response.getEmployee().getRole());
		}
        return emp;
	}
}
