package prototype.users;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class Horaire extends AbstractList<List<Boolean>> {

    private final List<List<Boolean>> schedule;

    public Horaire() {
        schedule = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<Boolean> days = new ArrayList<>();
            for (int j = 0; j < 13; j++) {
                days.add(false);
            }
            schedule.add(days);
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
}
