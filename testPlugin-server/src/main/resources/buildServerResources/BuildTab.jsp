<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:useBean id="Settings"
             scope="request"
             type="com.ewarwick.teamcity.testPlugin.settings.PluginSettings"/>

<div id="settingsContainer">

    <c:forEach items="${Settings.projectSettings}" var="entry">
        Key = ${entry.key}, value = ${entry.value}<br>
    </c:forEach>

</div>