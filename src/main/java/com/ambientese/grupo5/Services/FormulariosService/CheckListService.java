package com.ambientese.grupo5.Services.FormulariosService;

import com.ambientese.grupo5.Model.CheckListModel;
import com.ambientese.grupo5.Repository.CheckListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckListService {

    @Autowired
    private CheckListRepository checkListRepository;

    public List<CheckListModel> buscarChecklists() {
        return checkListRepository.findAll();
    }
}
