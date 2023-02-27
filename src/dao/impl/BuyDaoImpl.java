package dao.impl;

import dao.BuyDao;
import entity.Goods;
import entity.Order;
import entity.User;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author test
 * @classname BuyDaoImpl
 * @date 2023/2/25 20:18
 */
public class BuyDaoImpl implements BuyDao {
    Scanner scanner = new Scanner(System.in);
    List<Order> orders = new ArrayList<>();
    Map<Integer, List<Order>> cart = new HashMap<>();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    List<Goods> goodsList = GoodsDaoImpl.goodsList;

    public void insert(User user) {
        if (!cart.containsKey(user.getUserId())) {
            orders = new ArrayList<>();
        } else {
            orders = cart.get(user.getUserId());
        }
        Order order = null;
        boolean flag = true;
        int a = 0;
        while (flag) {
            boolean exist = false;
            GoodsDaoImpl.findAll1();
            System.out.println("选择你要购买的商品编号");
            int goodsId = scanner.nextInt();
            for (int i = 0; i < goodsList.size(); i++) {
                Goods goods = goodsList.get(i);
                if (goods.getGoodsId() == goodsId) {
                    GoodsDaoImpl.findById(goodsId);
                    System.out.println("请输入要购买的数量");
                    int count = scanner.nextInt();
                    if (count > goods.getGoodsCount()) {
                        System.out.println("商品库存不足，无法购买");
                        return;
                    }
                    BigDecimal total = BigDecimal.valueOf(goods.getGoodsPrice() * count);
//                    if (user.getUserBalance().intValue() < total.intValue()) {
//                        System.out.println("余额不足，无法购买");
//                        return;
//                    }
                    if (orders.size() != 0) {
                        for (int j = 0; j < orders.size(); j++) {
                            order = orders.get(j);
                            if (goodsId == order.getGoodsId()) {
                                a = j;
                                exist = true;
                            }
                        }
                    }
                    if (exist) {
//                        user.setUserBalance(user.getUserBalance().subtract(total));
                        goods.setGoodsCount(goods.getGoodsCount() - count);
                        orders.get(a).setGoodsCount(order.getGoodsCount() + count);
                        orders.get(a).setTimeOrder(df.format(System.currentTimeMillis()));
                        orders.get(a).setTotal(order.getTotal().add(total));
                        System.out.println("向购物车添加" + count + "个<<" + goods.getGoodsName() + ">>成功1");
                    } else {
//                        user.setUserBalance(user.getUserBalance().subtract(total));
                        goods.setGoodsCount(goods.getGoodsCount() - count);
                        Order order1 = new Order();
                        order1.setOrderId(user.getUserId());
                        order1.setUserId(user.getUserId());
                        order1.setTimeOrder(df.format(System.currentTimeMillis()));
                        order1.setTotal(total);
                        order1.setGoodsId(goods.getGoodsId());
                        order1.setGoodsCount(count);
                        order1.setGoodsPrice(BigDecimal.valueOf(goods.getGoodsPrice()));
                        order1.setGoodsName(goods.getGoodsName());
                        System.out.println("向购物车添加" + count + "个<<" + goods.getGoodsName() + ">>成功2");
                        orders.add(order1);
                        if (cart.get(user.getUserId()) == null) {
                            cart.put(user.getUserId(), orders);
                        } else {
                            cart.replace(user.getUserId(), orders);
                        }
                    }
                }
            }
            System.out.println("是否继续添加(y/n)");
            String input = scanner.next();
            if ("n".equals(input)) {
                flag = false;
            }
        }

    }

    @Override
    public void delete(User user) {
        orders = cart.get(user.getUserId());
        if (orders == null || orders.size() == 0) {
            System.out.println("请先添加商品");
            return;
        }
        findAll(user);
        System.out.println("1.一键清空");
        System.out.println("2.单个删除");
        System.out.println("请选择:");
        String input = scanner.next();
        switch (input) {
            case "1":
                for (Order order : orders) {
                    for (int i = 0; i < goodsList.size(); i++) {
                        Goods goods = goodsList.get(i);
                        if (order.getGoodsId() == goods.getGoodsId()) {
                            goodsList.get(i).setGoodsCount(order.getGoodsCount() + goods.getGoodsCount());
//                            user.setUserBalance(user.getUserBalance().add(order.getTotal()));
                        }
                    }
                }
                orders.clear();
                System.out.println("清除成功");
                break;
            case "2":
                System.out.println("选择你要从购物车中删除的商品ID");
                int goodsId = scanner.nextInt();
                for (int i = 0; i < orders.size(); i++) {
                    Order order = orders.get(i);
                    if (order.getGoodsId() == goodsId) {
                        for (int j = 0; j < goodsList.size(); j++) {
                            Goods goods = goodsList.get(j);
                            if (goods.getGoodsId() == goodsId) {
                                goodsList.get(j).setGoodsCount(order.getGoodsCount() + goods.getGoodsCount());
//                                user.setUserBalance(user.getUserBalance().add(order.getTotal()));
                            }
                        }
                        orders.remove(i);
                        System.out.println("删除成功");
                    }
                }

        }

    }

    @Override
    public void update(User user) {
        orders = cart.get(user.getUserId());
        if (orders == null || orders.size() == 0) {
            System.out.println("请先向购物车中添加商品");
            return;
        }
        findAll(user);
        System.out.println("选择你要修改的商品ID");

    }

    @Override
    public void findAll(User user) {
        orders = cart.get(user.getUserId());
        if (orders == null || orders.size() == 0) {
            System.out.println("请先添加商品");
            return;
        }
        System.out.println("购物车ID\t商品ID\t用户名称\t商品名称\t商品数量\t商品总额\t添加时间");
        for (Order order : orders) {
            if (user.getUserId() == order.getOrderId()) {
                System.out.println("\t" + order.getOrderId() + "\t" + order.getGoodsId() + "\t" + user.getUserName() + "\t\t" + order.getGoodsName() + "\t\t" + order.getGoodsCount() +
                        "\t" + order.getTotal() + "\t" + order.getTimeOrder());
            }
        }
    }

    public void pay(User user) {
        BigDecimal total = new BigDecimal(0);
        findAll(user);
        orders = cart.get(user.getUserId());
        System.out.println("是否要支付(y/n)");
        String input = scanner.next();
        if (!"y".equals(input)) {
            return;
        }
        for (Order order : orders) {
            total = order.getTotal().add(total);
        }
        int subtract = user.getUserBalance().subtract(total).intValue();
        System.out.println("共需支付" + total + "元");
        System.out.println("1.会员余额支付");
        System.out.println("2.现金支付");
        System.out.println("请选择：");
        input = scanner.next();
        switch (input) {
            case "1":
                if (subtract < 0) {
                    System.out.println("余额不足，你的余额为" + user.getUserBalance() + "元，还差" + Math.abs(subtract) + "元");
                    break;
                }
                user.setUserBalance(user.getUserBalance().subtract(total));
                System.out.println("支付成功，你剩余的余额为"+user.getUserBalance()+"元");
                for (Order order : orders) {
                    order.setOrderType("会员余额支付");
                }
                orders.clear();
                break;
            case "2" :
                for (Order order : orders) {
                    order.setOrderType("现金支付");
                }
                System.out.println("现金支付成功");
                orders.clear();
        }

    }
}
