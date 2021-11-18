package algoritmo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Kruskal {

    private double[][] matriz;
    private int vertice;
    private int arista;
    private Edge[] edges;
    private int[] set;
    private int primerNodo, segundoNodo;
    private int numeroVertices;
    private int numeroAristas;
    private int to;
    private int from;

    public void menu() {
        //Dilegenciar el grafo
        boolean salir = false;
        this.vertice = mostraMenuVertices();
        this.arista = mostraMenuAristas();
        instaciar(this.vertice, this.arista);
        ordenar();
        verificar();
        imprimirResultados();
        //Busqueda de verices
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Desea conectar dos nodos");
            System.out.println("1. continuar");
            System.out.println("2. salir");
            int eleccion = validarEleccion();
            if (eleccion == 1) {
                System.out.println("Eliga dos nodos a conectar");
                System.out.println("primer nodo");
                this.primerNodo = validarNodo();
                System.out.println("segundo nodo");
                this.segundoNodo = validarNodo();
                relacion(set, primerNodo, segundoNodo);
                System.out.println("¿Desea seguir relacionando?");
                System.out.println("1. Continuar");
                System.out.println("2. Salir");
                int subEleccion = validarEleccion();
                if (subEleccion == 2) {
                    salir = true;
                } else {
                    salir = false;
                }
            } else {
                System.exit(0);
            }
        } while (!salir);
        System.exit(0);
    }

    public int mostraMenuVertices() {
        Scanner sc = new Scanner(System.in);
        System.out.println("-------Bienvenido--------");
        boolean re = false;
        while (!re) {
            try {
                String mensaje = "Digite la cantidad de Vertices ";
                System.out.println(mensaje);

                while (this.numeroVertices < 1 || this.numeroVertices >= 21) {
                    System.out.println("Los vertices deben ser enteros 1-20");
                    this.numeroVertices = sc.nextInt();
                }
                re = true;
            } catch (Exception e) {
                System.out.println("");
                System.out.println("Solo se reciben numeros enteros ");
                sc.nextLine();
            }
        }

        return this.numeroVertices;

    }

    private int mostraMenuAristas() {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        boolean re = false;
        while (!re) {
            try {
                System.out.println("Digite la Cantidad de Aristas");
                while (!re) {
                    while (this.numeroAristas < this.vertice) {
                        System.out.println("Las Aristas deben ser enteras, mayor o igual a " + this.vertice);
                        System.out.println("Tenga en cuenta que no puede ser menor que el numero de vertices");
                        this.numeroAristas = sc.nextInt();
                    }
                    re = true;
                }
            } catch (Exception e) {
                System.out.println("");
                System.out.println("Solo se reciben numeros enteros ");
                sc.nextLine();
            }

        }
        return numeroAristas;
    }

    private void instaciar(int vertice, int aristas) {
        matriz = new double[vertice + 1][vertice + 1];
        for (int i = 1; i <= vertice; i++) {
            for (int j = 1; j <= vertice; j++) {
                matriz[i][j] = Integer.MAX_VALUE;
            }
        }
        edges = new Edge[aristas];
        for (int i = 0; i < vertice; i++) {
            System.out.println("Indique el " + (i + 1) + " vertice de origen");
            this.from = validarFrom();
            System.out.println("Indique el vertice de llegada");
            this.to = validarTo(); // Nodo de asignación
            System.out.println("Indique la distancia");
            double e_data = validarDistancia(); // El peso del borde asignado
            Edge edge = new Edge();
            edge.setData(e_data);
            //edge.data = e_data;
            edge.setFrom(this.from);
            //edge.from = from;
            edge.setTo(this.to);
            //edge.to = to;
            edges[i] = edge;
        }
    }

    private void ordenar() {
        Arrays.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                if (o1.getData() > o2.getData()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    private void verificar() {
        set = new int[this.arista + 1];
        for (int i = 1; i <= this.arista; i++) {
            // Por defecto, el jefe del nodo es él mismo
            set[i] = i;
        }
        int count = 0;
        for (int i = 0; i < edges.length; i++) {
            if (count == this.vertice - 1) {
                break;
            }
            int from = edges[i].getFrom();
            int to = edges[i].getTo();
            double data = edges[i].getData();
            if (set[from] == set[to]) {// Si se busca el conjunto, el jefe superior de dos nodos es el mismo, significa que se formará un anillo.
                continue;
            }
            count++;
            //relación (conjunto, desde, hasta); // Conecta dos nodos.
            matriz[from][to] = data;
        }
    }

    private void imprimirResultados() {
        for (int i = 1; i <= this.vertice; i++) {
            for (int j = 1; j <= this.vertice; j++) {
                if (matriz[i][j] != Integer.MAX_VALUE) {
                    System.out.print(matriz[i][j] + " ");
                } else {
                    System.out.print("∞" + " ");
                }
            }
            System.out.println();
        }
    }

    private void relacion(int[] set, int a, int b) {
        int x = find(set, a);
        int y = find(set, b);
        if (x < y) {
            set[y] = x;
        } else {
            set[x] = y;
        }
        if (x == y) {
            System.out.println("Existe un bucle entre el nodo: " + x);
        } else {
            System.out.println("Coneccion establacida entre los nodos: " + x + " y " + y);
        }
    }

    public int find(int[] set, int a) {
        int res = a;
        while (set[res] != res) {
            res = set[res];
        }
        while (a != res) {
            int j = set[a];
            set[a] = res;
            a = j;
        }
        System.out.println("el res es = " + res);
        return res;
    }


    /*
    *************
    *****Validaciones****
    *************
     */
    public int validarFrom() {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        boolean re = false;
        int from = 0;
        while (!re) {
            try {
                System.out.println("Indicativo del Vertice en Numeros Enteros");
                while (!re) {
                    while (from < 1 || from >= 99) {
                        System.out.println("Digite un numero entre 1-99");
                        from = sc.nextInt();
                    }
                    re = true;
                }
            } catch (Exception e) {
                System.out.println("");
                System.out.println("Solo se reciben numeros enteros ");
                sc.nextLine();
            }
        }
        return from;
    }

    public int validarTo() {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        boolean re = false;
        int to = 0;
        while (!re) {
            try {
                System.out.println("Indicativo en Numeros Enteros");
                System.out.println("Tenga en cuenta que el vertice de origen no puede ser el llegada");
                System.out.println("Digite un numero entre 1-99");
                to = sc.nextInt();
                while (!re) {
                    while ((to == this.from) || (to<1 || to>99)) {
                        System.out.println("Tenga en cuenta que el vertice de origen no puede ser el llegada");
                        System.out.println("Digite un numero entre 1-99");
                        to = sc.nextInt();
                    }
                    re = true;
                }
            } catch (Exception e) {
                System.out.println("");
                System.out.println("Solo se reciben numeros enteros");
                sc.nextLine();
            }
        }
        return to;
    }

    public double validarDistancia() {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        boolean re = false;
        double distancia = 0;
        while (!re) {
            try {
                String mensaje = "Indicativo en Numeros";
                System.out.println(mensaje);
                while (!re) {
                    while (distancia < 1 || distancia >= 999) {
                        System.out.println("Digite un numero entre 1-999");
                        distancia = sc.nextDouble();
                    }
                    re = true;
                }
            } catch (Exception e) {
                System.out.println("");
                System.out.println("Solo se reciben numeros");
                sc.nextLine();
            }
        }
        return distancia;
    }

    public int validarEleccion() {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        boolean re = false;
        int eleccion = 0;
        while (!re) {
            try {
                String mensaje = "Seleccione una opcion";
                System.out.println(mensaje);
                while (!re) {
                    while (eleccion < 1 || eleccion > 2) {
                        System.out.println("Digite un numero entre 1-2");
                        eleccion = sc.nextInt();
                    }

                    re = true;
                }
            } catch (Exception e) {
                System.out.println("");
                System.out.println("Solo se reciben numeros enteros");
                sc.nextLine();
            }
        }
        return eleccion;
    }

    public int validarNodo() {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        boolean re = false;
        int nodo = 0;
        int res = 0;
        while (!re) {
            try {
                String mensaje = "Digite un nodo ya existente";
                System.out.println(mensaje);
                nodo = sc.nextInt();
                while (nodo < 1 || nodo > 99) {
                    System.out.println("El nodo no es permitido");
                    nodo = sc.nextInt();
                }
                res = nodo;
                while (set[res] != res) {
                    res = set[res];
                }
                while (nodo != res) {
                    int j = set[nodo];
                    set[nodo] = res;
                    nodo = j;
                }
                re = true;

            } catch (Exception e) {
                System.out.println("");
                System.out.println("Solo nodos existentes");
                sc.nextLine();
            }
        }
        return res;
    }

}