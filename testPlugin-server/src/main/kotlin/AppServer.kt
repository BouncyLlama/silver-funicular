package com.ewarwick.teamcity.testPlugin

import com.ewarwick.teamcity.testPlugin.settings.ProjectSettings
import com.ewarwick.teamcity.testPlugin.settings.SettingsManager
import jetbrains.buildServer.controllers.BaseController
import jetbrains.buildServer.web.openapi.PluginDescriptor
import jetbrains.buildServer.web.openapi.WebControllerManager
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AppServer() : BaseController() {
    private lateinit var myDescriptor: PluginDescriptor
    private lateinit var settingsManager: SettingsManager

    constructor(manager: WebControllerManager, descriptor: PluginDescriptor, myDescriptor: PluginDescriptor, settingsManager: SettingsManager) : this() {
        manager.registerController("/demoPlugin.html", this)
        this.myDescriptor = descriptor
        this.myDescriptor = myDescriptor
        this.settingsManager = settingsManager
    }

    constructor(myDescriptor: PluginDescriptor) : this() {

    }

    @Throws(Exception::class)
    override fun doHandle(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse): ModelAndView? {
        settingsManager.saveConfiguration()
        return ModelAndView(myDescriptor.getPluginResourcesPath("example.jsp"))
    }
}