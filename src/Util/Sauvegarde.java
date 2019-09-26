package Util;

import Objets.Systeme;

import java.io.*;

public class Sauvegarde {
    static String path;

    public Sauvegarde(String path){
        this.path = path;
    }

    public static Systeme charger() throws FileNotFoundException {
        File astroFile = new File(path);
        System.out.print("Le fichier "+astroFile.getName()+" à été chargé.");
        BufferedReader br = new BufferedReader(new FileReader(astroFile));
        try {
            String thisLine = br.readLine();
            while (thisLine != null) {
                if(thisLine.startsWith("PARAMS")){
                    String valeur[] = thisLine.split(" ");
                    String valeur2[] = new String[valeur.length];
                    for (int i = 2; i < valeur.length ; i++) {
                        valeur2[i-2] = valeur[i].split("=")[1];
                    }
                }

            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
