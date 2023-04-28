package servlet;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectTest {

    @Test
    @DisplayName("JDBC 연결 상태")
    public void testConnection() throws Exception {

        Class.forName("org.mariadb.jdbc.Driver");

        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/webdb",
                "root",
                "");

        Assertions.assertNotNull(connection);

        connection.close(); // 필수!!!
    }

    @Test
    @DisplayName("HikariCP 사용")
    public void testHikariCP() throws Exception {

        HikariConfig config = new HikariConfig();

        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/webdb");
        config.setUsername("root");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds = new HikariDataSource(config);
        Connection connection = ds.getConnection();

        Assertions.assertNotNull(connection);
        System.out.println(connection);

        connection.close();
    }
}

// Class.forName()
//        : JDBC 드라이버 클래스를 메모리상으로 로딩하는 역할을 한다. 이때 문자열은 패키지명과, 클래스명의 대소문자까지 정확히 일치해야 한다.
//        만일 JDBC 드라이버 파일이 없는 경우에는 이부분에서 예외가 발생하게 된다.

// Connection connection
//         : java.sql 패키지의 Connection 인터페이스 타입의 변수이다. Connection 은 데이터베이스와 네트워크 연결을 의미한다.

// DriverManager.getConnection(url, user, password)
//         : 데이터베이스 내에 있는 여러 정보들을 통해서 특정한 데이터베이스에 연결을 시도한다.

// Assertions.assertNotNull()
//         : 데이터베이스와 정상적으료 연결이 된다면 Connection 타입의 객체는 null 이 아니라는 것을 확신한다는 의미이다.

// connection.close()
//         : 데이터베이스와 연결을 종료한다. JDBC 프로그램은 데이터베이스와 연결을 잠깐씩 맺고 종료하는 방식으로 처리된다. 따라서 반드시 작업이
//         완료되면 데이터베이스와의 연결을 종료해주어야만 한다.

