package com.ambientese.grupo5.DTO;

<<<<<<< HEAD
import com.ambientese.grupo5.Model.Enums.EixoEnum;
=======

>>>>>>> develop-empresa-front
import com.ambientese.grupo5.Model.Enums.RespostasEnum;

public class FormularioRequest {
    private Long perguntaId;
    private Long numeroPergunta;
    private String perguntaDescricao;
    private RespostasEnum respostaUsuario;
<<<<<<< HEAD
    private EixoEnum perguntaEixo;
    private Long idFormulario;

    public Long getPerguntaId() {
        return perguntaId;
    }

    public void setPerguntaId(Long perguntaId) {
        this.perguntaId = perguntaId;
    }
=======
>>>>>>> develop-empresa-front

    public Long getNumeroPergunta() {
        return numeroPergunta;
    }

    public void setNumeroPergunta(Long numeroPergunta) {
        this.numeroPergunta = numeroPergunta;
    }

    public String getPerguntaDescricao() {
        return perguntaDescricao;
    }

    public void setPerguntaDescricao(String perguntaDescricao) {
        this.perguntaDescricao = perguntaDescricao;
    }

    public RespostasEnum getRespostaUsuario() {
        return respostaUsuario;
    }

    public void setRespostaUsuario(RespostasEnum respostaUsuario) {
        this.respostaUsuario = respostaUsuario;
    }
<<<<<<< HEAD

    public EixoEnum getPerguntaEixo() {
        return perguntaEixo;
    }

    public void setPerguntaEixo(EixoEnum perguntaEixo) {
        this.perguntaEixo = perguntaEixo;
    }

    public Long getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Long idFormulario) {
        this.idFormulario = idFormulario;
    }

    public FormularioRequest(Long perguntaId, EixoEnum perguntaEixo, RespostasEnum respostaUsuario) {
        this.perguntaId = perguntaId;
        this.perguntaEixo = perguntaEixo;
        this.respostaUsuario = respostaUsuario;
    }
=======
>>>>>>> develop-empresa-front
}
