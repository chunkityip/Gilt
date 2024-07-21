package dao;

import gilts.AlreadyExpiredGiltException;
public class User {
    private int id;
    private UserData data;

    public User(UserData data) {
        this(-1, data);
    }

    public User(int id, UserData data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public double getCoupon() {
        return data.getCoupon();
    }

    public double getPrincipal() {
        return data.getPrincipal();
    }

    public int getYearsRemaining() {
        return data.getYearsRemaining();
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isExpired() {
        return data.getYearsRemaining() <= 0;
    }

    public void tick() throws AlreadyExpiredGiltException {
        if (this.isExpired()) {
            throw new AlreadyExpiredGiltException();
        }
        data = new UserData(data.getCoupon(), data.getPrincipal(), data.getYearsRemaining() - 1);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", " + data +
                '}';
    }
}
