package ${package.Service2};

<#list service2ImportPackages as pkg>
import ${pkg};
</#list>
import com.larkea.boot.core.data.Page;
import com.larkea.boot.core.data.PageQueryParam;
import java.util.List;
/**
 * <p>
 * ${table.comment!}服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${serviceName}
<#else>
public interface ${serviceName} {

  Page<${data}> list${data}(PageQueryParam pageQueryParam);

  Page<${dataResult}> list${dataResult}(PageQueryParam pageQueryParam);

  ${data} get${data}ById(Long id);

  ${dataResult} get${dataResult}ById(Long id);

  ${dataResult} create${data}(${data} ${dataVarName});

  ${dataResult} update${data}(${data} ${dataVarName});

  boolean delete${data}ById(Long id);

  boolean delete${data}(List<Long> ids);

}
</#if>
