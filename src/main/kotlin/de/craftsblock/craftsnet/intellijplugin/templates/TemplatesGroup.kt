package de.craftsblock.craftsnet.intellijplugin.templates

import ai.grazie.utils.toDistinctTypedArray
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import de.craftsblock.craftsnet.intellijplugin.templates.impl.*
import de.craftsblock.craftsnet.intellijplugin.uitls.CraftsNetVersionUtils

private val DIALOG_TEMPLATES: Set<CustomAction> = setOf(
    AddonTemplates(),
    AutoRegisterTemplates(),
    CliTemplates(),
    EndpointTemplates(),
    EventTemplates(),
    HttpTemplates(),
    LoggingTemplates(),
    MiddlewareTemplates(),
    RequirementTemplates(),
    WebsocketTemplates(),
)

private val SINGLETON_TEMPLATES: Set<SingletonTemplateAction> = setOf(
    SingletonTemplateAction(CraftsNetTemplates.ADDON_JSON, AllIcons.FileTypes.Json, visibility = TemplateVisibility.JAVA_RESOURCES),
)

private val TEMPLATES: Set<CustomAction> = DIALOG_TEMPLATES + SINGLETON_TEMPLATES

class TemplatesGroup : DefaultActionGroup(
    "CraftsNet",
    true
) {

    init {
        templatePresentation.isHideGroupIfEmpty = true
        isSearchable = false
    }

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT
    override fun update(e: AnActionEvent) {
        val project: Project = e.project ?: return

        val craftsNetAvailable = CraftsNetVersionUtils.isAvailable(project, e)
        e.presentation.isHideGroupIfEmpty = craftsNetAvailable
        e.presentation.isPerformGroup = !craftsNetAvailable

        super.update(e)
    }

    override fun actionPerformed(e: AnActionEvent) {
        super.actionPerformed(e)
        Messages.showWarningDialog(e.presentation.description, "CraftsNet Not Available!")
    }

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        if (e?.presentation?.isPerformGroup == true) return arrayOf()

        val context = e?.dataContext.let { e?.dataContext ?: DataContext.EMPTY_CONTEXT }
        val actions: MutableList<AnAction> = mutableListOf()

        for (action: CustomAction in TEMPLATES) {
            if (!action.checkAvailability(context)) continue
            if (action !is AnAction) continue
            actions.add(action as AnAction)
        }

        return actions.toDistinctTypedArray()
    }

}