package br.com.application.resources.reports;

import br.com.application.persistencia.DBApp;
import br.univates.system32.report.Report;
import br.univates.system32.report.ReportGenerator;

import java.util.HashMap;

public class BracketBuilderReports {

    public static enum ReportType{
        EVENTO_LISTING("/br/com/application/resources/reports/Listagem_Eventos.jrxml"),
        PARTICIPANTE_LISTING("/br/com/application/resources/reports/Listagem_Participantes.jrxml"),
        EVENTO_REPORT("/br/com/application/resources/reports/Relatorio_Eventos.jrxml"),
        PARTICIPANTE_REPORT("/br/com/application/resources/reports/Relatorio_Participantes.jrxml");

        public String path;

        ReportType(String path) {
            this.path = path;
        }

        public String getPath(){
            return this.path;
        }

    }

    public static void generateReport(ReportType type){

        ReportGenerator generator = new ReportGenerator(DBApp.getConnection());
        generator.generateReport(new Report(type.getPath(), null));

    }

    public static void generateReport(ReportType type, HashMap hashMap){


        ReportGenerator generator = new ReportGenerator(DBApp.getConnection());

        generator.generateReport(new Report(type.getPath(), hashMap));

    }

}
