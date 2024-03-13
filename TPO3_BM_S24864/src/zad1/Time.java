/**
 *
 *  @author Bożek Michał S24864
 *
 */

package zad1;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Time {
    public static String timePassed(String from, String to){
        String date="";
        double hrs = 24.0;
        double wks = 7.0;
        String timePattern = "d MMMM yyyy (EEEE) 'godz.' HH:mm";
        String datePattern = "d MMMM yyyy (EEEE)";
        try
        {
            if(from.contains("T") && to.contains("T")) {
                LocalDateTime localDateFrom = LocalDateTime.parse(from);
                LocalDateTime localDateTo = LocalDateTime.parse(to);
                ZonedDateTime zonedDateFrom = ZonedDateTime.of(localDateFrom, ZoneId.of("Europe/Warsaw"));
                ZonedDateTime zonedDateTo = ZonedDateTime.of(localDateTo, ZoneId.of("Europe/Warsaw"));
                double wksDays=Math.round(Math.round(ChronoUnit.HOURS.between(zonedDateFrom,zonedDateTo)/hrs)/wks*100)/100.0;
                if(wksDays%1==0){
                    int wksDaysInt=(int)wksDays;
                    date = date+"Od "+localDateFrom.format(DateTimeFormatter.ofPattern(timePattern))+" do "+localDateTo.format(DateTimeFormatter.ofPattern(timePattern))+"\n"+" - mija: "+Math.round(ChronoUnit.HOURS.between(zonedDateFrom,zonedDateTo)/hrs)+" "+(Math.round(ChronoUnit.HOURS.between(zonedDateFrom,zonedDateTo)/hrs) != 1 ? "dni" : "dzień")+", tygodni "+wksDaysInt+"\n"+" - godzin: "+ChronoUnit.HOURS.between(zonedDateFrom,zonedDateTo)+", minut: "+ChronoUnit.MINUTES.between(zonedDateFrom,zonedDateTo)+"\n";
                    date = date + getCalendarDays(localDateFrom.toLocalDate(), localDateTo.toLocalDate());
                }
                else {
                    date = date + "Od " + localDateFrom.format(DateTimeFormatter.ofPattern(timePattern)) + " do " + localDateTo.format(DateTimeFormatter.ofPattern(timePattern)) + "\n" + " - mija: " + Math.round(ChronoUnit.HOURS.between(zonedDateFrom, zonedDateTo) / hrs) + " " + (Math.round(ChronoUnit.HOURS.between(zonedDateFrom, zonedDateTo) / hrs) != 1 ? "dni" : "dzień") + ", tygodni " + wksDays + "\n" + " - godzin: " + ChronoUnit.HOURS.between(zonedDateFrom, zonedDateTo) + ", minut: " + ChronoUnit.MINUTES.between(zonedDateFrom, zonedDateTo) + "\n";
                    date = date + getCalendarDays(localDateFrom.toLocalDate(), localDateTo.toLocalDate());
                }}
            else
            {
                LocalDate localDateFrom=LocalDate.parse(from);
                LocalDate localDateTo=LocalDate.parse(to);
                double wksDays=Math.round(ChronoUnit.DAYS.between(localDateFrom,localDateTo)/wks*100)/100.0;
                if(wksDays%1==0){
                    int wksDaysInt=(int)wksDays;
                    date = date+"Od "+localDateFrom.format(DateTimeFormatter.ofPattern(datePattern))+" do "+localDateTo.format( DateTimeFormatter.ofPattern(datePattern))+"\n"+" - mija: "+ChronoUnit.DAYS.between(localDateFrom,localDateTo)+" "+ (ChronoUnit.DAYS.between(localDateFrom,localDateTo)!=1?"dni":"dzień")+", tygodni "+wksDaysInt+"\n"+getCalendarDays(localDateFrom,localDateTo);

                }else {
                    date = date + "Od " + localDateFrom.format(DateTimeFormatter.ofPattern(datePattern)) + " do " + localDateTo.format(DateTimeFormatter.ofPattern(datePattern)) + "\n" + " - mija: " + ChronoUnit.DAYS.between(localDateFrom, localDateTo) + " " + (ChronoUnit.DAYS.between(localDateFrom, localDateTo) != 1 ? "dni" : "dzień") + ", tygodni " + wksDays + "\n" + getCalendarDays(localDateFrom, localDateTo);
                }
            }
        }
        catch(Exception e)
        {
            return e.getMessage();
        }
        return date;
    }
    private static String getCalendarDays(LocalDate dateFrom,LocalDate dateTo){
        if(ChronoUnit.DAYS.between(dateFrom,dateTo)>=1) {
            String days="";
            String months="";
            String years="";
            Period p = Period.between(dateFrom,dateTo);
            if(p.getYears()==1){
                years="1 rok";
            }
            else if((p.getYears()>=2)&&(p.getYears()<=5)){
                years=p.getYears()+" lata";
            }
            else if((p.getYears()>=6)){
                years=p.getYears()+" lat";
            }
            if(years!=""){
                months+=", ";
            }
            if(p.getMonths()==1){
                months+="1 miesiąc";
            }
            else if((p.getMonths()>=2)&&(p.getMonths()<=4)){
                months+=p.getMonths()+" miesiące";
            }
            else if((p.getMonths()>=5)&&(p.getMonths()<=12)){
                months+=p.getMonths()+" miesięcy";
            }
            if(months!=""){
                days+=", ";
            }
            if(p.getDays()==1){
                days+="1 dzień";
            }
            else if((p.getDays()>=2)&&(p.getDays()<=31)){
                days+=p.getDays()+" dni";
            }
            String periodString =" - kalendarzowo: "+years+months+days;
            return periodString;
        }
        return "";
    }
}
