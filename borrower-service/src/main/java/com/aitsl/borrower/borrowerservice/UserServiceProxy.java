package com.aitsl.borrower.borrowerservice;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="user-service", url="localhost:8000")
//@FeignClient(name="user-service")
@FeignClient(name="netflix-zuul-api-gateway-server")
@RibbonClient(name="user-service")
public interface UserServiceProxy {

	//@GetMapping("users/{id}")
	@GetMapping("user-service/users/{id}")
	//public UserDetail getUserInfo(@PathVariable("id") long id);
	public Optional<UserDetail> getUserInfo(@PathVariable long id);
	
	@GetMapping("users")
	public List<UserDetail> getUsers();
}
