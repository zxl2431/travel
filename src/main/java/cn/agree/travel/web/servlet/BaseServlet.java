package cn.agree.travel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 使用一个servlet处理多个请求
        // 1、获取客户端携带过来的请求参数"action"
        String action = req.getParameter("action");
        //2.我们在Servlet中定义的处理请求的方法的方法名要和action一模一样
        //相当于已知了方法名，要调用对应的方法
        //使用反射获取当前类的方法(方法名为action)
        Class clazz = this.getClass();
        // 3、使用字节码对象根据方法名获取方法
        try {
            Method method = clazz.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            // 4、调用方法
            method.setAccessible(true);
            method.invoke(this, req, resp);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
