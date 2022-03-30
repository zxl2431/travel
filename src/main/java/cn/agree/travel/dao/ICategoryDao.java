package cn.agree.travel.dao;

import cn.agree.travel.model.Category;

import java.util.List;

public interface ICategoryDao {
    List<Category> findAllCategory();
}
