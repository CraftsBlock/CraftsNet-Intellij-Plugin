package de.craftsblock.craftsnet.intellijplugin.inspection.rules.craftsnet

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassObjectAccessExpression
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiType
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomAnnotatedInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.fixes.RemoveElementQuickFix
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.craftsnet.ParameterInspectionRule
import de.craftsblock.craftsnet.intellijplugin.uitls.Utils

class TransformerInspectionRule(
    private val argOffset: Int = 1
) : CustomInspectionRule() {

    override fun checkMethod(holder: ProblemsHolder, method: PsiMethod) {
        val rootClass: PsiClass = method.parent as PsiClass
        val annotations: MutableList<PsiAnnotation> = Utils.collectAnnotation(
            "de.craftsblock.craftsnet.api.transformers.annotations.Transformer",
            "de.craftsblock.craftsnet.api.transformers.annotations.TransformerCollection",
            method, rootClass
        )

        if (annotations.isEmpty()) return

        val parent = super.parent as? CustomAnnotatedInspection ?: return

        val pathPattern = Utils.getMergedStringValueOfAnnotation(parent.annotation, "value", rootClass, method)
        val dynamicGroups: List<String> = Utils.getDynamicUrlParams(pathPattern)
        val dynamicGroupsUsed: MutableList<String> = mutableListOf()

        for (annotation in annotations) {
            val paramAttr = annotation.findAttributeValue("parameter") as? PsiLiteralExpression ?: continue
            val paramName: String = paramAttr.value.toString()

            if (!dynamicGroups.contains(paramName)) {
                registerProblem(
                    holder, annotation, "Dynamic url param $paramName has a transformer but isn't used in the route!",
                    RemoveElementQuickFix(), highlightType = ProblemHighlightType.WARNING
                )
                continue
            }

            val transformerAttr = annotation.findAttributeValue("transformer") as? PsiClassObjectAccessExpression ?: continue
            val transformer = (transformerAttr.operand.type as? PsiClassType)?.resolve() ?: continue
            val transformMethod: PsiMethod = transformer.methods.firstOrNull { it.name == "transform" } ?: continue
            val returnType: PsiType = transformMethod.returnType ?: continue

            val expectedTypes: MutableSet<String> = mutableSetOf()
            expectedTypes.add(Utils.typeToQualifiedName(returnType) ?: continue)
            if (Utils.isPrimitive(returnType))
                expectedTypes.add(Utils.typeToQualifiedName(Utils.getPrimitive(returnType)!!)!!)

            val index = dynamicGroups.indexOf(paramName)
            val rule = ParameterInspectionRule(
                offsetIndex(index), paramName, *expectedTypes.reversed().toTypedArray(),
                errorDescription = "Due to a transformer, argument ${offsetIndex(index, 1)} must be one of: "
                        + expectedTypes.reversed().joinToString(", ")
            )
            rule.adopt(parent)
            rule.checkMethod(holder, method)
            dynamicGroupsUsed.add(paramName)
        }
    }

    private fun offsetIndex(index: Int, extraOffset: Int = 0): Int = index + argOffset + extraOffset

}