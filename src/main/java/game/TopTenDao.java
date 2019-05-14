package game;

import com.google.inject.persist.Transactional;
import util.jpa.GenericJpaDao;

import java.util.List;

public class TopTenDao extends GenericJpaDao<TopTen> {


    public TopTenDao() {
        super(TopTen.class);
    }

    @Transactional
    public List<TopTen> findBest(int n) {
        return entityManager.createQuery("SELECT r FROM TopTen r ORDER BY r.winCount ASC", TopTen.class)
                .setMaxResults(n)
                .getResultList();
    }
}
