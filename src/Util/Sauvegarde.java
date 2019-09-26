package Util;

import Objets.Systeme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Sauvegarde {
    static String path;

    public Sauvegarde(String path){
        this.path = path;
    }

    public static Systeme charger() throws FileNotFoundException {
        File astroFile = new File(path);
        System.out.print("Le fichier "+astroFile.getName()+" à été chargé.");
        BufferedReader br = new BufferedReader(new FileReader(astroFile));


        return null;
    }

}
