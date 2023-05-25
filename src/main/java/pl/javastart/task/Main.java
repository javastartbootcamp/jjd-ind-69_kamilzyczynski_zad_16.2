package pl.javastart.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        ZonedDateTime userDateZoned = null;
        while (userDateZoned == null) {
            System.out.println("Podaj datę: ");
            String userInput = scanner.nextLine();
            try {
                userDateZoned = getLocalizedDate(userInput);

            } catch (DateTimeParseException ex) {
            }

            if (userDateZoned == null) {
                try {
                    userDateZoned = getLocalizedDataTime(userInput);

                } catch (DateTimeParseException ex) { }
            }
            if (userDateZoned == null) {
                System.out.println("Wprowadzono datę w złym formacie. Spróbuj raz jeszcze.");
            } else {
                printDifferentTimeZones(userDateZoned);
            }
        }
    }

    private static ZonedDateTime getLocalizedDataTime(String userInput) {
        List<String> availablePatternsWithTime = Arrays.asList("yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy HH:mm:ss");
        ZoneId localDateZone = ZoneId.systemDefault();
        LocalDateTime userDate = null;
        for (String availablePattern : availablePatternsWithTime) {
            try {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(availablePattern);
                TemporalAccessor parse = dateTimeFormatter.parse(userInput);
                userDate = LocalDateTime.from(parse);
                break;
            } catch (DateTimeParseException ex) {
            }
        }
        return userDate.atZone(localDateZone);
    }

    private static ZonedDateTime getLocalizedDate(String userInput) {
        String availablePatternWithoutTime = "yyyy-MM-dd";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(availablePatternWithoutTime);
        TemporalAccessor parse = dateTimeFormatter.parse(userInput);
        LocalDate localDate = LocalDate.from(parse);
        ZoneId localDateZone = ZoneId.systemDefault();
        return localDate.atStartOfDay(localDateZone);
    }

    private static void printDifferentTimeZones(ZonedDateTime userDateZoned) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String userDateOutput = userDateZoned.format(formatter);

        System.out.println("Czas lokalny: " + userDateOutput);
        System.out.println("UTC: " + calculateTimeZone(userDateZoned, "Etc/UTC"));
        System.out.println("Londyn: " + calculateTimeZone(userDateZoned, "Europe/London"));
        System.out.println("Los Angeles: " + calculateTimeZone(userDateZoned, "America/Los_Angeles"));
        System.out.println("Sydney: " + calculateTimeZone(userDateZoned, "Australia/Sydney"));
    }

    private static String calculateTimeZone(ZonedDateTime userDateZoned, String timeZoneCode) {
        ZoneId userTimeZone = ZoneId.of(timeZoneCode);
        ZonedDateTime userTimeZoneDateTime = userDateZoned.withZoneSameInstant(userTimeZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return userTimeZoneDateTime.format(formatter);
    }
}
