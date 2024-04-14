//package myHydraulicNetwork;


import java.util.ArrayList;
import java.util.List;
public class Network {
    
    /***************************************** Attributes *****************************************/
// probably the nodes should be placed in a set, insteas of lists, because we don't want to have duplicate nodes and order is not needed
    private ArrayList<BoundaryNode> boundaryNodes = new ArrayList<>();
    private ArrayList<InternalNode> internalNodes = new ArrayList<>();
     private ArrayList<SinglePhaseSegment> singlePhaseSegments= new ArrayList<>();
    private ArrayList<TwoPhaseSegment> twoPhaseSegments= new ArrayList<>();
    private ArrayList<SolidStream> solidStreams = new ArrayList<>();
    private ArrayList<GasStream> gasStreams = new ArrayList<>();
    private boolean isWellConnected = false;

    /***************************************** Constructors *****************************************/

    public Network(int numBoundaryNodes, int numInternalNodes, int numSinglePhaseSegments, int numTwoPhaseSegments, List<List<Integer>> streams) {
    // the list of streams shall be in the following order
    // the segments should be connected in increasing order of their IDs
    // within each segment, the streams should be connected in the increasing order of their port numbers
    // finally the boundary streams should be defined
        int nodeId = -1;
        int segmentId = -1;
        // Instantiate boundary nodes
        for (int i = 0; i < numBoundaryNodes; i++) {
            nodeId = nodeId + 1;
            BoundaryNode boundaryNode = new BoundaryNode(nodeId); // Create a new boundary node with ID i
            this.boundaryNodes.add(boundaryNode); // Add the boundary node to the Network
        }
        // Instantiate internal nodes
        for (int i = 0; i < numInternalNodes; i++) {
            nodeId = nodeId + 1;
            InternalNode internalNode = new InternalNode(nodeId); // Create a new internal node with ID i
            this.internalNodes.add(internalNode); // Add the internal node to the Network
        }
        // Instantiate single phase segments
        for (int i = 0; i < numSinglePhaseSegments; i++) {
            segmentId = segmentId + 1;
            SinglePhaseSegment singlePhaseSegment = new SinglePhaseSegment(segmentId); // Create a new single phase segment
            this.singlePhaseSegments.add(singlePhaseSegment); // Add the single phase segment to the Network
        }
        // Instantiate two phase segments
        for (int i = 0; i < numTwoPhaseSegments; i++) {
            segmentId = segmentId + 1;
            TwoPhaseSegment twoPhaseSegment = new TwoPhaseSegment(segmentId); // Create a new two phase segment
            this.twoPhaseSegments.add(twoPhaseSegment); // Add the two phase segment to the Network 
        }
        
        // Instantiate solid and gas streams based on the provided list
         
        for (List<Integer> streamData : streams) {
            int streamId = streams.indexOf(streamData);
            int streamPhase = streamData.get(0);
            int connectedNodeId = streamData.get(1);
            int connectedSegmentId = streamData.get(2);
            int direction = streamData.get(3);
            int portInSegment = streamData.get(4);
            Stream stream = null;
            boolean isSolid = (streamPhase == 1);
            if (isSolid) {
                // Instantiate a SolidStream
                stream = new SolidStream(streamId);
                this.solidStreams.add((SolidStream) stream);

            } else {
                // Instantiate a GasStream
                stream = new GasStream(streamId);
                this.gasStreams.add((GasStream) stream);
             }
            // connect the stream to the node
            Node node = getNodeById(connectedNodeId);
            connectStreamToNode(stream, node);



            // define whether it is a boundary or an internal stream
            boolean isConnectedToSegment = (connectedSegmentId != -1);
            boolean isInternalStream = isConnectedToSegment;
            if  (!isInternalStream) {
                // this is a boundary stream
                stream.setBoundaryStream(true);
            } else  {
                // this is an internal stream
                stream.setBoundaryStream(false);
            }
 
            // set the direction of the stream. Boundary streams are always positive when entering the boundary node, i.e. direction = 1
            stream.setDirection(direction);
            if (stream.isBoundaryStream()){
                stream.setDirection(1);
            }
            if (!stream.isBoundaryStream()){
                Segment segment = getSegmentById(connectedSegmentId); 
                connectStreamToSegment(stream, segment,portInSegment);
            }

        }
    }


    /***************************************** Methods *****************************************/

    public void connectStreamToNode(Stream stream, Node node) {
        stream.setConnectedNode(node);
        node.addConnectedStream(stream);
    }
    
    public void connectStreamToSegment(Stream stream, Segment segment, Integer portInSegment) {
        stream.setConnectedSegment (segment);
        stream.setPortInSegment(portInSegment);
        if (segment != null) {
        segment.addStream(stream);
        }
    }
    
    public boolean checkConnectivity() {
        // Check connectivity for boundary nodes
        for (BoundaryNode node : boundaryNodes) {
            if (!node.isWellConnected()) {
                return false;
            }
        }
        
        // Check connectivity for internal nodes
        for (InternalNode node : internalNodes) {
            if (!node.isWellConnected()) {
                return false;
            }
        }
        
        // Check connectivity for single phase segments
        for (SinglePhaseSegment segment : singlePhaseSegments) {
            if (!segment.isWellConnected()) {
                return false;
            }
        }
        
        // Check connectivity for two phase segments
        for (TwoPhaseSegment segment : twoPhaseSegments) {
            if (!segment.isWellConnected()) {
                return false;
            }
        }
        
        // Check connectivity for gas streams
        for (GasStream stream : gasStreams) {
            if (!stream.isWellConnected()) {
                return false;
            }
        }
        
        // Check connectivity for solid streams
        for (SolidStream stream : solidStreams) {
            if (!stream.isWellConnected()) {
                return false;
            }
        }
        
        return true;
    }
    
    
    /***************************************** Getters & Setters *****************************************/

    public Node getNodeById(int id) {
        // Search in boundary nodes
        for (BoundaryNode node : this.boundaryNodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        // Search in internal nodes
        for (InternalNode node : this.internalNodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null; 
    }
public List<Node> getNodes() {
    List<Node> allNodes = new ArrayList<>();
    allNodes.addAll(boundaryNodes);
    allNodes.addAll(internalNodes);
    return allNodes;
}
    public Segment getSegmentById(int id) {
        // Search in single phase segments
        for (SinglePhaseSegment segment : this.singlePhaseSegments) {
            if (segment.getId() == id) {
                return segment;
            }
        }
        // Search in two phase segments
        for (TwoPhaseSegment segment : this.twoPhaseSegments) {
            if (segment.getId() == id) {
                return segment;
            }
        }
        return null; // Return null if no segment with the given id is found
    }
public List<Stream> getStreams() {
    List<Stream> allStreams = new ArrayList<>();
    allStreams.addAll(solidStreams);
    allStreams.addAll(gasStreams);
    return allStreams;
    
}
public List<Segment> getSegments() {
    List<Segment> allSegments = new ArrayList<>();
    allSegments.addAll(singlePhaseSegments);
    allSegments.addAll(twoPhaseSegments);
    return allSegments;
}

}

    
