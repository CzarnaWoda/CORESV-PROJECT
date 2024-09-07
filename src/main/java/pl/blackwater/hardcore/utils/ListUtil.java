package pl.blackwater.hardcore.utils;

import pl.blackwater.hardcore.enums.MessageType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtil {

    public static List<Long> convertStringToLongList(String s){
        List<Long> list = new ArrayList<>();
        String string = s.replace("[" , "").replace("]" ,"").replace(" ", "");
        if(string.equals("")){
            return list;
        }
        for(String s1 : string.split(",")){
            list.add(Long.valueOf(s1));
        }
        return list;
    }
    public static String convertLongListToString(List<Long> list){
        List<String> stringList = new ArrayList<>();
        list.forEach(aLong -> stringList.add(aLong.toString()));
        return stringList.toString().replace("[" , "").replace("]","");
    }
    public static List<Integer> convertStringToIntegerList(String s){
        List<Integer> integers = new ArrayList<>();

        String string = s.replace("[" , "").replace("]" ,"").replace(" ", "");
        if(string.equals("")){
            return integers;
        }
        for(String s1 : string.split(",")){
            integers.add(Integer.parseInt(s1));
        }
        return integers;
    }
    public static String convertIntegerListToString(List<Integer> list){
        List<String> stringList = new ArrayList<>();
        list.forEach(integer -> stringList.add(integer.toString()));
        return stringList.toString().replace("[" , "").replace("]","");
    }
    public static String convertListToString(List<String> list){
        StringBuilder s = new StringBuilder();
        for(String string : list){
            s.append(string).append(";");
        }
        return s.toString();
    }
    public static List<String> convertStringToList(String s){
        String [] array = s.split(";");
        List<String> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }
    public static List<MessageType> convertStringToMessageTypes(String messages){
        List<MessageType> mlist = new ArrayList<>();
        if(messages.equalsIgnoreCase("")){
            return mlist;
        }
        String[] array = messages.split(";");
        for(String s : array){
            final MessageType messageType = MessageType.getMessageType(s);
            if(messageType != null){
                mlist.add(messageType);
            }
        }
        return mlist;
    }
    public static String convertMessageTypesToString(List<MessageType> list){
        StringBuilder s = new StringBuilder();
        for(MessageType type : list){
            s.append(type.getDesc()).append(";");
        }
        return s.toString();
    }
}
