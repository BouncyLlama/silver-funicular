package com.ewarwick.teamcity.testPlugin

import com.ewarwick.teamcity.testPlugin.settings.SettingsManager
import jetbrains.buildServer.serverSide.BuildServerAdapter
import jetbrains.buildServer.serverSide.BuildServerListener
import jetbrains.buildServer.serverSide.SRunningBuild
import jetbrains.buildServer.util.EventDispatcher
import java.util.ArrayList


class LogReceiver : BuildServerAdapter {
    private lateinit var settingsManager: SettingsManager

    constructor(dispatch: EventDispatcher<BuildServerListener>, settingsManager: SettingsManager) {
        dispatch.addListener(this)
        this.settingsManager = settingsManager
    }

    constructor() {

    }

    override fun beforeBuildFinish(runningBuild: SRunningBuild) {
        val projSettings = settingsManager.pluginSettings.projectSettings[runningBuild.projectId]
        val oldTags = runningBuild.tags
        val newTags = ArrayList(oldTags)
        projSettings?.patterns?.entries?.forEach { foo ->
            runningBuild.buildLog.messagesIterator.forEach {
                if (Regex(foo.key).containsMatchIn(it.text)) {
                    newTags.add(foo.value)
                }
            }
        }


        runningBuild.tags = newTags

    }
}
