package com.ambientese.grupo5.Services.CheckListService;

import com.ambientese.grupo5.Model.CheckListModel;
import com.ambientese.grupo5.Repository.CheckListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListarCheckListService {

    private final CheckListRepository checkListRepository;

    public ListarCheckListService(CheckListRepository checkListRepository) {
        this.checkListRepository = checkListRepository;
    }

    public Optional<CheckListModel> findById(Long id) {
        return checkListRepository.findById(id);
    }

    public List<CheckListModel> findAll() {
        return checkListRepository.findAll();
    }
}
