package com.ewarwick.teamcity.testPlugin;

import jetbrains.buildServer.Build;
import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.util.EventDispatcher;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LogReceiver extends BuildServerAdapter {
    public LogReceiver(@NotNull final EventDispatcher<BuildServerListener> dispatch) {
        dispatch.addListener(this);
    }

    public LogReceiver() {

    }

    @Override
    public void beforeBuildFinish(@NotNull SRunningBuild runningBuild) {
        List<String> oldTags = runningBuild.getTags();
        List<String> newTags = new ArrayList<>(oldTags);
        newTags.add("testtag");
        runningBuild.setTags(newTags);
    }
}
