package cn.agree.travel.web.servlet;

import cn.agree.travel.model.ResultInfo;
import cn.agree.travel.service.ICategoryService;
import cn.agree.travel.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CategoryServlet", urlPatterns = "/category")
public class CategoryServlet extends BaseServlet {

    private ICategoryService categoryService = new CategoryServiceImpl();

    /*
    *  分类列表的获取
    *
    * */
    private ResultInfo findAllCategory(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo resultInfo = null;
        try {
            String jsonData = categoryService.findAllCategory();
            resultInfo = new ResultInfo(true, jsonData, null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false);
        }

        return resultInfo;

    }


}
