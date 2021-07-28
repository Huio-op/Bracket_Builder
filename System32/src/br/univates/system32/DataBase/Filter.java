package br.univates.system32.DataBase;

public interface Filter<T> {

    public boolean isApproved(T obj);

}
