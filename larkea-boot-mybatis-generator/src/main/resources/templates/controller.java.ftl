package ${package.Controller};


<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
<#if restControllerStyle>
<#list controllerImportPackages as pkg>
import ${pkg};
</#list>
import Page;
import PageQueryParam;
import Result;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
<#else>
import org.springframework.stereotype.Controller;
</#if>

/**
 * <p>
 * ${table.comment!}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@Api(value = "${table.comment!}", tags = "${table.comment!}")
@RestController
<#else>
@Controller
</#if>
@AllArgsConstructor
@RequestMapping("/${dataVarNamePluralized}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

  private ${data}Service ${dataVarName}Service;

  @ApiOperation(value = "分页查询${table.comment!}", notes = "分页查询${table.comment!}")
  @GetMapping
  public Result<Page<${dataResult}>> list${dataResult}(PageQueryParam pageQueryParam) {
    return ok(${dataVarName}Service.list${dataResult}(pageQueryParam));
  }

  @ApiOperation(value = "查询指定${table.comment!}的详细信息", notes = "查询指定${table.comment!}的详细信息")
  @GetMapping("/{${dataVarName}Id}")
  public Result<${dataResult}> get${dataResult}ById(@PathVariable("${dataVarName}Id") Long ${dataVarName}Id) {
    return ok(${dataVarName}Service.get${dataResult}ById(${dataVarName}Id));
  }

  @ApiOperation(value = "创建${table.comment!}", notes = "创建${table.comment!}")
  @PostMapping
  public Result<${dataResult}> create${data}(@Valid ${dataParam} ${dataParamVarName}) {
    ${data} ${dataVarName} = ${dataParamVarName}.toData();
    return ok(${dataVarName}Service.create${data}(${dataVarName}));
  }

  @ApiOperation(value = "修改指定${table.comment!}", notes = "修改指定${table.comment!}")
  @PutMapping("/{${dataVarName}Id}")
  public Result<${dataResult}> update${data}(@PathVariable("${dataVarName}Id") Long ${dataVarName}Id, @Valid ${dataParam} ${dataParamVarName}) {
    ${data} ${dataVarName} = ${dataParamVarName}.toData();
    ${dataVarName}.setId(${dataVarName}Id);
    return ok(${dataVarName}Service.update${data}(${dataVarName}));
  }

  @ApiOperation(value = "删除指定${table.comment!}", notes = "删除指定${table.comment!}")
  @DeleteMapping("/{${dataVarName}Id}")
  public Result delete${data}ById(@PathVariable("${dataVarName}Id") Long ${dataVarName}Id) {
    return ok(${dataVarName}Service.delete${data}ById(${dataVarName}Id));
  }

  @ApiOperation(value = "批量删除${table.comment!}", notes = "批量删除${table.comment!}")
  @DeleteMapping
  public Result delete${data}(@RequestParam List<Long> ids) {
    return ok(${dataVarName}Service.delete${data}(ids));
  }
}
</#if>
