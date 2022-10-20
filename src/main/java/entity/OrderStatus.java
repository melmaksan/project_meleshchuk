package entity;

import java.io.Serializable;

public class OrderStatus extends Designation implements Serializable {

    public OrderStatus(int id, String name) {
        super(id, name);
    }

    public enum StatusIdentifier {

        BOOKED_STATUS(1), DONE_STATUS(2), CANCELED_STATUS(3);

        private final int id;

        StatusIdentifier(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
