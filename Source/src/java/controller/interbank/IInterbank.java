package controller.interbank;

import java.io.IOException;

public interface IInterbank {

    public void addMoney(int money) throws IOException;
    public int getMoney() throws IOException;
    public void subMoney(int money) throws IOException;
    public void resetMoney();

}