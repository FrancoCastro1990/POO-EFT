package Repository;

import java.util.List;

public interface Repository<T> {
    public List<T> getAll();

    public void add(T value);

    public void updateAll(List<T> values);
}
