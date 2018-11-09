package org.liuhao.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.liuhao.config.Config;
import org.liuhao.constant.Constant;
import org.liuhao.constant.ObjectMap;
import org.liuhao.queen.Queen;

public class ConsElementHandler implements ElementHandler {

	Class<?> cls;
	Config config;

	public ConsElementHandler(Class<?> cls,Config config) {
		super();
		this.cls = cls;
		this.config = config;
	}

	@Override
	public void onStart(ElementPath path) {
	}

	@Override
	public void onEnd(ElementPath path) {
		Element eleC = path.getCurrent();
		Element parent = eleC.getParent();
		// Document document = eleC.getDocument();
		try {
			// 生成实体对象
			final Object obj = cls.newInstance();
			List<Element> elements = eleC.elements();
			Map<String, String> map = new HashMap<>();
			elements.forEach(ele -> {
				String propertyname = ele.getName();
				String propertyvalue = ele.getText();
				Method m;
				try {
					Method[] methods = cls.getMethods();
					//记录重复propertyname   集合情况
					for (int i = 0; i < methods.length; i++) {
						m = methods[i];
						if (m.getName()
								.equals("set" + propertyname.substring(0, 1).toUpperCase() + propertyname.substring(1))
								&& m.getParameterCount() == 1) {
							Class<?>[] parameterTypes = m.getParameterTypes();
							// System.out.println(m.getName());
							// TODO 还需添加其他基本类型
							if ("int".equals(parameterTypes[0].getName())
									|| "java.lang.Integer".equals(parameterTypes[0].getCanonicalName())) {
								m.invoke(obj, Integer.valueOf(propertyvalue));
							} else if ("java.lang.String".equals(parameterTypes[0].getCanonicalName())) {
								m.invoke(obj, String.valueOf(propertyvalue));
							}
							else{
								// 过滤掉基础类型后,处理自定义类型
								String key = ele.getPath();
								//重复元素不再走下面逻辑
								String value = map.get(key);
								if(value!=null) continue;
								map.put(key, "1");
								List<Object> list = ObjectMap.objMap.get(key);
								// 判断是否为List集合 //TODO 还需添加其他结合类型判断
								if (parameterTypes[0].isAssignableFrom(List.class)
										|| parameterTypes[0].isAssignableFrom(ArrayList.class)) {
									m.invoke(obj, list);
								} else {// 非集合
									if (list != null && list.size() > 0) {
										m.invoke(obj, list.get(0));
									}
								}
								
								ObjectMap.objMap.remove(key);
							}
							
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});

			// 判断是否根对象
			if (parent.getName().equals(config.getDocument().getPojo().getRoot())) {
				// 对象入库
//				 System.out.println(obj.toString());
				Queen.queue.put(obj);
				Queen.productNum++;
				// 移除内存
				eleC.detach();
			} else {
				String key = eleC.getPath();
				List<Object> list = ObjectMap.objMap.get(key);
				if (list == null) {
					list = new ArrayList<>();
					ObjectMap.objMap.put(key, list);
				}
				list.add(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
