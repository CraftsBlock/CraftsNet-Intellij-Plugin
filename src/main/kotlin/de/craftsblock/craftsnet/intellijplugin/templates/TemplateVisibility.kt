package de.craftsblock.craftsnet.intellijplugin.templates

import com.intellij.ide.actions.CreateTemplateInPackageAction
import com.intellij.ide.actions.JavaCreateTemplateInPackageAction
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.PsiDirectory
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes
import org.jetbrains.jps.model.module.JpsModuleSourceRootType
import java.util.concurrent.CompletableFuture

enum class TemplateVisibility(private val rootType: Set<JpsModuleSourceRootType<*>>?) {

    ALWAYS(null),
    JAVA_SOURCES(JavaModuleSourceRootTypes.SOURCES),
    JAVA_RESOURCES(JavaModuleSourceRootTypes.RESOURCES),
    JAVA_PRODUCTION(JavaModuleSourceRootTypes.PRODUCTION),
    JAVA_TESTS(JavaModuleSourceRootTypes.TESTS);

    fun isAvailable(context: DataContext): Boolean {
        if (this == ALWAYS || rootType == null) return true

        val app = ApplicationManager.getApplication()
        val result = CompletableFuture<Boolean>()

        app.executeOnPooledThread {
            app.runReadAction {
                val available = CreateTemplateInPackageAction.isAvailable(context, rootType) { dir: PsiDirectory ->
                    JavaCreateTemplateInPackageAction.doCheckPackageExists(dir)
                }
                result.complete(available)
            }
        }

        return result.get()
    }

}