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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AutorServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    AutorJpaController autorJpa = new AutorJpaController();
    LibroJpaController libroJpa = new LibroJpaController();
    EditorialJpaController editorialJpa = new EditorialJpaController();

    Editorial editorial = new Editorial();
    Libro libro = new Libro();
    Autor autor = new Autor();

    public void crearAutor(Autor autor) {
        try {
            boolean yaexiste = false;
            if (autor == null) {
                throw new Exception("Los datos no pueden estar vacíos");
            }
            System.out.println("Ingrese el nombre del autor");
            autor.setNombre(leer.next().toUpperCase());
            do {
                yaexiste = false;
                List<Autor> listaA = autorJpa.findAutorEntities();
                while (autor.getNombre().trim().isEmpty()) {

                    autor.setNombre(leer.nextLine());
                }
                for (Autor aux : listaA) {
                    if (aux.getNombre().equalsIgnoreCase(autor.getNombre())) {
                        yaexiste = true;
                        System.out.println("❌ Este Autor ya existe en la Base de datos! \nIngrese un nuevo nombre: ");
                        autor.setNombre(leer.nextLine());

                    }

                }
            } while (yaexiste);

            autor.setAlta(true);

            autorJpa.create(autor);
            System.out.println("Nuevo autor añadido a la base de datos ✅");
            System.out.println(" ");
        } catch (Exception e) {

        }
    }


public ArrayList<Autor> buscarAutorNombre()throws Exception{ 
    try {
        
    } catch (Exception e) {
    }
        System.out.println("Ingresa el nombre del autor que deseas buscar");
        String nombre = leer.next();
        List<Autor> listaA = autorJpa.findAutorByNombre(nombre);        
        ArrayList<Autor> Autores = new ArrayList<>(listaA);
        if (Autores.isEmpty()) {
            System.out.println("❌ Este autor no existe en la Base de Datos");
        } else {
           System.out.println("Autor encontrado! ✅");
               System.out.println(Autores.toString()); 
        
    }
        
        return Autores;
    }
    
    public void mostrarAutores() {
        List<Autor> autor = new ArrayList();
        autor = autorJpa.findAutorEntities();
        for (Autor aux : autor) {
            if (aux.isAlta()) {
                System.out.println(aux.toString());
            }
        }
    }

    public void eliminarAutor() throws NonexistentEntityException {
        System.out.println("Ingrese el ID del autor que desea eliminar");
        mostrarAutores();
        autorJpa.destroy(leer.nextInt());
        System.out.println("El autor ha sido eliminado de la Base de Datos ✅");
    }

    public void editarAutor() throws Exception {
        try {
            System.out.println("Ingrese el ID del autor a editar: ");
            System.out.println("-----Lista de autores-----");
            mostrarAutores();
            int ida = leer.nextInt();
            autor = autorJpa.getEntityManager().find(Autor.class, ida);
            System.out.println("Ingrese el nuevo nombre");
            autor.setNombre(leer.next());
            autor.setId(ida);

            autorJpa.edit(autor);
            System.out.println("ƪ​​Los datos han sido editados! ✅");
            System.out.println(" ");
        } catch (Exception e) {
        }

    }

}
