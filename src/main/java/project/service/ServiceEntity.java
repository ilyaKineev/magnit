package project.service;

import project.model.EntityTest;
import project.repository.Dao;

import java.util.List;

public class ServiceEntity {

    private Dao dao = new Dao();

    public ServiceEntity() {
    }

    public EntityTest findTest(int id) {
        return dao.findById(id);
    }

    public void saveTest(EntityTest test) {
        dao.save(test);
    }

    public void deleteTest(EntityTest test) {
        dao.delete(test);
    }

    public void updateTest(EntityTest test) {
        dao.update(test);
    }

    public List<EntityTest> findAllTest() {
        return dao.findAll();
    }

    public int countRowsTest() {
        return dao.countRows();
    }

    public void deleteAll() {
        dao.deleteAll();
    }
}
