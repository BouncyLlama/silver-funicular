package com.ewarwick.teamcity.testPlugin

import com.ewarwick.teamcity.testPlugin.settings.SandboxAdminSettings
import com.ewarwick.teamcity.testPlugin.settings.potato
import jetbrains.buildServer.controllers.BaseController
import jetbrains.buildServer.web.openapi.PluginDescriptor
import jetbrains.buildServer.web.openapi.WebControllerManager
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AppServer() : BaseController() {
    private lateinit var myDescriptor: PluginDescriptor
    private lateinit var settings: SandboxAdminSettings

    constructor(manager: WebControllerManager, descriptor: PluginDescriptor, myDescriptor: PluginDescriptor, settings: SandboxAdminSettings) : this() {
        manager.registerController("/demoPlugin.html", this)
        this.myDescriptor = descriptor
        this.myDescriptor = myDescriptor
        this.settings = settings
    }

    constructor(myDescriptor: PluginDescriptor) : this() {

    }

    @Throws(Exception::class)
    override fun doHandle(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse): ModelAndView? {
       settings.init()
        settings.serverAddress = httpServletRequest.getParameter("hooray")
        settings.foo.potatos.add(potato("stuffs"))
        settings.foo.moar["something"] = potato("omgwut")
        settings.saveProperties()
        return ModelAndView(myDescriptor.getPluginResourcesPath("example.jsp"))
    }
}