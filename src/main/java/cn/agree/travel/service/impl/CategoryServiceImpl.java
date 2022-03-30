package cn.agree.travel.service.impl;

import cn.agree.travel.dao.ICategoryDao;
import cn.agree.travel.dao.impl.CategoryDaoImpl;
import cn.agree.travel.model.Category;
import cn.agree.travel.service.ICategoryService;
import cn.agree.travel.util.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.List;

public class CategoryServiceImpl implements ICategoryService {

    private ICategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public String findAllCategory() throws Exception {
        // 这里就要写到Redis中
        Jedis jedis = JedisUtil.getJedis();

        String jsonData = jedis.get("categoryList");
        // 判断有效性 redis里面没有从数据库里面读取
        if (jsonData == null || "".equals(jsonData.trim())) {
            List<Category> categoryList = categoryDao.findAllCategory();
            jsonData = new ObjectMapper().writeValueAsString(categoryList);
            // 放到Redis里面去
            jedis.set("categoryList", jsonData);
        }

        return jsonData;
    }
}
