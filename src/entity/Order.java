package entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author test
 * @classname Orders
 * @date 2023/2/25 21:31
 */
@Data
@Accessors(chain = true)
public class Order {
    private int orderId;//订单ID
    private int userId;//用户ID
    private BigDecimal total;//订单总金额
    private String timeOrder;//下单时间
    private String orderType;//支付类型
    private int goodsId;//购买商品id
    private int goodsCount;//购买商品数量
    private BigDecimal goodsPrice;//购买商品单价
    private String goodsName;//商品名称


}
