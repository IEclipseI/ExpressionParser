package expr;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;

public class TreeVisualizator extends JFrame {
    private static int WIDTH = 20;
    private static int HEIGHT = 20;

    private static void buildGraph(mxGraph graph, ExpressionParser.Node node, Object n) {
        Object parent = graph.getDefaultParent();
        for (ExpressionParser.Node ch : node.ch) {
            Object kid = graph.insertVertex(parent, "", ch.value, 0, 0, WIDTH, HEIGHT);
            graph.insertEdge(parent, null, "", n, kid);
            buildGraph(graph, ch, kid);
        }
    }

    public static void show(ExpressionParser.Node node) {
        TreeVisualizator frame = new TreeVisualizator();
        frame.draw(node);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        frame.setSize((int) width, (int) height);
        frame.setVisible(true);
        while (frame.isVisible()) ;
    }

    private void draw(ExpressionParser.Node node) {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();


        Object o = graph.insertVertex(parent, "", node.value, 0, 0, WIDTH, HEIGHT);
        for (ExpressionParser.Node ch : node.ch) {
            Object kid = graph.insertVertex(parent, "", ch.value, 0, 0, WIDTH, HEIGHT);
            graph.insertEdge(parent, null, "", o, kid);
            buildGraph(graph, ch, kid);
        }
        mxIGraphLayout layout = new mxCompactTreeLayout(graph, false);
        layout.execute(parent);
        graph.getModel().endUpdate();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }
}
