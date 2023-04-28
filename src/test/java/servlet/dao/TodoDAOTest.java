package servlet.dao;

import com.webdev.servlet.dao.TodoDAO;
import com.webdev.servlet.domain.TodoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class TodoDAOTest {

    private TodoDAO todoDAO;

    @BeforeEach
    public void ready() {
        todoDAO = new TodoDAO();
    }

    @Test
    @DisplayName("INSERT 작동")
    public void testInsert() throws Exception {

        TodoVO todoVO = TodoVO.builder()
                .title("sample title")
                .dueDate(LocalDate.of(2023, 4, 28))
                .build();

        todoDAO.insert(todoVO);
    }

    @Test
    @DisplayName("SELECT ONE 작동")
    public void testSelectOne() throws Exception {

        Long tno = 2L;

        TodoVO todoVO = todoDAO.selectOne(tno);

        System.out.println(todoVO);
    }

    @Test
    @DisplayName("SELECT ALL 작동")
    public void testSelectAll() throws Exception {

        List<TodoVO> list = todoDAO.selectAll();

        list.forEach(vo -> System.out.println(vo));
    }

    @Test
    @DisplayName("UPDATE 작동")
    public void testUpdateOne() throws Exception {

        TodoVO todoVo = TodoVO.builder()
                .tno(2L)
                .title("update test")
                .dueDate(LocalDate.of(2021, 12, 31))
                .finished(true)
                .build();

        todoDAO.updateOne(todoVo);
    }


}
