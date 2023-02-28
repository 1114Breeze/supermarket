import dao.GoodsDao;
import dao.GoodsTypeDao;
import dao.impl.BuyDaoImpl;
import dao.impl.GoodsDaoImpl;
import dao.impl.GoodsTypeDaoImpl;
import dao.impl.UserDaoImpl;
import entity.User;
import util.EncryptMd5;
import util.InputUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * @author test
 * @classname ${NAME}
 * @date $DATE ${TIME}
 */
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static GoodsTypeDao goodsTypeDao = new GoodsTypeDaoImpl();
    static GoodsDao goodsDao = new GoodsDaoImpl();
    static UserDaoImpl userDao = new UserDaoImpl();
    static BuyDaoImpl buyDaoImpl = new BuyDaoImpl();
    static int type;
    public static User user;
    static List<User> users = UserDaoImpl.users;
    public static void main(String[] args) {
        users.add(new User().setUserName("admin").setUserPassword(EncryptMd5.getMd5("123456")));
        users.add(new User().setUserId(1).setUserPassword(EncryptMd5.getMd5("123456")).setUserName("测试1").setUserBalance(new BigDecimal(2000)));
        users.add(new User().setUserId(2).setUserPassword(EncryptMd5.getMd5("123456")).setUserName("测试2").setUserBalance(new BigDecimal(3000)));
        System.out.println("欢迎使用超市管理系统");
        login();
//        main();
    }

    private static void login() {
        System.out.println("*****登录*****");
        System.out.println("请输入用户名");
        String userName = scanner.next();
        System.out.println("请输入密码");
        String userPassword = scanner.next();
        userPassword = EncryptMd5.getMd5(userPassword);
        for (User user1 : users) {
            if (user1.getUserName().equals(userName)){
                if (user1.getUserPassword().equals(userPassword)){
                    if (userName.equals("admin")&&userPassword.equals(EncryptMd5.getMd5("123456"))){
                        type=1;
                    }else {
                        type=0;
                    }
                    System.out.println("登录成功");
                    user = user1;
                    main();
                }
            }
        }
        System.out.println("登录失败,请重新登录");
        login();
    }

    private static void main() {
        while (true) {
            System.out.println("*******************主菜单*************************");
            System.out.println("                0.重新登录");
            System.out.println("                1.商品类型管理");
            System.out.println("                2.商品管理");
            System.out.println("                3.会员管理");
            System.out.println("                4.购买管理");
            System.out.println("                5.订单查询");
            System.out.println("                6.退出系统");
            System.out.println("*************************************************");
            System.out.println("请选择：");
            String input = scanner.next();
            switch (input) {
                case "1":
                    if (type!=1){
                        System.out.println("你不是管理员，没有此权限");
                        break;
                    }
                    goodsType();
                    break;
                case "2":
                    if (type!=1){
                        System.out.println("你不是管理员，没有此权限");
                        break;
                    }
                    goods();
                    break;
                case "3":
                    if (type!=1){
                        System.out.println("你不是管理员，没有此权限");
                        break;
                    }
                    user();
                    break;
                case "4":
                    buy();
                    break;
                case "5":
                    buyDaoImpl.findAllUserOrders(user);
                    break;
                case "6" :
                    System.out.println("谢谢使用");
                    System.exit(1);
                case "0" :
                    user=null;
                    login();
                    return;
            }
        }
    }




    private static void goodsType() {
        while (true) {
            System.out.println("*****************商品类型管理***********************");
            System.out.println("                0.主菜单");
            System.out.println("                1.添加商品类型信息");
            System.out.println("                2.修改商品类型信息");
            System.out.println("                3.查询商品类型信息");
            System.out.println("                4.删除商品类型信息");
            System.out.println("                5.退出系统");
            System.out.println("*************************************************");
            String input = InputUtil.input("^[0-5]$","请选择：");
            switch (input) {
                case "1":
                    goodsTypeDao.insert();
                    break;
                case "2" :
                    goodsTypeDao.update();
                    break;
                case "3" :
                    goodsTypeDao.findAll();
                    break;
                case "4" :
                    goodsTypeDao.delete();
                    break;
                case "5" :
                    System.out.println("谢谢使用");
                    System.exit(1);
                case "0" :
                    return;
            }
        }
    }
    private static void goods() {
        while (true) {
            System.out.println("*****************商品管理***********************");
            System.out.println("                0.主菜单");
            System.out.println("                1.添加商品信息");
            System.out.println("                2.修改商品信息");
            System.out.println("                3.查询商品信息");
            System.out.println("                4.删除商品信息");
            System.out.println("                5.退出系统");
            System.out.println("*************************************************");
            String input = InputUtil.input("^[0-5]$","请选择：");
            switch (input) {
                case "1":
                    goodsDao.insert();
                    break;
                case "2" :
                    goodsDao.update();
                    break;
                case "3" :
                    goodsDao.findAll();
                    break;
                case "4" :
                    goodsDao.delete();
                    break;
                case "5" :
                    System.out.println("谢谢使用");
                    System.exit(1);
                case "0" :
                    return;
            }
        }
    }
    private static void user() {
        while (true) {
            System.out.println("*****************会员管理***********************");
            System.out.println("                0.主菜单");
            System.out.println("                1.添加会员信息");
            System.out.println("                2.修改会员信息");
            System.out.println("                3.查询会员信息");
            System.out.println("                4.删除会员信息");
            System.out.println("                5.会员余额充值");
            System.out.println("                6.退出系统");
            System.out.println("*************************************************");
            String input = InputUtil.input("^[0-6]$","请选择：");
            switch (input) {
                case "1":
                    userDao.insert();
                    break;
                case "2" :
                    userDao.update();
                    break;
                case "3" :
                    userDao.findAll();
                    break;
                case "4" :
                    userDao.delete();
                    break;
                case "5" :
                    userDao.recharge();
                    break;
                case "6" :
                    System.out.println("谢谢使用");
                    System.exit(1);
                case "0" :
                    return;
            }
        }
    }
    private static void buy() {
        while (true) {
            System.out.println("*****************购买管理***********************");
            System.out.println("                0.主菜单");
            System.out.println("                1.向购物车添加商品");
            System.out.println("                2.修改商品数量");
            System.out.println("                3.删除购物车中的商品");
            System.out.println("                4.展示购物车商品列表");
            System.out.println("                5.支付");
            System.out.println("                6.查看个人信息");
            System.out.println("                7.退出系统");
            System.out.println("*************************************************");
            System.out.println("请选择:");
            String input = scanner.next();
            switch (input) {
                case "1":
                    buyDaoImpl.insert(user);
                    break;
                case "2" :
                    System.out.println("无法修改，请删除后再添加");
//                    userDao.update();
                    break;
                case "3" :
                    buyDaoImpl.delete(user);
                    break;
                case "4" :
                    buyDaoImpl.findAll(user);
                    break;
                case "5" :
                    buyDaoImpl.pay(user);
                    break;
                case "6" :
                    userDao.findByID(user);
                    break;
                case "7" :
                    System.out.println("谢谢使用");
                    System.exit(1);
                case "0" :
                    return;
            }
        }
    }

}