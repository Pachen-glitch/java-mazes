import java.util.*;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║         Solver de Laberintos BFS — SU TAREA (Grupo A)       ║
 * ╠══════════════════════════════════════════════════════════════╣
 * ║  Implementar Búsqueda en Amplitud usando una COLA (Queue).  ║
 * ║                                                             ║
 * ║  Conceptos clave:                                           ║
 * ║    - Queue = FIFO (First-In, First-Out / Primero en         ║
 * ║      entrar, primero en salir)                              ║
 * ║    - Explora celdas nivel por nivel (las más cercanas       ║
 * ║      primero)                                               ║
 * ║    - Garantiza encontrar el camino MÁS CORTO               ║
 * ║                                                             ║
 * ║  Clases útiles de Java:                                     ║
 * ║    - LinkedList<int[]> implementa Queue<int[]>              ║
 * ║    - queue.add(elemento)  → encolar al final                ║
 * ║    - queue.poll()         → desencolar del frente           ║
 * ║    - queue.isEmpty()      → verificar si está vacía         ║
 * ║    - HashMap<Long, int[]> para rastrear celdas padre        ║
 * ║    - HashSet<Long> para rastrear celdas visitadas           ║
 * ║                                                             ║
 * ║  Completen las secciones TODO. NO renombren la clase.       ║
 * ╚══════════════════════════════════════════════════════════════╝
 */
public class BFSSolver implements MazeSolver {

    // La cuadrícula del laberinto (0 = pared, 1 = camino). NO modificar.
    private int[][] maze;
    private int[] start;
    private int[] end;

    // ──────────────────────────────────────────
    //  TODO 1: Declaren sus estructuras de datos aquí

    private LinkedList<int[]> queue; // Usamos LinkedList para implementar la Queue

    private Set<Long> visited;
    private Map<Long, int[]> parents;
    private List<int[]> visitedThisStep;
    private List<int[]> path;

    // ──────────────────────────────────────────
    // Van a necesitar:
    //   - Un Queue<int[]> para la frontera del BFS (usen LinkedList)
    //   - Un Set<Long> para rastrear qué celdas ya visitaron
    //   - Un Map<Long, int[]> para recordar cómo llegaron a cada celda (para reconstruir el camino)
    //   - Un List<int[]> para almacenar las celdas visitadas en el paso actual
    //   - Un List<int[]> para el camino final una vez encontrado



    // Direcciones: arriba, abajo, izquierda, derecha (desplazamientos en fila, columna)
    private final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    /**
     * Helper: convierte (fila, columna) a una clave long única para usar en Sets/Maps.
     * Uso: long key = toKey(fila, columna);
     */
    private long toKey(int r, int c) {
        return ((long) r << 32) | (c & 0xFFFFFFFFL);
    }

    @Override
    public void init(int[][] maze, int[] start, int[] end) {
        this.maze = maze;
        this.start = start;
        this.end = end;

        this.queue = new LinkedList<>();
        this.visited = new HashSet<>();
        this.parents = new HashMap<>();
        this.visitedThisStep = new ArrayList<>();
        this.path = new ArrayList<>();

        queue.add(start); // Agregamos la celda inicial a la queue
        visited.add(toKey(start[0], start[1])); // Marcamos la celda inicial como visitada

        // ──────────────────────────────────────────
        //  TODO 2: Inicialicen sus estructuras de datos
        // ──────────────────────────────────────────
        // - Creen su Queue, conjunto de visitados y mapa de padres
        // - Agreguen la celda inicial a la queue
        // - Marquen la celda inicial como visitada


    }

    @Override
    public boolean step() {

        visitedThisStep.clear(); // Limpiamos la lista de celdas visitadas en este paso

        if (queue.isEmpty()) {
            return true; // No hay más celdas por explorar no hay camino
        }

        int[] current = queue.poll(); // Sacamos una celda del frente de la queue
        visitedThisStep.add(current); // Agregamos a la lista de visitados en este paso

        if (current[0] == end[0] && current[1] == end[1]) {
     // Reconstruimos el camino si llegamos al final
            return true;
        }

        for (int[] dir : DIRS) {
            int newRow = current[0] + dir[0];
            int newCol = current[1] + dir[1];

            long key = toKey(newRow, newCol);

            if (newRow >= 0 && newRow < maze.length && newCol >= 0 && newCol < maze[0].length &&
                maze[newRow][newCol] == 1 && !visited.contains(key)) {
                visited.add(key);
                parents.put(key, current);
                queue.add(new int[]{newRow, newCol});
            }
        }

        return false; // Aún no terminamos
    }

        // Limpien la lista de últimos visitados

        //
        // Si la queue está vacía → retornar true (no hay camino)
        //
        // Saquen UNA celda del FRENTE de la queue (¡FIFO!)
        // Agréguela a la lista de últimos visitados
        //
        // Si esta celda es el final → reconstruir camino, retornar true
        //
        // Para cada dirección (arriba, abajo, izquierda, derecha):
        //   Calculen la posición del vecino
        //   Si el vecino está dentro de los límites Y es camino (==1) Y no fue visitado:
        //     Márquenlo como visitado
        //     Registren su padre (celda actual) en el mapa de padres
        //     Agreguen el vecino al FINAL de la queue
        //
        // Retornar false (todavía no terminamos)


    @Override
    public List<int[]> getVisitedThisStep() {
        

        // ──────────────────────────────────────────
        //  TODO 4: Retornen las celdas visitadas en el último paso
        // ──────────────────────────────────────────
        return visitedThisStep;
    }

    @Override
public List<int[]> getPath() {

    path.clear();

    long endKey = toKey(end[0], end[1]);

    if (!parents.containsKey(endKey) &&
        !(start[0] == end[0] && start[1] == end[1])) {
        return path;
    }

    int[] current = end;

    while (current != null) {
        path.add(current);
        long key = toKey(current[0], current[1]);
        current = parents.get(key);
    }

    Collections.reverse(path);

    return path;




        // ──────────────────────────────────────────
        //  TODO 5: Retornen el camino final del inicio al final
        // ──────────────────────────────────────────
        // Usen su mapa de padres para recorrer desde el final hasta el inicio:
        //   Empiecen en la celda final
        //   Mientras la celda actual tenga un padre:
        //     Agreguen la celda actual al camino
        //     Avancen al padre
        //   Agreguen la celda inicial
        //   Inviertan la lista (para que vaya de inicio → final)
        //
        // Si no se encontró camino, retornen una lista vacía.

        //return Collections.emptyList(); // ← Reemplacen esto
    }

    @Override
    public String getName() {
        return "BFS (Queue)";
    }
}