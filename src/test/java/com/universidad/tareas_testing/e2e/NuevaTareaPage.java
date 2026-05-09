package com.universidad.tareas_testing.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NuevaTareaPage {

    private final WebDriver driver;
    private final By inputTitulo = By.id("titulo");
    private final By inputDescripcion = By.id("descripcion");
    private final By btnGuardar = By.id("btn-guardar");

    public NuevaTareaPage(WebDriver driver) {
        this.driver = driver;
    }

    public TareasPage crearTarea(String titulo, String descripcion) {
        driver.findElement(inputTitulo).sendKeys(titulo);
        driver.findElement(inputDescripcion).sendKeys(descripcion);
        driver.findElement(btnGuardar).click();
        return new TareasPage(driver);
    }
}
