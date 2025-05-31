package de.craftsblock.craftsnet.intellijplugin.templates

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import javax.swing.Icon


abstract class CustomDialogTemplateAction(
    text: String,
    private val title: String,
    icon: Icon,
    private vararg val kinds: TemplateKind
) : CreateFileFromTemplateAction(text, "Erstelle eine $text Datei", icon), CustomAction {

    private var lastContext: DataContext? = null

    override fun update(e: AnActionEvent) {
        lastContext = e.dataContext
        super.update(e)
    }

    override fun buildDialog(
        project: Project,
        directory: PsiDirectory,
        builder: CreateFileFromTemplateDialog.Builder
    ) {
        builder.setTitle(title)

        for (kind in kinds) {
            if (!kind.isAvailable(lastContext.let { lastContext ?: DataContext.EMPTY_CONTEXT })) continue
            builder.addKind(kind.kind, kind.icon, kind.templateName)
        }
    }

    override fun createFileFromTemplate(name: String, template: FileTemplate, dir: PsiDirectory): PsiFile? {
        template.isLiveTemplateEnabled = false
        template.isReformatCode = false

        val psiFile: PsiFile = createFileFromTemplate(name, template, dir, null, false) ?: return null
        val kind = kinds.first { kind -> kind.templateName == "${template.name}.${template.extension}" }

        val project: Project = dir.project
        startTemplate(
            project, psiFile, kind, liveDefaultValues = mapOf(
                Pair("SPI", "Spi"),
                Pair("CLASSTYPE", "Classtype"),
                Pair("MESSAGE_TYPE", "byte[]")
            )
        )

        return psiFile
    }

    override fun checkAvailability(dataContext: DataContext): Boolean = isAvailable(dataContext)
    override fun isAvailable(dataContext: DataContext): Boolean = kinds.any { kind -> kind.isAvailable(dataContext) }

    override fun getActionName(directory: PsiDirectory?, newName: String, templateName: String?): String {
        val kind = kinds.first { kind -> kind.templateName == templateName }
        return "Create ${templateName?.replace(".${kind.extension}", "")} '$newName'"
    }

}

