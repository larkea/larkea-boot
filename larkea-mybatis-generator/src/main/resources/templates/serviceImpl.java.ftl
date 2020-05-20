package ${package.ServiceImpl2};

<#list serviceImpl2ImportPackages as pkg>
import ${pkg};
</#list>
import ${package.Service2}.${serviceName};
import Page;
import PageQueryParam;
import BeanUtil;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import lombok.AllArgsConstructor;

/**
 * <p>
 * ${table.comment!}服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
@AllArgsConstructor
<#if kotlin>
open class ${serviceImplName}{

}
<#else>
public class ${serviceImplName} implements ${serviceName} {

  private ${data}Dao ${dataVarName}Dao;

  @Override
  public Page<${data}> list${data}(PageQueryParam pageQueryParam) {
    Page<${data}Entity> ${dataVarName}EntityPage = ${dataVarName}Dao.page(pageQueryParam);
    return ${dataVarName}EntityPage.map(${data}Entity::toData);
  }

  @Override
  public Page<${dataResult}> list${dataResult}(PageQueryParam pageQueryParam) {
    return list${data}(pageQueryParam).map(this::apply);
  }

  @Override
  public ${data} get${data}ById(Long id) {
    return Optional.ofNullable(${dataVarName}Dao.getById(id)).map(${data}Entity::toData).orElse(null);
  }

  @Override
  public ${dataResult} get${dataResult}ById(Long id) {
    return Optional.ofNullable(get${data}ById(id)).map(this::apply).orElse(null);
  }

  @Override
  public ${dataResult} create${data}(${data} ${dataVarName}) {
    ${data}Entity ${dataVarName}Entity = new ${data}Entity();
    ${dataVarName}Entity.fromData(${dataVarName});
    ${dataVarName}Dao.saveOrUpdate(${dataVarName}Entity);
    return Optional.ofNullable(${dataVarName}Entity.toData()).map(this::apply).orElse(null);
  }

  @Override
  public ${dataResult} update${data}(${data} ${dataVarName}) {
    Assert.notNull(${dataVarName}.getId(), "${dataVarName}.id cannot be null");
    ${data}Entity ${dataVarName}Entity = new ${data}Entity();
    ${dataVarName}Entity.fromData(${dataVarName});
    ${dataVarName}Dao.updateById(${dataVarName}Entity);
    return Optional.ofNullable(${dataVarName}Entity.toData()).map(this::apply).orElse(null);
  }

  @Override
  public boolean delete${data}ById(Long id) {
    return ${dataVarName}Dao.removeById(id);
  }

  @Override
  public boolean delete${data}(List<Long> ids) {
    Assert.notEmpty(ids, "ids cannot be empty");
    return ${dataVarName}Dao.removeByIds(ids);
  }

  private ${dataResult} apply(${data} ${dataVarName}) {
    // TODO Apply ${data} to ${dataResult}
    ${dataResult} ${dataResultVarName} = new ${dataResult}();
    BeanUtil.copyProperties(${dataVarName}, ${dataResultVarName});
    return ${dataResultVarName};
  }
}
</#if>
