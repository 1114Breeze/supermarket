package dao;

import entity.GoodsType;

import java.io.IOException;
import java.util.List;

/**
 * @author test
 * @classname GoodsTypeDao
 * @date 2023/2/23 20:19
 */
public interface GoodsTypeDao extends BaseDao {
    List<GoodsType> initialize();
}
