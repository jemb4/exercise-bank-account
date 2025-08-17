package dev.jesus.model;

public class CheckingAccount extends BankAccount {
  private float overdraft;

  public CheckingAccount(float balance, float anualRate) {
    super(balance, anualRate);
    this.overdraft = 0;
  }

  public float getOverdraft() {
    return overdraft;
  }

  @Override
  public void withdraw(float amount) {
    if (getBalance() < amount) {
      overdraft = amount - getBalance();
      super.withdraw(getBalance());
      return;
    }

    super.withdraw(amount);
  }

  @Override
  public void deposit(float amount) {
    boolean isOverdrafted = overdraft > 0;

    if (isOverdrafted && overdraft == amount) {
      overdraft = 0;
      return;
    }

    if (isOverdrafted && overdraft > amount) {
      overdraft -= amount;
      return;
    }

    if (isOverdrafted && overdraft < amount) {
      super.deposit(amount - overdraft);
      overdraft = 0;
      return;
    }

    super.deposit(amount);
  }

  @Override
  public void monthlyStatement() {
    super.monthlyStatement();
  }

  @Override
  public void printAccountDetails() {
    System.out.println("Saldo: " + getBalance());
    System.out.println("Comisión mensual: " + getMonthlyFee());
    System.out.println("Transacciones realizadas: %s consignaciones y %s retiros".formatted(
        getNumberOfDeposits(),
        getNumberOfWithdrawals()));
    System.out.println("Valor de sobregiro: " + getOverdraft());
  }
}
