package registry.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by anzhen on 2016/5/25.
 */
public class ExceptionUtil {

    public static String getError(Throwable e){
        if(e == null){
            return "";
        }

        StringWriter  sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try{
            e.printStackTrace(pw);
            pw.flush();
        }finally {
            pw.close();
        }
        return sw.toString();
    }


    public static Throwable getCause(Throwable e,Class<?> clazz){
        if(e == null || clazz == null){
            return null;
        }
        Throwable cause = e;
        while (cause !=null){
            if(cause.getClass().equals(clazz)){
                return cause;
            }
            cause  = cause.getCause();
        }
        return null;
    }
}
