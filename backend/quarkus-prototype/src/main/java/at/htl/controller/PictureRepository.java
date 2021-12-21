package at.htl.controller;

import at.htl.model.Picture;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class PictureRepository implements PanacheRepository<Picture> {

    @Inject
    EntityManager em;

    @Transactional
    public Picture save(Picture picture) {
        return em.merge(picture);
    }

}
