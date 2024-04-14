import java.util.ArrayList;
import java.util.List;

public class StreamCCData extends Stream {
  
    private Float Temperature;
    private Float Pressure;
    private Float FlowRate;
    private Float FracMol;
    private List<StreamCCData> ComposentList ;
    
    //private boolean isWellConnected = false;

    public StreamCCData(int arg) {
        super(arg);
        this.ComposentList = new ArrayList<>();
        

    }
}
