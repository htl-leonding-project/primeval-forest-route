package at.htl.backend.Control;

import at.htl.backend.Entity.CatalogImage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;

@ApplicationScoped
public class CatalogImageRepository {

    public static SessionFactory getCurrentSessionFromJPA() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-tutorial");
        EntityManager entityManager = emf.createEntityManager();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        SessionFactory factory = session.getSessionFactory();
        return factory;
    }

    @Transactional
    public CatalogImage saveOrUpdateCatalogImage(String category, MultipartFile image) throws Exception {
        CatalogImage img = new CatalogImage();
        byte[] imageData = new byte[(int) image.getSize()];
        try {
            FileInputStream fileInputStream = new FileInputStream((File) image);
            fileInputStream.read(imageData);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        img.setCatlogImg(imageData);
        img.setCategory(category);
        img.setImageName(image.getOriginalFilename());
        img.setImgSize(image.getSize());
        getCurrentSessionFromJPA().getCurrentSession().saveOrUpdate(img);
        return img;
    }
}
