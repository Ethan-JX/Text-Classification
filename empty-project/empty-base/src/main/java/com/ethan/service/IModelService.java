package com.ethan.service;

import com.ethan.entity.Model;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ethan on 2017/3/29.
 */
public interface IModelService {
    void saveOrUpdataModel(Model model);

    void trainModelAndSaveModel(Model model,String pathPrefix);

    void deleteModelById(String id);

    Model getModelById(String id);

    String classifyTextByModelId(String text,String modelId) throws IOException;

    List<Model> getAllModelList();
}
