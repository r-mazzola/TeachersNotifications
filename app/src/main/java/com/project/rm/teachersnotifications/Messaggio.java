package com.project.rm.teachersnotifications;

/**
 * Created by rm on 28/06/2017.
 */

public class Messaggio {
    private String id;
    private String corso;
    private String testoMessaggio;
    private String timeStamp;


    public Messaggio() {
    }

    public Messaggio(String id, String corso, String testoMessaggio, String timeStamp) {
        this.id = id;
        this.corso = corso;
        this.testoMessaggio = testoMessaggio;
        this.timeStamp = timeStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorso() {
        return corso;
    }

    public void setCorso(String corso) {
        this.corso = corso;
    }

    public String getTestoMessaggio() {
        return testoMessaggio;
    }

    public void setTestoMessaggio(String testoMessaggio) {
        this.testoMessaggio = testoMessaggio;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
