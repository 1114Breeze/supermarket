package dao.impl;

import dao.GoodsDao;
import entity.Goods;
import entity.GoodsType;
import util.InputUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author test
 * @classname GoodsDaoImpl
 * @date 2023/2/25 15:48
 */
public class GoodsDaoImpl implements GoodsDao {
    static List<GoodsType> goodsTypes = GoodsTypeDaoImpl.goodsTypes;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Scanner scanner = new Scanner(System.in);
    GoodsTypeDaoImpl goodsTypeDaoImpl = new GoodsTypeDaoImpl();
    static List<Goods> goodsList = GoodsDaoImpl.initialize();

    public static List<Goods> initialize() {
        List<Goods> goodsList = new ArrayList<Goods>();
        goodsList.add(new Goods().setGoodsId(1).setGoodsName("测试").setGoodsPrice(1).setGoodsCount(100).setGoodsStatus(1).setGoodsTypeId(1).setDiscount(10));
        goodsList.add(new Goods().setGoodsId(2).setGoodsName("测试1").setGoodsPrice(10).setGoodsCount(1000).setGoodsStatus(1).setGoodsTypeId(2).setDiscount(10));
        return goodsList;
    }
    @Override
    public void insert() {
        int goodsId;
        if (goodsList.size()==0){
            goodsId = 0;
        }else {
            goodsId= goodsList.get(goodsList.size() - 1).getGoodsId();
        }
        Goods goods = new Goods();
        System.out.println("请输入商品名称");
        String goodsName = scanner.next();
        goodsTypeDaoImpl.findAll();
        System.out.println("请输入商品所属类型");
        int goodsTypeId = scanner.nextInt();
        System.out.println("请输入商品数量");
        int goodsCount = scanner.nextInt();
        if (goodsCount > 0) {
            goods.setGoodsStatus(1);
        }
        System.out.println("请输入商品单价");
        double goodsPrice = scanner.nextDouble();
        System.out.println("请输入折扣（输入10不打折）");
        int discount = scanner.nextInt();
        goodsList.add(goods.setGoodsId(++goodsId).setGoodsName(goodsName).setGoodsTypeId(goodsTypeId).
                setGoodsCount(goodsCount).setGoodsPrice(goodsPrice).setGoodsStatus(1).setDiscount(discount));
        System.out.println("添加<<"+goodsName+">>成功");

    }

    @Override
    public void delete() {
        System.out.println("请输入要删除的商品编号");
        int goodsId = scanner.nextInt();
        for (int i = 0; i < goodsList.size(); i++) {
            Goods goods = goodsList.get(i);
            if (goodsId==goods.getGoodsId()){
                goodsList.remove(i);
                System.out.println("删除<<"+goods.getGoodsName()+">>成功");
            }
        }
    }

    @Override
    public void update() {
        findAll1();
        System.out.println("请输入要修改的商品编号");
        int goodsId = scanner.nextInt();
        for (int i = 0; i < goodsList.size(); i++) {
            Goods goods = goodsList.get(i);
            if (goodsId==goods.getGoodsId()){
                while (true) {
                    System.out.println("|商品编号|   商品名称    | 价格 | 数量 | 折扣 | 所属类型 |状态|");
                    for (GoodsType goodsType : goodsTypes) {
                        if (goods.getGoodsTypeId()==goodsType.getTypeId()){
                            System.out.println("|   " + goods.getGoodsId() + "   |   " + goods.getGoodsName() + "   | " + goods.getGoodsPrice() + " | " +
                                    goods.getGoodsCount() + " |  " + goods.getDiscount() + " |  " + goodsType.getTypeName() + "  |  " +
                                    ((goods.getGoodsStatus() == 1) ? "正常  |" : "下架  |"));
                        }
                    }
                    System.out.println("1.修改商品名称");
                    System.out.println("2.修改商品所属类型");
                    System.out.println("3.修改商品数量");
                    System.out.println("4.修改商品单价");
                    System.out.println("5.修改商品状态");
                    System.out.println("6.修改商品折扣");
                    System.out.println("0.修改完毕");
                    String input = InputUtil.input("^[0-6]$","选择你要修改的属性");
                    switch (input){
                        case "1":
                            System.out.println("输入修改后的商品名称");
                            String goodsNmae = scanner.next();
                            goods.setGoodsName(goodsNmae);
                            System.out.println("修改成功");
                            break;
                        case "2":
                            goodsTypeDaoImpl.findAll();
                            System.out.println("输入修改后的商品所属类型");
                            int goodsTypeId = scanner.nextInt();
                            goods.setGoodsTypeId(goodsTypeId);
                            System.out.println("修改成功");
                            break;
                        case "3":
                            System.out.println("输入修改后的商品数量");
                            int goodsCount = scanner.nextInt();
                            goods.setGoodsCount(goodsCount);
                            if (goodsCount > 0){
                                goods.setGoodsStatus(1);
                            }
                            System.out.println("修改成功");
                            break;
                        case "4":
                            System.out.println("输入修改后的商品单价");
                            double goodsPrice = scanner.nextDouble();
                            goods.setGoodsPrice(goodsPrice);
                            System.out.println("修改成功");
                            break;
                        case "5":
                            break;
                        case "6":
                            System.out.println("输入修改后的商品折扣");
                            int discount = scanner.nextInt();
                            goods.setDiscount(discount);
                            System.out.println("修改成功");
                            break;
                        case "0":
                            System.out.println("修改<<"+goods.getGoodsName()+">>完毕");
                            return;
                    }
                }
            }
        }
    }

