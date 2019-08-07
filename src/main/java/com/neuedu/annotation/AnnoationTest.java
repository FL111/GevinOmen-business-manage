package com.neuedu.annotation;


import com.neuedu.pojo.UserInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnoationTest {

    public static void main(String[] args) {
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
