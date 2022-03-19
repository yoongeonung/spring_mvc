package user.dao;

import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import user.domain.Level;
import user.domain.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@NoArgsConstructor
public class UserDaoJdbc implements UserDao{

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level"))); // 이넘 쓰기힘드네
            user.setRecommend(rs.getInt("recommend"));
            user.setLogin(rs.getInt("login"));
            return user;
        }
    };


    // jdbcTemplate을 빈으로 등록해서 사용하고 싶은경우 setJdbcTemplate()로 이름을 변경해서 사용하면된다.
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) {
        // jdbcTemplate 사용
        jdbcTemplate.update("insert into USER (id, name, password, level, recommend, login) values (?,?,?,?,?,?)", user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getRecommend(), user.getLogin());
    }

    public User get(String id) {
        // jdbcTemplate 적용
        return jdbcTemplate.queryForObject("select * from USER where id = ?", userMapper, id);
    }

    // 클라이언트
    public void deleteAll() {
        // jdbcTemplate 사용 2
        jdbcTemplate.update("delete from USER");
    }

    public Integer getCount() {

        // jdbcTemplate 2, queryForInt() 대체 방법
        return jdbcTemplate.queryForObject("select COUNT(*) from USER", Integer.class);

        // jdbcTemplate 1, queryForInt() 는 deprecated 됨.
//        return jdbcTemplate.query(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                return con.prepareStatement("select COUNT(*) from USER");
//            }
//        }, new ResultSetExtractor<Integer>() {
//            @Override
//            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
//                rs.next();
//                return rs.getInt(1); // 1부터 시작
//            }
//        });
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from USER order by id", userMapper);
    }

    @Override
    public void update(User user) {
        jdbcTemplate
                .update("update USER set name = ?, password = ?, level = ?, recommend = ?, login = ? where id = ?",
                        user.getName(), user.getPassword(), user.getLevel().intValue(), user.getRecommend(), user.getLogin(), user.getId());
    }
}