package com.ewarwick.teamcity.testPlugin


import com.ewarwick.teamcity.testPlugin.settings.PluginSettings
import com.ewarwick.teamcity.testPlugin.settings.SettingsManager
import jetbrains.buildServer.controllers.admin.AdminPage
import jetbrains.buildServer.log.Loggers
import jetbrains.buildServer.serverSide.auth.Permission
import jetbrains.buildServer.web.openapi.Groupable
import jetbrains.buildServer.web.openapi.PagePlaces
import jetbrains.buildServer.web.openapi.PluginDescriptor
import jetbrains.buildServer.web.openapi.PositionConstraint

import javax.servlet.http.HttpServletRequest
import java.util.ArrayList

class SandboxAdminPage protected constructor(pagePlaces: PagePlaces,
                                             descriptor: PluginDescriptor,
                                             private val sandboxAdminSettings: SettingsManager) : AdminPage(pagePlaces) {

    private val jspHome: String

    init {

        pluginName = "some text"
        includeUrl = descriptor.getPluginResourcesPath(PAGE)
        jspHome = descriptor.pluginResourcesPath
        tabTitle = TAB_TITLE
        val after = ArrayList<String>()
        val before = ArrayList<String>()
        setPosition(PositionConstraint.between(after, before))
        sandboxAdminSettings.init()
        register()
    }

    override fun fillModel(model: MutableMap<String, Any>, request: HttpServletRequest) {
        super.fillModel(model, request)
        sandboxAdminSettings.init()
        model["sandboxSettings"] = sandboxAdminSettings.pluginSettings

        model["jspHome"] = this.jspHome
    }

    override fun getGroup(): String {
        return Groupable.PROJECT_RELATED_GROUP
    }

    override fun isAvailable(request: HttpServletRequest): Boolean {
        return super.isAvailable(request) && checkHasGlobalPermission(request, Permission.EDIT_PROJECT)
    }

    companion object {
        private val PAGE = "sandboxAdminSettings.jsp"
        private val TAB_TITLE = "woo stuff"
    }
}