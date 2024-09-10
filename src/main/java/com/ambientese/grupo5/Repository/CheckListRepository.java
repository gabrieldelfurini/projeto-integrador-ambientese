package com.ambientese.grupo5.Repository;

import com.ambientese.grupo5.Model.CheckListModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckListModel, Long> {
}
