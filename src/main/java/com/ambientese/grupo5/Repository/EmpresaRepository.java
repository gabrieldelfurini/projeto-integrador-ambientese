package com.ambientese.grupo5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ambientese.grupo5.Model.EmpresaModel;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<EmpresaModel, Long> {

    // Usando view vw_empresas_nomefantasia para obter empresas ordenadas por nomeFantasia
    @Query(value = "SELECT * FROM vw_empresas_nomefantasia", nativeQuery = true)
    List<EmpresaModel> findAllOrderByNomeFantasiaAsc();    

    // Usando view vw_empresas_ramos_distintos para listar ramos distintos
    @Query(value = "SELECT ramo FROM vw_empresas_ramos_distintos", nativeQuery = true)
    List<String> findDistinctRamos();

    // Usando view vw_empresas_por_nomefantasia para buscar por nomeFantasia específico
    @Query(value = "SELECT * FROM vw_empresas_por_nomefantasia WHERE LOWER(nome_fantasia) LIKE LOWER(CONCAT(:nomeFantasia, '%')) ORDER BY nome_fantasia ASC", nativeQuery = true)
    List<EmpresaModel> findAllByNomeFantasiaStartingWithIgnoreCaseOrderByNomeFantasiaAsc(@Param("nomeFantasia") String nomeFantasia);

    // Usando view vw_empresas_por_razaosocial para buscar por razaoSocial específico
    @Query(value = "SELECT * FROM vw_empresas_por_razaosocial WHERE LOWER(razao_social) LIKE LOWER(CONCAT(:razaoSocial, '%')) ORDER BY razao_social ASC", nativeQuery = true)
    List<EmpresaModel> findAllByRazaoSocialStartingWithIgnoreCaseOrderByRazaoSocialAsc(@Param("razaoSocial") String razaoSocial);

    // Usando view vw_empresas_por_ramo para buscar empresas por ramo específico
    @Query(value = "SELECT * FROM vw_empresas_por_ramo WHERE LOWER(ramo) LIKE LOWER(CONCAT(:ramo, '%')) ORDER BY nome_fantasia ASC", nativeQuery = true)
    List<EmpresaModel> findAllByRamoStartingWithIgnoreCaseOrderByNomeFantasiaAsc(@Param("ramo") String ramo);

    // Usando view vw_empresa_por_cnpj para buscar empresa por CNPJ
    @Query(value = "SELECT * FROM vw_empresa_por_cnpj WHERE cnpj = :cnpj", nativeQuery = true)
    Optional<EmpresaModel> findByCnpj(@Param("cnpj") String cnpj);
}
