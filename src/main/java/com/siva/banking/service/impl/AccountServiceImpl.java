package com.siva.banking.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.siva.banking.dto.AccountDto;
import com.siva.banking.entity.Account;
import com.siva.banking.mapper.AccountMapper;
import com.siva.banking.repository.AccountRepository;
import com.siva.banking.service.AccountService;

@Service

public class AccountServiceImpl implements AccountService {

    

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.maptoAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
        .findById(id)
        .orElseThrow(()-> new RuntimeException("not exits")); 
        return AccountMapper.maptoAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        
        Account account = accountRepository
        .findById(id)
        .orElseThrow(()-> new RuntimeException("not exits")); 

        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.maptoAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {

        Account account = accountRepository
        .findById(id)
        .orElseThrow(()-> new RuntimeException("not exits"));

        if(account.getBalance() < amount){
            throw new RuntimeException("insuficient amount");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        
        return AccountMapper.maptoAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account>accounts = accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.maptoAccountDto(account))
        .collect(Collectors.toList());        
    
    }

    @Override
    public void deleteAccount(Long id) {

        Account account = accountRepository
        .findById(id)
        .orElseThrow(()-> new RuntimeException("not exits"));

        accountRepository.deleteById(id);
        
        
    }

    


    
}
