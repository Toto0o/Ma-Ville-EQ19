package prototype.users;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Objet d'horaire
 */
public class Horaire extends AbstractList<List<Boolean>> implements Serializable {

    private List<List<Boolean>> schedule;

    public Horaire() {
        initialize();
    }

    public void initialize() {
        schedule = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ArrayList<Boolean> day = new ArrayList<>();
            for (int j = 0; j < 13; j++) {
                day.add(false);
            }
            schedule.add(day);
        }
    }

    @Override
    public List<Boolean> get(int i) {
        return schedule.get(i);
    }

    @Override
    public int size() {
        return schedule.size();
    }

    public boolean get(int day, int hour) {
        return schedule.get(day).get(hour);
    }

    public void set(int day, int hour, boolean value) {
        schedule.get(day).set(hour, value);
    }

    public void setSchedule(List<List<Boolean>> schedule) {
        this.schedule = schedule;
    }
}
