package cn.agree.travel.dao.impl;

import cn.agree.travel.dao.ICategoryDao;
import cn.agree.travel.model.Category;
import cn.agree.travel.util.JDBCUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements ICategoryDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtil.getDataSource());

    @Override
    public List<Category> findAllCategory() {
        String sql="SELECT * FROM tab_category ORDER BY cid ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }
}
