package com.universidad.tareas_testing.controller;

import com.universidad.tareas_testing.entity.Tarea;
import com.universidad.tareas_testing.service.TareaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TareaController.class)
@AutoConfigureMockMvc(addFilters = false)
class TareaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TareaService service;

    @Test
    void get_tareaExiste_retorna200() throws Exception {
        Tarea t = new Tarea();
        t.setId(1L);
        t.setTitulo("Test");
        when(service.buscarPorId(1L)).thenReturn(t);

        mockMvc.perform(get("/api/tareas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Test"));
    }

    @Test
    void get_noExiste_retorna404() throws Exception {
        when(service.buscarPorId(99L)).thenThrow(new EntityNotFoundException("no encontrada"));

        mockMvc.perform(get("/api/tareas/99"))
                .andExpect(status().isNotFound());
    }
}
