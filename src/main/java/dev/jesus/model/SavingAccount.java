package dev.jesus.model;

public class SavingAccount extends BankAccount {

  private boolean isActive;

  public SavingAccount(float balance, float anualRate) {
    super(balance, anualRate);
    this.isActive = (balance >= 10000);
  }

  private void updateActiveStatus() {
    this.isActive = (getBalance() >= 10000);
  }

  @Override
  public void deposit(float amount) {
    if (!isActive)
      return;

    super.deposit(amount);
  }

  @Override
  public void withdraw(float ammount) {
    if (!isActive)
      return;

    super.withdraw(ammount);
  }

  @Override
  public void monthlyStatement() {
    float extraFees = 0;

    if (getNumberOfWithdrawals() > 4)
      extraFees = getNumberOfWithdrawals() - 4;

    setMonthlyFee(extraFees * 1000);

    super.monthlyStatement();

    updateActiveStatus();
  }

  public void printAccountDetails() {
    System.out.println("Saldo: " + getBalance());
    System.out.println("Comisión Mensual: " + getMonthlyFee());
    System.out.println("Transacciones realizadas: %s consignaciones y %s retiros".formatted(
        getNumberOfDeposits(),
        getNumberOfWithdrawals()));
  }

  public boolean getIsActive() {
    return isActive;
  }

}
