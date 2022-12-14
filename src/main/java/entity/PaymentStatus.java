package entity;

import java.io.Serializable;

public class PaymentStatus extends Designation implements Serializable {

    public PaymentStatus(int id, String name) {
        super(id, name);
    }

    public enum PaymentIdentifier {

        UNPAID(1), PAID(2);

        private final int id;

        PaymentIdentifier(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
