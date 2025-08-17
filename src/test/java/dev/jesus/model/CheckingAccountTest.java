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
  private CheckingAccount checkingAccountLowBalance;

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
    checkingAccount = new CheckingAccount(10000, 100);
    checkingAccountLowBalance = new CheckingAccount(100, 100);
  }

  @AfterEach
  void tearDown() {
    System.setErr(standardOut);
  }

  @Test
  void testWithdraw_WhenAmount_IsGreater_ThanBalance() {
    float expectedBalance = 0;
    float expectedOverdraft = 900;

    checkingAccountLowBalance.withdraw(1000);
    float resultBalance = checkingAccountLowBalance.getBalance();
    float resultOverdraft = checkingAccountLowBalance.getOverdraft();

    assertThat(expectedBalance, is(equalTo(resultBalance)));
    assertThat(expectedOverdraft, is(equalTo(resultOverdraft)));
  }

  @Test
  void testWithdraw_WhenAmount_IsLower_ThanBalance() {
    float expectedBalance = 9900;
    float expectedOverdraft = 0;

    checkingAccount.withdraw(100);
    float resultBalance = checkingAccount.getBalance();
    float resultOverdraft = checkingAccount.getOverdraft();

    assertThat(expectedBalance, is(equalTo(resultBalance)));
    assertThat(expectedOverdraft, is(equalTo(resultOverdraft)));
  }

  @Test
  void testDeposit_WhenOverdraftedAnd_AmountGreaterThanOverDraft() {
    checkingAccountLowBalance.withdraw(200);
    float expectedBalance = 100;
    float expectedOverdraft = 0;

    checkingAccountLowBalance.deposit(200);
    float resultBalance = checkingAccountLowBalance.getBalance();
    float resultOverdraft = checkingAccountLowBalance.getOverdraft();

    assertThat(expectedBalance, is(equalTo(resultBalance)));
    assertThat(expectedOverdraft, is(equalTo(resultOverdraft)));
  }

  @Test
  void testDeposit_WhenOverdraftedAnd_AmountLessThanOverDraft() {
    checkingAccountLowBalance.withdraw(200);
    float expectedBalance = 0;
    float expectedOverdraft = 50;

    checkingAccountLowBalance.deposit(50);
    float resultBalance = checkingAccountLowBalance.getBalance();
    float resultOverdraft = checkingAccountLowBalance.getOverdraft();

    assertThat(expectedOverdraft, is(equalTo(resultOverdraft)));
    assertThat(expectedBalance, is(equalTo(resultBalance)));
  }

  @Test
  void testDeposit_WhenOverdraftedAnd_AmountEqualThanOverDraft() {
    checkingAccountLowBalance.withdraw(200);
    float expectedBalance = 0;
    float expectedOverdraft = 0;

    checkingAccountLowBalance.deposit(100);
    float resultBalance = checkingAccountLowBalance.getBalance();
    float resultOverdraft = checkingAccountLowBalance.getOverdraft();

    assertThat(expectedBalance, is(equalTo(resultBalance)));
    assertThat(expectedOverdraft, is(equalTo(resultOverdraft)));
  }

  @Test
  void testDeposit_Wheitout_Overdraft() {
    float deposit = 100;
    float expected = 10100;

    checkingAccount.deposit(deposit);
    float result = checkingAccount.getBalance();

    assertThat(result, is(equalTo(expected)));
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
}
