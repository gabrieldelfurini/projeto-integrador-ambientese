// package com.ambientese.grupo5.Services.FormulariosService;

// import com.ambientese.grupo5.Model.Enums.EixoEnum;
// import com.ambientese.grupo5.DTO.QuestionarioResponse;
// import com.ambientese.grupo5.Model.PerguntasModel;
// import com.ambientese.grupo5.Repository.PerguntasRepository;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.util.Arrays;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class)
// class BuscarPerguntasDoBancoServiceTest {

//     @Mock
//     private PerguntasRepository perguntasRepository;

//     @InjectMocks
//     private BuscarPerguntasDoBancoService buscarPerguntasDoBancoService;

//     @Test
//     void buscarPerguntasDoBanco_DeveRetornarListaCom15Perguntas() {
//         // Mockando a resposta do repositório
//         List<PerguntasModel> perguntasMock = Arrays.asList(
//                 new PerguntasModel("Pergunta 1", EixoEnum.Social),
//                 new PerguntasModel("Pergunta 2", EixoEnum.Ambiental),
//                 new PerguntasModel("Pergunta 3", EixoEnum.Governamental),
//                 new PerguntasModel("Pergunta 4", EixoEnum.Social),
//                 new PerguntasModel("Pergunta 5", EixoEnum.Ambiental)
//                 // Adicione mais perguntas conforme necessário
//         );
//         when(perguntasRepository.findByEixo(EixoEnum.Social)).thenReturn(perguntasMock);
//         when(perguntasRepository.findByEixo(EixoEnum.Ambiental)).thenReturn(perguntasMock);
//         when(perguntasRepository.findByEixo(EixoEnum.Governamental)).thenReturn(perguntasMock);

//         // Chamando o método a ser testado
//         QuestionarioResponse perguntas = buscarPerguntasDoBancoService.buscarPerguntasDoBanco(true, 1L);

//         // Verificando se o método retorna uma lista com 15 perguntas
//         if (perguntas.getFormularioRequests().size() > 0) {
//             assertEquals(15, perguntas.getFormularioRequests().size());
//         } else {
//             assertEquals(15, perguntas.getPerguntas().size());
//         }
//     }
// }
