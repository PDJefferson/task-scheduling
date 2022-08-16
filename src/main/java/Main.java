import bootstrap.Bootstrap;
import graph_elements.Graph;
import menu.Menu;
import services.FileRepository;
import services.fileData.fileRepositoryImpl;

public class Main {
    private static final FileRepository fileRepository = new fileRepositoryImpl();

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap(fileRepository);
        Graph graph = bootstrap.onStartUp();
        System.out.println("Welcome to my Task Scheduling Project:");
        System.out.println(graph.toString());
        new Menu(graph);
    }
}
