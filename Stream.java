
//import myHydraulicNetwork.*;

import java.util.List;
import java.util.ArrayList;
public abstract class Stream {
    /***************************************** Attributes *****************************************/

    private int id;
    private Segment connectedSegment ;
    private Node connectedNode;
    private int direction;
    private boolean isBoundaryStream;
    private int portInSegment;
    private String FlowRateSymbol ;
    private boolean isWellConnected = false;
    /***************************************** Constructors *****************************************/
    public Stream(int id) {
        this.id = id;
        this.FlowRateSymbol = "V"+id;
        this.connectedSegment = null;
        this.connectedNode = null;
        this.direction = 0;
        this.isBoundaryStream = false;
        this.portInSegment = 0;
    }
    /***************************************** Methods *****************************************/
    private boolean checkConnectivity() {
        // check it has a node
        // if it is a boundary stream, check that node is of type boundary node
        // if not, check it has a segment
        if (this.connectedNode == null) {
            return false;
        }
        else if (this.isBoundaryStream) {
            boolean isBoundaryNode = connectedNode instanceof BoundaryNode;
            if (!isBoundaryNode) {
                return false;
            } else if (this.connectedSegment == null) {
                return false;
            }
        }
        return true;
    }
    /***************************************** Getters & Setters *****************************************/
    public int getId() {
        return id;
    }
    public String getFlowRateSymbol() {
        return FlowRateSymbol ;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setFlowRateSymbol(String FlowRateSymbol) {
        this.FlowRateSymbol = FlowRateSymbol;
    }


    public Segment getConnectedSegment() {
        return connectedSegment;
    }
    public void setConnectedSegment(Segment connectedSegment) {
        this.connectedSegment = connectedSegment;
    }

    public Node getConnectedNode() {
        return connectedNode;
    }
    public void setConnectedNode(Node connectedNode) {
        this.connectedNode = connectedNode;
    }

    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isBoundaryStream() {
        return isBoundaryStream;
    }
    public void setBoundaryStream(boolean isBoundaryStream) {
        this.isBoundaryStream = isBoundaryStream;
    }

    public int getPortInSegment() {
        return portInSegment;
    }
    public void setPortInSegment(int portInSegment) {
        this.portInSegment = portInSegment;
    }
    public boolean isWellConnected() {
        isWellConnected = checkConnectivity();
        return isWellConnected;
    }   
}
