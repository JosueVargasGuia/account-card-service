package com.nttdata.accountcard.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.accountcard.service.entity.AccountCard;
@Repository
public interface AccountCardRepository extends ReactiveMongoRepository<AccountCard, Long> {

}
