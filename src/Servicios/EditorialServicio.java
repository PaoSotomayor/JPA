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
public class EditorialServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    AutorJpaController autorJpa = new AutorJpaController();
    LibroJpaController libroJpa = new LibroJpaController();
    EditorialJpaController editorialJpa = new EditorialJpaController();

    Libro libro = new Libro();
    Autor autor = new Autor();
    Editorial editorial = new Editorial();

    public void crearEditorial(Editorial editorial) {
        try {
            boolean yaexiste = false;
            if (editorial == null) {
                throw new Exception("Los datos no pueden estar vacíos");
            }
            System.out.println("Ingrese el nombre de la editorial");
            editorial.setNombre(leer.next().toUpperCase());
            do {
                yaexiste = false;
                List<Editorial> listaE = editorialJpa.findEditorialEntities();
                while (editorial.getNombre().trim().isEmpty()) {
                    
                    editorial.setNombre(leer.nextLine());
                }
                for (Editorial aux : listaE) {
                    if (aux.getNombre().equalsIgnoreCase(editorial.getNombre())) {
                        yaexiste = true;
                        System.out.println("❌ Esta Editorial ya existe en la Base de datos! \nIngrese un nuevo nombre: ");
                        editorial.setNombre(leer.nextLine());

                    }

                }
            } while (yaexiste);

            editorial.setAlta(true);

            editorialJpa.create(editorial);
            System.out.println("Nueva Editorial añadida a la Base de Datos ✅");
            System.out.println(" ");
        } catch (Exception e) {

        }
    }

    public void buscarEditorial() throws Exception {

        try {
            System.out.println("Ingrese el nombre de la editorial a buscar");
            editorial.setNombre(leer.next());
            List<Editorial> listaBusqueda = editorialJpa.findEditorialEntities();

            System.out.println("Listado de autores");
            for (Editorial aux : listaBusqueda) {
                System.out.println(editorial.getId() + " " + editorial.getNombre());

            }

        } catch (Exception e) {
        }

    }

    public void mostrarEditoriales() {
        List<Editorial> edit = new ArrayList();
        edit = editorialJpa.findEditorialEntities();
        for (Editorial aux : edit) {
            if (aux.isAlta()) {
                System.out.println(aux.toString());
            }
        }
    }

    public void eliminarEditorial() throws NonexistentEntityException {
        try {
            System.out.println("Seleccione la editorial que desea eliminar");
            mostrarEditoriales();
            editorialJpa.destroy(leer.nextInt());
            System.out.println("La editorial ha sido eliminada de la Base de Datos");

            System.out.println(" ");
        } catch (Exception e) {
            System.out.println("❌ No se puede eliminar la editorial porque tiene libros registrados!");
        }

    }

    public void editarEditorial() throws Exception {
        try {
            System.out.println("Ingrese el ID de la editorial que desee modificar");
            System.out.println("-----Lista de editoriales-----");
            mostrarEditoriales();
            int ide = leer.nextInt();
            editorial = editorialJpa.getEntityManager().find(Editorial.class, ide);
            System.out.println("Ingrese el nuevo nombre: ");
            editorial.setNombre(leer.next());

            editorial.setId(ide);

            editorialJpa.edit(editorial);
            System.out.println("ƪ(ړײ)‎ƪ​​Los datos han sido editados!");
            System.out.println(" ");
        } catch (Exception e) {
        }

    }

    public void buscarLibrosporEditorial() {
        try {
            System.out.println("Ingrese el nombre de la Editorial:");
            String editorialN = leer.next().toUpperCase();

            editorial = editorialJpa.findNameEditorial(editorialN);
            boolean encontrado = false;
            if (editorial.getNombre().equalsIgnoreCase(editorialN)) {
                if (editorial.isAlta()) {
                    System.out.println("Editorial encontrada! ✅");
                    System.out.println("Libros encontrados: ");
                    System.out.println(" ");
                    Integer ID = editorial.getId();

                    List<Libro> libros = libroJpa.findLibroEntities();

                    if (!libros.isEmpty()) {
                        for (int i = 0; i < libros.size(); i++) {
                            if (libros.get(i).getAutor() == null) {
                                continue;
                            }

                            if (libros.get(i).getEditorial().getId() == ID) {
                                System.out.println(libros.get(i));
                                encontrado = true;
                            }
                        }

                    }
                }
            }
            if (encontrado == false) {
                System.out.println("No hay libros de esta editorial en la base de datos!");
            }
        } catch (Exception e) {
            System.out.println("ERROR! >>>> " + e);
            System.out.println("❌ Error al buscar Editorial. Vuelva al MENU e ingrese el NOMBRE COMPLETO");
            System.out.println("❌ Es posible que la editorial no se encuentra registrado en la Base de Datos");
            System.out.println(" ");
        }
    }

}
