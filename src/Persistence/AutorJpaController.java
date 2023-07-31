/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistence;

import Entidades.Autor;
import Entidades.Libro;
import Persistence.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author user
 */
public class AutorJpaController implements Serializable {

    public AutorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    public AutorJpaController(){
        emf = Persistence.createEntityManagerFactory("JPA_Ej01-libreriaPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Autor autor) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(autor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Autor autor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            autor = em.merge(autor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = autor.getId();
                if (findAutor(id) == null) {
                    throw new NonexistentEntityException("The autor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autor autor;
            try {
                autor = em.getReference(Autor.class, id);
                autor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The autor with id " + id + " no longer exists.", enfe);
            }
            em.remove(autor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Autor> findAutorEntities() {
        return findAutorEntities(true, -1, -1);
    }

    public List<Autor> findAutorEntities(int maxResults, int firstResult) {
        return findAutorEntities(false, maxResults, firstResult);
    }

    private List<Autor> findAutorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Autor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Autor findAutor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Autor.class, id);
        } finally {
            em.close();
        }
    }
    public Autor findNameAutor(String nombre) {
        EntityManager em = getEntityManager();
        try {
            
            return (Autor)em.createQuery("SELECT a FROM Autor a WHERE a.nombre like :nombre").setParameter("nombre", "%"+nombre+"%")
                    .getSingleResult();
            //return em.find(Autor.class, nombre);
        } finally {
            em.close();
        }
    }
      public List<Autor> ObtenerPorNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {
            
            List<Autor> autores = em.createQuery("SELECT a FROM Autor a WHERE a.nombre LIKE :name", Autor.class)
                    .setParameter("name", nombre)
                    .getResultList();
            return autores;
        } finally {
            em.close();
        }
    }
      public List<Autor> findAutorByNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Autor> query = em.createQuery("SELECT a FROM Autor a WHERE a.nombre like :nombre", Autor.class);
            query.setParameter("nombre", "%"+nombre+"%");
            List<Autor> listaAutor = query.getResultList();
            return listaAutor;       
        } finally {
            em.close();
        }
    }
             
    public Autor findLibroxAutor(String titulo) {
        EntityManager em = getEntityManager();
        try {
            //"SELECT a"
//                + " FROM Autor a"
//                + " WHERE a.nombre like :nombre").setParameter("nombre", nombre)
            return (Autor)em.createQuery("SELECT a from Autor a where a.titulo = :titulo").setParameter("titulo", titulo).getSingleResult();
            
        } finally {
            em.close();
        }
    }

    public int getAutorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Autor> rt = cq.from(Autor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
