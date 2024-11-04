package com.ambientese.grupo5.FormularioServiceTests;

import com.ambientese.grupo5.DTO.FormularioRequest;
import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Model.Enums.EixoEnum;
import com.ambientese.grupo5.Model.Enums.NivelCertificadoEnum;
import com.ambientese.grupo5.Model.Enums.RespostasEnum;
import com.ambientese.grupo5.Model.FormularioModel;
import com.ambientese.grupo5.Model.PerguntasModel;
import com.ambientese.grupo5.Model.RespostaModel;
import com.ambientese.grupo5.Repository.EmpresaRepository;
import com.ambientese.grupo5.Repository.FormularioRepository;
import com.ambientese.grupo5.Repository.PerguntasRepository;
import com.ambientese.grupo5.Repository.RespostaRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessarFormularioServiceTest {

    @Mock
    private FormularioRepository formularioRepository;

    @Mock
    private EmpresaRepository empresaRepository;
    
    @Mock
    private PerguntasRepository perguntasRepository;

    @Mock
    private RespostaRepository respostaRepository;

    @InjectMocks
    private ProcessarFormularioService processarFormularioService;

    @Test
    void testCriarProcessarEGerarCertificado() {
        Long empresaId = 1L;

        // Mock dos dados de entrada
        List<FormularioRequest> formularioRequestList = Arrays.asList(
                new FormularioRequest(1L, EixoEnum.Social, RespostasEnum.Conforme),
                new FormularioRequest(2L, EixoEnum.Ambiental, RespostasEnum.Conforme),
                new FormularioRequest(3L, EixoEnum.Governamental, RespostasEnum.NaoConforme)
        );

        // Mock do objeto PerguntasModel retornado pelo PerguntasRepository
        PerguntasModel perguntaMock = new PerguntasModel();
        perguntaMock.setId(1L);

        // Mock do objeto EmpresaModel retornado pelo EmpresaRepository
        EmpresaModel empresaMock = new EmpresaModel();
        empresaMock.setId(empresaId);
        when(empresaRepository.findById(empresaId)).thenReturn(Optional.of(empresaMock));

        // Mock do objeto esperado retornado pelo método auxiliar
        FormularioModel expectedFormularioModel = getFormularioModel();
        expectedFormularioModel.setEmpresa(empresaMock); // Associar a empresa mockada

        // Configuração do comportamento do mock do repositório
        when(formularioRepository.save(any())).thenReturn(expectedFormularioModel);
        when(perguntasRepository.findById(anyLong())).thenReturn(Optional.of(perguntaMock));

        // Chamada do método a ser testado
        FormularioModel actualFormularioModel = processarFormularioService.criarProcessarEGerarCertificado(empresaId, formularioRequestList);

        // Verificação dos resultados
        assertEquals(expectedFormularioModel.getPontuacaoFinal(), actualFormularioModel.getPontuacaoFinal());
        assertEquals(expectedFormularioModel.getPontuacaoSocial(), actualFormularioModel.getPontuacaoSocial());
        assertEquals(expectedFormularioModel.getPontuacaoAmbiental(), actualFormularioModel.getPontuacaoAmbiental());
        assertEquals(expectedFormularioModel.getPontuacaoGovernamental(), actualFormularioModel.getPontuacaoGovernamental());
        assertEquals(expectedFormularioModel.getCertificado(), actualFormularioModel.getCertificado());
        assertEquals(expectedFormularioModel.getEmpresa(), actualFormularioModel.getEmpresa());

        // Verifica as respostas individualmente
        for (int i = 0; i < expectedFormularioModel.getRespostas().size(); i++) {
            RespostaModel expectedResposta = expectedFormularioModel.getRespostas().get(i);
            RespostaModel actualResposta = actualFormularioModel.getRespostas().get(i);
            assertEquals(expectedResposta.getResposta(), actualResposta.getResposta());
        }

        verify(formularioRepository, times(1)).save(any());
        verify(respostaRepository, times(1)).saveAll(any());
        verify(empresaRepository, times(1)).findById(empresaId); // Verificação adicional para o repositório de empresa
    }

    private static FormularioModel getFormularioModel() {
        FormularioModel expectedFormularioModel = new FormularioModel();
        expectedFormularioModel.setPontuacaoFinal(66);
        expectedFormularioModel.setPontuacaoSocial(1);
        expectedFormularioModel.setPontuacaoAmbiental(1);
        expectedFormularioModel.setPontuacaoGovernamental(0);
        expectedFormularioModel.setCertificado(NivelCertificadoEnum.Bronze);

        List<RespostaModel> respostas = Arrays.asList(
                createRespostaModel(RespostasEnum.Conforme),
                createRespostaModel(RespostasEnum.Conforme),
                createRespostaModel(RespostasEnum.NaoConforme)
        );

        expectedFormularioModel.setRespostas(respostas);
        return expectedFormularioModel;
    }

    private static RespostaModel createRespostaModel(RespostasEnum respostaEnum) {
        RespostaModel respostaModel = new RespostaModel();
        respostaModel.setResposta(respostaEnum);
        return respostaModel;
    }

    @Test
    void testCalcularNivelCertificadoOuro() {
        NivelCertificadoEnum actualNivel = processarFormularioService.calcularNivelCertificado(100);
        assertEquals(NivelCertificadoEnum.Ouro, actualNivel);
    }

    @Test
    void testCalcularNivelCertificadoPrata() {
        NivelCertificadoEnum actualNivel = processarFormularioService.calcularNivelCertificado(80);
        assertEquals(NivelCertificadoEnum.Prata, actualNivel);
    }

    @Test
    void testCalcularNivelCertificadoBronze() {
        NivelCertificadoEnum actualNivel = processarFormularioService.calcularNivelCertificado(60);
        assertEquals(NivelCertificadoEnum.Bronze, actualNivel);
    }
}
