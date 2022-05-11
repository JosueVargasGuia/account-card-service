package com.nttdata.accountcard.service;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.nttdata.accountcard.service.controller.AccountCardController;
import com.nttdata.accountcard.service.entity.AccountCard;
import com.nttdata.accountcard.service.entity.TypeAccount;
import com.nttdata.accountcard.service.service.AccountCardService;
import reactor.test.StepVerifier;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
@Log4j2
 
@ExtendWith(SpringExtension.class)
@WebFluxTest(AccountCardController.class)
class AccountCardControllerTests {
	 @Autowired
	 private WebTestClient webTestClient;
	 @MockBean
	 private AccountCardService accountCardService;

	@Test
	public void Add() {
		var employeeMono = Mono.just(new AccountCard(Long.valueOf(1),Long.valueOf(1),Long.valueOf(1),Long.valueOf(1),true,TypeAccount.BankAccounts,Calendar.getInstance().getTime(),Calendar.getInstance().getTime()));
		/*when(accountCardService.save(new AccountCard(Long.valueOf(1),Long.valueOf(1),Long.valueOf(1),Long.valueOf(1),true,TypeAccount.BankAccounts,Calendar.getInstance().getTime(),Calendar.getInstance().getTime()))).thenReturn(employeeMono);
		webTestClient.post().uri("/accountcard")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(employeeMono,AccountCard.class)
				.exchange()
				.expectStatus().isOk();*/
	}
	@Test
	public void findById() {
		var employeeMono = Mono.just(new AccountCard(Long.valueOf(1),Long.valueOf(1),Long.valueOf(1),Long.valueOf(1),true,TypeAccount.BankAccounts,Calendar.getInstance().getTime(),Calendar.getInstance().getTime()));
		when(accountCardService.findByIdMono(any())).thenReturn(employeeMono);
		var employeeResp = webTestClient.get().uri("/accountcard/1")
				.exchange()
				.expectStatus().isOk()
				.returnResult(AccountCard.class)
				.getResponseBody();

		StepVerifier.create(employeeMono)
				.expectSubscription()
				.expectNextMatches(e -> e.getIdAccount()==1)				
				.verifyComplete();
	}
}
