package com.lblin.weixin.infrastruture.orm.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

import com.lblin.weixin.infrastruture.orm.Page;
import com.lblin.weixin.infrastruture.orm.dialect.Dialect;

@Intercepts({ @org.apache.ibatis.plugin.Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class MyBatisPagePlugin implements Interceptor{
	private static final Log logger = LogFactory.getLog(MyBatisPagePlugin.class);
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static String pageSqlId = ".*Page$";
	private Dialect dialect;
	
	public Object intercept(Invocation invocation) throws Throwable {
		
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY);
        // ������������(����Ŀ������ܱ�������������أ��Ӷ��γɶ�δ���ͨ�����������ѭ�����Է������ԭʼ�ĵ�Ŀ����)
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        // �������һ����������Ŀ����
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        // ֻ��д��Ҫ��ҳ��sql��䡣ͨ��MappedStatement��IDƥ�䣬Ĭ����д��Page��β��MappedStatement��sql
        if (mappedStatement.getId().matches(pageSqlId)) {
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null) {
                throw new NullPointerException("parameterObject is null!");
            } else {
                @SuppressWarnings("rawtypes")
				Page page = (Page) metaStatementHandler
                        .getValue("delegate.boundSql.parameterObject.page");
                String sql = boundSql.getSql();
                // ��дsql
                String pageSql = dialect.getLimitString(sql, page.getFirst() - 1,
        				page.getPageSize());
                metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                // ���������ҳ�󣬾Ͳ���Ҫmybatis���ڴ��ҳ�ˣ����������������������
                metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
                metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
                Connection connection = (Connection) invocation.getArgs()[0];
                // �����ҳ���������ҳ����
                setPageParameter(sql, connection, mappedStatement, boundSql, page);
            }
        }
		
		return invocation.proceed();
	}

	public Object plugin(Object arg0) {
		return null;
	}

	public void setProperties(Properties props) {
		String dialectPro = props.getProperty("dialect");
		String pageSqlIdPro = props.getProperty("pageSqlId");
		if (!isStringHasText(pageSqlIdPro)) {
            logger.warn("Property pageSqlId is not setted,use default '.*Page$' ");
        }else{
        	pageSqlId = pageSqlIdPro;
        }
		if(!isStringHasText(dialectPro)){
			throw new IllegalArgumentException("[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
		};
		try {
			Class<?> clazz = Class.forName(dialectPro);
			this.dialect = (Dialect)clazz.newInstance();
		} catch (Exception e) {
			Throwable throwable = e.getCause();
			throw new RuntimeException(throwable);
		} 
	}
	
	private static boolean isStringHasText(String text) {
		if (!(text != null && text.length() > 0)) {
			return false;
		}

		int strLen = text.length();
		for (int i = 0; i < strLen; ++i) {
			if (!(Character.isWhitespace(text.charAt(i)))) {
				return true;
			}
		}
		return false;
	}

	/**
     * �����ݿ����ѯ�ܵļ�¼����������ҳ������д����ҳ����<code>PageParameter</code>,���������߾Ϳ���ͨ�� ��ҳ����
     * <code>PageParameter</code>��������Ϣ��
     * 
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    @SuppressWarnings("rawtypes")
	private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
            BoundSql boundSql, Page page) {
        // ��¼�ܼ�¼��
        String countSql = "select count(0) from (" + sql + ") as total";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotalCount(totalCount);

        } catch (SQLException e) {
            logger.error("Ignore this exception", e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
        }

    }
    
    /**
     * ��SQL����(?)��ֵ
     * 
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }
}
