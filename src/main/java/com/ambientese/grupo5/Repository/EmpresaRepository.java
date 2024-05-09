    package com.ambientese.grupo5.Repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import com.ambientese.grupo5.Model.EmpresaModel;

    import java.util.List;
    import java.util.Optional;

    public interface EmpresaRepository extends JpaRepository<EmpresaModel, Long> {
        List<EmpresaModel> findByNomeFantasiaContainingIgnoreCase(String nomeFantasia);

        List<EmpresaModel> findByRazaoSocialContainingIgnoreCase(String razaoSocial);

        Optional<EmpresaModel> findByCnpjAndIdNot(String cnpj, Long id);

        Optional<EmpresaModel> findByCnpj(String cnpj);
    }
