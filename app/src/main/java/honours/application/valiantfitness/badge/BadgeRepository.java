package honours.application.valiantfitness.badge;


import android.content.Context;

import java.util.List;


public class BadgeRepository {
    private static final String TAG = "BadgeRepository";

    private BadgeDao badgeDao;

    public BadgeRepository(Context context){

        super();
        badgeDao = BadgeDatabase.getDatabase(context).badgeDao();

    }

    public void AddBadge(Badge badge) {
        this.badgeDao.insert(badge);
    }

    public void UpdateBadge(Badge badge){
        this.badgeDao.update(badge);
    }


    public Badge GetBadgeFromTitle(String name) {
        return this.badgeDao.getBadgeFromTitle(name);
    }

    public List<Badge> GetAllBadges() {
        return this.badgeDao.getAllBadges();
    }



    public void RemoveBadge(Badge user) {
        this.badgeDao.delete(user);
    }


}
