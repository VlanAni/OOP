package vanisimov.graphdevkit.graphelements;

public class Edge {

    private Vertex[] adjVers;

    public Edge(Vertex ver1, Vertex ver2) {
        this.adjVers = new Vertex[] {ver1, ver2};
    }

    public Vertex[] getVers() {
        Vertex[] copy = this.adjVers.clone();
        return copy;
    }

}