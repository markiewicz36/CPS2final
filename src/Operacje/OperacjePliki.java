package Operacje;

import java.io.*;

public class OperacjePliki<T> {

    public void zapiszDoPliku(T object, String nazwa) throws IOException{
        FileOutputStream fos = new FileOutputStream(nazwa);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
    }

    public T wczytajZPliku(String nazwa) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(nazwa);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return (T) ois.readObject();
    }
}
