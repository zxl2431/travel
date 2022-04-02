package cn.agree.travel.web.servlet;

import cn.agree.travel.exception.UnActiveException;
import cn.agree.travel.model.Favorite;
import cn.agree.travel.model.PageBean;
import cn.agree.travel.model.ResultInfo;
import cn.agree.travel.model.User;
import cn.agree.travel.service.IFavoriteService;
import cn.agree.travel.service.impl.FavoriteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "FavoriteServlet", urlPatterns = "/favorite")
public class FavoriteServlet extends BaseServlet {

    private IFavoriteService favoriteService = new FavoriteServiceImpl();

    /*
     *  添加收藏
     *
     * */
    private ResultInfo addFavorite(HttpServletRequest request, HttpServletResponse response) {
        // 获取rid
        String rid = request.getParameter("rid");
        // 判断是否已经登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ResultInfo info = new ResultInfo(false);

        System.out.println("FavoriteServlet.addFavorite():"+rid);

        try {
            if (user != null) { // 已登录
                boolean flag = favoriteService.addFavorite(user, rid);
                info.setFlag(flag);
                if (flag) {
                    // 添加成功
                    // 重新查找该条线路的收藏次数
                    int count = favoriteService.findCount(rid);
                    info.setData(count);
                }
            }else {
                throw new UnActiveException("未登录");
            }
        } catch (UnActiveException e) {
            // 说明未登录
            info.setFlag(false);
            e.printStackTrace();
        }

        return info;
    }

    /*
    *  判断线路是否已收藏
    *
    * */
    private ResultInfo isFavorite(HttpServletRequest request, HttpServletResponse response) {
        //1.获取请求参数rid
        String rid = request.getParameter("rid");
        //怎么判断当前是否登录，就看session中有没有user
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        ResultInfo info = new ResultInfo(true);

        if (user != null) {
            // 已登录 判断用户是否已经收藏了这条线路
            try {
                Favorite favorite = favoriteService.isFavorite(rid, user);
                info.setData(favorite != null);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 未登录
            info.setData(false);
        }

        return info;
    }

    /*
    *  查看个人收藏
    *
    * */
    private ResultInfo findMyFavorite(HttpServletRequest request, HttpServletResponse response) {
        // 获取客户端携带过来的参数
        Integer curPage = Integer.parseInt(request.getParameter("curPage"));
        // 获取当前用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        ResultInfo info = new ResultInfo(true);

        // 调用业务
        try {
            PageBean<Favorite> pageBean = favoriteService.findMyFavorite(user, curPage);
            info.setData(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            info.setFlag(false);
        }

        return info;

    }


}
