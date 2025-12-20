package de.craftsblock.craftsnet.intellijplugin.templates

import com.intellij.ide.fileTemplates.actions.CreateFromTemplateActionBase
import com.intellij.ide.fileTemplates.actions.CreateFromTemplateManager
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleManager
import de.craftsblock.craftsnet.intellijplugin.uitls.Updatable

interface CustomAction : Updatable {

    fun checkAvailability(dataContext: DataContext): Boolean

    fun startTemplate(
        project: Project,
        psiFile: PsiFile,
        kind: TemplateKind,
        customOpen: Boolean = false,
        liveDefaultValues: Map<String, String> = mapOf()
    ) {
        val manager: PsiDocumentManager = PsiDocumentManager.getInstance(project)
        val document: Document = manager.getDocument(psiFile) ?: return

        manager.doPostponedOperationsAndUnblockDocument(document)
        if (kind.liveTemplate)
            CreateFromTemplateManager.startLiveTemplate(
                psiFile, mapOf(
                    Pair("FALSE", "false"),
                    Pair("TRUE", "false"),
                    Pair("NULL", "null")
                ) + liveDefaultValues
            )
        else if (customOpen)
            FileEditorManager.getInstance(project).openFile(psiFile.virtualFile, true)

        manager.doPostponedOperationsAndUnblockDocument(document)
        WriteCommandAction.runWriteCommandAction(project) {
            CodeStyleManager.getInstance(project).reformat(psiFile)
        }
    }

}