    @Override
    public void findAll() {
        if (goodsList.size()==0){
            System.out.println("暂无信息");
        }
        while (true) {
            System.out.println("1.按商品名称查询");
            System.out.println("2.查询全部商品信息");
            System.out.println("0.返回上一级");
            String input = scanner.next();
            switch (input) {
                case "1":
                    System.out.println("输入要搜索的商品名称");
                    String search = scanner.next();
                    System.out.println("---------------------------------------------------------");
//        System.out.println("|商品编号|   商品名称    | 价格 | 数量 | 折扣 | 所属类型 |状态|");
                    System.out.printf("%-12s","商品编号");
                    System.out.printf("%-12s","商品名称");
                    System.out.printf("%-12s","价格");
                    System.out.printf("%-12s","数量");
                    System.out.printf("%-12s","折扣");
                    System.out.printf("%-12s","所属类型");
                    System.out.printf("%-12s","状态");
                    System.out.println();
                    System.out.println("---------------------------------------------------------");
                    for (Goods goods : goodsList) {
                        for (GoodsType goodsType : goodsTypes) {
                            if (goods.getGoodsTypeId()==goodsType.getTypeId()&&goods.getGoodsName().contains(search)){
                                System.out.printf("%-14s",goods.getGoodsId());
                                System.out.printf("%-12s",goods.getGoodsName());
                                System.out.printf("%-12s",goods.getGoodsPrice());
                                System.out.printf("%-13s",goods.getGoodsCount());
                                System.out.printf("%-14s",goods.getDiscount());
                                System.out.printf("%-13s",goodsType.getTypeName());
                                System.out.printf("%-12s",((goods.getGoodsStatus()==1)?"正常":"下架"));
                                System.out.println();
//                    System.out.println("|   "+goods.getGoodsId()+"   |   "+goods.getGoodsName() + "   | "+goods.getGoodsPrice()+" | "+
//                            goods.getGoodsCount()+" |  "+goods.getDiscount()+" |  "+goodsType.getTypeName()+"  |  "+
//                            ((goods.getGoodsStatus()==1)?"正常  |":"下架  |"));
                            }
                        }
                    }
                    break;
                case "2":
                    findAll1();
                    break;
                case "0":
                    return;
            }

        }

    }

    public static void findAll1() {
        System.out.println("---------------------------------------------------------");
//        System.out.println("|商品编号|   商品名称    | 价格 | 数量 | 折扣 | 所属类型 |状态|");
        System.out.printf("%-12s","商品编号");
        System.out.printf("%-12s","商品名称");
        System.out.printf("%-12s","价格");
        System.out.printf("%-12s","数量");
        System.out.printf("%-12s","折扣");
        System.out.printf("%-12s","所属类型");
        System.out.printf("%-12s","状态");
        System.out.println();
        System.out.println("---------------------------------------------------------");
        for (Goods goods : goodsList) {
            for (GoodsType goodsType : goodsTypes) {
                if (goods.getGoodsTypeId()==goodsType.getTypeId()){
                    System.out.printf("%-14s",goods.getGoodsId());
                    System.out.printf("%-12s",goods.getGoodsName());
                    System.out.printf("%-12s",goods.getGoodsPrice());
                    System.out.printf("%-13s",goods.getGoodsCount());
                    System.out.printf("%-14s",goods.getDiscount());
                    System.out.printf("%-13s",goodsType.getTypeName());
                    System.out.printf("%-12s",((goods.getGoodsStatus()==1)?"正常":"下架"));
                    System.out.println();
//                    System.out.println("|   "+goods.getGoodsId()+"   |   "+goods.getGoodsName() + "   | "+goods.getGoodsPrice()+" | "+
//                            goods.getGoodsCount()+" |  "+goods.getDiscount()+" |  "+goodsType.getTypeName()+"  |  "+
//                            ((goods.getGoodsStatus()==1)?"正常  |":"下架  |"));
                }
            }
        }
    }
    public static void findById(int id){
        System.out.printf("%-12s","商品编号");
        System.out.printf("%-12s","商品名称");
        System.out.printf("%-12s","价格");
        System.out.printf("%-12s","数量");
        System.out.printf("%-12s","折扣");
        System.out.printf("%-12s","所属类型");
        System.out.printf("%-12s","状态");
        System.out.println();
        System.out.println("---------------------------------------------------------");
        for (Goods goods : goodsList) {
            for (GoodsType goodsType : goodsTypes) {
                if (goods.getGoodsTypeId()==goodsType.getTypeId()&&goods.getGoodsId()==id){
                    System.out.printf("%-14s",goods.getGoodsId());
                    System.out.printf("%-12s",goods.getGoodsName());
                    System.out.printf("%-12s",goods.getGoodsPrice());
                    System.out.printf("%-13s",goods.getGoodsCount());
                    System.out.printf("%-14s",goods.getDiscount());
                    System.out.printf("%-13s",goodsType.getTypeName());
                    System.out.printf("%-12s",((goods.getGoodsStatus()==1)?"正常":"下架"));
                    System.out.println();
//                    System.out.println("|   "+goods.getGoodsId()+"   |   "+goods.getGoodsName() + "   | "+goods.getGoodsPrice()+" | "+
//                            goods.getGoodsCount()+" |  "+goods.getDiscount()+" |  "+goodsType.getTypeName()+"  |  "+
//                            ((goods.getGoodsStatus()==1)?"正常  |":"下架  |"));
                }
            }
        }
    }

}
