package com.ambientese.grupo5.Repository;

import com.ambientese.grupo5.Model.CheckListModel;
import com.ambientese.grupo5.Model.Enums.EixoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckListRepository extends JpaRepository<CheckListModel, Long> {

    // Novo método para buscar checklists por eixo
    List<CheckListModel> findByEixo(EixoEnum eixo);
}
