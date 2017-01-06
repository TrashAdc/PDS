//created by ryan v on 1/4/2017.
//this class just makes calling time easier by putting it in some methods.
//nothing too special here. it's pretty self explanatory. call any one of these methods to get the current time.

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime {
    private DateFormat dateFormat; //dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    private Date date;

    public GetTime(){
        date = new Date();
    }

    public int getSecond(){
        dateFormat = new SimpleDateFormat("ss");
        return Integer.parseInt(dateFormat.format(date));
    }
    public int getMinute(){
        dateFormat = new SimpleDateFormat("mm");
        return Integer.parseInt(dateFormat.format(date));
    }
    public int getHour(){
        dateFormat = new SimpleDateFormat("HH");
        return Integer.parseInt(dateFormat.format(date));
    }

    public int getDay(){
        dateFormat = new SimpleDateFormat("dd");
        return Integer.parseInt(dateFormat.format(date));
    }
    public int getMonth(){
        dateFormat = new SimpleDateFormat("MM");
        return Integer.parseInt(dateFormat.format(date));
    }
    public int getYear(){
        dateFormat = new SimpleDateFormat("yyyy");
        return Integer.parseInt(dateFormat.format(date));
    }

    public String getTime(){
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(date);
    }
    public String getDate(){
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
    }
    public String getDateTime(){
        dateFormat = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
        return dateFormat.format(date);
    }
}
