package com.ewarwick.teamcity.testPlugin


import com.ewarwick.teamcity.testPlugin.settings.ProjectSettings
import com.ewarwick.teamcity.testPlugin.settings.SettingsManager
import jetbrains.buildServer.serverSide.ProjectManager
import jetbrains.buildServer.serverSide.SBuildType
import jetbrains.buildServer.serverSide.SProject
import jetbrains.buildServer.users.SUser
import jetbrains.buildServer.web.openapi.*
import jetbrains.buildServer.web.openapi.buildType.BuildTypeTab
import jetbrains.buildServer.web.openapi.project.ProjectTab

import javax.servlet.http.HttpServletRequest

class ProjectTab protected constructor(manager: WebControllerManager, projectManager: ProjectManager, descriptor: PluginDescriptor,
                                       private val settingsManager: SettingsManager) : ProjectTab("footabid", "footab", manager, projectManager, descriptor.getPluginResourcesPath(PAGE)) {


    override fun fillModel(p0: MutableMap<String, Any>, p1: HttpServletRequest, p2: SProject, p3: SUser?) {
        p0["Settings"] = settingsManager.pluginSettings
        p0["jspHome"] = jspHome
    }

    private val jspHome: String

    init {
        jspHome = descriptor.pluginResourcesPath
    }


    override fun isAvailable(request: HttpServletRequest): Boolean {
        return super.isAvailable(request)
    }

    companion object {
        private val PAGE = "ProjectTab.jsp"
    }
}