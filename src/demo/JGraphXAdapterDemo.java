package demo;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import com.mxgraph.layout.*;
import com.mxgraph.swing.*;

public class JGraphXAdapterDemo
    extends JApplet
{
    private static final long serialVersionUID = 2202072534703043194L;
    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);
    private ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> g;
    private HashMap<String, HashMap<String, Double>> weightsHash;

    private JGraphXAdapter<String, DefaultWeightedEdge> jgxAdapter;
    
    public JGraphXAdapterDemo(){
    	// create a JGraphT graph
    	weightsHash = new HashMap<String, HashMap<String,Double>>();	
    	g = new ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    }
    private void addNodeHash(String v){
    	HashMap<String,Double> weights = new HashMap<String, Double>();
    	weightsHash.put(v, weights);
    	g.addVertex(v);
    }
    private void addEdgeWeight(String v1,String v2,double weight){
    	HashMap<String,Double> l1 = weightsHash.get(v1);
    	l1.put(v2, weight);
    	
    	HashMap<String,Double> l2 = weightsHash.get(v2);
    	l2.put(v1, weight);
    	g.addEdge(v1, v2);
    }
    private void extendMyGraph(int bg,int ed,int rep){
    	for(int i=bg;i<=ed;i++){
    		this.addNodeHash("v"+i);
    	}
    	for(int j=0;j<rep;j++){
	    	for(int i=bg;i<ed;i++){
	    		int sc = (int)(1+Math.random()*(i-1));
	    		//System.out.println(i+"\t"+sc);
	    		this.addEdgeWeight("v"+i, "v"+(sc), 1);
	    	}
    	}
    }
    private double getEdgeWeight(String v1,String v2){
    	return weightsHash.get(v1).get(v2).doubleValue();
    }
    private void baseGraph(){
    	String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";
        String v5 = "v5";
        String v6 = "v6";
        
        // add some sample data (graph manipulated via JGraphX)
        this.addNodeHash(v1);
        this.addNodeHash(v2);
        this.addNodeHash(v3);
        this.addNodeHash(v4);
        this.addNodeHash(v5);
        this.addNodeHash(v6);
        
        this.addEdgeWeight(v1, v2, 1.4);
        this.addEdgeWeight(v2, v3, 4);
        this.addEdgeWeight(v3, v1, 6);
        this.addEdgeWeight(v5, v4, 1.9);
        this.addEdgeWeight(v4, v6, 8);
        this.addEdgeWeight(v5, v6, 1.4);
        this.addEdgeWeight(v5, v2, 1.4);
        this.addEdgeWeight(v2, v4, 1.9);
    }
    @Override
    public void init()
    {
        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>(g);

        getContentPane().add(new mxGraphComponent(jgxAdapter));
        resize(DEFAULT_SIZE);
        
        baseGraph();
        //extendMyGraph(7, 20, 1);
        
        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
        layout.execute(jgxAdapter.getDefaultParent());

    }
    public static void main(String[] args)
    {
        JGraphXAdapterDemo applet = new JGraphXAdapterDemo();
        applet.init();
        
        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("Graph - raoqi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

// End JGraphXAdapterDemo.java
