package com.siva.banking.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.siva.banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Long>{

}
