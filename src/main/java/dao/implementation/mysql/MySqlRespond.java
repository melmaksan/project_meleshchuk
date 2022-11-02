package dao.implementation.mysql;

import dao.abstraction.RespondDao;
import dao.datasource.PooledConnection;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.RespondDtoConverter;
import entity.Respond;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlRespond implements RespondDao {

    private static final String SELECT_ALL =
            "SELECT respond.id, respond.username, respond.datetime, respond.mark, respond.respond " +
                    "FROM respond ";

    private static final String WHERE_ID =
            "WHERE respond.id = ? ";

    private static final String INSERT =
            "INSERT into respond (username, datetime, mark, respond)" +
                    "VALUES(?, ?, ?, ?) ";

    private static final String UPDATE =
            "UPDATE respond SET respond = ? ";

    private static final String DELETE =
            "DELETE FROM respond ";

    private final DefaultDaoImpl<Respond> defaultDao;

    public MySqlRespond(Connection connection) {
        this(connection, new RespondDtoConverter());
    }

    public MySqlRespond(Connection connection, DtoConverter<Respond> converter) {
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
                INSERT, respond.getUserName(), respond.getDateTime(), respond.getMark(), respond.getResponse());
        respond.setId(id);
        return respond;
    }

    @Override
    public void update(Respond respond) {
        Objects.requireNonNull(respond);
        defaultDao.executeUpdate(UPDATE + WHERE_ID,
                respond.getResponse(), respond.getId());
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(DELETE + WHERE_ID, id);
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

//            System.out.println("~~~~~~~~~~~~");
//
//            System.out.println("Insert test:");
//            for (int i = 1; i < 10; i++) {
//                mySqlRespond.insert(Respond.newBuilder().addName("user" + i)
//                        .addRespond("bla bla").addRespondTime(LocalDateTime.now()).addMark(i).build());
//            }

            mySqlRespond.printAll(mySqlRespond.findAll());

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Find one with id 1:");
            System.out.println(mySqlRespond.findById((long) 3));

            System.out.println("~~~~~~~~~~~~");

//            System.out.println("Update:");
//            account1.setRespond("12345");
//            mySqlRespond.update(account1);
//            mySqlRespond.printAll(mySqlRespond.findAll());
//
//            System.out.println("~~~~~~~~~~~~");
//
//            System.out.println("Delete:");
//            mySqlRespond.delete(account1.getId());
//            mySqlRespond.printAll(mySqlRespond.findAll());


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
