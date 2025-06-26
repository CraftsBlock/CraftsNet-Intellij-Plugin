package de.craftsblock.craftsnet.intellijplugin.templates

import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.ide.fileTemplates.FileTemplateUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import org.jetbrains.annotations.NotNull


abstract class CustomTemplateAction(
    private val kind: TemplateKind
) : AnAction(kind.kind, "Erstelle eine ${kind.templateName}", kind.icon), CustomAction {

    private var lastContext: DataContext? = null

    override fun update(e: AnActionEvent) {
        lastContext = e.dataContext

        super<AnAction>.update(e)
    }

    override fun actionPerformed(@NotNull e: AnActionEvent) {
        val project = e.project ?: return
        val dir = getTargetDirectory(e) ?: return
        val template = FileTemplateManager.getInstance(project).getInternalTemplate(kind.templateName)

        val psiFile: PsiFile = createFileFromTemplate(project, template, dir) ?: return
        startTemplate(project, psiFile, kind, true)
    }

    override fun checkAvailability(dataContext: DataContext): Boolean = kind.isAvailable(dataContext)

    private fun createFileFromTemplate(
        project: Project, template: FileTemplate, dir: PsiDirectory
    ): PsiFile? {
        try {
            return FileTemplateUtil.createFromTemplate(template, kind.kind, null, dir).containingFile
        } catch (ex: Exception) {
            ex.printStackTrace()
            Messages.showErrorDialog(project, "Error creating file: " + ex.message, "Error")
        }
        return null
    }

    private fun getTargetDirectory(e: AnActionEvent): PsiDirectory? {
        val dataContext = e.dataContext
        val vFile = CommonDataKeys.VIRTUAL_FILE.getData(dataContext)
        val project = e.project
        if (vFile == null || project == null) return null

        val dir = PsiManager.getInstance(project).findDirectory(vFile)
        return dir
    }

}

