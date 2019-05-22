package game;

import com.google.inject.persist.Transactional;
import util.jpa.GenericJpaDao;

import javax.persistence.Query;
import java.util.List;

/**
 * DAO osztály a {@link GameResult}-hoz.
 */

public class GameResultDao extends GenericJpaDao<GameResult> {

    public GameResultDao() {
        super(GameResult.class);
    }

    /**
     * Vissza ad egy {@code n} darabból álló listát, a legnagyobb {@code winCount} alapján.
     *
     * @param n A lista maximális hossza.
     * @return A legjobbak listája.
     */

    @Transactional
    public static List<GameResult> findBest(int n) {
        return entityManager.createQuery("SELECT r FROM GameResult r ORDER BY winCount DESC", GameResult.class)
                .setMaxResults(n)
                .getResultList();
    }

    /**
     * Vissza ad egy listát az adatbázisban megtalálható összes regisztrált felhasználórol.
     *
     * @return A játékosok listája.
     */

    @Transactional
    public List<GameResult> getPlayerList() {
        return entityManager.createQuery("SELECT r FROM GameResult r", GameResult.class)
                .getResultList();
    }

   /* @Transactional
    public int getWinNum(String s) {
        return entityManager.createQuery("SELECT winCount FROM GameResult WHERE name = "+ s, GameResult.class).getFirstResult();
    }

    @Transactional
    public Query setNewWinNum(int i, String s) {
       return entityManager.createQuery(" UPDATE GameResult SET winCount = " + i + " WHERE GameResult.name = " + s );
    }

    int i = gameDao.getWinNum(input1);
    i++;
    //String update = " UPDATE GameResult SET winCount = " + i + " WHERE GameResult.name = " + input1;
            gameDao.setNewWinNum(i, input1);*/

}
