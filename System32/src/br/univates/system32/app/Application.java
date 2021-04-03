//package br.univates.system32.app;
//
//import java.util.ArrayList;
//
//import br.univates.system32.DataBase.DBConnection;
//import br.univates.system32.JFX.JFXErrorDialog;
//
//public abstract class Application {
//
//
//    private String dataBaseUserName;
//    private String dataBasePassword;
//    private String dataBaseName;
//    
//    boolean firstExecution;
//    String applicationName;
//	
//    private DBConnection dataBaseManager;
//    private ArrayList<ApplicationProcess> firstExecutionProcess; // processos de inicialização
//    private ArrayList<ApplicationProcess> initialProcess; // processos de inicialização
//    private ArrayList<ApplicationProcess> finalProcess; // processos de finalização do sistema
//    
//    public Application()
//    {
//        this( "Falta definir a nome da aplicação","semnome" );
//    }
//    
//    
//    public Application( String applicationName, String dataBaseName )
//    {
//        this.firstExecutionProcess = new ArrayList();
//        this.initialProcess = new ArrayList();
//        this.finalProcess = new ArrayList();
//        
//        this.dataBasePassword = null;
//        this.dataBaseUserName = null;
//        
//        this.dataBaseName = dataBaseName;
//        this.applicationName = applicationName;
//        this.firstExecution = !existsPropertiesFile();
//    }
//	
//    public void addFirstExecutionProcess(ApplicationProcess process) {
//    	this.firstExecutionProcess.add(process);
//    }
//    
//    public void addInitialProcess(ApplicationProcess process) {
//    	this.initialProcess.add(process);
//    }
//    
//    public void addFinalProcess(ApplicationProcess process) {
//    	this.finalProcess.add(process);
//    }
//    
//    public abstract void defineFirstExecutionProcesses();
//    
//    public abstract void defineInitialProcesses();
//    
//    public abstract void defineFinalProcesses();
//    
//    
//    public void runFirstExecutionProcesses()
//    {
//        for (ApplicationProcess process: firstExecutionProcess)
//        {
//            System.out.println(process.getDescription());
//            try
//            {
//                process.run();
//            } 
//            catch (FatalSystemException ex)
//            {
//                //JFXErrorDialog error = new JFXErrorDialog(ex);
//            }
//        }
//    }
//    
//    public void runInitalProcesses()
//    {    
//        for (ApplicationProcess process: initialProcess)
//        {
//            System.out.println( process.getDescription() );
//            try
//            {
//                process.run();
//            } 
//            catch (FatalSystemException ex)
//            {
//            	//JFXErrorDialog error = new JFXErrorDialog(ex);
//            }
//        }
//    }
//    
//    public void runFinalProcesses()
//    {
//        for (ApplicationProcess process: finalProcess)
//        {
//            System.out.println(process.getDescription());
//            try
//            {
//                process.run();
//            } 
//            catch (FatalSystemException ex)
//            {
//            	//JFXErrorDialog error = new JFXErrorDialog(ex);
//            }
//        }
//    }
//    
//}
