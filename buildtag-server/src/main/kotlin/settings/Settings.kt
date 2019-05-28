package com.ewarwick.teamcity.buildtag.settings

data class ProjectSettings(val patterns: HashMap<String, String> = hashMapOf())

class PluginSettings {
    val projectSettings: HashMap<String, ProjectSettings> = hashMapOf()
}