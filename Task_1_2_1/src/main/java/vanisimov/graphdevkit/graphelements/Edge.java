package vanisimov.graphdevkit.graphelements;

import java.util.Objects;

public class Edge {

    private String[] adjVers;

    public Edge(String ver1, String ver2) {
        this.adjVers = new String[] {ver1, ver2};
    }

    public String[] getVers() {
        String[] copy = this.adjVers.clone();
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Edge edge = (Edge) obj;
        return (this.adjVers[0].equals(edge.adjVers[0]) &&
                this.adjVers[1].equals(edge.adjVers[1]));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.adjVers[0], this.adjVers[1]);
    }

}