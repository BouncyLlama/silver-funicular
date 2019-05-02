package com.ewarwick.teamcity.testPlugin

import jetbrains.buildServer.serverSide.BuildServerAdapter
import jetbrains.buildServer.serverSide.BuildServerListener
import jetbrains.buildServer.serverSide.SRunningBuild
import jetbrains.buildServer.util.EventDispatcher
import java.util.ArrayList


class LogReceiver : BuildServerAdapter {
    constructor(dispatch: EventDispatcher<BuildServerListener>) {
        dispatch.addListener(this)
    }

    constructor() {

    }

    override fun beforeBuildFinish(runningBuild: SRunningBuild) {
        val oldTags = runningBuild.tags
        val newTags = ArrayList(oldTags)
        newTags.add("testtag")
        runningBuild.tags = newTags
    }
}
