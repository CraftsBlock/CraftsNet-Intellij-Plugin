package de.craftsblock.craftsnet.intellijplugin.templates

import com.intellij.ide.actions.CreateTemplateInPackageAction
import com.intellij.ide.actions.JavaCreateTemplateInPackageAction
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.psi.PsiDirectory
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes
import org.jetbrains.jps.model.module.JpsModuleSourceRootType

enum class TemplateVisibility(private val rootType: Set<JpsModuleSourceRootType<*>>?) {

    ALWAYS(null),
    JAVA_SOURCES(JavaModuleSourceRootTypes.SOURCES),
    JAVA_RESOURCES(JavaModuleSourceRootTypes.RESOURCES),
    JAVA_PRODUCTION(JavaModuleSourceRootTypes.PRODUCTION),
    JAVA_TESTS(JavaModuleSourceRootTypes.TESTS);

    fun isAvailable(context: DataContext): Boolean {
        if (this == ALWAYS) return true
        return CreateTemplateInPackageAction.isAvailable(context, rootType) { dir: PsiDirectory ->
            JavaCreateTemplateInPackageAction.doCheckPackageExists(dir)
        }
    }

}