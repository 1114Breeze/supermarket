package dao;

import java.io.IOException;

/**
 * @author test
 * @classname BaseDao
 * @date 2023/2/25 15:44
 */
public interface BaseDao {
    /**
     * 新增
     */
    void insert();

    /**
     * 删除
     */
    void delete();

    /**
     * 修改
     */
    void update();

    /**
     * 查询
     */
    void findAll();
}
