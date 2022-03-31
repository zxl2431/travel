package cn.agree.travel.web.servlet;

import cn.agree.travel.model.PageBean;
import cn.agree.travel.model.ResultInfo;
import cn.agree.travel.model.Route;
import cn.agree.travel.service.IRouteService;
import cn.agree.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "RouteServlet", urlPatterns = "/route")
public class RouteServlet extends BaseServlet {

    private IRouteService routeService = new RouteServiceImpl();

    /*
    *  处理精选
    *
    * */
    private ResultInfo routeCareChoose(HttpServletRequest request,
                                 HttpServletResponse response) throws ServletException, IOException {
        //定义返回数据对象
        ResultInfo resultInfo = null;
        try {
            //调用业务逻辑层获取人气旅游、最新旅游、主题旅游等列表数据
            Map<String, List<Route>> map = routeService.routeCareChoose();
            //实例返回数据对象
            resultInfo = new ResultInfo(true,map,null);
        }catch (Exception e){
            e.printStackTrace();
            //实例错误消息
            resultInfo = new ResultInfo(false);
        }

        System.out.println("RouteServlet.routeCareChoose() 结果:"+resultInfo);
        return resultInfo;
    }

    /*
    *  根据分类id获取当前分类的某页信息
    *
    *
    * */
    private ResultInfo findRoutesByCid(HttpServletRequest request, HttpServletResponse response) {
        //1.获取请求参数cid以及curPage
        String cid = request.getParameter("cid");
        int curPage = Integer.parseInt(request.getParameter("curPage"));

        ResultInfo info = new ResultInfo(true);
        //2.调用业务层的方法，获取当前数据分页的PageBean对象
        try {
            PageBean<Route> pageBean = routeService.findPageBean(cid, curPage);
            info.setData(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            info.setFlag(false);
        }

        return info;


    }


}
