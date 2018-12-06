package etf.unsa.ba.rpr.tutorijal7;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    public static UN ucitajXml(ArrayList<Grad> gradovi) {
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

        UN un = new UN();
        ArrayList<Drzava> drzave = new ArrayList<>();

        NodeList xmlDrzave = xmldoc.getElementsByTagName("drzava");

        for(int i = 0; i < xmlDrzave.getLength(); i++) {
            Node drzavaNode = xmlDrzave.item(i);

            if(drzavaNode instanceof Element) {
                Element drzava = (Element)drzavaNode;

                int stanovnika = Integer.parseInt(drzava.getAttribute("brojStanovnika"));
                String naziv = drzava.getElementsByTagName("naziv").item(0).getTextContent();

                Element povrsinaXml = (Element)drzava.getElementsByTagName("povrsina").item(0);
                String jedinica = povrsinaXml.getAttribute("jedinicaZaPovrsinu");
                double povrsina = Double.parseDouble(drzava.getElementsByTagName("povrsina").item(0).getTextContent());

                Element glavniGradXml = (Element)drzava.getElementsByTagName("glavnigrad").item(0);
                int gStanovnika = Integer.parseInt(glavniGradXml.getAttribute("brojStanovnika"));
                String nazivGrada = glavniGradXml.getTextContent().trim();

                Grad glavniGrad = new Grad(nazivGrada, gStanovnika, 0, null);
                drzave.add(new Drzava(naziv, stanovnika, povrsina, jedinica, glavniGrad));
            }
        }
        un.setDrzave(drzave);
        return un;
    }

    public static void zapisiXml(UN un) {
        try {
            XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("un.xml"));
            izlaz.writeObject(un);
            izlaz.close();
        } catch(Exception e) {
            System.out.println("Greška: " + e);
        }
    }

    public static void main(String[] args) {

        ArrayList<Grad> grads = ucitajGradove();
        /*
        for(int i = 0; i < grads.size(); i++) {
            System.out.print(grads.get(i).getNaziv() + " " + grads.get(i).getBrojStanovnika() + " ");
            for(int j = 0; j < grads.get(i).getVel(); j++)
                System.out.print(grads.get(i).getTemperature()[j] + " ");
            System.out.println();
        }

        ArrayList<Drzava> drzave = new ArrayList<>();
        drzave.add(new Drzava("BiH", 3500000, (double)51129, "km2", grads.get(0)));
        UN un = new UN();
        un.setDrzave(drzave);
        zapisiXml(un);
        */

        UN un = ucitajXml(grads);
        for(Drzava drzava : un.getDrzave()) {
            System.out.println(drzava);
        }

    }
}
