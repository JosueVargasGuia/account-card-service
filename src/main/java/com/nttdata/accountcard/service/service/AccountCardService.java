package com.nttdata.accountcard.service.service;


import com.nttdata.accountcard.service.entity.AccountCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountCardService {
	Flux<AccountCard> findAll();

	AccountCard findById(Long idAccountCard);

	Mono<AccountCard> findByIdMono(Long idAccountCard);

	Mono<AccountCard> save(AccountCard accountCard);

	Mono<AccountCard> update(AccountCard accountCard);

	Mono<Void> delete(Long idAccountCard);

	Flux<AccountCard> findByIdCredit(Long idCard);


	Mono<AccountCard> findByAccountCardForExample(AccountCard accountCard);

	
	Mono<AccountCard> findByIdForExample(AccountCard accountCard);

}
