package dao.impl;

import dao.UserDao;
import entity.Goods;
import entity.User;
import util.EncryptMd5;
import util.InputUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author test
 * @classname UserDaoImpl
 * @date 2023/2/25 17:49
 */
public class UserDaoImpl implements UserDao {
    public static List<User> users = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    int userId;

    @Override
    public void insert() {
        if (users.size() == 0) {
            userId = 0;
        } else {
            userId = users.get(users.size() - 1).getUserId();
        }
        System.out.println("请输入会员名称");
        String userName = scanner.next();
        for (User user : users) {
            if (userName.equals(user.getUserName())) {
                System.out.println("已有此会员，请重新输入");
                return;
            }
        }
        System.out.println("请输入密码");
        String userPassword = scanner.next();
        userPassword = EncryptMd5.getMd5(userPassword);
        System.out.println("请输入联系方式");
        String userTel = scanner.next();
        System.out.println("请输入会员积分");
        double userPoints = scanner.nextDouble();
        System.out.println("请输入余额");
        BigDecimal userBalance = scanner.nextBigDecimal();
        users.add(new User().setUserId(++userId).setUserName(userName).setUserPassword(userPassword).
                setUserTel(userTel).setUserPoints(userPoints).setUserBalance(userBalance));
        System.out.println("添加<<" + userName + ">>成功");

    }

    @Override
    public void delete() {
        findAll();
        System.out.println("请输入要删除的会员编号");
        int userId = scanner.nextInt();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (userId == user.getUserId()) {
                users.remove(i);
                System.out.println("删除<<" + user.getUserName() + ">>成功");
            }
        }
    }

    @Override
    public void update() {
        findAll();
        System.out.println("请输入要修改的会员编号");
        int userId = scanner.nextInt();
        for (User user : users) {
            if (user.getUserId() == userId) {
                while (true) {
                    System.out.println("|会员编号|  会员名称  |  会员联系方式  | 积分 | 余额 |");
                    System.out.print("\t" + user.getUserId());
                    System.out.print("\t\t" + user.getUserName());
                    System.out.print("\t\t" + user.getUserTel());
                    System.out.print("\t\t" + user.getUserPoints());
                    System.out.println("\t" + user.getUserBalance());
                    System.out.println("1.修改会员名称");
                    System.out.println("2.修改会员联系方式");
                    System.out.println("3.修改会员积分");
                    System.out.println("4.修改会员余额");
                    System.out.println("0.修改完毕");
                    String input = InputUtil.input("^[0-4]$", "选择你要修改的属性");
                    switch (input) {
                        case "1":
                            System.out.println("输入修改后的会员名称");
                            String userName = scanner.next();
                            user.setUserName(userName);
                            System.out.println("修改成功");
                            break;
                        case "2":
                            System.out.println("输入修改后的会员联系方式");
                            String userTel = scanner.next();
                            user.setUserTel(userTel);
                            System.out.println("修改成功");
                            break;
                        case "3":
                            System.out.println("输入修改后的会员积分");
                            double userPoints = scanner.nextDouble();
                            user.setUserPoints(userPoints);
                            System.out.println("修改成功");
                            break;
                        case "4":
                            System.out.println("输入修改后的会员余额");
                            BigDecimal userBalance = scanner.nextBigDecimal();
                            user.setUserBalance(userBalance);
                            System.out.println("修改成功");
                            break;
                        case "0":
                            System.out.println("修改<<" + user.getUserName() + ">>完毕");
                            return;
                    }
                }

            }
        }
    }

    @Override
    public void findAll() {
        System.out.println("|会员编号|    会员名称    |  会员联系方式  | 积分 | 余额 |");
        for (User user : users) {
            System.out.print("\t" + user.getUserId());
            System.out.print("\t\t" + user.getUserName());
            System.out.print("\t\t" + user.getUserTel());
            System.out.print("\t\t" + user.getUserPoints());
            System.out.println("\t\t" + user.getUserBalance());
        }
    }

    public void findByID(User user) {
        System.out.println("|会员编号|    会员名称    |  会员联系方式  | 积分 | 余额 |");
        System.out.print("\t" + user.getUserId());
        System.out.print("\t\t" + user.getUserName());
        System.out.print("\t\t" + user.getUserTel());
        System.out.print("\t\t" + user.getUserPoints());
        System.out.println("\t\t" + user.getUserBalance());
    }

    public void recharge() {
        findAll();
        System.out.println("选择你要充值的会员编号");
        int userId = scanner.nextInt();
        for (User user : users) {
            if (user.getUserId() == userId) {
                System.out.println("输入你要为<<" + user.getUserName() + ">>充值的金额");
                BigDecimal recharge = scanner.nextBigDecimal();
                user.setUserBalance(recharge.add(user.getUserBalance()));
                System.out.println("已为<<" + user.getUserName() + ">>充值" + recharge + "元,拥有余额为" + user.getUserBalance());
            }
        }
    }
}
