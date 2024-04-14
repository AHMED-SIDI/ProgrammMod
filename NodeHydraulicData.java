public class NodeHydraulicData extends Node {
    
    private Float Balance ;
    public NodeHydraulicData(int arg ,Float Balance) {
        super(arg); 
        this.Balance = Balance ;
    }
        public float CalculateBalance() {
            for (Stream stream : network.getStreams()) {
               
                Balance  =     stream.getFlowRateSymbol();
                       return Balance ;
        }
                
    }
}
