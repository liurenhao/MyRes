# MyRes
快速解析超大XML工具


## 配置文件config.properties

#pojo.package-name=org.liuhao.pojo
指定pojo类的包路径
#pojo.root=/gen
指定XML文档的根路径
#thread.num=20
指定处理对象的线程数量

## 自定义注解
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Doc {
	String path();
}

#在POJO类上定义注解，指定类在XML上对应的路径，示例：
@Doc(path="/gen/Root/PolicyList/Policy/Person")
public class Person {
  ...
  ...
}

## 关于解析对象的自定义处理 ConsumerMyThread

继承ConsumerThread类， 实现handle(Object obj)方法


## 入口类
FastXml.readXml("e:\\test11.xml", ConsumerMyThread.class);
