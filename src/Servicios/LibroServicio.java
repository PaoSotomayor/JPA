/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Entidades.Autor;
import Entidades.Editorial;
import Entidades.Libro;
import Persistence.AutorJpaController;
import Persistence.EditorialJpaController;
import Persistence.LibroJpaController;
import Persistence.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class LibroServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    AutorJpaController autorJpa = new AutorJpaController();
    LibroJpaController libroJpa = new LibroJpaController();
    EditorialJpaController editorialJpa = new EditorialJpaController();

    AutorServicio autorS = new AutorServicio();
    EditorialServicio editorialS = new EditorialServicio();

    Editorial editorial = new Editorial();
    Autor autor = new Autor();
    Libro libro = new Libro();

    public void crearLibro() throws Exception {
        try {
            boolean yaexiste = false;

            if (libro == null) {
                throw new Exception("Los datos no pueden estar vacíos");
            }
            System.out.println("""
                                 _                 _        
                                | |__   ___   ___ | | _____ 
                                | '_ \\ / _ \\ / _ \\| |/ / __|
                                | |_) | (_) | (_) |   <\\__ \\
                                |_.__/ \\___/ \\___/|_|\\_\\___/""");
            System.out.println(" ");

            System.out.println("Ingrese el ISBN del libro");
            libro.setIsbn(leer.nextLong());
            do {
                yaexiste = false;
                List<Libro> listL = libroJpa.findLibroEntities();
                while (libro.getIsbn().equals(this)) {
                    libro.setIsbn(leer.nextLong());
                }
                for (Libro aux : listL) {
                    if (aux.getIsbn().equals(libro.getIsbn())) {
                        yaexiste = true;
                        System.out.println("Este ISBN ya existe. Ingrese uno diferente! ");
                        libro.setIsbn(leer.nextLong());
                    }

                }
            } while (yaexiste);
            System.out.println("Ingrese el titulo del libro");
            libro.setTitulo(leer.next());
            do {
                yaexiste = false;
                List<Libro> listL2 = libroJpa.findLibroEntities();
                while (libro.getTitulo().trim().isEmpty()) {
                    libro.setTitulo(leer.next());
                }
                for (Libro aux : listL2) {
                    if (aux.getTitulo().equalsIgnoreCase(libro.getTitulo())) {
                        yaexiste = true;
                        System.out.println("Este libro ya existe en la base de datos. \n Ingrese uno nuevo!");
                        libro.setTitulo(leer.next());
                    }
                }

            } while (yaexiste);
            System.out.println("Ingrese año del libro");
            libro.setAnio(leer.nextInt());
            System.out.println("Ingrese la cantidad de ejemplares");
            libro.setEjemplares(leer.nextInt());
            System.out.println("Ingrese la cantidad de ejemplares prestados");
            libro.setEjemplares_prestados(leer.nextInt());

            libro.setEjemplares_restantes(libro.getEjemplares() - libro.getEjemplares_prestados());
            libro.setAlta(true);

            System.out.println("---Lista de autores en la Base de datos---");
            autorS.mostrarAutores();

            System.out.println(" ");
            System.out.println("Desea crear un nuevo autor para el libro? S/N");
            if ("S".equalsIgnoreCase(leer.next())) {
                autorS.crearAutor(autor);

            } else {
                System.out.println("---Lista de autores en la Base de datos---");
                autorS.mostrarAutores();

                System.out.println("Ingrese el Id del autor según la lista");
                int idAut = leer.nextInt();
                autor.setId(idAut);
                libro.setAutor(autor);
            }

            System.out.println("---Lista de editoriales en la Base de datos---");
            editorialS.mostrarEditoriales();

            System.out.println(" ");
            System.out.println("Desea crear una nueva editorial para el libro? S/N");
            if (("S".equalsIgnoreCase(leer.next()))) {
                editorialS.crearEditorial(editorial);
            } else {
                System.out.println("-----Lista de editoriales-----");
                editorialS.mostrarEditoriales();

                System.out.println("Ingrese el ID de la editorial según la lista");
                int idEd = leer.nextInt();
                editorial.setId(idEd);
            }

            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libroJpa.create(libro);
            System.out.println("Nuevo Libro añadido a la base de datos ✅");
            System.out.println(" ");
        } catch (Exception e) {
            throw e;
        }
    }

    public void buscarLibroporTitulo() throws Exception {

        try {
            System.out.println("Ingrese el TITULO COMPLETO del Libro:");
            String titulo = leer.next().toUpperCase();

            Libro libroTit = (Libro) libroJpa.findTituloLibro(titulo);
            if (libroTit == null) {
                System.out.println("No existe el título");
            } else {
                System.out.println("Título encontrado! ✅");
                System.out.println(libroTit.toString());
            }
            {

            }

        } catch (Exception e) {
            System.out.println("❌ Error al buscar este Título. No existe un titulo con este nombre \n Intente ingresando el nombre completo ");
            System.out.println("ERROR! >>>> " + e);

        }
    }

    public void buscarLibroporISBN() throws Exception {
        mostrarLibros();
        try {

            System.out.println("Ingrese el ISBN del libro a buscar: ");
            long isbn = leer.nextLong();
            Libro libroISBN = libroJpa.findISBNLibro(isbn);

            if (libroISBN == null) {
                System.out.println("No existe el ISBN ingresado.");
            } else {
                System.out.println("ISBN encontrado! ✅");
                System.out.println(libroISBN.toString());
            }
        } catch (Exception e) {
            System.out.println("ERROR! >>>> " + e);
            System.out.println("❌ Error al buscar ISBN. No existe este ISBN para un libro");
        }

//        try {
//
//            System.out.println("Ingrese el ISBN del libro a buscar");
//            List<Libro> listaBusqueda = (List<Libro>) libroJpa.findLibro(id);
//            isbn.setIsbn(leer.nextLong());
//
//            System.out.println(libro.getIsbn() + " " + libro.getTitulo());
//            System.out.println("-----Lista de libros-----");
//            for (Libro aux : listaBusqueda) {
//                System.out.println(aux.getIsbn() + " " + aux.getTitulo());
//
//            }
//
//        } catch (Exception e) {
//            throw e;
//        }
    }

    public void mostrarLibros() {
        List<Libro> libr = new ArrayList();
        libr = libroJpa.findLibroEntities();
        for (Libro aux : libr) {
            if (aux.isAlta()) {
                System.out.println(aux.toString());
            }
        }
    }

    public void eliminarLibro() throws NonexistentEntityException {
        try {
            System.out.println("Ingrese el ID del libro que desea eliminar");
            mostrarLibros();
            libroJpa.destroy(leer.nextLong());
            System.out.println("El libro ha sido eliminado de la Base de Datos ✅");
        } catch (Exception e) {
        }

    }

    public void editarLibro() throws Exception {
        mostrarLibros();
        try {

            System.out.println("Ingrese el ISBN del libro a editar a editar: ");
            long idL = leer.nextLong();

            Libro libro = libroJpa.getEntityManager().find(Libro.class, idL);
            System.out.println("Que desea editar? \n1: Título \n2: Año del libro \n3: Cantidad de ejemplares "
                    + "\n4: Ejemplares prestados \n5: Autor \n6: Editorial");
            int op = leer.nextInt();
            switch (op) {
                case 1:
                    System.out.println("El título es: " + libro.getTitulo());
                    System.out.println("Ingrese el título del libro a cambiar: ");
                    libro.setTitulo(leer.next());
                    break;
                case 2:
                    System.out.println("El año actual es: " + libro.getAnio());
                    System.out.println("Ingrese año del libro a cambiar: ");
                    libro.setAnio(leer.nextInt());
                    break;
                case 3:
                    System.out.println("La cantidad actual de ejemplares es: " + libro.getEjemplares());
                    System.out.println("Ingrese la cantidad de ejemplares");
                    libro.setEjemplares(leer.nextInt());
                    break;
                case 4:
                    System.out.println("La cantidad actual de ejemplares prestado es: " + libro.getEjemplares_prestados());
                    System.out.println("Ingrese la cantidad de ejemplares prestados");
                    libro.setEjemplares_prestados(leer.nextInt());
                    break;
                case 5:
                    System.out.println("El autor actual es: " + libro.getAutor().getNombre());
                    autorS.mostrarAutores();
                    System.out.println("Ingrese el ID de un autor existente en la Base de Datos");
                    int idA = leer.nextInt();
                    autor = autorJpa.findAutor(idA);
                    libro.setAutor(autor);
                    break;
                case 6:
                    System.out.println("La editorial actual es: " + libro.getEditorial().getNombre());
                    editorialS.mostrarEditoriales();
                    System.out.println("Ingrese el ID de una editorial existente en la Base de Datos");
                    int idE = leer.nextInt();
                    editorial = editorialJpa.findEditorial(idE);
                    libro.setEditorial(editorial);
                    break;

                default:
                    System.out.println("❌ Opción inválida!");
            }
            libroJpa.edit(libro);
            System.out.println("​​Los datos han sido editados! ✅");

        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<Libro> buscarLibrosporAutor() {

        System.out.println("Ingresa el nombre del autor del libro");
        String nombreA = leer.next();
        List<Libro> listaL = libroJpa.findLibrosNombreAutor(nombreA);
        ArrayList<Libro> aLibros = new ArrayList<>(listaL);
        if (aLibros.isEmpty()) {
            System.out.println("❌ Este autor no existe en la Base de Datos");
        } else {
            System.out.println("Autor encontrado! ✅");
        }
        System.out.println(aLibros.toString());
        return aLibros;

    }
}
