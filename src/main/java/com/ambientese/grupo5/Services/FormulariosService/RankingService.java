package com.ambientese.grupo5.Services.FormulariosService;

import com.ambientese.grupo5.Model.FormularioModel;
import com.ambientese.grupo5.Repository.FormularioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingService {

    private final FormularioRepository formularioRepository;

    @Autowired
    public RankingService(FormularioRepository formularioRepository) {
        this.formularioRepository = formularioRepository;
    }

    public List<FormularioModel> classificarPorPontuacao() {
        return formularioRepository.findAllByOrderByPontuacaoFinalAsc();
    }
    public List<FormularioModel>classificarPorEixoAmbiental(){
        return formularioRepository.findAllByOrderByPontuacaoAmbientalAsc();
    }
    public List<FormularioModel>classificarPorEixoSocial (){
        return formularioRepository.findAllByOrderByPontuacaoSocialAsc();
    }
    public List<FormularioModel>classificarPorEixoGovernamental (){
        return formularioRepository.findAllByOrderByPontuacaoGovernamentalAsc();
    }
}
