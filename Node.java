//package myHydraulicNetwork;

import java.util.List;
import java.util.ArrayList;

//import myHydraulicNetwork.*;
public abstract class Node {
    /***************************************** Attributes *****************************************/

    private int id;
    private List<Stream> connectedStreams = new ArrayList<>();
    private List<Node> PressureSymboleList = new ArrayList<>();
    private boolean isWellConnected = false; 
    private String PressureSymbol;
    /***************************************** Constructors *****************************************/
    public Node(int id) {
        this.id = id;
        this.PressureSymbol = "P"+id;
       
    }

    
    /***************************************** Methods *****************************************/
    private boolean checkConnectivity() {
        int solidCount = 0;
        int gasCount = 0;
        int streamCount = 0;
        for (Stream stream : connectedStreams) {
            if (stream instanceof SolidStream) {
                solidCount++;
            } else if (stream instanceof GasStream) {
                gasCount++;
            }
        }
        streamCount = solidCount + gasCount;
        return streamCount >= 2 &&  (solidCount >= 2 || solidCount == 0)  && (gasCount >= 2 || gasCount == 0);
    }


    /***************************************** Getters & Setters *****************************************/
    public int getId() {
        return id;
        
    }
    public String getPressureSymbol() {
        return PressureSymbol;
        
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setPressureSymbol(String PressureSymbol) {
        this.PressureSymbol = PressureSymbol;
    }

    public List<Stream> getConnectedStreams() {
        return connectedStreams;
    }

    public void setConnectedStreams(List<Stream> connectedStreams) {
        this.connectedStreams = connectedStreams;
    }

    public void setConnectedStreamAtIndex(int index, Stream stream) {
        this.connectedStreams.set(index, stream);
    }
    public void addConnectedStream(Stream stream) {
        connectedStreams.add(stream);  
    }

     public void SetWellConnected() {
        this.isWellConnected = checkConnectivity();
    }
    
    public boolean isWellConnected() {
        isWellConnected = checkConnectivity();
        return isWellConnected;
    }
}
