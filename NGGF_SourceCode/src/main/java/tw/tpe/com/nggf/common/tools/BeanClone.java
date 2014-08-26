package tw.tpe.com.nggf.common.tools;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanClone extends BeanUtilsBean {
	private static Logger logger = LoggerFactory.getLogger(BeanClone.class);
	private static String xmlFormat = "<%1$s>%2$s</%1$s>";

	/**
	 * <H3>物件複製</H3>
	 * 可接受：<br>
	 * Map -> Object<br>
	 * Object -> Map<br>
	 * Object -> Object<br>
	 * 自動排除 Null 內容
	 * @param source 來源物件
	 * @param target 目標物件
	 */
	@SuppressWarnings("unchecked")
	public static void clone(Object source, Object target) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (source == null) {
			logger.warn("物件複製時，來源物件不可為 Null！");
		    throw new IllegalArgumentException("No sourcein bean specified");
		}
		if (target == null) {
			logger.warn("物件複製時，目標物件不可為 Null！");
		    throw new IllegalArgumentException("No targetination bean specified");
		}

		// 若來源為 Map, 依照目標物件屬性值名稱複製
		if (source instanceof Map) {
			map2Object((Map<?, ?>) source, target, null);

		// 若目標為 Map, 依照來源物件屬性值名稱複製
		} else if (target instanceof Map) {
			object2Map(source, (Map<String, Object>) target, null);

		// 單純物件複製物件
		} else {
			BeanUtilsBean beanTool = BeanUtilsBean.getInstance();
			PropertyDescriptor[] sourceDescriptors = beanTool.getPropertyUtils().getPropertyDescriptors(source);

			for (int i = 0; i < sourceDescriptors.length; i++) {
				String name = sourceDescriptors[i].getName();
				if ("class".equals(name))
					continue;

				if ((!beanTool.getPropertyUtils().isReadable(source, name)) || (!beanTool.getPropertyUtils().isWriteable(target, name)))
					continue;

		        try {
		        	Object value = beanTool.getPropertyUtils().getSimpleProperty(source, name);

		        	if (null == value)
						continue;

		        	beanTool.copyProperty(target, name, value);
		        } catch (NoSuchMethodException e) {
		        	// 錯誤原因, 屬性名稱相同, 類別不同, 暫不處理
		        	logger.warn("複製時，屬性名稱相同， 類別不同， 暫不處理！", e);
		        }
			}
		}
	}

	/**
	 * <H3>物件複製 - 以 Table Name 作為 Map Key</H3>
	 * 僅接受：<br>
	 * Map -> Do<br>
	 * Do -> Map<br>
	 * 自動排除 Null 內容
	 * @param source 來源物件
	 * @param target 目標物件
	 */
	@SuppressWarnings("unchecked")
	public static void cloneMapWithDo(Object source, Object target) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (source == null) 
		      throw new IllegalArgumentException("No sourcein bean specified");
		if (target == null) 
		      throw new IllegalArgumentException("No targetination bean specified");

		// 若來源為 Map, 依照目標物件屬性值名稱複製
		if (source instanceof Map) {
			String fullClassName = target.getClass().getSimpleName();
			if (false == fullClassName.substring(fullClassName.length() - 2).equals("Do"))
				throw new IllegalArgumentException("Target className must end with Do!");

			String tableName = fullClassName.substring(0, fullClassName.length() - 2);

			map2Object((Map<?, ?>) source, target, tableName);

		// 若目標為 Map, 依照來源物件屬性值名稱複製
		} else if (target instanceof Map) {
			String fullClassName = source.getClass().getSimpleName();
			if (false == fullClassName.substring(fullClassName.length() - 2).equals("Do"))
				throw new IllegalArgumentException("Source className must end with Do!");

			String tableName = fullClassName.substring(0, fullClassName.length() - 2);

			object2Map(source, (Map<String, Object>) target, tableName);

		// 單純物件複製物件
		} else {
			throw new IllegalArgumentException("Source or target must extend Map!");
		}
	}

	/**
	 * 可附加 Table Name 之 Map copy to Object
	 * @param source 來源 Map
	 * @param target 目標 Object
	 * @param nameSpace Map Key 要附加之 Table Name，不需要時請傳 Null
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void map2Object(Map<?, ?> source, Object target, String nameSpace)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		if (source == null) 
		      throw new IllegalArgumentException("Source Map can't be Null!");
		if (target == null) 
		      throw new IllegalArgumentException("Target object can't be Null!");

		String prefixName = (null == nameSpace || 0 == nameSpace.trim().length()) ? "" : nameSpace + ".";
		BeanUtilsBean beanTool = BeanUtilsBean.getInstance();
		PropertyDescriptor[] targetDescriptors = beanTool.getPropertyUtils().getPropertyDescriptors(target);

		for (int i = 0; i < targetDescriptors.length; i++) {
			String name = targetDescriptors[i].getName();
			if ("class".equals(name))
				continue;

			if (false == beanTool.getPropertyUtils().isWriteable(target, name))
				continue;

        	Object value = source.get(prefixName + name);

        	if (null == value)
				continue;

			beanTool.copyProperty(target, name, value);
		}
	}

	/**
	 * 可附加 Table Name 之 Object copy to Map
	 * @param source 來源 Object
	 * @param target 目標 Map
	 * @param nameSpace Map Key 要附加之 Table Name，不需要時請傳 Null
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void object2Map(Object source, Map<String, Object> target, String nameSpace)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		if (source == null) 
		      throw new IllegalArgumentException("Source object can't be Null!");
		if (target == null) 
		      throw new IllegalArgumentException("Target Map can't be Null!");

		String prefixName = (null == nameSpace || 0 == nameSpace.trim().length()) ? "" : nameSpace + ".";
		BeanUtilsBean beanTool = BeanUtilsBean.getInstance();
		PropertyDescriptor[] sourceDescriptors = beanTool.getPropertyUtils().getPropertyDescriptors(source);

		for (int i = 0; i < sourceDescriptors.length; i++) {
			String name = sourceDescriptors[i].getName();
			if ("class".equals(name))
				continue;

			if (false == beanTool.getPropertyUtils().isReadable(source, name))
				continue;

			try {
				Object value = beanTool.getPropertyUtils().getSimpleProperty(source, name);

				if (null == value)
					continue;

	        	target.put(prefixName + name, value);
			} catch (NoSuchMethodException e) {
				// 錯誤原因, 屬性名稱相同, 類別不同, 暫不處理
				logger.warn("複製時，屬性名稱相同， 類別不同， 暫不處理！", e);
			}
		}
	}

	/**
	 * 接受附有 nameSpace 之 Map 複製至 object Map
	 * @param source 來源 Map，key must be nameSpace.property
	 * @param target 目標 object Map，key must be nameSpace
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void map2ObjectMap(Map<String, ?> source, Map<String, Object> target)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		if (source == null) 
		      throw new IllegalArgumentException("Source Map can't be Null!");
		if (target == null) 
		      throw new IllegalArgumentException("Target Map can't be Null!");

		Iterator<String> names = target.keySet().iterator();
		while (names.hasNext()) {
			String name = names.next();
			Object targetObject = target.get(name);
			if (null == targetObject)
				continue;

			map2Object(source, targetObject, name);
		}
	}

	/**
	 * 接受附有 nameSpace 之 object Map 複製至 Map
	 * @param source 來源 object Map，key is nameSpace
	 * @param target 目標 Map，the key will be nameSpace.property
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public static void objectMap2Map(Map<String, Object> source, Map<String, ?> target)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		if (source == null) 
		      throw new IllegalArgumentException("Source Map can't be Null!");
		if (target == null) 
		      throw new IllegalArgumentException("Target Map can't be Null!");

		Iterator<String> names = source.keySet().iterator();
		while (names.hasNext()) {
			String name = names.next();
			Object sourceObject = source.get(name);
			if (null == sourceObject)
				continue;

			object2Map(sourceObject, (Map<String, Object>) target, name);
		}
	}

	/**
	 * Object 轉 XML String<br>
	 * 物件屬性 className 之 value 將作為最外層 tag 名稱，預設'XMLTag'<br>
	 * @param source 來源物件
	 * @return XML String
	 */
	public static String object2XmlString(Object source) {
		StringBuffer result = new StringBuffer();
		BeanUtilsBean beanTool = BeanUtilsBean.getInstance();
		PropertyDescriptor[] sourceDescriptors = beanTool.getPropertyUtils().getPropertyDescriptors(source);
		String className = "XMLTag";

		for (int i = 0; i < sourceDescriptors.length; i++) {
			String name = sourceDescriptors[i].getName();

			if ("class".equals(name))
				continue;

			if (false == beanTool.getPropertyUtils().isReadable(source, name))
				continue;

	        try {
	        	Object value = beanTool.getPropertyUtils().getSimpleProperty(source, name);

	        	if ("className".equals(name)) {
					className = value.toString();
					continue;
				}

	        	name = name.substring(0, 1).toUpperCase() + name.substring(1);
	        	result.append(String.format(xmlFormat, name, null == value ? "" : value.toString()));
	        } catch (Exception e) {
	        	// 錯誤原因, 屬性名稱相同, 類別不同, 暫不處理
	        }
		}

		return String.format(xmlFormat, className, result.toString());
	}

	/**
	 * Map 轉 XML String<br>
	 * 
	 * @param tagName XML 最外層 tag 名稱，預設'XMLTag'
	 * @param source 來源 Map
	 * @return XML String
	 */
	public static String map2XmlString(String tagName, Map<String, Object> source) {
		if (null == tagName || tagName.trim().length() <= 0)
			tagName = "XMLTag";

		StringBuffer result = new StringBuffer();
		List<String> names = new ArrayList<String>(source.keySet());
		Collections.sort(names);

		for (String name : names){
			Object value = source.get(name);
			if (null == value)
				continue;

			if (value instanceof Number || value instanceof String)
				result.append(String.format(xmlFormat, name, value.toString()));
		}

		return String.format(xmlFormat, tagName, result.toString());
	}

	/**
	 * 依據 relation Map 的規範，將 source Map 內容寫入 entityClass 中
	 * @param entityClass 欲生成 Class
	 * @param source 資料來源 Map (HyCase Property Name -> Value)
	 * @param relation 對應關係 Map (Do Property Name -> HyCase Property Name)
	 * @return 依照使用者提供之型態，設定好內容之 Class
	 * @throws IllegalArgumentException 傳遞參數異常
	 * @throws InstantiationException if the class or its nullary constructor is not accessible.
	 * @throws IllegalAccessException 
	 * if this Class represents an abstract class, an interface, an array class, a primitive type, or void; or if the class has no nullary constructor; 
	 * or if the instantiation fails for some other reason.
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception propertyName 為 Null 或不存在
	 */
	public static <T> T getClassByMap(Class<T> entityClass, Map<String, Object> source, Map<String, String> relation)
			throws IllegalArgumentException, InstantiationException, IllegalAccessException,
					IllegalAccessException, InvocationTargetException, Exception {
		if (null == entityClass) {
			logger.error("Class entity can't Null！");
			throw new IllegalArgumentException("Class entity can't Null！");
		} else if (null == source || source.size() == 0) {
			logger.error("source Map can't Null！");
			throw new IllegalArgumentException("source Map can't Null！");
		} else if (null == relation || relation.size() == 0) {
			logger.error("relation Map can't Null！");
			throw new IllegalArgumentException("relation Map can't Null！");
		}

		T result = null;
		Iterator<String> relationProperty = relation.keySet().iterator();
		BeanUtilsBean beanTool = BeanUtilsBean.getInstance();
		String propertyName, relationName;
		Object valueObject;

		result = entityClass.newInstance();

		while (relationProperty.hasNext()) {
			propertyName = relationProperty.next();

			relationName = relation.get(propertyName);
			if (null == relationName) {
				logger.error("relation Map value is Null! relation key: " + propertyName);
				throw new Exception("relation Map value is Null! relation key: " + propertyName);
			}

			valueObject = source.get(relationName);
			if (null == valueObject)
				continue;

			if (false == beanTool.getPropertyUtils().isWriteable(result, propertyName)) {
				logger.error("The property: " + propertyName + " can't be set!");
				throw new Exception("The property: " + propertyName + " can't be set!");
			}

			if (valueObject instanceof String && 0 == valueObject.toString().trim().length()) {
				if (beanTool.getPropertyUtils().getPropertyType(result, propertyName) == String.class)
					beanTool.copyProperty(result, propertyName, valueObject);
			} else {
				beanTool.copyProperty(result, propertyName, valueObject);
			}
		}

		return result;
	}
}
