package br.univates.system32.report;

import java.util.HashMap;

public class Report
{
    private HashMap hashMap;
    private String path;

    public Report(String path){
        this.path = path;
    }

    public Report(String path, HashMap hashMap){
        this.path = path;
        this.hashMap = hashMap;
    }

    public HashMap getHashMap() {
        return hashMap;
    }

    public String getPath() {
        return path;
    }
}
