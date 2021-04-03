package br.univates.system32.app;

public abstract class ApplicationProcess {

	private String description;
	
	public ApplicationProcess(String description) {
		
		this.description = description;
		
	}
	
	public abstract void run() throws FatalSystemException;

    public String getDescription()
    {
        return description;
    }

}
