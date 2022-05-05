package com.nttdata.accountcard.service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.accountcard.service.entity.AccountCard;
import com.nttdata.accountcard.service.service.AccountCardService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("accountcard")
public class AccountCardController {
	@Autowired
	AccountCardService accountCardService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<AccountCard> findAll() {
		return accountCardService.findAll();

	}

	@PostMapping
	public Mono<ResponseEntity<AccountCard>> save(@RequestBody AccountCard accountCard) {
		return accountCardService.save(accountCard).map(_accountCard -> ResponseEntity.ok().body(_accountCard))
				.onErrorResume(e -> {
					log.info("Error:" + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				});
	}

	@GetMapping("/{idAccountCard}")
	public Mono<ResponseEntity<AccountCard>> findById(@PathVariable(name = "idAccountCard") long idAccountCard) {
		return accountCardService.findById(idAccountCard).map(accountCard -> ResponseEntity.ok().body(accountCard))
				.onErrorResume(e -> {
					log.info(e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}

	@GetMapping("/findByidCard/{idCard}")
	public Flux<AccountCard> findByIdCard(@PathVariable(name = "idCard") long idCard) {
		return accountCardService.findByIdCredit(idCard);
	}

	@PutMapping
	public Mono<ResponseEntity<AccountCard>> update(@RequestBody AccountCard accountCard) {

		Mono<AccountCard> mono = accountCardService.findById(accountCard.getIdAccountCard()).flatMap(objAccountCard -> {
			log.info("Update:[new]" + accountCard + " [Old]:" + objAccountCard);
			return accountCardService.update(accountCard);
		});

		return mono.map(_accountCard -> {
			log.info("Status:" + HttpStatus.OK);
			return ResponseEntity.ok().body(_accountCard);
		}).onErrorResume(e -> {
			log.info("Status:" + HttpStatus.BAD_REQUEST + " menssage" + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		}).defaultIfEmpty(ResponseEntity.noContent().build());

	}

	@DeleteMapping("/{idAccountCard}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable(name = "idAccountCard") long idAccountCard) {
		return accountCardService.findById(idAccountCard).flatMap(accountCard -> {
			return accountCardService.delete(accountCard.getIdAccountCard())
					.then(Mono.just(ResponseEntity.ok().build()));
		});
	}
	
	@PostMapping("/findByIdForExample")
	public Mono<AccountCard> findByIdForExample(@RequestBody AccountCard accountCard){
		return accountCardService.findByIdForExample(accountCard);
	}
}
