package com.example.redisspringboot;

import com.example.redisspringboot.entity.HrSalary;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @author miss
 * <p>
 * Created by miss on 2018/2/2
 */
public class ReToListTest {

    @Test
    public void tesrsql() {
        String URL = "jdbc:mysql://deyun.51vip.biz:10521/mpaas?useSSL=false";
        String USER = "root";
        String PASSWORD = "definesys";
        ResultSet rs;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //2.获得数据库链接
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            Statement st = conn.createStatement();
            String sql = "SELECT hr.* FROM hr_salary hr INNER JOIN hr_employee he ON hr.employee_id = he.employee_id";
            rs = st.executeQuery(sql);

            ResultSetMetaData md = rs.getMetaData();
            List list = new ArrayList();
            int colCount = md.getColumnCount();
            List<HrSalary> res = new ArrayList();
            Field[] fields = HrSalary.class.getDeclaredFields();
            while(rs.next()){//对每一条记录进行操作
                HrSalary obj = new HrSalary();//构造业务对象实体
                //将每一个字段取出进行赋值
                for(int i = 1;i<=colCount;i++){
                    Object value = rs.getObject(i);
                    //寻找该列对应的对象属性
                    for(int j=0;j<fields.length;j++){
                        Field f = fields[j];
                        //如果匹配进行赋值
                        if(f.getName().equalsIgnoreCase(md.getColumnName(i))){
                            boolean flag = f.isAccessible();
                            f.setAccessible(true);
                            f.set(obj, value);
                            f.setAccessible(flag);
                        }
                    }
                }
                list.add(obj);
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
}
}
