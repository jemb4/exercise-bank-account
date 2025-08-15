package dev.jesus.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  private BankAccount bankAccount;

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
    bankAccount = new BankAccount(100, 10);

  }

  @AfterEach
  void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  void testCalculateMonthlyInterest() {
    float monthlyRateExpected = bankAccount.getAnualRate() / 12 / 100;
    float monthlyInterestExpected = monthlyRateExpected * bankAccount.getBalance();
    float expected = bankAccount.getBalance() + monthlyInterestExpected;

    bankAccount.calculateMonthlyInterest();
    float result = bankAccount.getBalance();

    assertThat(result, is(equalTo(expected)));
  }

  @Test
  void testDeposit() {
    float deposit = 100;
    float expected = 200;

    bankAccount.deposit(deposit);
    float result = bankAccount.getBalance();

    assertThat(result, is(equalTo(expected)));
  }

  @Test
  void testMonthlyStatement() {
    float monthlyRateExpected = bankAccount.getAnualRate() / 12 / 100;
    float monthlyInterestExpected = monthlyRateExpected * (bankAccount.getBalance() - bankAccount.getMonthlyFee());
    float expected = bankAccount.getBalance() + monthlyInterestExpected;

    bankAccount.monthlyStatement();
    float result = bankAccount.getBalance();

    assertThat(result, is(equalTo(expected)));
  }

  @Test
  void testPrintAccountDetails() {
    String stringExpected1 = "Saldo: " + bankAccount.getBalance();
    String stringExpected2 = "Número de retiros: " + bankAccount.getNumberOfDeposits();
    String stringExpected3 = "Comisión mensual: " + bankAccount.getMonthlyFee();
    String stringExpected4 = "Número de retiros: " + bankAccount.getNumberOfWithdrawals();

    bankAccount.printAccountDetails();

    assertTrue(outputStreamCaptor.toString().contains(stringExpected1));
    assertTrue(outputStreamCaptor.toString().contains(stringExpected2));
    assertTrue(outputStreamCaptor.toString().contains(stringExpected3));
    assertTrue(outputStreamCaptor.toString().contains(stringExpected4));
  }

  @Test
  void testWithdraw() {
    float withdraw = 100;
    float expected = 0;

    bankAccount.withdraw(withdraw);
    float result = bankAccount.getBalance();

    assertThat(result, is(equalTo(expected)));
  }

  @Test
  void testWithdraw_WichIsNotPossible() {
    float withdraw = 120;
    float expected = 100;
    String expectedString = "No tienes suficiente saldo para hacer este retiro.\r\n" + //
        "";

    bankAccount.withdraw(withdraw);
    float result = bankAccount.getBalance();

    assertThat(result, is(equalTo(expected)));
    assertThat(outputStreamCaptor.toString(), is(equalTo(expectedString)));
  }
}
