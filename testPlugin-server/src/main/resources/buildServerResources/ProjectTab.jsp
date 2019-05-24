<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:useBean id="Settings"
             scope="request"
             type="com.ewarwick.teamcity.testPlugin.settings.PluginSettings"/>
<c:url value="${tagEndpoint}" var="tagEndpoint"/>

<script type="text/javascript">
    <%@include file="js/util.js"%>
</script>

<div id="settingsContainer">
    <h3>Current tags:</h3>
    <table border="1">
        <tr>
            <th>Regex</th>
            <th>Tag</th>
            <th>Action</th>
        </tr>
        <tbody>
        <c:forEach items="${Settings.projectSettings[projectId].patterns}" var="entry">
            <tr>
                <td><c:out value="${entry.key}"></c:out></td>
                <td><c:out value="${entry.value}"></c:out></td>
                <td>
                    <button onclick="ProjectConfig.deleteRegex(this,'${tagEndpoint}','${projectId}')"
                            name="${entry.key}">Delete
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="addPatternContainer">
    <h3>Add a new tag:</h3>
    <form id="submitPattern" method="post">
        <label>Regex:</label>
        <input id="regex" name="regex" type="text"/>
        <label>Tag:</label>
        <input id="tag" name="tag" type="text"/>
        <input id="submit" type="button" onclick="ProjectConfig.addRegex('${tagEndpoint}','${projectId}')"
               value="Submit"/>
    </form>
</div>