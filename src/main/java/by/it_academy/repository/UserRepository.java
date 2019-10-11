package by.it_academy.repository;

import by.it_academy.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static by.it_academy.util.ErrorConstant.INVALID_USER__AUTHENTICATION_DATA;

public class UserRepository implements CrudRepository<User> {

    private final static Map<Long, User> users = new HashMap<>();
    private final static AtomicLong ID = new AtomicLong(1);

    @Override
    public User create(User user) {

        long id = ID.getAndIncrement();
        user.setId(id);
        users.put(id, user);

        return getById(id);
    }

    @Override
    public User getById(long id) {
        return users.get(id);
    }

    public User getByLoginAndPassword(String login, String password) {
        return users.values().stream()
                .filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(INVALID_USER__AUTHENTICATION_DATA));

    }

    public boolean isUserLoginExist(String login) {
        return users.values().stream()
                .anyMatch(u -> u.getLogin().equals(login));
    }

    public List <User> getAllUsers(){
    return new ArrayList<>(users.values());
    }

    @Override
    public User update(User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public void delete(long id) {
        users.remove(id);
    }

}
