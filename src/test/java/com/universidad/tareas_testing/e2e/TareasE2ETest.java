package com.universidad.tareas_testing.e2e;

import com.universidad.tareas_testing.repository.TareaRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
class TareasE2ETest {

    private WebDriver driver;

    @Autowired
    private TareaRepository repo;

    @BeforeEach
    void setUp() {
        repo.deleteAll();
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(opts);
        driver.get("http://localhost:8080/tareas");
    }

    @Test
    void paginaTareas_cargaCorrectamente() {
        assertThat(driver.getTitle()).contains("Tareas");
    }

    @Test
    void crearTarea_desdeFormulario_incrementaListado() {
        TareasPage tareasPage = new TareasPage(driver);
        int inicial = tareasPage.contarTareas();

        TareasPage resultado = tareasPage.irANuevaTarea()
                .crearTarea("Estudiar Selenium", "Practicar Page Object Model");

        assertThat(resultado.contarTareas()).isEqualTo(inicial + 1);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
