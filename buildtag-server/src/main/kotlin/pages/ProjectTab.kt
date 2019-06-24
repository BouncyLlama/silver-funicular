package com.ewarwick.teamcity.buildtag


import com.ewarwick.teamcity.buildtag.settings.Constants
import com.ewarwick.teamcity.buildtag.settings.SettingsManager
import jetbrains.buildServer.serverSide.ProjectManager
import jetbrains.buildServer.serverSide.SProject
import jetbrains.buildServer.serverSide.auth.Permission
import jetbrains.buildServer.users.SUser
import jetbrains.buildServer.web.openapi.*
import jetbrains.buildServer.web.openapi.project.ProjectTab
import jetbrains.buildServer.web.util.SessionUser

import javax.servlet.http.HttpServletRequest

class ProjectTab protected constructor(private val manager: WebControllerManager, projectManager: ProjectManager, descriptor: PluginDescriptor,
                                       private val settingsManager: SettingsManager) : ProjectTab("com.ewarwick.teamcity.buildtagstab", "Build Tags", manager, projectManager, descriptor.getPluginResourcesPath(PAGE)) {

    private lateinit var projectId: String
    override fun fillModel(p0: MutableMap<String, Any>, p1: HttpServletRequest, p2: SProject, p3: SUser?) {
        settingsManager.init()
        this.projectId = p2.projectId
        p0["Settings"] = settingsManager.pluginSettings
        p0["jspHome"] = jspHome
        p0["tagEndpoint"] = Constants().TagEndpoint
        p0["projectId"] = p2.projectId
    }

    private val jspHome: String

    init {

        jspHome = descriptor.pluginResourcesPath
    }


    override fun isAvailable(request: HttpServletRequest): Boolean {


        return SessionUser.getUser(request).isPermissionGrantedGlobally(Permission.TAG_BUILD) || SessionUser.getUser(request).isPermissionGrantedForProject(projectId, Permission.TAG_BUILD)
    }

    companion object {
        private val PAGE = "ProjectTab.jsp"
    }
}