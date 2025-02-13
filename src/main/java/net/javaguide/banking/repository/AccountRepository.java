package net.javaguide.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguide.banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
