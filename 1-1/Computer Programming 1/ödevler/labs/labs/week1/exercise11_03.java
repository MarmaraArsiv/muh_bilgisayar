public class Exercise11_03 {
  public static void main(String[] args) {
    CheckingAccount checking = new CheckingAccount(1, 35);
    SavingsAccount savings = new SavingsAccount(2, 25);
    checking.withdraw(50);
    savings.withdraw(50);

    System.out.println(checking.getBalance());
    System.out.println(savings.getBalance());
  }
}

class CheckingAccount extends Account {
  protected int overdraftLimit = 5000;

  public CheckingAccount(int id, double balance) {
    super(id, balance);
  }

  @Override
  public void withdraw(double amount) {
	  if(balance - amount + overdraftLimit > 0)
	    balance -= amount;
	  else
		  System.out.println("You have exeeded overdraft limit!");
  }

  @Override
  public String toString() {
    return "Checkings";
  }
}

class SavingsAccount extends Account {

  public SavingsAccount(int id, double balance) {
    super(id, balance);
  }

  @Override
  public void withdraw(double amount) {
	  if(balance - amount > 0)
	    balance -= amount;
	  else
		  System.out.println("Insufficient balance");
  }

  @Override
  public String toString() {
    return "Saving";
  }
}
