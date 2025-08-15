package dev.jesus.model;

public class BankAccount {

  private float balance;
  private int numberOfDeposits;
  private int numberOfWithdrawals;
  private float anualRate;
  private float monthlyFee;

  public BankAccount(float balance, float anualRate) {
    this.balance = balance;
    this.anualRate = anualRate;
    numberOfDeposits = 0;
    numberOfWithdrawals = 0;
    monthlyFee = 0;
  }

  public void deposit(float amount) {
    numberOfDeposits++;
    balance += amount;
  }

  public void withdraw(float amount) {
    if (balance < amount) {
      System.out.println("No tienes suficiente saldo para hacer este retiro.");
    }

    if (balance >= amount) {
      numberOfWithdrawals++;
      balance -= amount;
    }
  }

  public void calculateMonthlyInterest() {
    float monthlyRate = anualRate / 12 / 100;
    float monthlyInterest = monthlyRate * balance;
    balance += monthlyInterest;
  }

  public void monthlyStatement() {
    balance -= monthlyFee;
    calculateMonthlyInterest();
  }

  public void printAccountDetails() {
    System.out.println("Saldo: " + balance);
    System.out.println("Número de consignacones: " + numberOfDeposits);
    System.out.println("Número de retiros: " + numberOfWithdrawals);
    System.out.println("Tasa anual: " + anualRate + "%");
    System.out.println("Comisión mensual: " + monthlyFee);
  }

  public float getBalance() {
    return balance;
  }

  public int getNumberOfDeposits() {
    return numberOfDeposits;
  }

  public int getNumberOfWithdrawals() {
    return numberOfWithdrawals;
  }

  public float getAnualRate() {
    return anualRate;
  }

  public float getMonthlyFee() {
    return monthlyFee;
  }
}
