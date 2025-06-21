package de.craftsblock.craftsnet.intellijplugin.inspection.rules.direct

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.fixes.CustomQuickFix
import de.craftsblock.craftsnet.intellijplugin.inspection.fixes.`object`.AddImplementationQuickFix
import de.craftsblock.craftsnet.intellijplugin.uitls.Utils

class RequireImplementationInspectionRule(
    private vararg val requiredInterfaces: String
) : CustomInspectionRule() {

    private var quickFixes: MutableMap<String, CustomQuickFix> = mutableMapOf()

    override fun adopt(parent: CustomInspection) {
        for (required in requiredInterfaces)
            quickFixes[required] = AddImplementationQuickFix(required)

        super.adopt(parent)
    }

    override fun checkMethod(holder: ProblemsHolder, method: PsiMethod) {
        val parent: PsiClass = method.parent as? PsiClass ?: return
        val interfaces = parent.implementsListTypes.mapNotNull(Utils::typeToQualifiedName)

        for (required in requiredInterfaces) {
            if (interfaces.contains(required)) continue

            registerProblem(
                holder, parent, "Classes with @${getAnnotationName()} methods must implement $required.",
                quickFixes[required]
            )
        }
    }

}