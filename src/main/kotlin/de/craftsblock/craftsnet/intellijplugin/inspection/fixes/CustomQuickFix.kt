package de.craftsblock.craftsnet.intellijplugin.inspection.fixes

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.psi.*

abstract class CustomQuickFix : LocalQuickFix {

    fun import(factory: PsiElementFactory, psiClass: PsiClass, target: Any) {
        val resolved = when (target) {
            is PsiReference -> target.resolve() as? PsiClass
            is PsiClassType -> target.resolve()
            else -> null
        } ?: return

        if (psiClass.name.equals(resolved.name)) return

        val file = psiClass.containingFile
        val javaFile = file as? PsiJavaFile ?: return
        val importList = javaFile.importList ?: return

        val qualifiedName = resolved.qualifiedName ?: return
        val exists = importList.importStatements.any {
            it.importReference?.qualifiedName == qualifiedName
        }
        if (exists) return

        val import = factory.createImportStatement(resolved)
        javaFile.importList?.add(import)
    }

}