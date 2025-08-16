package dev.jesus.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingAccountTest {

  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  private SavingAccount savingAccountActive;
  private SavingAccount savingAccountUnActive;

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
    savingAccountActive = new SavingAccount(10000, 100);
    savingAccountUnActive = new SavingAccount(100, 10);
  }

  @AfterEach
  void tearDown() {
    System.setErr(standardOut);
  }

  @Test
  void testValidDeposit() {
    float deposit = 100;
    float expected = 10100;

    savingAccountActive.deposit(deposit);
    float result = savingAccountActive.getBalance();

    assertThat(result, is(equalTo(expected)));
  }

  @Test
  void testUnvalidDeposit() {
    float deposit = 100;
    float expected = 100;

    savingAccountUnActive.deposit(deposit);
    float result = savingAccountUnActive.getBalance();

    assertThat(result, is(equalTo(expected)));
  }

  @Test
  void testMonthlyStatement_NoExtraFees() {
    float monthlyRateExpected = savingAccountActive.getAnualRate() / 12 / 100;
    float monthlyInterestExpected = monthlyRateExpected
        * (savingAccountActive.getBalance() - savingAccountActive.getMonthlyFee());
    float expected = savingAccountActive.getBalance() + monthlyInterestExpected;

    savingAccountActive.monthlyStatement();
    float result = savingAccountActive.getBalance();

    assertThat(result, is(equalTo(expected)));
  }

  @Test
  void testMonthlyStatement_WithExtraFees() {
    for (int i = 0; i < 5; i++) {
      savingAccountActive.withdraw(100.0f);
    }

    float baseBalance = savingAccountActive.getBalance();
    float extraFees = (savingAccountActive.getNumberOfWithdrawals() - 4) * 1000;
    float balanceAfterFees = baseBalance - extraFees;

    float monthlyRateExpected = savingAccountActive.getAnualRate() / 12 / 100;
    float monthlyInterestExpected = balanceAfterFees * monthlyRateExpected;
    float expected = balanceAfterFees + monthlyInterestExpected;

    savingAccountActive.monthlyStatement();
    float result = savingAccountActive.getBalance();

    assertFalse(savingAccountActive.getIsActive());
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  void testPrintAccountDetails() {
    String stringExpected1 = "Saldo: " + savingAccountActive.getBalance();
    String stringExpected2 = "Comisión Mensual: " + savingAccountActive.getMonthlyFee();
    String stringExpected3 = ("Transacciones realizadas: %s consignaciones y %s retiros".formatted(
        savingAccountActive.getNumberOfDeposits(),
        savingAccountActive.getNumberOfWithdrawals()));

    savingAccountActive.printAccountDetails();

    assertTrue(outputStreamCaptor.toString().contains(stringExpected1));
    assertTrue(outputStreamCaptor.toString().contains(stringExpected2));
    assertTrue(outputStreamCaptor.toString().contains(stringExpected3));
  }

  @Test
  void testValidWithdraw() {
    float withdraw = 100;
    float expected = 9900;

    savingAccountActive.withdraw(withdraw);
    float result = savingAccountActive.getBalance();

    assertThat(result, is(equalTo(expected)));
  }

  @Test
  void testUnvalidWithdraw() {
    float withdraw = 100;
    float expected = 100;

    savingAccountUnActive.withdraw(withdraw);
    float result = savingAccountUnActive.getBalance();

    assertThat(result, is(equalTo(expected)));
  }
}
