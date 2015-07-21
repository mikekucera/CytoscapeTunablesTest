package org.baderlab.tt.internal;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.baderlab.tt.internal.tunables.VoteList;

public class Main {

    public static void main(String[] args) throws Exception {
        VoteList voteList = new VoteList();
        for(Field field : voteList.getClass().getFields()) {
            Type type = field.getGenericType();
            if(type instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) type;
                for(Type arg : paramType.getActualTypeArguments()) {
                    System.out.println(arg.getTypeName());
                    Class<?> c = Class.forName(arg.getTypeName());
                    System.out.println(c);
                }
            }
            System.out.println(field.getName());
            System.out.println(type.getTypeName());
            System.out.println(type.toString());
        }
    }

}
