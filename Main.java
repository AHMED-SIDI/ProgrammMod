import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//import myHydraulicNetwork.*;
public class Main {
    public static void main(String[] args) {
        int numberOfBoundaryNodes = 2;
        int numberOfInternalNodes = 0;
        int numberOfSinglePhaseSegments = 1;
        int numberOfTwoPhaseSegments = 0;
        // 1st argument is the phase of the stream (0 for solid, 1 for gas)
        // 2nd argument is the id of the node connected to the stream
        // 3rd the id of the segment, -1 for boundary streams
        // 4th the direction of the stream (+1 is into the node, -1 is out of it)
        // 5th is the port in the segment where the stream is connected
        List<Integer> stream0 = List.of(1, 0, 0, -1, 0);
        List<Integer> stream1 = List.of(1, 1, 0, 1, 1);
        List<Integer> stream2 = List.of(1, 0, -1, 1, 1);
        List<Integer> stream3 = List.of(1, 1, -1, -1, 0);
        
        List<List<Integer>> streamsData = new ArrayList<>();
        streamsData.add(stream0);
        streamsData.add(stream1);
        streamsData.add(stream2);
        streamsData.add(stream3);
        // network1
        Network network1 = new Network(numberOfBoundaryNodes, numberOfInternalNodes, numberOfSinglePhaseSegments, numberOfTwoPhaseSegments, streamsData);
        streamsData.clear();
        System.out.println("data for network 1 ");

        printNodes(network1);
        printSegments(network1);
        printStreams(network1);
        // network2
        numberOfBoundaryNodes =5;
        numberOfInternalNodes = 3;
        numberOfSinglePhaseSegments = 2;
        numberOfTwoPhaseSegments = 4;
        String line = ";";
        String splitBy = ",";
        try {
            // Passe den Pfad zu deiner CSV-Datei an
            BufferedReader br = new BufferedReader(new FileReader("Network3.csv"));
            
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue ;
                String[] streamDataStr = line.split(splitBy);
                List<Integer> streamData = new ArrayList<>();
                for (String s : streamDataStr) {
                    // Entferne Leerzeichen und Semikolon
                    s = s.trim();
                    if (s.trim().isEmpty()) continue;
                    if (s.endsWith(";")) {
                        s = s.substring(0, s.length() - 1);
                    }
                    // Wandle den String in eine Ganzzahl um
                    streamData.add(Integer.parseInt(s));
                }
                streamsData.add(streamData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("List of streamsData:");
        for (List<Integer> streamData : streamsData) {
            System.out.println(streamData);
        }
        Network network2 = new Network(numberOfBoundaryNodes, numberOfInternalNodes, numberOfSinglePhaseSegments, numberOfTwoPhaseSegments, streamsData);
        System.out.println("data for network 2 ");

        printNodes(network2);
        printSegments(network2);
        printStreams(network2);

    }
    public static void printNodes(Network network) {
        for (Node node : network.getNodes()) {

            System.out.println("Node ID: " + node.getId());
            System.out.println("Node Pressure : " + node.getPressureSymbol());
            String nodeType ;
            if (node instanceof BoundaryNode) {
                nodeType = "Boundary";
            } else {
                nodeType = "Internal";
            }
            System.out.println("Node Type: " + nodeType );
            System.out.print("Connected Streams: ");
            for (Stream stream : node.getConnectedStreams()) {
                System.out.print(stream.getId() + " ");
            }
            System.out.println();
        }
    
    }
    
    
    

public static void printSegments(Network network) {
    for (Segment segment : network.getSegments()) {
        System.out.println("Segment ID: " + segment.getId());
        System.out.println("Segment Resistance : " + segment.getResistanceSymbol());
        String segmentType;
        if (segment instanceof SinglePhaseSegment) {
            segmentType = "Single Phase";
        } else {
            segmentType = "Two Phase";
        }
        System.out.println("Segment Type: " + segmentType);
        System.out.print("Connected Streams: ");
        for (Stream stream : segment.getStreams()) {
            System.out.print(stream.getId() + " ");
        }
        System.out.println();
    }
}


public static void printStreams(Network network) {
    for (Stream stream : network.getStreams()) {
        System.out.println("Stream ID: " + stream.getId());
        System.out.println("Stream Flow Rate : " + stream.getFlowRateSymbol());
        String streamType;
        if (stream instanceof SolidStream) {
            streamType = "Solid";
        } else if (stream instanceof GasStream) {
            streamType = "Gas";
        } else {
            streamType = "Unknown";
        }
        System.out.println("Stream Type: " + streamType);
        
        String streamCategory;
        if (stream.isBoundaryStream()) {
            streamCategory = "Boundary";
        } else {
            streamCategory = "Internal";
        }
        System.out.println("Stream Category: " + streamCategory);
        
        System.out.print("Connected Node: ");
        Node node = stream.getConnectedNode();
        if (node != null) {
            System.out.println(node.getId());
        } else {
            System.out.println("None");
        }
        
        System.out.print("Connected Segment: ");
        Segment segment = stream.getConnectedSegment();
        if (segment != null) {
            System.out.println(segment.getId());
        } else {
            System.out.println("None");
        }
        
        System.out.println("Direction: " + stream.getDirection());
    }
}

// how to print the output in the form of table  
/* 
public static void printStreams(Network network) {
    System.out.println("Stream ID\tStream Type"); // Print table header
    for (Stream stream : network.getStreams()) {
        String streamType;
        if (stream instanceof SolidStream) {
            streamType = "Solid";
        } else if (stream instanceof GasStream) {
            streamType = "Gas";
        } else {
            streamType = "Unknown";
        }
        System.out.printf("%d\t%s\n", stream.getId(), streamType); // Print table row
    }
} */ 
}


