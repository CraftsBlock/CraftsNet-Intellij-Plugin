package de.craftsblock.craftsnet.intellijplugin.templates

import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NlsContexts
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.CraftsNetVersionUtils
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.FeatureFlag
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import javax.swing.Icon

data class TemplateKind(
    @NlsContexts.ListItem @NotNull val kind: String,
    @Nullable val icon: Icon,
    @NonNls @NotNull val templateName: String,
    @NotNull val extension: String = templateName.split(".").last(),
    @NotNull val visibility: TemplateVisibility = TemplateVisibility.JAVA_SOURCES,
    @NotNull val liveTemplate: Boolean = true,
    @NotNull val dynamicName: Boolean = (visibility == TemplateVisibility.JAVA_SOURCES),
    @NotNull val featureFlag: FeatureFlag = FeatureFlag.BASE
) {

    internal fun isAvailable(context: DataContext): Boolean {
        val project: Project = CommonDataKeys.PROJECT.getData(context) ?: return false
        if (!CraftsNetVersionUtils.isFeatureFlagAvailable(project, featureFlag)) return false

        return visibility.isAvailable(context)
    }

}