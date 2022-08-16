package menu;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.util.Objects;

import constants.MainOptions;
import constants.OnAddDependency;
import constants.OnAddTaskOptions;
import constants.OnDeleteTask;
import graph_elements.Graph;
import org.jetbrains.annotations.NotNull;

public class Menu {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Menu(Graph graph) throws Exception {
        String options;
        do {
            System.out.println(MainOptions.OPTION_ONE);
            System.out.println(MainOptions.OPTION_TWO);
            System.out.println(MainOptions.OPTION_THREE);
            System.out.println(MainOptions.OPTION_FOUR);
            if ((options = reader.readLine()) != null) {
                switch (options) {
                    case "a":
                        addTasks(graph);
                        System.out.println(graph.toString());
                        break;
                    case "b":
                        deleteTask(graph);
                        System.out.println(graph.toString());
                        break;
                    case "c":
                        addDependency(graph);
                        System.out.println(graph.toString());
                        break;
                    case "d":
                        break;
                    default:
                        System.out.println("Wrong input please select the ones available!");
                        System.out.println(graph.toString());
                }
            }
        } while (!Objects.equals(options, "d"));
    }

    private void addTasks(Graph graph) throws Exception {
        System.out.println(OnAddTaskOptions.TASK_TO_ADD);
        String request;
        if ((request = reader.readLine()) != null && !request.equals("none")) {
            String[] nodes = request.split(" ");
            for (String nodeIndep : nodes) {
                graph.addNode(nodeIndep);
                System.out.println("What dependencies does " + nodeIndep + " have?\n" +
                        "Separate each task with a space.  \n" +
                        "Enter “none” if there is no dependency to be added.");
                if (!(request = reader.readLine()).equals("none")) {
                    String[] nodeDeps = request.split(" ");
                    for (String nodeDep : nodeDeps) {
                        graph.addEdge(nodeDep, nodeIndep);
                    }
                }
                System.out.println("What tasks depend on " + nodeIndep + " ? \n" +
                        "Separate each task with a space.\n" +
                        "Enter “none” if there are no tasks that depend on " + nodeIndep);

                if (!(request = reader.readLine()).equals("none")) {
                    String[] nodeDeps = request.split(" ");
                    for (String nodeDep : nodeDeps) {
                        graph.addEdge(nodeIndep, nodeDep);
                    }
                }
            }
        }
    }

    private void deleteTask(Graph graph) throws Exception {
        System.out.println(OnDeleteTask.TASK_TO_DELETE);
        String request;
        if (!(request = reader.readLine()).equals("")) {
            if (!graph.deleteNode(request)) {
                System.out.println(request + " is not a valid task. No changes were made.");
            }
        }
    }

    private void addDependency(Graph graph) throws Exception {
        System.out.println(OnAddDependency.DEPENDENCY_TO_ADD);
        String request;
        if (!(request = reader.readLine()).equals("")) {
            String[] edges = request.split(",");
            if (sanitizeInput(edges)) {
                if (!graph.addEdge(Character.toString(edges[0].charAt(1)), Character.toString(edges[1].charAt(0)))) {
                    System.out.println(request + " is not a valid dependency. No changes were made.\n");
                }
            }else{
                System.out.println("""
                        Please add the dependency as follows:
                        (task1,task2)
                        """);
            }
        }
    }

    private boolean sanitizeInput(String @NotNull [] edges) {
        return edges.length == 2;
    }
}
