package bootstrap;

import domain.Data;
import graph_elements.Graph;
import graph_elements.Node;
import org.jetbrains.annotations.NotNull;
import services.FileRepository;

import java.util.ArrayList;

public class Bootstrap {

    public final String PATH_FILE = System.getenv("PATH_FILE");
    private final FileRepository fileRepository;

    public Bootstrap(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public Graph onStartUp() {
        Data data = fileRepository.readFromFile(PATH_FILE);

        Graph graph = new Graph(handleVertices(data.getData().get(0)));
        handleEdges(graph, data.getData().get(1));
        return graph;
    }

    private void handleEdges(Graph graph, String @NotNull [] strings) {
        for (String separator : strings) {
            String[] sides = separator.split(",");
            graph
                    .addEdge(Character.toString(sides[0].charAt(1)),
                            Character.toString(sides[1].charAt(0)));
        }
    }

    private @NotNull ArrayList<Node> handleVertices(String @NotNull [] names) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            nodes.add(new Node(names[i], i));
        }
        return nodes;
    }
}
