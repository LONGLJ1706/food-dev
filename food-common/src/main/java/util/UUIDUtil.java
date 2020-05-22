package util;

import java.util.UUID;

public class UUIDUtil {

    public static String uuid(){
       UUID uuid = UUID.randomUUID();
       String result = uuid.toString().replace("-","");
       return result;
    }

}
