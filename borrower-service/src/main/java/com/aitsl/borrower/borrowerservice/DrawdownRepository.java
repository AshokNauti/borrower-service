package com.aitsl.borrower.borrowerservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawdownRepository extends JpaRepository<Drawdown, Long> {

}
