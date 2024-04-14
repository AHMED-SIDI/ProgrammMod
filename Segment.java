//package myHydraulicNetwork;

import java.util.ArrayList;
import java.util.List;
//import myHydraulicNetwork.*;
public abstract class Segment {

   /***************************************** Attributes *****************************************/

    private int id;
    private List<Stream> streams = new ArrayList<>();
    private String ResistanceSymbol ;
    private boolean isWellConnected = false;
    private int numPorts;

   /***************************************** Constructors *****************************************/
 
    public Segment(int id) {
        this.id = id;
        this.ResistanceSymbol = "R"+id;
    }

    /***************************************** Methods *****************************************/
    private boolean checkConnectivity() {
 
        if (streams.size() != numPorts) {
            return false;
        }
        
        boolean firstStreamIsSolid = streams.get(0) instanceof SolidStream;
        boolean secondStreamIsSolid = streams.get(1) instanceof SolidStream;
        boolean firstStreamPairIsPhaseConsistent = (firstStreamIsSolid == secondStreamIsSolid);
        if (!firstStreamPairIsPhaseConsistent) {
            return false;
        }
        if (numPorts == 4) {
            boolean thirdStreamIsSolid = streams.get(2) instanceof SolidStream;
            boolean fourthStreamIsSolid = streams.get(3) instanceof SolidStream;
            if (!firstStreamIsSolid) {
                return false;
            } else if (!secondStreamIsSolid) {
                return false;
            } else if (thirdStreamIsSolid) {
                return false;
            } else if (fourthStreamIsSolid) {
                return false;
            }
        }        
        return true;
    }

    /***************************************** Getters & Setters *****************************************/
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    public void setResistanceSymbol(String ResistanceSymbol) {
        this.ResistanceSymbol = ResistanceSymbol;
    }
    public String getResistanceSymbol() {
        return ResistanceSymbol ;
    }

   /* public void setStreamAtPort(int port, Stream stream) {
        if (port < numPorts) {
            streams.set(port, stream);
        } else {
            throw new IndexOutOfBoundsException("Invalid port");
        }
    }
    */
    
    public Stream getStreamAtPort(int port) {
        if (port < numPorts) {
            return streams.get(port);
        } else {
            throw new IndexOutOfBoundsException("Invalid port");
        }
    }
    
    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    public List<Stream> getStreams() {
        return streams;
    }
    public void addStream(Stream stream) {
        if (streams.size() < numPorts) {
            streams.add(stream);
        } else {
            throw new IllegalStateException("Cannot add more streams. Maximum number of ports reached.");
        }
    }

    public boolean isWellConnected() {
        return isWellConnected;
    }
    
    public void setNumPorts(int numPorts) {
        this.numPorts = numPorts;
    }
    
    public int getNumPorts() {
        return numPorts;
    }
    

}
