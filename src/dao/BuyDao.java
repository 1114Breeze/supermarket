package dao;

import entity.User;

/**
 * @author test
 * @classname BuyDao
 * @date 2023/2/25 20:17
 */
public interface BuyDao{
    /**
     * 新增
     */
    void insert(User user);

    /**
     * 删除
     */
    void delete(User user);

    /**
     * 修改
     */
    void update(User user);

    /**
     * 查询
     */
    void findAll(User user);
}
