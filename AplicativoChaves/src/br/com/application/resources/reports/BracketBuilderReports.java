package br.com.application.resources.reports;

import br.com.application.persistencia.DBApp;
import br.univates.system32.report.Report;
import br.univates.system32.report.ReportGenerator;

import java.util.HashMap;

public class BracketBuilderReports {

    public static enum ReportType{
        EVENTO_REPORT("/br/com/application/resources/reports/Listagem_Eventos.jrxml", null),
        PARTICIPANTE_REPORT("/br/com/application/resources/reports/Listagem_Participantes.jrxml", null);

        public String path;
        public HashMap hash;

        ReportType(String path, HashMap hash) {
            this.path = path;
            this.hash = hash;
        }

        public String getPath(){
            return this.path;
        }
        public HashMap getHash() { return this.hash; }

    }

    public static void generateReport(ReportType type){

        ReportGenerator generator = new ReportGenerator(DBApp.getConnection());
        generator.generateReport(new Report(type.getPath()));

    }

}
