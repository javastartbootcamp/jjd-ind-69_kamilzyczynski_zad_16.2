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
        boolean added = false;
        while (!added) {
            List<String> availablePatternsWithTime = Arrays.asList("yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy HH:mm:ss");
            List<String> availablePatternsWithoutTime = Arrays.asList("yyyy-MM-dd");
            System.out.println("Podaj datę: ");
            String userInput = scanner.nextLine();

            for (String availablePatternWithoutTime : availablePatternsWithoutTime) {
                try {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(availablePatternWithoutTime);
                    TemporalAccessor parse = dateTimeFormatter.parse(userInput);
                    LocalDate localDate = LocalDate.from(parse);
                    ZoneId localDateZone = ZoneId.systemDefault();
                    ZonedDateTime userDateZoned = localDate.atStartOfDay(localDateZone);

                    printDifferentTimeZones(userDateZoned);
                    return;
                } catch (DateTimeParseException ex) {

                }
            }

            for (String availablePattern : availablePatternsWithTime) {
                try {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(availablePattern);
                    TemporalAccessor parse = dateTimeFormatter.parse(userInput);
                    LocalDateTime userDate = LocalDateTime.from(parse);
                    ZoneId localDateZone = ZoneId.systemDefault();
                    ZonedDateTime userDateZoned = userDate.atZone(localDateZone);

                    printDifferentTimeZones(userDateZoned);
                    added = true;
                    break;
                } catch (DateTimeParseException ex) {

                }

            }
            if (!added) {
                System.out.println("Wprowadzono datę w złym formacie. Spróbuj raz jeszcze.");
            }
        }
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
