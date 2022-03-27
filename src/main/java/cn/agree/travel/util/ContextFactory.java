package cn.agree.travel.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;


public class ContextFactory {
    public static Object getContext(String id){
        //1.解析context.xml文件
        //1.1将context.xml文件转换成字节输入流
        ClassLoader loader = ContextFactory.class.getClassLoader();
        InputStream in = loader.getResourceAsStream("context.xml");

        //1.2使用DOM4J解析
        SAXReader reader = new SAXReader();
        Object obj = null;
        try {
            Document document = reader.read(in);
            //1.3根据传入的id找到对应的标签中的文本
            Element element = (Element) document.selectSingleNode("//context[@id='" + id + "']");
            //1.4获取标签element中的文本
            String className = element.getText().trim();//获取文本并且去掉前后的空格
            //2.获取到了类的全限定名之后，使用反射创建对象
            //2.1根据全限定名，获取字节码对象
            Class<?> clazz = Class.forName(className);
            //2.2调用newInstance()方法，创建对象
            obj = clazz.newInstance();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
