package entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author test
 * @classname Goods
 * @date 2023/2/25 15:29
 */
@Data
@Accessors(chain = true)
public class Goods {
    private int goodsId;//商品编号
    private String goodsName;//商品名称
    private int goodsTypeId;//商品所属类型
    private int goodsCount;//商品数量
    private double goodsPrice;//商品单价
    private int goodsStatus;//商品状态
    private int discount;//折扣
    private String createTime;//创建时间
    private String updateTime;//修改时间

}
