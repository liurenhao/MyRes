package org.liuhao.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

public class ReflectUtils {

	/**
	 * @param obj
	 * @param path
	 *            �Ե�Ÿ���
	 * @param value
	 * @throws Exception
	 * @throws NoSuchFieldException
	 */
	public static void setValue(Object obj, String path, Object value) throws Exception {
		String name;
		if (path.startsWith(".")) {
			path = path.substring(1);
		}
		if (!path.contains(".")) {
			name = path;
			path = "";
		} else {
			name = path.substring(0, path.indexOf("."));
			path = path.substring(path.indexOf("."));
		}
		Class<? extends Object> cls = obj.getClass();
		// �������� �շ����� ����: �� test-one ��Ϊ testOne
		while (name.contains("-")) {
			int index = name.indexOf("-");
			name = name.substring(0, index) + name.substring(index + 1, index + 2).toUpperCase()
					+ name.substring(index + 2);
		}

		Field field = cls.getDeclaredField(name);
		field.setAccessible(true);
		if (path.length() > 0) {
			Object object = field.get(obj);
			setValue(object, path, value);
		} else {
			// �ж��Ƿ�ΪList���� //TODO �������������������ж�
			if (field.getType().isAssignableFrom(List.class) || field.getType().isAssignableFrom(ArrayList.class)) {
				if (!value.getClass().isAssignableFrom(List.class)
						&& !value.getClass().isAssignableFrom(ArrayList.class)) {
					String[] split = ((String)value).split(",");
					value = new ArrayList<>();
					for (int i = 0; i < split.length; i++) {
						((List)value).add(split[i]);
					}
				}
			}else if("int".equals(field.getType().getName())){
				value = Integer.valueOf((String)value);
			}
			try {
				Method method = field.getType().getMethod("valueOf", String.class);
				value = method.invoke(obj, value);
			} catch (NoSuchMethodException e) {
			}
			
			field.set(obj, value);
		}
	}
}
