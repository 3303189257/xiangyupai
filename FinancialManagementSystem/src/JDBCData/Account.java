package JDBCData;
import JDBCData.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Account {
    private String userName;
    private String password;

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }



    public String getUserName() {
        return userName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 插入账户
     */
    public void insertAccount() {
        String sql = "insert into Account(userName,password) value(?,?)";
        connectTool.UPdate(sql, userName, password);
    }

    /**
     * 删除账户
     */
    public void deleteAccount() {
        String sql = "delete from Account where userName=? and password=?";
        connectTool.UPdate(sql, userName, password);
    }

    /**
     * 账户更新
     */
    public void updateAccount() {
        String sql = "update  Account set password=? where userName=?";
        connectTool.UPdate(sql, password, userName);
    }

    /**
     * 账户查询
     *
     * @return
     */
    public Account selectAccount() {
        String sql = "select userName,password from Account where userName=? and password=?";
        List<Account> accounts = connectTool.selectAll(Account.class, sql, userName, password);
        Iterator<Account> iterator = accounts.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    public Account() {
    }

    /**
     * 查询是否存在此用户名
     *
     * @return
     */
    public boolean selectUserName() {
        String sql = "select userName from Account where userName=?";
        List<Account> accounts = connectTool.selectAll(Account.class, sql, userName);
        Iterator<Account> iterator = accounts.iterator();
        if (iterator.hasNext()) {
            return true;
        } else return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(userName, account.userName) &&
                Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }


    /**
     * 查询花费清单
     *
     * @return
     */
    public List<costList> selectCostList() {
        String sql = "select event,userName,count,Reason from costList where userName=?";
        return connectTool.selectAll(costList.class, sql, this.userName);
    }

    /**
     * 插入花费清单
     *
     * @param count
     * @param Reason
     */
    public void insertCostList(String count, String Reason) {
        String sql = "insert into costList(userName,count,Reason) value(?,?,?)";
        connectTool.UPdate(sql, userName, Integer.parseInt(count), Reason);
    }

    /**
     * 删除花费清单，按event删除
     *
     * @param event
     * @param count
     * @param Reason
     */
    public void deleteCostList(int event, int count, String Reason) {
        String sql = "delete from costList where event=? and count=? and Reason=?";
        connectTool.UPdate(sql, event, count, Reason);
    }
}