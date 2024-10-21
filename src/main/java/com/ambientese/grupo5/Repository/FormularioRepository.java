package com.ambientese.grupo5.Repository;

import com.ambientese.grupo5.Model.FormularioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FormularioRepository extends JpaRepository<FormularioModel, Long>, JpaSpecificationExecutor<FormularioModel> {
    
    @Query(value = "SELECT * FROM vw_formulario_ranking ORDER BY pontuacao_final DESC", nativeQuery = true)
    List<FormularioModel> findLatestByEmpresaOrderByPontuacaoFinalDesc();

    @Query(value = "SELECT * FROM vw_formulario_ranking WHERE empresa_id = :empresaId ORDER BY pontuacao_final DESC LIMIT 1", nativeQuery = true)
    FormularioModel findLatestFormByEmpresaId(@Param("empresaId") Long empresaId);

    @Query(value = "SELECT * FROM vw_formulario_ranking WHERE empresa_id = :empresaId AND pontuacao_final IS NULL", nativeQuery = true)
    Optional<FormularioModel> findIncompleteByEmpresaId(@Param("empresaId") Long empresaId);

    List<FormularioModel> findAllByOrderByPontuacaoFinalAsc();
    List<FormularioModel> findAllByOrderByPontuacaoSocialAsc();
    List<FormularioModel> findAllByOrderByPontuacaoAmbientalAsc();
    List<FormularioModel> findAllByOrderByPontuacaoGovernamentalAsc();

    List<FormularioModel> findByEmpresaId(Long empresaId);
}
