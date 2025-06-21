package de.craftsblock.craftsnet.intellijplugin.inspection.rules.direct

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiElementFactory
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiType
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.fixes.method.MethodReturnTypeQuickFix
import de.craftsblock.craftsnet.intellijplugin.uitls.Utils

class MethodReturnTypeInspectionRule(
    private vararg val expectedTypes: String?,
    private var errorDescription: String? = null
) : CustomInspectionRule() {

    private var expectedTypesRef: List<PsiType>? = null
    private var disabled: Boolean = false

    private var changeReturnTypeFix: MethodReturnTypeQuickFix? = null

    override fun adopt(parent: CustomInspection) {
        changeReturnTypeFix = MethodReturnTypeQuickFix(expectedTypes.filterNotNull().first())
        super.adopt(parent)

        disabled = expectedTypes.any { it == "*" }

        if (errorDescription != null) return
        errorDescription = "The return type must be one of: ${expectedTypes.filterNotNull().joinToString(", ") { Utils.stripClass(it) }}"
    }

    override fun checkMethod(holder: ProblemsHolder, method: PsiMethod) {
        if (disabled) return

        val project: Project = method.project
        if (expectedTypesRef == null) {
            val factory: PsiElementFactory = JavaPsiFacade.getElementFactory(project)
            expectedTypesRef = expectedTypes.filterNotNull().map { factory.createTypeFromText(it, null) }
        }

        val typeElement = method.returnTypeElement ?: return
        val type = method.returnType

        if (type == null) {
            registerProblem(holder, typeElement, "Could not determine the return type.")
            return
        }

        for (typeRef in expectedTypesRef!!)
            if (typeRef.isAssignableFrom(type)) return

        registerProblem(holder, typeElement, errorDescription!!, changeReturnTypeFix)
    }

}