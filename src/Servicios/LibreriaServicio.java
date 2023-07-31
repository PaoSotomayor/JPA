
package Servicios;
import Entidades.Autor;
import Entidades.Editorial;
import java.util.Scanner;

public class LibreriaServicio {
    
    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    
    public void menu() throws Exception {
        
        int op = 0;
        int ope = 0;
        
        Editorial editorial = new Editorial();
        Autor autor = new Autor();
        
        EditorialServicio edSer = new EditorialServicio();
        LibroServicio libSer = new LibroServicio();        
        AutorServicio autSer = new AutorServicio();
        
        do {
            System.out.println("------------✪✪ MENU ✪✪------------");
            System.out.println("1- Dar de Alta");
            System.out.println("2- Dar de Baja");
            System.out.println("3- Editar ");
            System.out.println("4- Buscar Autor por Nombre");
            System.out.println("5- Buscar Libro por ISBN");
            System.out.println("6- Buscar Libro por Título");
            System.out.println("7- Buscar Libro/s por Autor");
            System.out.println("8- Buscar Libro/s por Editorial.");
            System.out.println("9- Salir");
            System.out.println("------------✪✪✪✪✪✪✪✪✪✪------------");
            
            op = leer.nextInt();
            
            switch (op) {
                
                case 1:
                    System.out.println("Que desea dar de alta? \n1: Autor\n 2: Libro\n 3:Editorial");
                    ope = leer.nextInt();
                    
                    if (ope == 1) {
                        autSer.crearAutor(autor);
                        
                    } else if (ope == 2) {
                        
                        libSer.crearLibro();
                        
                    } else if (ope == 3) {
                        edSer.crearEditorial(editorial);
                        
                    }
                    break;
                
                case 2:
                    System.out.println("Que desea dar de baja? \n1: Autor\n 2: Libro\n 3:Editorial");
                    ope = leer.nextInt();
                    
                    if (ope == 1) {
                        autSer.eliminarAutor();
                        
                    } else if (ope == 2) {
                        
                        libSer.eliminarLibro();
                        
                    } else if (ope == 3) {
                        edSer.eliminarEditorial();
                        
                    }
                    break;
                
                case 3:
                    System.out.println("Que desea editar? \n1: Autor\n 2: Libro\n 3:Editorial");
                    ope = leer.nextInt();
                    
                    if (ope == 1) {
                        autSer.editarAutor();
                        
                    } else if (ope == 2) {
                        
                        libSer.editarLibro();
                        
                    } else if (ope == 3) {
                        edSer.editarEditorial();
                        
                    }
                    break;
                
                case 4:
                    System.out.println("4- Buscar Autor por nombre.");
                    autSer.buscarAutorNombre();
                    break;
                
                case 5:
                    System.out.println("5- Buscar Libro por ISBN");
                    libSer.buscarLibroporISBN();
                    break;
                
                case 6:
                    System.out.println("6- Buscar Libro por Título");
                    libSer.buscarLibroporTitulo();
                    break;
                
                case 7:
                    System.out.println("7- Buscar Libro/s por Autor.");
                    libSer.buscarLibrosporAutor();
                    break;
                
                case 8:
                    System.out.println("8- Buscar Libro/s por Editorial.");
                    edSer.buscarLibrosporEditorial();
                    break;
                
                case 9:
                    System.out.println("""
                                       --------------------✪✪✪✪✪--------------------
                                          Gracias por utilizar nuestros servicios
                                       --------------------✪✪✪✪✪--------------------
                                       
                                       """);
                    break;
                
                default:
                    System.out.println("❌ Opción inválida");
            }
            
        } while (op != 9);
        
    }
    
}
