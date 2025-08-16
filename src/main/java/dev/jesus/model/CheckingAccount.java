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
    boolean isOverdraft = overdraft > 0;

    if (isOverdraft && overdraft == amount) {
      overdraft = 0;
      return;
    }

    if (isOverdraft && overdraft > amount) {
      overdraft -= amount;
      return;
    }

    if (isOverdraft && overdraft < amount) {
      overdraft = 0;
      super.deposit(amount - overdraft);
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
