package vanisimov.graphdevkit.graphelements;

public class Edge {

    private String[] adjVers;

    public Edge(String ver1, String ver2) {
        this.adjVers = new String[] {ver1, ver2};
    }

    public String[] getVers() {
        String[] copy = this.adjVers.clone();
        return copy;
    }

}