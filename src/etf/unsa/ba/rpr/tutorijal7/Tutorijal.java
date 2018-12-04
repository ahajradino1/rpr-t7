package etf.unsa.ba.rpr.tutorijal7;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {

    public static ArrayList<Grad> ucitajGradove()  {
        ArrayList<Grad> gradovi = new ArrayList<>();
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileReader("mjerenja.txt")).useDelimiter("[\\r\\n,]");
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka mjerenja.txt se ne moze otvoriti!");
        }

        while(ulaz.hasNext()) {
            String imeGrada = ulaz.next();
            double []temperature = new double[1000];
            int vel = 0;
            while(ulaz.hasNextDouble()) {
                temperature[vel] = ulaz.nextDouble();
                vel = vel + 1;
                if(vel == 1000) {
                    System.out.println("Dostignut je maksimalan broj podataka");
                    break;
                }
            }
             if(ulaz.hasNext()) ulaz.next();
            gradovi.add(new Grad(imeGrada, 0, vel, temperature));
        }
        ulaz.close();
        return gradovi;
    }

    public static void ucitajElemente(Element element, UN un, ArrayList<Grad> gradovi) {

    }

    public static UN ucitajXml(ArrayList<Grad> gradovi) {
        /*
        Tako učitane države se pohranjuju u klasu UN te se ta klasa
        vraća iz funkcije. Osim toga, funkcija kao parametar prima
        ArrayList<Grad> iz prethodnog zadatka, te u svaki grad koji
        se nalazi u datoteci drzave.xml dodaje mjerenja koja se nalaze na
        listi. Grad se smatra da je identičan ako je njegov naziv identičan.
        Za gradove koji se nalaze u listi a ne nalaze u datoteci (i obrnuto)
        ne treba raditi ništa.
        */
        UN un = new UN();
        Document xmldoc = null;
        try {
            DocumentBuilder docReader
                    = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc = docReader.parse(new File("drzave.xml"));
        }
        catch (Exception e) {
            System.out.println("drzave.xml nije validan XML dokument");
            //return;
        }
        Element korijen = null;
        if (xmldoc != null) {
            korijen = xmldoc.getDocumentElement();
        }
        ucitajElemente(korijen, un, gradovi);
        return un;
    }

    public static void main(String[] args) {
        ArrayList<Grad> grads = ucitajGradove();
         for(int i = 0; i < grads.size(); i++) {
            System.out.print(grads.get(i).getNaziv() + " " + grads.get(i).getBrojStanovnika() + " ");
            for(int j = 0; j < grads.get(i).getVel(); j++)
                System.out.print(grads.get(i).getTemperature()[j] + " ");
            System.out.println();
        }
    }
}
