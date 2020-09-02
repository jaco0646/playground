package design.patterns.state;

public class Account {
    private int balance = 0;
    private AccountState currentState = AccountState.ACTIVE;

    public int deposit(int amount) {
        balance = currentState.deposit(balance, amount);
        return balance;
    }

    public int withdraw(int amount) {
        balance = currentState.withdraw(balance, amount);
        return balance;
    }

    public AccountState activate() {
        this.currentState = AccountState.ACTIVE;
        return currentState;
    }

    public AccountState block() {
        this.currentState = AccountState.BLOCKED;
        return currentState;
    }

    public enum AccountState {
        ACTIVE {
            @Override int deposit(int balance, int amount) {
                return balance + amount;
            }
            @Override int withdraw(int balance, int amount) {
                int newBalance = balance - amount;
                if (newBalance >= 0) {
                    return newBalance;
                }
                throw new IllegalArgumentException("Withdrawal amount is greater than balance.");
            }
        },
        BLOCKED {
            @Override int deposit(int balance, int amount) {
                throw new UnsupportedOperationException("Account is blocked.");
            }
            @Override int withdraw(int balance, int amount) {
                throw new UnsupportedOperationException("Account is blocked.");
            }
        };
        abstract int deposit(int balance, int amount);
        abstract int withdraw(int balance, int amount);
    }
}