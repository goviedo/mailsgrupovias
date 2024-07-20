package cl.goviedo.emails.grupovias.utils;

import java.text.ParseException;
import org.quartz.CronExpression;

public class CronExpressionInterpreter {

    public static String interpretCronExpression(String cronExpression) {
        try {
            CronExpression cron = new CronExpression(cronExpression);
            return interpret(cronExpression);
        } catch (ParseException e) {
            return "Invalid cron expression: " + cronExpression;
        }
    }

    private static String interpret(String cronExpression) {
        String[] parts = cronExpression.split(" ");
        if (parts.length != 6) {
            return "Invalid cron expression: " + cronExpression;
        }

        String seconds = parts[0];
        String minutes = parts[1];
        String hours = parts[2];
        String dayOfMonth = parts[3];
        String month = parts[4];
        String dayOfWeek = parts[5];

        StringBuilder description = new StringBuilder("The cron expression represents: ");

        description.append("Every ");
        description.append(describe(minutes, "minute"));
        description.append(describe(hours, "hour"));
        description.append(describe(dayOfMonth, "day of the month"));
        description.append(describe(month, "month"));
        description.append(describe(dayOfWeek, "day of the week"));
        description.append(".");

        return description.toString();
    }

    private static String describe(String part, String unit) {
        if (part.equals("*")) {
            return "every " + unit + " ";
        } else if (part.contains("/")) {
            String[] subParts = part.split("/");
            return "every " + subParts[1] + " " + unit + " starting at " + subParts[0] + " ";
        } else if (part.equals("?")) {
            return "";
        } else {
            return part + " " + unit + " ";
        }
    }

    /**
     * Para rapidas preguntas en el mismo codigo.
     * @param args
     */
    public static void main(String[] args) {
        String cronExpression = "0 0/1 * * * ?";
        System.out.println(interpretCronExpression(cronExpression));
    }
}

