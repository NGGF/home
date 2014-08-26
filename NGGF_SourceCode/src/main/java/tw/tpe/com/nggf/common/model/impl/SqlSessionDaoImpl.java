package tw.tpe.com.nggf.common.model.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.tpe.com.nggf.common.model.SqlSessionDao;

/**
 * SqlSessionDAOImpl provides mybatis DAO function.
 */
public class SqlSessionDaoImpl extends SqlSessionDaoSupport implements
		SqlSessionDao {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 提供 DAO 新增資料的功能.
	 * 
	 * @param  statementName Dao 存取 SQL 指令的 ID ，此 ID 的 名稱為 Mapper 的  NameSpace　加上  sql ID　<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ex. "RVReceipt.insert"<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  nameSpace :  &lt;mapper namespace="RVReceipt" /&gt <br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  sql Id :  &lt;insert id="insert" parameterType="gov.nii.fee.common.model.dataobject.RVReceiptDo"&gt;
	 *             
	 * @param  o  此物件會置換 mapper 的關聯屬性的變數，達成 insert into data 
	 * @return int  回傳 SQL 的結果
	 */
	public int insert(String statementName, Object o) {
		return getSqlSession().insert(statementName, o);
	}

	@SuppressWarnings("unchecked")
	public <T> T insertNRtnPk(Class<T> entityClass, String statementName, Object o) {
		return (T)getSqlSession().selectOne(statementName, o);
	}

	/**
	 * 提供 DAO 更新資料的功能.
	 * 
	 * @param statementName  Dao 存取 SQL 指令的 ID ，此 ID 的 名稱為 Mapper 的  NameSpace　加上  sql ID　<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ex. "RVReceipt.updateByPrimaryKey"<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  nameSpace : &lt;mapper namespace="RVReceipt"&gt<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  sql Id : &lt;update id="updateByPrimaryKey" parameterType="gov.nia.persistence.model.RVReceiptDo"&gt
	 * @param  o  此物件會置換 mapper 的關聯屬性的變數，達成 update data by PrimaryKey
	 * @return update row number   回傳 SQL 的結果
	 */
	public int update(String statementName, Object o) {
		return getSqlSession().update(statementName, o);
	}

	/**
	 * 提供 DAO 刪除資料的功能.
	 * 
	 * @param  statementName  Dao 存取 SQL 指令的 ID ，此 ID 的 名稱為 Mapper 的  NameSpace　加上  sql ID　<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ex. "RVReceipt.deleteByPrimaryKey"<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  nameSpace : &lt;mapper namespace="RVReceipt"&gt<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  sql Id : <delete id="deleteByPrimaryKey" parameterType="java.lang.String"&gt
	 * @param  o  此物件會置換 mapper 的關聯屬性的變數，達成 delete data by PrimaryKey
	 * @return delete row number   回傳 SQL 的結果
	 */
	public int delete(String statementName, Object o) {
		return getSqlSession().delete(statementName, o);
	}

	/**
	 * 提供 DAO 查詢資料的功能，不含參數.
	 * 
	 * @param  entityClass  回傳物件型別
	 * @param  statementName  Dao 存取 SQL 指令的 ID ，此 ID 的 名稱為 Mapper 的  NameSpace　加上  sql ID　<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ex. "RVReceipt.selectByPrimaryKey"<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  nameSpace : &lt;mapper namespace="RVReceipt"&gt<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  sql Id : &lt;select id="selectByPrimaryKey" parameterType="java.lang.String" resultMap="BaseResultMap"&gt
	 * @return query result    傳回查詢結果的物件，泛型由使用者自行轉型
	 */
	@SuppressWarnings("unchecked")
	public <T> T find(Class<T> entityClass, String statementName) {
		return (T)getSqlSession().selectOne(statementName);
	}
	
	/**
	 * 提供 DAO 查詢資料的功能，傳入查找關聯參數.
	 * 
	 * @param  entityClass   回傳物件型別
	 * @param  statementName  Dao 存取 SQL 指令的 ID ，此 ID 的 名稱為 Mapper 的  NameSpace　加上  sql ID　<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ex. "RVReceipt.selectByPrimaryKey"<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  nameSpace : &lt;mapper namespace="RVReceipt"&gt<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  sql Id : &lt;select id="selectByPrimaryKey" parameterType="java.lang.String" resultMap="BaseResultMap"&gt
	 * @param  o  傳入查詢的參數物件 <br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;例如 HashMap，HashMap key 必須與 Mapper 的變數名稱相同<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ex. where ReceiptNo = #{receiptNo,jdbcType=VARCHAR}<br>
	 *                HashMap.put("receiptNo","XXXXXXXXXXX");<br>
	 * @return query result<br>
	 *            傳回查詢結果的物件，泛型由使用者自行轉型
	 */
	@SuppressWarnings("unchecked")
	public <T> T find(Class<T> entityClass, String statementName, Object o) {
		return (T)getSqlSession().selectOne(statementName, o);
	}
	
	/**
	 * 提供 DAO 查詢資料的功能，不含參數.
	 * 
	 * @param  entityClass  回傳物件型別
	 * @param  statementName   Dao 存取 SQL 指令的 ID ，此 ID 的 名稱為 Mapper 的  NameSpace　加上  sql ID　<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ex. "RVReceipt.selectByPrimaryKey"<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  nameSpace : &lt;mapper namespace="RVReceipt"&gt<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  sql Id : &lt;select id="selectByPrimaryKey" parameterType="java.lang.String" resultMap="BaseResultMap"&gt
	 * @return query list result 傳回查詢結果的物件，泛型由使用者自行轉型
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> query(Class<T> entityClass, String statementName) {
		return (List<T>)getSqlSession().selectList(statementName);
	}

	/**
	 * 提供 DAO 查詢資料的功能，傳入查找關聯參數.
	 * 
	 * @param  entityClass    回傳物件型別
	 * @param  statementName  Dao 存取 SQL 指令的 ID ，此 ID 的 名稱為 Mapper 的  NameSpace　加上  sql ID　<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ex. "RVReceipt.selectByPrimaryKey"<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  nameSpace : &lt;mapper namespace="RVReceipt"&gt<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XML Mapper 的  sql Id : &lt;select id="selectByPrimaryKey" parameterType="java.lang.String" resultMap="BaseResultMap"
	 * @param  o  傳入查詢的參數物件 ，例如 HashMap，HashMap key 必須與 Mapper 的變數名稱相同<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ex. where ReceiptNo = #{receiptNo,jdbcType=VARCHAR}<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;HashMap.put("receiptNo","XXXXXXXXXXX");<br>
	 * @return query list result 傳回查詢結果的物件，泛型由使用者自行轉型
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> query(Class<T> entityClass, String statementName, Object o) {
		List<T> list = (List<T>)getSqlSession().selectList(statementName, o);
		return list;
	}
}