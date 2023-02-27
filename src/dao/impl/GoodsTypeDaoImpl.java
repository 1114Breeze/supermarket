package dao.impl;

import dao.GoodsTypeDao;
import entity.GoodsType;
import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author test
 * @classname GoodsTypeDaoImpl
 * @date 2023/2/23 20:23
 */
public class GoodsTypeDaoImpl implements GoodsTypeDao {
    String str = "|-";
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Scanner scanner = new Scanner(System.in);
    static GoodsTypeDaoImpl goodsTypeDaoImpl = new GoodsTypeDaoImpl();
    static List<GoodsType> goodsTypes = goodsTypeDaoImpl.initialize();
    BufferedWriter log;
    {
        try {
            log = new BufferedWriter(new FileWriter("E:\\io\\log.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<GoodsType> initialize() {
        List<GoodsType> goodsTypes = new ArrayList<>();
        goodsTypes.add(new GoodsType(1, 0, "食品", true, true));
        goodsTypes.add(new GoodsType(2, 1, "饼干", true, true));
        goodsTypes.add(new GoodsType(3, 1, "糖果", true, true));
        goodsTypes.add(new GoodsType(4, 2, "夹心饼干", false, true));
        goodsTypes.add(new GoodsType(5, 2, "酥脆饼干", false, true));
        goodsTypes.add(new GoodsType(6, 3, "巧克力", false, true));
        goodsTypes.add(new GoodsType(7, 3, "口香糖", false, true));
        goodsTypes.add(new GoodsType(8, 0, "家电", true, true));
        goodsTypes.add(new GoodsType(9, 8, "电视", true, true));
        goodsTypes.add(new GoodsType(10, 8, "洗衣机", true, true));
        goodsTypes.add(new GoodsType(11, 10, "滚筒洗衣机", false, true));
        goodsTypes.add(new GoodsType(12, 10, "波轮洗衣机", false, true));
        return goodsTypes;
    }

    @SneakyThrows
    @Override
    public void insert()  {
        int typeId = goodsTypes.get(goodsTypes.size() - 1).getTypeId();
        findAll();
        GoodsType goodsType = new GoodsType();
        System.out.println("请输入上一级id");
        int parentId = scanner.nextInt();
        System.out.println("请输入类型名称");
        String typeName = scanner.next();
        System.out.println("是否设置为父类型(y/n)");
        String isParent = scanner.next();
        goodsType.setTypeId(++typeId);
        goodsType.setParentId(parentId);
        goodsType.setTypeName(typeName);
        goodsType.setParent(isParent.equals("y"));
        goodsType.setStatus(true);
        String createTime = df.format(System.currentTimeMillis());
        String updateTime = df.format(System.currentTimeMillis());
        goodsType.setCreateTime(createTime);
        goodsType.setUpdateTime(updateTime);
        goodsTypes.add(goodsType);
        System.out.println("添加<<" + typeName + ">>成功");
        log.write("添加<<" + typeName + ">>成功  " + createTime);
        log.newLine();
        log.flush();

    }

    @Override
    public void delete() {
        findAllAndHide();
        System.out.println("输入要删除/隐藏的类型id");
        int typeId = scanner.nextInt();
        for (int i = 0; i < goodsTypes.size(); i++) {
            GoodsType goodsType = goodsTypes.get(i);
            if (goodsType.getTypeId() == typeId && !goodsType.isStatus()) {
                goodsTypes.remove(i);
                System.out.println("删除<<" + goodsType.getTypeName() + ">>成功");
            }
            if (goodsType.getTypeId() == typeId && goodsType.isStatus()) {
                goodsType.setStatus(false);
                goodsType.setUpdateTime(df.format(System.currentTimeMillis()));
                System.out.println("隐藏<<" + goodsType.getTypeName() + ">>成功");
            }
        }

    }

    @Override
    public void update() {
        boolean flag = false;
        findAllAndHide();
        System.out.println("输入要修改的类型id");
        int typeId = scanner.nextInt();
        for (int i = 0; i < goodsTypes.size(); i++) {
            GoodsType goodsType = goodsTypes.get(i);
            if (goodsType.getTypeId() == typeId) {
                System.out.println("是否修改上一级的id(y/n)");
                String input = scanner.next();
                if (input.equals("y")) {
                    System.out.println("请输入修改后的上一级id");
                    input = scanner.next();
                    goodsType.setParentId(Integer.parseInt(input));
                    flag = true;
                }
                System.out.println("是否修改类型名称(y/n)");
                input = scanner.next();
                if (input.equals("y")) {
                    System.out.println("请输入修改后的类型名称");
                    input = scanner.next();
                    goodsType.setTypeName(input);
                    flag = true;
                }
                if (!goodsType.isParent()) {
                    System.out.println("是否将其设置为父类型(y/n)");
                    input = scanner.next();
                    if (input.equals("y")) {
                        goodsType.setParent(true);
                        flag = true;
                    }
                }
                if (!goodsType.isStatus()) {
                    System.out.println("是否显示该类型(y/n)");
                    input = scanner.next();
                    if (input.equals("y")) {
                        goodsType.setStatus(true);
                        flag = true;
                    }
                }
                if (flag) {
                    goodsType.setUpdateTime(df.format(System.currentTimeMillis()));
                }
            }
        }
    }
    @Override
    public void findAll() {
        if (goodsTypes.isEmpty()) {
            System.out.println("暂无数据");
            return;
        }
        for (GoodsType goodsType : goodsTypes) {
            if (goodsType.getParentId() == 0 && goodsType.isStatus()) {
                System.out.println(goodsType.getTypeId() + ":" + goodsType.getTypeName());
                getChildGoodsType(goodsType, str);
            }
        }
    }

    public void getChildGoodsType(GoodsType goodsParentType, String str) {
        for (GoodsType goodsType : goodsTypes) {
            if (goodsType.getParentId() == goodsParentType.getTypeId() && goodsType.isStatus()) {
                System.out.println(" " + str + goodsType.getTypeId() + ":" + goodsType.getTypeName());
                if (goodsType.isParent()) {
                    getChildGoodsType(goodsType, "|" + str);
                }
            }
        }
    }

    //hide
    public void findAllAndHide() {
        for (GoodsType goodsType : goodsTypes) {
            if (goodsType.getParentId() == 0) {
                System.out.println(goodsType.getTypeId() + ":" + goodsType.getTypeName()+(goodsType.isStatus()?"":"      已隐藏"));
                getChildGoodsTypeAndHide(goodsType, str);
            }
        }
    }

    public void getChildGoodsTypeAndHide(GoodsType goodsParentType, String str) {
        for (GoodsType goodsType : goodsTypes) {
            if (goodsType.getParentId() == goodsParentType.getTypeId()) {
                System.out.println(" " + str + goodsType.getTypeId() + ":" + goodsType.getTypeName()+(goodsType.isStatus()?"":"      已隐藏"));
                if (goodsType.isParent()) {
                    getChildGoodsType(goodsType, "|" + str);
                }
            }
        }
    }
}
