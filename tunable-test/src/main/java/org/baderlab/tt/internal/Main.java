package org.baderlab.tt.internal;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.baderlab.tt.internal.tunables.VoteList;

public class Main {

    public static void main(String[] args) {
        VoteList voteList = new VoteList();
        for(Field field : voteList.getClass().getFields()) {
            Type type = field.getGenericType();
            if(type instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) type;
                for(Type arg : paramType.getActualTypeArguments()) {
                    System.out.println(arg);
                }
            }
            System.out.println(field.getName() + " " + type.getTypeName() + " " + type.getClass());
        }
    }

}
