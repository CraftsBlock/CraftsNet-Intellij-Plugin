package de.craftsblock.craftsnet.intellijplugin.uitls

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.OrderEnumerator

object ModuleUtils {

    internal fun findDependenciesWithVersions(project: Project, vararg needed: String): Any {
        val dependencies = mutableListOf<String>()

        val modules = ModuleManager.getInstance(project).modules
        for (module in modules)
            OrderEnumerator.orderEntries(module)
                .forEachLibrary { library ->
                    dependencies.addAll(library.getUrls(com.intellij.openapi.roots.OrderRootType.CLASSES).toList())
                    true
                }

        val result = mutableMapOf<String, String>()

        for (required in needed) {
            val regex = Regex("${Regex.escape(required)}${if (required.endsWith("/")) "" else "/"}(?<version>[^/]+)")

            val match = dependencies.firstOrNull { regex.containsMatchIn(it) }
            if (match == null) return false

            val version = regex.find(match)?.groups?.get("version")?.value ?: "unknown"
            result[required] = version
        }

        return result
    }

}