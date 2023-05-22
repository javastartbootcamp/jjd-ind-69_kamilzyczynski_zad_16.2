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
                    ZoneId warsaw = ZoneId.of("Europe/Warsaw");
                    ZonedDateTime userDateZoned = localDate.atStartOfDay(warsaw);

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
                    ZoneId warsaw = ZoneId.of("Europe/Warsaw");
                    ZonedDateTime userDateZoned = userDate.atZone(warsaw);

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
        ZoneId utc = ZoneId.of("Etc/UTC");
        ZoneId london = ZoneId.of("Europe/London");
        ZoneId losAngeles = ZoneId.of("America/Los_Angeles");
        ZoneId sydney = ZoneId.of("Australia/Sydney");

        ZonedDateTime utcDateTime = userDateZoned.withZoneSameInstant(utc);
        ZonedDateTime londonDateTime = userDateZoned.withZoneSameInstant(london);
        ZonedDateTime losAngelesDateTime = userDateZoned.withZoneSameInstant(losAngeles);
        ZonedDateTime sydneyDateTime = userDateZoned.withZoneSameInstant(sydney);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String userDateOutput = userDateZoned.format(formatter);
        String utcOutput = utcDateTime.format(formatter);
        String londonOutput = londonDateTime.format(formatter);
        String losAngelesOutput = losAngelesDateTime.format(formatter);
        String sydneyOutput = sydneyDateTime.format(formatter);

        System.out.println("Czas lokalny: " + userDateOutput);
        System.out.println("UTC: " + utcOutput);
        System.out.println("Londyn: " + londonOutput);
        System.out.println("Los Angeles: " + losAngelesOutput);
        System.out.println("Sydney: " + sydneyOutput);
    }
}
