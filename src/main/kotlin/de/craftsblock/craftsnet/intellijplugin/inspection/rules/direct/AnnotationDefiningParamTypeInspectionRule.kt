package de.craftsblock.craftsnet.intellijplugin.inspection.rules.direct

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassObjectAccessExpression
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiType
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomAnnotatedInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspectionRule
import de.craftsblock.craftsnet.intellijplugin.uitls.Utils

class AnnotationDefiningParamTypeInspectionRule(
    private val index: Int,
    private val annotation: String,
    private val annotationRepeatable: String? = null,
    private val fallbackRuleIfAbsent: ParameterInspectionRule? = null
) : CustomInspectionRule() {

    override fun checkMethod(holder: ProblemsHolder, method: PsiMethod) {
        val parent = super.parent as? CustomAnnotatedInspection ?: return
        defineUnderlying(parent, method)?.checkMethod(holder, method)
    }

    private fun defineUnderlying(parent: CustomInspection, method: PsiMethod): ParameterInspectionRule? {
        val rootClass: PsiClass = method.parent as PsiClass
        val annotations: MutableList<PsiAnnotation> = Utils.collectAnnotation(annotation, annotationRepeatable, method, rootClass)
        if (annotations.isEmpty()) return fallbackRuleIfAbsent

        val annotation: PsiAnnotation = annotations.first()

        val decoderAttr = annotation.findAttributeValue("value") as? PsiClassObjectAccessExpression ?: return fallbackRuleIfAbsent
        val decoder = (decoderAttr.operand.type as? PsiClassType)?.resolve() ?: return fallbackRuleIfAbsent
        val decodeMethod: PsiMethod = decoder.methods.firstOrNull { it.name == "decode" } ?: return fallbackRuleIfAbsent
        val returnType: PsiType = decodeMethod.returnType ?: return fallbackRuleIfAbsent

        val expectedTypes: MutableSet<String> = mutableSetOf()
        expectedTypes.add(Utils.typeToQualifiedName(returnType) ?: return fallbackRuleIfAbsent)
        if (Utils.isPrimitive(returnType))
            expectedTypes.add(Utils.typeToQualifiedName(Utils.getPrimitive(returnType)!!)!!)


        val rule = ParameterInspectionRule(
            index, returnType.presentableText.replaceFirstChar { it.lowercase() },
            *expectedTypes.reversed().toTypedArray(),
            errorDescription = "Due to the annotation ${annotation.nameReferenceElement!!.referenceName}, argument $index must be one of: "
                    + expectedTypes.reversed().joinToString(", ")
        )
        rule.adopt(parent)

        return rule
    }

}