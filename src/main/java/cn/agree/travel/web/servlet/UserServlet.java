package cn.agree.travel.web.servlet;

import cn.agree.travel.model.ResultInfo;
import cn.agree.travel.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet", urlPatterns = "/user")
public class UserServlet extends BaseServlet {

    /*
    *  校验用户名是否已经存在
    *
    *
    * */
    private void checkUserName(HttpServletRequest request, HttpServletResponse response) {
        //1.获取请求参数
        String username = request.getParameter("username");
        System.out.println("UserServlet.checkUserName的方法username:"+username);
        //2.调用业务层的方法，根据username 查找用户
        User user = null;
        ResultInfo info = new ResultInfo(true);
        try {
            // user = service.findUserByUserName(username);
            //将user封装到json里面响应出去就可以了
            info.setData(user);
            //将info对象转换成json字符串
        } catch (Exception e) {
            //出了异常，让info.setFlag(false)
            info.setFlag(false);
            e.printStackTrace();
        }
        //使用jackson将对象转换成json字符串
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(info);
            //使用字符输出流将json写出去
            PrintWriter writer = response.getWriter();
            writer.write(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
