package br.com.clinica.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendaUtil {
    public static List<LocalDate> diasAtendimentoMes(int ano, int mes) {

        List<LocalDate> dias = new ArrayList<>();
        LocalDate data = LocalDate.of(ano, mes, 1);

        while (data.getMonthValue() == mes) {

            DayOfWeek dia = data.getDayOfWeek();

            if (dia != DayOfWeek.SUNDAY) {
                dias.add(data);
            }

            data = data.plusDays(1);
        }

        return dias;
    }

    public static List<LocalTime> horariosDoDia(LocalDate data) {

        List<LocalTime> horarios = new ArrayList<>();

        DayOfWeek dia = data.getDayOfWeek();

        if (dia == DayOfWeek.SUNDAY) {
            return horarios;
        }

        // manhã 08–12
        for (int h = 8; h < 12; h++) {
            horarios.add(LocalTime.of(h, 0));
        }

        // sábado só manhã
        if (dia == DayOfWeek.SATURDAY) {
            return horarios;
        }

        // tarde 13–17
        for (int h = 13; h < 17; h++) {
            horarios.add(LocalTime.of(h, 0));
        }

        return horarios;
    }
}
