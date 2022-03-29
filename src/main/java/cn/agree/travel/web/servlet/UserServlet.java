package cn.agree.travel.web.servlet;

import cn.agree.travel.constant.Constant;
import cn.agree.travel.exception.PasswordErrorException;
import cn.agree.travel.exception.UserNameErrorException;
import cn.agree.travel.model.ResultInfo;
import cn.agree.travel.model.User;
import cn.agree.travel.service.IUserService;
import cn.agree.travel.service.impl.UserServiceImpl;
import cn.agree.travel.util.Md5Util;
import cn.agree.travel.util.UuidUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "UserServlet", urlPatterns = "/user")
public class UserServlet extends BaseServlet {

    private IUserService service = new UserServiceImpl();

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
            user = service.findUserByUserName(username);
            if (user != null) {
                info.setFlag(false);
            }
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

    /*
    *  注册用户
    *
    * */
    public void register(HttpServletRequest request, HttpServletResponse response) {
        // 获取验证码
        String checkCode = request.getParameter("check");
        // 获取服务器端的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute(Constant.CHECKCODE);
        ResultInfo info = new ResultInfo();
        if (checkCode.equalsIgnoreCase(checkcode_server)){
            //验证码正确
            //1.获取客户端传入的所有数据，并存放到map中
            Map<String, String[]> map = request.getParameterMap();
            //2.将map中的数据存放到user对象中
            User user = new User();

            boolean flag = false;
            try {
                BeanUtils.populate(user,map);
                //手动设置status、code
                user.setStatus(Constant.UNACTIVE);

                user.setCode(UuidUtil.getUuid());

                //密码用MD5加密
                user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
                //3.调用业务层的方法，将用户信息存放到数据库

                flag = service.saveUser(user);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("注册的标示:"+flag);
            if (flag){
                //注册成功
                info.setFlag(true);
            }else {
                //注册失败
                info.setFlag(false);
                info.setErrorMsg("注册失败,请重新注册");
            }
        }else {
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
        }

        //将info对象转换成json字符串写出去
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

    /*
    *  用户激活
    *
    * */
    public void active(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.获取用户传入的激活码
        String code = request.getParameter("code");
        //2.调用业务层的方法根据激活码进行激活
        boolean flag = service.findUserByCode(code);
        //3.判断是否激活
        ResultInfo info = new ResultInfo();
        if(flag){
            //激活成功，跳转到登录页面
            response.sendRedirect(request.getContextPath()+"/login.html");
        }else{
            //激活失败，显示激活失败
            response.getWriter().write("激活失败");
        }


    }

    /*
    *  用户登录 login
    *
    * */
    public void login(HttpServletRequest request, HttpServletResponse response) {
        // 获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String check = request.getParameter("check");
        System.out.println("UserServlet.login的username:"+username);
        // 获取服务器端的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute(Constant.CHECKCODE);

        ResultInfo info = new ResultInfo(false);
        User user = null;

        if (check.equalsIgnoreCase(checkcode_server)) {
            try {
                password = Md5Util.encodeByMd5(password);
                user = service.doLogin(username, password);
                System.out.println("UserServlet.login查出来的user:"+user);
                // 将user放入session中
                session.setAttribute("user", user);

                info.setFlag(true);

            } catch (UserNameErrorException e) {
                info.setErrorMsg(e.getMessage());
            }
            catch (PasswordErrorException e) {
                info.setErrorMsg(e.getMessage());
            }
            catch (NotActiveException e) {
                info.setErrorMsg(e.getMessage());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            info.setErrorMsg("验证码错误");
        }

        info.setData(user);

        //将info对象转换成json字符串
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

    public void getLoginData(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("UserServlet.getLoginData");
        // 用session中获取user对选哪个
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ResultInfo info = new ResultInfo(false);
        if (user != null) {
            info.setFlag(true);
            info.setData(user);
        }

        //将info对象转换成json字符串
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

    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();

        //重定向
        response.sendRedirect("login.html");
    }

}
