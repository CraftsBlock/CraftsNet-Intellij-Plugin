package de.craftsblock.craftsnet.intellijplugin.uitls

import com.intellij.psi.*
import com.intellij.util.containers.forEachGuaranteed
import kotlinx.coroutines.Runnable

object Utils {

    private val dynamicUrlParamExtractor: Regex = Regex("\\{([^{}]+)}")

    internal fun getDynamicUrlParams(regex: String): MutableList<String> {
        val groupNames: LinkedHashSet<String> = LinkedHashSet()

        val matcher: Sequence<MatchResult> = dynamicUrlParamExtractor.findAll(regex)
        for (match in matcher)
            groupNames.add(match.groups[1]?.value ?: continue)

        return mutableListOf(*groupNames.toTypedArray())
    }

    internal fun collectAnnotation(single: String, repeatable: String? = null, vararg elements: PsiModifierListOwner): MutableList<PsiAnnotation> {
        val annotations: MutableList<PsiAnnotation> = mutableListOf()

        elements.forEach { element ->
            annotations += element.annotations.filter { it.qualifiedName == single }
            if (repeatable == null) return@forEach

            val container = element.getAnnotation(repeatable)
            val fromContainer = (container?.findDeclaredAttributeValue("value") as? PsiArrayInitializerMemberValue)
                ?.initializers
                ?.filterIsInstance<PsiAnnotation>()
                ?.filter { it.qualifiedName == single }
                ?: emptyList()

            annotations += fromContainer
        }

        return annotations
    }

    internal fun getMergedStringValueOfAnnotation(
        annotation: String, attribute: String,
        vararg elements: PsiModifierListOwner, delimiter: String = "/"
    ): String {
        return elements.mapNotNull { element ->
            (element.getAnnotation(annotation)
                ?.findAttributeValue(attribute) as? PsiLiteralExpression)
                ?.value as? String
        }.filter { it.isNotBlank() }
            .joinToString(separator = delimiter) {
                it.trimStart(*delimiter.toCharArray())
            }
    }

    internal fun typeToQualifiedName(type: PsiType): String? {
        return when (type) {
            is PsiPrimitiveType -> type.canonicalText
            is PsiClassType -> type.resolve()?.qualifiedName
            else -> type.canonicalText
        }
    }

    internal fun isPrimitive(type: PsiType): Boolean = getPrimitive(type) != null
    internal fun getPrimitive(type: PsiType): PsiType? = PsiPrimitiveType.getUnboxedType(type)

    internal fun stripClass(type: String): String = type.split(".").last()

}