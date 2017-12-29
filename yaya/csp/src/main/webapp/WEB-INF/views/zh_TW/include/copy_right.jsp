<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/16
  Time: 16:56
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="now" class="java.util.Date" scope="page"/>

<a href="http://www.beian.gov.cn/" target="_blank"><img src="${ctxStatic}/images/icp.png" align="absmiddle"/>粵公網安備 44010602003231號</a> <a href="http://www.miibeian.gov.cn/" target="_blank">粵ICP備12087993號</a> © <fmt:formatDate value="${now}" pattern="yyyy"/> 敬信科技版權所有
<%-- 友盟站点统计--%>
<script type="text/javascript">
    var cnzz_protocol =
        (("https:" == document.location.protocol) ? " https://" : " http://");
    document.write(unescape("%3Cspan id='cnzz_stat_icon_1271437132'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s22.cnzz.com/z_stat.php%3Fid%3D1271437132%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));
</script>



