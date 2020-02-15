package com.aitsl.borrower.borrowerservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

	Loan findByLoanIdAndBorrowerId(String loanId, long borrowerId);

}
