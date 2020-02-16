package com.aitsl.borrower.borrowerservice;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableFeignClients("com.aitsl.borrower.borrowerservice")
public class BorrowerController {

	@Autowired
	LoanRepository loanRepository;
	
	@Autowired
	UserServiceProxy userServiceFeignProxy;

	@Autowired
	DrawdownRepository drawdownRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("borrowers/{borrowerId}/loans/{loanId}")
	public Loan getLoan(@PathVariable long borrowerId, @PathVariable String loanId) {

		if (borrowerId <= 0 || loanId.isBlank()) {
			return null;
		} else {
			/*
			 * List<Loan> loans = loanRepository.findAll().stream() .filter(e ->
			 * (e.getBorrowerId() == borrowerId && e.getLoanId().equalsIgnoreCase(loanId)))
			 * .collect(Collectors.toList());
			 * 
			 * if(loans.size() > 0) { return loans.get(0); }else { return null; }
			 */

			/*########### USING RESTTEMPLATE ###############*/
			
			/*
			 * HttpHeaders headers = createHttpHeaders("secretuser", "secretpassword");
			 * HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			 * 
			 * Map<String, String> uriVariables = new HashMap<>(); uriVariables.put("id",
			 * String.valueOf(borrowerId));
			 * 
			 * ResponseEntity<UserDetail> responseEntity = new RestTemplate()
			 * //.getForEntity("http://localhost:8000/users/{id}", UserDetail.class,
			 * uriVariables); .exchange("http://localhost:8000/users/{id}", HttpMethod.GET,
			 * entity, UserDetail.class, uriVariables);
			 * 
			 * UserDetail userDetails = responseEntity.getBody();
			 */			
			
			/*########### USING FEIGN ###############*/
			
			Optional<UserDetail> userDetails = userServiceFeignProxy.getUserInfo(borrowerId);

			if (!userDetails.isPresent()) {
				return null;
			}
			
			logger.info("User Details: " + userDetails + " :::::::::::::: Port ::::::::: " + userDetails.get().getPort());

			return loanRepository.findByLoanIdAndBorrowerId(loanId, borrowerId);
		}

	}

	@GetMapping("borrowers/{borrowerId}/loans")
	public List<Loan> getLoans(@PathVariable long borrowerId) {

		if (borrowerId <= 0) {
			return null;
		} else {

			return loanRepository.findAll().stream().filter(e -> e.getBorrowerId() == borrowerId)
					.collect(Collectors.toList());
			// return loanRepository.findByLoanIdAndBorrowerId(loanId, borrowerId);
		}
	}

	private HttpHeaders createHttpHeaders(String user, String password) {

		String notEncoded = user + ":" + password;
		String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Basic " + encodedAuth);
		return headers;
	}
}
