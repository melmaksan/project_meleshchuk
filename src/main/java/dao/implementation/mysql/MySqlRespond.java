package dao.implementation.mysql;

import dao.abstraction.RespondDao;
import dao.datasource.PooledConnection;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.RespondDtoConverter;
import entity.Respond;
import entity.Role;
import entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlRespond implements RespondDao {

    private final static String SELECT_ALL =
            "SELECT respond.id AS respond_id, respond.respond, respond.user_id, user.first_name, user.last_name " +
                    "FROM respond " +
                    "JOIN user ON respond.user_id = user.id ";

    private final static String WHERE_ID =
            "WHERE respond.id = ? ";

    private static final String WHERE_USER_ID =
            "WHERE respond.user_id = ? ";

    private static final String GROUP_BY =
            "GROUP BY respond.user_id ";

    private final static String INSERT =
            "INSERT into respond (respond, user_id)" +
                    "VALUES(?, ?) ";

    private final static String UPDATE =
            "UPDATE respond SET respond = ? ";

    private final static String DELETE =
            "DELETE FROM respond ";

    private final DefaultDaoImpl<Respond> defaultDao;

    public MySqlRespond(Connection connection) {
        this(connection, new RespondDtoConverter());
    }

    public MySqlRespond(Connection connection,
                                  DtoConverter<Respond> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    @Override
    public Optional<Respond> findById(Long id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Respond> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public Respond insert(Respond respond) {
        Objects.requireNonNull(respond);
        long id = defaultDao.executeInsertWithGeneratedPrimaryKey(
                INSERT, respond.getRespond(), respond.getUserId());
        respond.setId(id);
        return respond;
    }

    @Override
    public void update(Respond respond) {
        Objects.requireNonNull(respond);
        defaultDao.executeUpdate(UPDATE + WHERE_ID,
                respond.getRespond(), respond.getId());
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(DELETE + WHERE_ID, id);
    }

    @Override
    public List<Respond> findByUser(long userId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_USER_ID +
                GROUP_BY, userId);
    }

    private void printAll(List<Respond> list) {
        System.out.println("Find all:");
        for (Respond type : list) {
            System.out.println(type);
        }
    }

    public static void main(String[] args) {
        DataSource dataSource = PooledConnection.getInstance();
        MySqlRespond mySqlRespond;

        try {
            mySqlRespond = new MySqlRespond(dataSource.getConnection());

            System.out.println("Respond TEST");

            mySqlRespond.printAll(mySqlRespond.findAll());

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Insert test:");
            Respond account1 = mySqlRespond.insert(Respond.newBuilder()
                    .addRespond("qwerty").addUserId(2).build());
            mySqlRespond.printAll(mySqlRespond.findAll());

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Find one with id 11:");
            System.out.println(mySqlRespond.findById((long) 11));

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Find one by user:");
            System.out.println(mySqlRespond.findByUser(2));

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Update:");
            account1.setRespond("12345");
            mySqlRespond.update(account1);
            mySqlRespond.printAll(mySqlRespond.findAll());

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Delete:");
            mySqlRespond.delete(account1.getId());
            mySqlRespond.printAll(mySqlRespond.findAll());


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
