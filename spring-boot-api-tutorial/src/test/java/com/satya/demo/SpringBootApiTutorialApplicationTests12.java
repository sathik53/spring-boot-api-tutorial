
package com.satya.demo;

import com.satya.demo.employee.model.Employee;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApiTutorialApplicationTests12.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootApiTutorialApplicationTests12 {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllEmployee() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/emp",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetEmpById() {
		Employee emp = restTemplate.getForObject(getRootUrl() + "/api/emp/1", Employee.class);
		System.out.println(emp.getName());
		Assert.assertNotNull(emp);
	}

	@Test
	public void testCreateEmployee() {
		Employee emp = new Employee();
		emp.setEmail("admin@gmail.com");
		emp.setName("admin");


		ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/emp", emp, Employee.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdatePost() {
		int id = 1;
		Employee emp = restTemplate.getForObject(getRootUrl() + "/api/emp/" + id, Employee.class);
		emp.setName("admin1");
		emp.setEmail("admin2@email.com");

		restTemplate.put(getRootUrl() + "/api/emp/" + id, emp);

		Employee updatedEmp = restTemplate.getForObject(getRootUrl() + "/api/emp/" + id, Employee.class);
		Assert.assertNotNull(updatedEmp);
	}

	@Test
	public void testDeletePost() {
		int id = 2;
		Employee emp = restTemplate.getForObject(getRootUrl() + "/api/emp/" + id, Employee.class);
		Assert.assertNotNull(emp);

		restTemplate.delete(getRootUrl() + "/api/emp/" + id);

		try {
			emp = restTemplate.getForObject(getRootUrl() + "/api/emp/" + id, Employee.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
