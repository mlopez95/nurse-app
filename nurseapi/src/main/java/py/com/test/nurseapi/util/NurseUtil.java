package py.com.test.nurseapi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mlopez
 * @fecha 11/29/18,11:41 AM
 */


public class NurseUtil {

    public static String convertirDateToString(Date fecha){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        return dateFormat.format(fecha);
    }

    public static Date convertirStringToDate(String fecha){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        try {
            return dateFormat.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
