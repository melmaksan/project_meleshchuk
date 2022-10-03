package dao.implementation.mysql;

import dao.abstraction.RespondDao;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.RespondDtoConverter;
import entity.Respond;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlRespond implements RespondDao {

    private final static String SELECT_ALL =
            "SELECT respond.id AS respond_id, respond.user_id, " +
                    "user.first_name, user.last_name, respond.respond" +
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
}
