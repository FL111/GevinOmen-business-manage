package com.neuedu.annotation;


import com.neuedu.pojo.UserInfo;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnoationTest {

    public static void main(String[] args) {
        UserInfo userInfo =new UserInfo();
        userInfo.setUsername("zs");
        System.out.println(new AnnoationTest().query(userInfo));
    }
    /**
     * 根据Userinfo封装sql查询语句
     * */
    public String query(UserInfo userInfo){
        //select 列名1、列名2 from tablename where 列名1=value1.。。。
        StringBuffer buffer=new StringBuffer("select ");
        Class c=userInfo.getClass();
        boolean isexist=c.isAnnotationPresent(Table.class);
        if(isexist){
            Table table=(Table)c.getAnnotation(Table.class);
            String tablename=table.value();//获取表名
            //获取所有属性
            Field[] fields=c.getDeclaredFields();
            for(int i=0;i<fields.length;i++){
                boolean isexit1=fields[i].isAnnotationPresent(Column.class);
                if(isexit1){
                    Column column=(Column)fields[i].getAnnotation(Column.class);
                    String columnName=column.value();
                    buffer.append(columnName);
                    if(i!=(fields.length-1)){
                        buffer.append(",");
                    }
                }

            }

            buffer.append(" form ").append(tablename);
            //解析查询条件
            buffer.append(" where 1=1");
            for(int i=0;i<fields.length;i++){
                Field field=fields[i];
                String fieldName=field.getName();
                String methodName="get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                try {
                    Method method=c.getMethod("setId");
                    Object object=method.invoke(userInfo);



                    if(object!=null){

                        //获取列名
                        boolean isexit1=field.isAnnotationPresent(Column.class);
                        if(isexit1){
                            Column column=(field.getAnnotation(Column.class));
                            String columnName=column.value();
                            if (object instanceof String){
                                buffer.append(" and ").append(columnName).append("='").append(object).append("'");
                            }else if(object instanceof Integer){
                                buffer.append(" and ").append(columnName).append("=").append(object);
                            }

                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }
    public void tses(){
        try {
            Class c=Class.forName("com.neuedu.pojo.UserInfo");
            boolean iset =c.isAnnotationPresent(Table.class);
            if (iset){
                Table table=(Table)c.getAnnotation(Table.class);
                System.out.println(table.value());
            }
            Field[] fields=c.getDeclaredFields();
            Method[] methods=c.getMethods();
            for(Field field:fields){

                boolean is1=field.isAnnotationPresent(Column.class);
                if (is1){
                    Column column =(Column)field.getAnnotation(Column.class);

                    System.out.println(column.value()+"=");
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
