package dev.jesus.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @AfterEach
  void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  void testCalculateMonthlyInterest() {
    BankAccount bankAccount = new BankAccount(0, 0);
  }

  @Test
  void testDeposit() {
    BankAccount bankAccount = new BankAccount(100, 0);
    float deposit = 100;
    float expected = 200;

    bankAccount.deposit(deposit);
    float result = bankAccount.getBalance();

    assertThat(result, is(equalTo(expected)));
  }

  @Test
  void testMonthlyStatement() {

  }

  @Test
  void testPrintAccountDetails() {

  }

  @Test
  void testWithdraw() {
    BankAccount bankAccount = new BankAccount(100, 0);
    float withdraw = 100;
    float expected = 0;

    bankAccount.withdraw(withdraw);
    float result = bankAccount.getBalance();

    assertThat(result, is(equalTo(expected)));
  }
}
