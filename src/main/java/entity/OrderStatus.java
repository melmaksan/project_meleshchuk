package entity;

import java.io.Serializable;

public class OrderStatus extends Designation implements Serializable {

    public OrderStatus(int id, String name) {
        super(id, name);
    }

    public enum StatusIdentifier {

        BOOKED(1), DONE(2), CANCELED(3);

        private final int id;

        StatusIdentifier(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
