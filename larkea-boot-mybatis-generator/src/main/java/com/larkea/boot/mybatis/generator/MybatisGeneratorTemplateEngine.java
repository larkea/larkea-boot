package com.larkea.boot.mybatis.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MybatisGeneratorTemplateEngine extends FreemarkerTemplateEngine {

    private PackageConfig packageConfig;

    public MybatisGeneratorTemplateEngine(PackageConfig packageConfig) {
        this.packageConfig = packageConfig;
    }

    private static String decapitalize(String string) {
        char c[] = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile)
            throws Exception {
        String packageParent = packageConfig.getParent();
        String entityClassName = (String) objectMap.get("entity");
        String dataClassName = entityClassName.replaceAll("Entity$", "");
        String dataResultClassName = dataClassName + "Result";
        String dataParamClassName = dataClassName + "Param";
        String createOrUpdateDataParamClassName = dataClassName + "CreateOrUpdateParam";
        String dataVarNamePluralized = TranslateUtil.pluralize(TranslateUtil.underscore(decapitalize(dataClassName)));

        objectMap.put("data", dataClassName);
        objectMap.put("dataResult", dataResultClassName);
        objectMap.put("dataParam", dataParamClassName);
		objectMap.put("createOrUpdateDataParam", createOrUpdateDataParamClassName);
		objectMap.put("dataVarName", decapitalize(dataClassName));
		objectMap.put("dataVarNamePluralized", dataVarNamePluralized);
        objectMap.put("dataParamVarName", decapitalize(dataClassName) + "Param");
        objectMap.put("dataResultVarName", decapitalize(dataClassName) + "Result");

        TableInfo tableInfo = (TableInfo) objectMap.get("table");
        String comment = tableInfo.getComment();
        comment = comment.replaceAll("表$", "");
        tableInfo.setComment(comment);
        objectMap.put("dataComment", comment);
        List<String> dataImportPackages = tableInfo.getImportPackages().stream()
                .filter(s -> !(s.contains("mybatisplus") || s.contains("BaseEntity"))).collect(
                        Collectors.toList());
        dataImportPackages.add("com.larkea.boot.core.data.BaseData");
        objectMap.put("dataImportPackages", dataImportPackages);

        List<String> dataParamImportPackages = tableInfo.getImportPackages().stream()
                .filter(s -> !(s.contains("mybatisplus") || s.contains("BaseEntity"))).collect(
                        Collectors.toList());
        dataParamImportPackages.add("com.larkea.boot.core.model.BaseModel");
        objectMap.put("dataParamImportPackages", dataParamImportPackages);

        List<String> entityImportPackages = new ArrayList<>(tableInfo.getImportPackages());
		entityImportPackages.add("com.baomidou.mybatisplus.annotation.TableField");
        entityImportPackages.add(String.format("%s.data.%s", packageParent, dataClassName));
        objectMap.put("entityImportPackages", entityImportPackages);

        List<String> controllerImportPackages = new ArrayList<>();
        controllerImportPackages.add(String.format("%s.data.%s", packageParent, dataClassName));
        controllerImportPackages.add(String.format("%s.data.%s", packageParent, dataParamClassName));
		controllerImportPackages.add(String.format("%s.data.%s", packageParent, createOrUpdateDataParamClassName));
		controllerImportPackages.add(String.format("%s.data.%s", packageParent, dataResultClassName));
        controllerImportPackages
                .add(String.format("%s.service.%sService", packageParent, dataClassName));
        objectMap.put("controllerImportPackages", controllerImportPackages);

        List<String> service2ImportPackages = new ArrayList<>();
        service2ImportPackages.add(String.format("%s.data.%s", packageParent, dataClassName));
        service2ImportPackages.add(String.format("%s.data.%s", packageParent, dataResultClassName));
        objectMap.put("service2ImportPackages", service2ImportPackages);

        List<String> serviceImpl2ImportPackages = new ArrayList<>();
        serviceImpl2ImportPackages.add(String.format("%s.dao.%sDao", packageParent, dataClassName));
        serviceImpl2ImportPackages
                .add(String.format("%s.dao.entity.%sEntity", packageParent, dataClassName));
        serviceImpl2ImportPackages.add(String.format("%s.data.%s", packageParent, dataClassName));
        serviceImpl2ImportPackages.add(String.format("%s.data.%s", packageParent, dataResultClassName));
        objectMap.put("serviceImpl2ImportPackages", serviceImpl2ImportPackages);

        @SuppressWarnings("unchecked")
        Map<String, String> packageMap = (Map<String, String>) objectMap.get("package");
        packageMap.put("data", packageParent + ".data");

        // for Service templates
        packageMap.put("Service2", packageParent + ".service");
        packageMap.put("ServiceImpl2", packageParent + ".service.impl");
        objectMap.put("serviceName", String.format("%sService", dataClassName));
        objectMap.put("serviceImplName", String.format("%sServiceImpl", dataClassName));
        super.writer(objectMap, templatePath, outputFile);
    }

}
