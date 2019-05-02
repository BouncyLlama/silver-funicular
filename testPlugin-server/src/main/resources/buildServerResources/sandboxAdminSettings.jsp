<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:useBean id="sandboxSettings"
             scope="request"
             type="com.ewarwick.teamcity.testPlugin.settings.PluginSettings"/>

<div id="settingsContainer">

    <c:forEach items="${sandboxSettings.projectSettings}" var="entry">
        Key = ${entry.key}, value = ${entry.value}<br>
    </c:forEach>

</div>