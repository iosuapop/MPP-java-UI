package donations.repository;

import java.util.ArrayList;

public interface IRepository<ID, E>{
    E findOne(ID id);
    ArrayList<E> getAll();
    ID add(E entity);
    void update(ID id, E entity);
    void delete(E entity);
}