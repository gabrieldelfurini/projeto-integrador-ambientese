package com.ambientese.grupo5.Services.EmpresaService;

import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Model.FormularioModel;
import com.ambientese.grupo5.Repository.EmpresaRepository;
import com.ambientese.grupo5.Repository.FormularioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeletarEmpresaServiceTests {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private FormularioRepository formularioRepository;

    @InjectMocks
    private DeletarEmpresaService deletarEmpresaService;

    @Test
    void deleteEmpresa_quandoEmpresaNaoEncontrada_naoRealizaNenhumaAcao() {
        Long id = 1L;
        when(empresaRepository.findById(id)).thenReturn(Optional.empty());

        deletarEmpresaService.deleteEmpresa(id);

        verify(empresaRepository, never()).delete(any(EmpresaModel.class));
        verify(formularioRepository, never()).delete(any(FormularioModel.class));
    }

    @Test
    void deleteEmpresa_quandoEmpresaEncontrada_deletaFormulariosEEmpresa() {
        Long id = 1L;
        EmpresaModel empresa = new EmpresaModel();
        empresa.setId(id);
        FormularioModel formulario = new FormularioModel();
        List<FormularioModel> formularios = Collections.singletonList(formulario);

        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresa));
        when(formularioRepository.findByEmpresaId(id)).thenReturn(formularios);

        deletarEmpresaService.deleteEmpresa(id);

        verify(formularioRepository, times(1)).findByEmpresaId(id);
        verify(formularioRepository, times(1)).delete(formulario);
        verify(empresaRepository, times(1)).delete(empresa);
    }

    @Test
    void deleteEmpresa_quandoEmpresaSemFormularios_deletaApenasEmpresa() {
        Long id = 1L;
        EmpresaModel empresa = new EmpresaModel();
        empresa.setId(id);

        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresa));
        when(formularioRepository.findByEmpresaId(id)).thenReturn(Collections.emptyList());

        deletarEmpresaService.deleteEmpresa(id);

        verify(formularioRepository, times(1)).findByEmpresaId(id);
        verify(formularioRepository, never()).delete(any(FormularioModel.class));
        verify(empresaRepository, times(1)).delete(empresa);
    }
}