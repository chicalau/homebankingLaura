package com.mindhub.homebanking;

import com.fasterxml.jackson.databind.node.FloatNode;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {

			LocalDate today = LocalDate.now();

			Client client1 = new Client("melba", "merel", "melba@mindhub.com", passwordEncoder.encode("melba"));
			clientRepository.save(client1);

			Client client2 = new Client("fiorela", "cristaldo", "fio@gmail.com", passwordEncoder.encode("fio"));
			clientRepository.save(client2);

			Client client3 = new Client("laura", "chica", "laura@admin.com", passwordEncoder.encode("laura"));
			clientRepository.save(client3);

			Account account1 = new Account("VIN001", LocalDate.now(), 500000, client1, AccountType.AHORRO);
			accountRepository.save(account1);

			Account account2 = new Account("VIN002", today.plusDays(1), 750000,client1, AccountType.CORRIENTE );
			accountRepository.save(account2);

			Account account3 = new Account("VIN003", today.plusDays(-11), 150000,client2, AccountType.AHORRO );
			accountRepository.save(account3);

			Transaction transaction1 = new Transaction(TransactionType.DEBITO, -500, "starbucks",LocalDateTime.now().plusDays(-10), account1 );
			transactionRepository.save(transaction1);

			Transaction transaction2 = new Transaction(TransactionType.CREDITO, 10000, "pago por alquiler",LocalDateTime.now().plusDays(-8), account1);
			transactionRepository.save(transaction2);

			Transaction transaction3 = new Transaction(TransactionType.CREDITO, 20000, "pago alquiler bodega", LocalDateTime.now().plusDays(-5), account1);
			transactionRepository.save(transaction3);

			Transaction transaction4 = new Transaction(TransactionType.DEBITO, -6000, "decameron baru", LocalDateTime.now().plusDays(-3),account1);
			transactionRepository.save(transaction4);

			Transaction transaction5 = new Transaction(TransactionType.CREDITO, 3000, "administracion", LocalDateTime.now().plusDays(-3),account2);
			transactionRepository.save(transaction5);

			Transaction transaction6 = new Transaction(TransactionType.CREDITO, 10000, "mercadopago", LocalDateTime.now().plusDays(-3),account3);
			transactionRepository.save(transaction6);

			List<Integer> paymentsH = List.of(12, 24, 36, 48, 60);
			Loan loan1 = new Loan("Hipotecario", 500000, paymentsH,0.18 );
			loanRepository.save(loan1);

			List<Integer> paymentsP = List.of(6,12,24);
			Loan loan2 = new Loan("Personal", 100000, paymentsP, 0.22);
			loanRepository.save(loan2);

			List<Integer> paymentsA = List.of(6,12,24,36);
			Loan loan3 = new Loan("Automotriz", 300000,paymentsA, 0.12);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000, 60, client1, loan1);
			clientLoanRepository.save(clientLoan1);

			ClientLoan clientLoan2 = new ClientLoan(50000, 12, client1,loan2 );
			clientLoanRepository.save(clientLoan2);

			Card card1 = new Card(today.plusYears(-5), today.plusYears(5),CardType.DEBITO, CardColor.GOLD, "4656-5900-8090-8888", 711, client1);
			cardRepository.save(card1);

			Card card2 = new Card(today.plusYears(-4), today.plusDays(-60), CardType.CREDITO, CardColor.TITANIUM, "8978-8673-7777-8956", 116, client1);
			cardRepository.save(card2);

		};
	}

}
