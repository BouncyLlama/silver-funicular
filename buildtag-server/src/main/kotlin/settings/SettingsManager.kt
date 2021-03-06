package com.ewarwick.teamcity.buildtag.settings


import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jetbrains.buildServer.serverSide.ServerPaths

import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.lang.Exception


class SettingsManager() {
    lateinit var serverPaths: ServerPaths

    constructor(serverPaths: ServerPaths) : this() {
        this.serverPaths = serverPaths
        init()
    }

    val CS_PROPERTIES_FILE = "taggingplugin.json"


    var pluginSettings: PluginSettings = PluginSettings()
    fun init() {
        loadConfiguration()
    }

    @Throws(IOException::class)
    fun saveConfiguration() {
        val keyFile = getFile() ?: throw RuntimeException("Configuration file not found")
        var outFile: FileWriter? = null
        try {
            var mapper = jacksonObjectMapper()

            outFile = FileWriter(keyFile)
            mapper.writeValue(outFile, pluginSettings)
        } catch (e: Exception) {

        } finally {
            outFile?.close()

        }

    }

    fun loadConfiguration() {
        val keyFile = getFile() ?: return

        var inFile: FileReader? = null
        try {
            inFile = FileReader(keyFile)
            var mapper = jacksonObjectMapper()
            val result = mapper.readValue<PluginSettings>(inFile!!.readText())
            pluginSettings = result
            inFile.close()
        } catch (e: IOException) {
        } finally {
            try {
                inFile?.close()
            } catch (e: IOException) {
            }

        }
    }

    private fun getFile(): File? {
        val keyFile = File(serverPaths.configDir + "/" + CS_PROPERTIES_FILE)
        if (!keyFile.exists()) {
            try {

                val created = keyFile.createNewFile()
                if (!created) {
                }
            } catch (e: IOException) {
                return null
            }

        }
        return keyFile
    }


}