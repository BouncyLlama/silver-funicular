package com.ewarwick.teamcity.testPlugin

import com.ewarwick.teamcity.testPlugin.settings.Constants
import com.ewarwick.teamcity.testPlugin.settings.ProjectSettings
import com.ewarwick.teamcity.testPlugin.settings.SettingsManager
import jetbrains.buildServer.controllers.BaseController
import jetbrains.buildServer.controllers.SimpleView
import jetbrains.buildServer.serverSide.ProjectManager
import jetbrains.buildServer.web.openapi.PluginDescriptor
import jetbrains.buildServer.web.openapi.WebControllerManager
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TagController() : BaseController() {
    private lateinit var myDescriptor: PluginDescriptor
    private lateinit var settingsManager: SettingsManager
    private lateinit var projectManager: ProjectManager

    constructor(manager: WebControllerManager, descriptor: PluginDescriptor, myDescriptor: PluginDescriptor, settingsManager: SettingsManager, projectManager: ProjectManager) : this() {
        manager.registerController(Constants().TagEndpoint, this)
        this.myDescriptor = descriptor
        this.myDescriptor = myDescriptor
        this.settingsManager = settingsManager
        this.projectManager = projectManager
        this.setSupportedMethods("HEAD", "OPTIONS", "POST", "DELETE")

    }

    constructor(myDescriptor: PluginDescriptor) : this() {

    }

    @Throws(Exception::class)
    override fun doHandle(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse): ModelAndView? {

        if (httpServletRequest.method == "POST") {
            return handlePost(httpServletRequest, httpServletResponse)
        } else if (httpServletRequest.method == "DELETE") {
            return handleDelete(httpServletRequest, httpServletResponse)
        }
        return null
    }

    private fun handlePost(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse): ModelAndView? {
        val pattern = httpServletRequest.parameterMap["regex"]
        val tag = httpServletRequest.parameterMap["tag"]
        val projectId = httpServletRequest.getParameter("project")
        if (!settingsManager.pluginSettings.projectSettings.containsKey(projectId)) {
            settingsManager.pluginSettings.projectSettings[projectId] = ProjectSettings()
        }
        settingsManager.pluginSettings.projectSettings[projectId]?.patterns?.put(pattern!!.get(0), tag!!.get(0))
        settingsManager.saveConfiguration()
        return null
    }

    private fun handleDelete(request: HttpServletRequest, response: HttpServletResponse): ModelAndView? {
        val id = request.getParameter("id")
        val projectId = request.getParameter("project")
        settingsManager.pluginSettings.projectSettings[projectId]?.patterns?.remove(id)
        settingsManager.saveConfiguration()
        return null
    }
}