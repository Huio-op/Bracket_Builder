package br.univates.system32.report;

import br.univates.system32.DataBase.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;

public class ReportGenerator {

    private DBConnection connection;

    public ReportGenerator(DBConnection connection){

        this.connection = connection;

    }

    public void generateReport( Report report )
    {
        try
        {
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream( report.getPath() ));
            JasperPrint print = JasperFillManager.fillReport(jasperReport, report.getHashMap(), connection.getConnection() );

            JasperViewer.viewReport(print,false);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }


}
