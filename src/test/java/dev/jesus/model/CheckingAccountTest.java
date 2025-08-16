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

public class CheckingAccountTest {

  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  private CheckingAccount checkingAccount;

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
    checkingAccount = new CheckingAccount(10000, 100);
  }

  @AfterEach
  void tearDown() {
    System.setErr(standardOut);
  }

  @Test
  void testDeposit() {

  }

  @Test
  void testGetOverdraft() {

  }

  @Test
  void testMonthlyStatement() {
    float monthlyRateExpected = checkingAccount.getAnualRate() / 12 / 100;
    float monthlyInterestExpected = monthlyRateExpected
        * (checkingAccount.getBalance() - checkingAccount.getMonthlyFee());
    float expected = checkingAccount.getBalance() + monthlyInterestExpected;

    checkingAccount.monthlyStatement();
    float result = checkingAccount.getBalance();

    assertThat(result, is(equalTo(expected)));
  }

  @Test
  void testPrintAccountDetails() {
    String stringExpected1 = "Saldo: " + checkingAccount.getBalance();
    String stringExpected2 = "Comisión mensual: " + checkingAccount.getMonthlyFee();
    String stringExpected3 = ("Transacciones realizadas: %s consignaciones y %s retiros".formatted(
        checkingAccount.getNumberOfDeposits(),
        checkingAccount.getNumberOfWithdrawals()));
    String stringExpected4 = "Valor de sobregiro: " + checkingAccount.getOverdraft();

    checkingAccount.printAccountDetails();

    assertTrue(outputStreamCaptor.toString().contains(stringExpected1));
    assertTrue(outputStreamCaptor.toString().contains(stringExpected2));
    assertTrue(outputStreamCaptor.toString().contains(stringExpected3));
    assertTrue(outputStreamCaptor.toString().contains(stringExpected4));
  }

  @Test
  void testWithdraw() {

  }
}
