package entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author test
 * @classname User
 * @date 2023/2/25 17:45
 */
@Data
@Accessors(chain = true)
public class User {
    private int userId;//会员编号
    private String userName;//会员名称
    private String userPassword;//会员密码
    private String userTel;//联系方式
    private double userPoints;//积分
    private BigDecimal userBalance;//余额
}
