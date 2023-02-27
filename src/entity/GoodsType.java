package entity;

import lombok.Data;

import java.util.Date;

/**
 * @author test
 * @classname GoodsType
 * @date 2023/2/23 19:57
 */
@Data
//商品类型
public class GoodsType {
    private int typeId;//类型ID
    private int parentId;//父id
    private String typeName;//类型名称
    private boolean isParent;//是否为父类型
    private boolean status;//类型状态
    private String createTime;//创建时间
    private String updateTime;//修改时间

    public GoodsType(int typeId, int parentId, String typeName, boolean isParent, boolean status) {
        this.typeId = typeId;
        this.parentId = parentId;
        this.typeName = typeName;
        this.isParent = isParent;
        this.status = status;
    }

    public GoodsType() {
    }
}
