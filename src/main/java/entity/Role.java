package entity;

import java.io.Serializable;

public class Role extends Designation implements Serializable {

    public Role(int id, String name) {
        super(id, name);
    }

    public enum RoleIdentifier {

        USER_ROLE(1), ADMIN_ROLE(2), SPECIALIST_ROLE(3);

        private final int id;

        RoleIdentifier(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
