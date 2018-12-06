package etf.unsa.ba.rpr.tutorijal7;

import java.io.Serializable;


public class Grad implements Serializable {
    private String naziv;
    private int brojStanovnika, vel;
    private double []temperature = new double[1000];

    public Grad() {}

    public Grad (String naziv, int brojStanovnika, int vel, double []temperature) {
        setNaziv(naziv);
        setBrojStanovnika(brojStanovnika);
        setVel(vel);
        setTemperature(temperature);
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getVel() {
        return vel;
    }

    public void setVel(int vel) {
        this.vel = vel;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public double[] getTemperature() {
        return temperature;
    }

    public void setTemperature(double[] temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return naziv + ", " + brojStanovnika;
    }
}
