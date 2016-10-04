package civex.ExtraStuff.Utils;

/**
 * Created by Ryan on 10/4/2016.
 */
public class StringUtils
{

    public String getFormatedTime(int hours)
    {
        String output = "";
        int month = 0;
        int week = 0;
        int day = 0;
        boolean notFirst = false;

        int remain = hours;

        if (remain >= 720)
        {
            month = remain / 720;
            remain = remain % 720;
        }

        if (remain >= 168)
        {
            week = remain / 168;
            remain = remain % 168;
        }

        if (remain >= 24)
        {
            day = remain / 24;
            remain = remain % 24;
        }

        if (month > 0)
        {
            output += month + "m";
            notFirst = true;
        }

        if (week > 0)
        {
            if (notFirst)
            {
                output += " ";
            }
            output += week + "w";
            notFirst = true;
        }

        if (day > 0)
        {
            if (notFirst)
            {
                output += " ";
            }
            output += day + "d";
            notFirst = true;
        }

        if (remain > 0)
        {
            if (notFirst)
            {
                output += " ";
            }
            output += remain + "h";
        }

        return output;
    }
}
