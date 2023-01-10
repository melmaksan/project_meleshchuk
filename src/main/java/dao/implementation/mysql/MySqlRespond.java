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
}
