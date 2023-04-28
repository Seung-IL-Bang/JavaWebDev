package servlet.service;

import com.webdev.servlet.dto.TodoDTO;
import com.webdev.servlet.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@Log4j2
public class TodoServiceTest {

    private TodoService todoService;

    @BeforeEach
    public void ready() {
        todoService = TodoService.INSTANCE;
    }

    @Test
    @DisplayName("Todo 등록하기")
    public void testRegister() throws Exception {

        TodoDTO todoDTO = TodoDTO.builder()
                .title("JDBC Test Title")
                .dueDate(LocalDate.now())
                .build();

        log.info("---------------------------------------------------");
        log.info(todoDTO);

        todoService.register(todoDTO);
    }
}
